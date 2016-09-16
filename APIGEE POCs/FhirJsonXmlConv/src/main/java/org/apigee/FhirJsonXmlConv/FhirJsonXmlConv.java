package org.apigee.FhirJsonXmlConv;

import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.io.StringReader;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.api.IResource;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.model.api.Bundle;

//import org.hl7.fhir.instance.model.IBaseResource;

import com.apigee.flow.execution.ExecutionContext;
import com.apigee.flow.execution.ExecutionResult;
import com.apigee.flow.execution.spi.Execution;
import com.apigee.flow.message.MessageContext;

/**
 * 		@version 0.0.7 
 * 		FhirJsonXmlConv class performs the JSON to XML conversion of
 *      FHIR resources. For conversion it takes input resource, its type and
 *      required output content type. It determine the standard FHIR type of
 *      input resource and decide standard FHIR type for output. It create
 *      appropriate FhirContext and use required parser to perform
 *      conversion.
 * 
 */
public class FhirJsonXmlConv implements Execution {
	boolean flag = false;
	private static final int BUFFER_SIZE = 256;
	static FHIRMediaType mtInput;
	static FHIRMediaType mtOutput;

	/**
	 * Enum defining standard FHIR media types for DSTU1 and DSTU2, its parser and
	 * necessary methods of this enum.
	 */
	enum FHIRMediaType {
		RESOURCE_XML_MEDIA_TYPE("application/xml+fhir", ParserType.XML), 
		BUNDLE_XML_MEDIA_TYPE("application/atom+xml",ParserType.XML),
		TAGLIST_XML_MEDIA_TYPE("application/xml+fhir",ParserType.XML), 
		RESOURCE_JSON_MEDIA_TYPE("application/json+fhir",ParserType.JSON), 
		BUNDLE_JSON_MEDIA_TYPE("application/json+fhir",ParserType.JSON), 
		TAGLIST_JSON_MEDIA_TYPE("application/json+fhir",ParserType.JSON);

		private String mediaType;
		private ParserType parserType;

		/**
		 * Construct the FHIRMediaType enum.
		 * 
		 * @param mediaType
		 *            Standard FHIR media type.
		 * @param parserType
		 *            json or xml.
		 */
		FHIRMediaType(final String mediaType, final ParserType parserType) {
			this.mediaType = mediaType;
			this.parserType = parserType;
		}

		/**
		 * This method returns standard FHIR media type from input string media type.
		 * 
		 * @param mediaType
		 *            type of input
		 * @return Standard FHIR media type.
		 */
		static FHIRMediaType getType(final String mediaType) {
			FHIRMediaType retValue = null;
			// if the input type contains semicolons and bits for things like
			// "charset", filter those out.
			final String[] mediaTypeSplits = mediaType.split(";");
			for (FHIRMediaType type : values()) {
				if (type.getMediaType().equals(mediaTypeSplits[0])) {
					retValue = type;
					break;
				}
			}
			return retValue;
		}

		/**
		 * returns media type of FHIRMediaType enum.
		 */
		public String getMediaType() {
			return mediaType;
		}

		/**
		 * returns parser type of FHIRMediaType enum.
		 */
		public ParserType getParserType() {
			return parserType;
		}
	}

	/**
	 * Enum for parser types.
	 */
	enum ParserType {
		XML, JSON;
	}

	/**
	 * This method returns the parser for specified FHIR context and FHIR media
	 * type
	 * 
	 * @param ctx
	 *            FHIR context for DST1 or DSTU2
	 * @param mt
	 *            FHIR media type of input
	 * @return parser of type IParser depend on resource type
	 */
	protected static IParser getParser(final FhirContext ctx, FHIRMediaType mt) {
		IParser parser = null;

		final ParserType parserType = mt.getParserType();
		switch (parserType) {
		case JSON:
			parser = ctx.newJsonParser();
			break;
		case XML:
		default:
			parser = ctx.newXmlParser();
			break;
		}
		return parser;
	}

	/**
	 * Get the parser required and parse the "Resource"
	 * 
	 * @param iReader
	 *            contains data read from input.
	 * @param ctx
	 *            FHIR context for DST1 or DSTU2.
	 * @param mt
	 *            FHIR media type of input.
	 * @return parsed "Resource" of type or sub-type of IResource.
	 */
	protected <T extends IResource> T importResource(final Reader iReader, final FhirContext ctx,
			final FHIRMediaType mt) {
		T resource = null;
		try {
			final IParser parser = getParser(ctx, mt);
			resource = (T) parser.parseResource(iReader);
		} catch (Exception e) {
			System.out.println("Error from importResource() " + e.getMessage());
		} finally {
			IOUtils.closeQuietly(iReader);
		}
		return resource;
	}

	/**
	 * This method encode the parsed data of type FHIR "Resource" to string as
	 * final result.
	 * 
	 * @param resource
	 *            parsed resource.
	 * @param ctx
	 *            FHIR context for DST1 or DSTU2.
	 * @param mt
	 *            required FHIR media type of output.
	 * @return final result i string format.
	 */
	protected String exportResourceToString(final IResource resource, final FhirContext ctx, final FHIRMediaType mt) {
		String result = null;
		try {
			final IParser parser = getParser(ctx, mt);
			result = parser.encodeResourceToString(resource);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {

		}

		return result;
	}

	/**
	 * Get the parser required and parse the "Bundle"
	 * 
	 * @param iReader
	 *            contains data read from input.
	 * @param ctx
	 *            FHIR context for DST1 or DSTU2.
	 * @param mt
	 *            FHIR media type of input.
	 * @return parsed "Bundle" of type or sub-type of IResource.
	 */
	protected Bundle importBundle(final Reader iReader, final FhirContext ctx, final FHIRMediaType mt) {
		Bundle resource = null;
		try {
			final IParser parser = getParser(ctx, mt);

			resource = parser.parseBundle(iReader);
		} catch (Exception e) {
			System.out.println("error from importbundle : " + e.getMessage());
		} finally {
			IOUtils.closeQuietly(iReader);
		}
		return resource;
	}

	/**
	 * This method encode the parsed data of type FHIR "Bundle" to string as
	 * final result.
	 * 
	 * @param resource
	 *            parsed resource.
	 * @param ctx
	 *            FHIR context for DST1 or DSTU2.
	 * @param mt
	 *            required FHIR media type of output.
	 * @return final result i string format.
	 */
	protected String exportBundleToString(final Bundle bundle, final FhirContext ctx, final FHIRMediaType mt) {
		String result = null;
		try {
			final IParser parser = getParser(ctx, mt);
			result = parser.encodeBundleToString(bundle);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
		}
		return result;
	}

	/**
	 * Takes input data stored in iReader and decide the FHIR media Type of
	 * Input Data. flag is used to determine Resource is of type DSTU1 or DSTU2
	 * 
	 * @param iReader
	 *            contains data read from input.
	 * @return standard FHIR media Type
	 * @throws IOException
	 * 
	 */
	private FHIRMediaType determineInputMediaType(final PushbackReader iReader) throws IOException {
		FHIRMediaType mt = FHIRMediaType.RESOURCE_XML_MEDIA_TYPE;

		char[] sample = new char[BUFFER_SIZE];
		int len = iReader.read(sample, 0, BUFFER_SIZE);
		iReader.unread(sample, 0, len);
		if (len > 0) {
			final String strSample = new String(sample).toLowerCase().trim();
			if (strSample.contains("<feed") || strSample.contains("<bundle")) {
				mt = FHIRMediaType.BUNDLE_XML_MEDIA_TYPE;
				if (strSample.contains("<feed"))
					flag = true; // Resource is of type DSTU1
			} else if (strSample.startsWith("{") && strSample.contains("\"resourcetype\"")
					&& strSample.contains("\"bundle\"")) {
				mt = FHIRMediaType.BUNDLE_JSON_MEDIA_TYPE;
				if (strSample.contains("\"updated\""))
					flag = true; // Resource is of type DSTU1
			} else if (strSample.startsWith("<"))
				mt = FHIRMediaType.RESOURCE_XML_MEDIA_TYPE;
			else if (strSample.startsWith("{"))
				mt = FHIRMediaType.RESOURCE_JSON_MEDIA_TYPE;
			else
				throw new IllegalArgumentException("Unable to determine input media type.");
		} else {
			throw new IllegalArgumentException("Input sample is zero length");
		}

		return mt;
	}

	/**
	 * Takes inputMediaType and outputMediaType received from apigee flow and
	 * decide FHIR media Type of required output.
	 * 
	 * @param inputMediaType
	 *            FHIR media type of input.
	 * @param outputMediaType
	 *            type required for output.
	 * @return FHIR media type of output.
	 */
	private FHIRMediaType determineOutputMediaType(final FHIRMediaType inputMediaType, final String outputMediaType) {
		FHIRMediaType mt = FHIRMediaType.RESOURCE_XML_MEDIA_TYPE;

		final String lcOutputMediaType = outputMediaType.toLowerCase();
		switch (inputMediaType) {
		case RESOURCE_XML_MEDIA_TYPE:
		case RESOURCE_JSON_MEDIA_TYPE:
			if (lcOutputMediaType.contains("xml"))
				mt = FHIRMediaType.RESOURCE_XML_MEDIA_TYPE;
			else
				mt = FHIRMediaType.RESOURCE_JSON_MEDIA_TYPE;
			break;
		case BUNDLE_XML_MEDIA_TYPE:
		case BUNDLE_JSON_MEDIA_TYPE:
			if (lcOutputMediaType.contains("xml"))
				mt = FHIRMediaType.BUNDLE_XML_MEDIA_TYPE;
			else
				mt = FHIRMediaType.BUNDLE_JSON_MEDIA_TYPE;
			break;
		case TAGLIST_XML_MEDIA_TYPE:
		case TAGLIST_JSON_MEDIA_TYPE:
			if (lcOutputMediaType.contains("xml"))
				mt = FHIRMediaType.TAGLIST_XML_MEDIA_TYPE;
			else
				mt = FHIRMediaType.TAGLIST_JSON_MEDIA_TYPE;
			break;
		}
		return mt;
	}

	/**
	 * This method takes the input from iReader and determines FHIR context for
	 * DSTU1 or DSTU2. Calls the required method for conversion.
	 * 
	 * @param iReader
	 *            contains data read from input.
	 * @param outputMediaType
	 *            type required for output.
	 * @return final converted result in string format.
	 */
	public String convertFhirMedia(final PushbackReader iReader, final String outputMediaType) throws Exception {
		mtInput = determineInputMediaType(iReader);
		mtOutput = determineOutputMediaType(mtInput, outputMediaType);
		FhirContext ctx = new FhirContext();
		if (flag) {
			ctx = FhirContext.forDstu1();
		} else {
			ctx = FhirContext.forDstu2();
		}
		IResource inputResource = null;
		Bundle inputBundle = null;
		switch (mtInput) {
		case RESOURCE_XML_MEDIA_TYPE:
		case RESOURCE_JSON_MEDIA_TYPE:
			inputResource = importResource(iReader, ctx, mtInput);
			break;
		case BUNDLE_XML_MEDIA_TYPE:
		case BUNDLE_JSON_MEDIA_TYPE:
			if (flag) {
				// Bundle Resource is of type DSTU1 and operated as Bundle
				inputBundle = importBundle(iReader, ctx, mtInput);
			} else {

				// Bundle Resource is of type DSTU2 and operated as Resource
				inputResource = importResource(iReader, ctx, mtInput);
			}
			break;
		case TAGLIST_XML_MEDIA_TYPE:
		case TAGLIST_JSON_MEDIA_TYPE:
			throw new UnsupportedOperationException();
		}
		// switch the output to whatever the input is not
		String result = null;
		switch (mtInput) {
		case RESOURCE_XML_MEDIA_TYPE:
		case RESOURCE_JSON_MEDIA_TYPE:
			result = exportResourceToString(inputResource, ctx, mtOutput);
			break;
		case BUNDLE_XML_MEDIA_TYPE:
		case BUNDLE_JSON_MEDIA_TYPE:
			if (flag) {
				result = exportBundleToString(inputBundle, ctx, mtOutput);
			} else {
				result = exportResourceToString(inputResource, ctx, mtOutput);
			}
			break;
		case TAGLIST_XML_MEDIA_TYPE:
		case TAGLIST_JSON_MEDIA_TYPE:
			throw new UnsupportedOperationException();
		}
		return result;
	}

	/**
	 * Starting point for normal execution
	 */
	public static final void main(String[] args) {
		final FhirJsonXmlConv pg = new FhirJsonXmlConv();
		Reader iReader = null;
		PushbackReader pbr = null;
		final String outputMediaType = (args.length > 2) ? args[2] : "application/json+fhir";
		try {
			String filePath = "D:/Metadata.xml";
			iReader = new FileReader(new File(filePath));
			// Use a PushbackReader so that we can unread the data we use to
			// determine the input data type.
			// StringReader doesn't support reset(), unfortunately.
			pbr = new PushbackReader(iReader, BUFFER_SIZE);
			String result = pg.convertFhirMedia(pbr, outputMediaType);
			System.out.println("success : " + result);
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException : " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IOException : " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pbr != null)
				IOUtils.closeQuietly(pbr);
			if (iReader != null)
				IOUtils.closeQuietly(iReader);
		}
		String optput = mtOutput.getMediaType() + ";charset=UTF-8";
		System.out.println("moutput:" + optput);

	}

	/**
	 * 
	 * Start point when executing from apigee flow.
	 * 
	 * @param messageContext
	 * @param executionContext
	 * @return result of type ExecutionResult to apigee flow.
	 */
	public ExecutionResult execute(MessageContext messageContext, ExecutionContext executionContext) {
		final ExecutionResult er = ExecutionResult.SUCCESS;
		int status = 200;
		String reason = "OK";
		String errorMessage = null;
		String result = null;
		// Get the response received from server stored in apigee variable
		String inputDocument = messageContext.getVariable("input_fhir_document");
		inputDocument = inputDocument.trim();

		// Check and remove any BOM character present in response.
		if (inputDocument.charAt(0) != '<' && inputDocument.charAt(0) != '{') {
			int start = inputDocument.indexOf("<");
			inputDocument = inputDocument.substring(start);
		}

		// Set error message when response is null.
		if (StringUtils.isEmpty(inputDocument)) {
			status = 400;
			reason = "Bad Request";
			errorMessage = "No input document specified in \"input_fhir_document\" flow variable.";
		}

		// Get the actual Content type of response and required content type.
		final String inputMediaType = messageContext.getVariable("input_media_type");
		final String outputMediaType = messageContext.getVariable("output_media_type");

		final FhirJsonXmlConv pg = new FhirJsonXmlConv();
		Reader iReader = null;
		PushbackReader pbr = null;
		try {
			iReader = new StringReader(inputDocument);
			// Use a PushbackReader so that we can unread the data we use to
			// determine the input data type.
			// StringReader doesn't support reset(), unfortunately.
			pbr = new PushbackReader(iReader, BUFFER_SIZE);

			result = pg.convertFhirMedia(pbr, outputMediaType);
			if (StringUtils.isEmpty(result)) {
				status = 422;
				reason = "Unprocessable Entity";
				errorMessage = "An error occurred during the conversion process.";
			}
		} catch (UnsupportedOperationException e) {
			status = 415;
			reason = "Unsupported media type";
			errorMessage = "Media type not supported.";
		} catch (Throwable e) {
			status = 500;
			reason = "Internal Server Error";
			errorMessage = e.getMessage();
		} finally {
			if (pbr != null)
				IOUtils.closeQuietly(pbr);
			if (iReader != null)
				IOUtils.closeQuietly(iReader);
		}
		if (status == 200) {
			// Set result and message variables for apigee flow if response
			// converted successfully
			messageContext.setVariable("ouput_fhir_document", result);
			messageContext.setVariable("output_media_type", mtOutput.getMediaType() + ";charset=UTF-8");
			messageContext.setVariable("output_content_type", mtOutput.getMediaType() + ";charset=UTF-8");
		} else {
			// Set error message variables for apigee flow if any error occur in
			// process.
			messageContext.setVariable("output_fhir_status", Integer.toString(status));
			messageContext.setVariable("output_fhir_reason", reason);
			if (!StringUtils.isEmpty(errorMessage))
				messageContext.setVariable("output_fhir_error", errorMessage);
		}
		return er;
	}

}
