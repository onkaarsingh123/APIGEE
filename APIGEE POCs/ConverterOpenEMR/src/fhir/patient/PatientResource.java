package fhir.patient;

import ca.uhn.fhir.model.dstu2.composite.HumanNameDt;
import ca.uhn.fhir.model.dstu2.composite.IdentifierDt;
import ca.uhn.fhir.model.dstu2.resource.Patient;

import java.io.File;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.apigee.flow.execution.ExecutionContext;
import com.apigee.flow.execution.ExecutionResult;
import com.apigee.flow.execution.spi.Execution;
import com.apigee.flow.message.MessageContext;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import openemr.patient.PatientList;

public class PatientResource implements Execution {
	String result = null;
	
	
	public String convertToFhir(String inputString, MessageContext messageContext) throws Exception {
		
		messageContext.setVariable("inside_fun", true);
	
		StringReader stringReader = new StringReader(inputString);
		
		messageContext.setVariable("completed_fun", "1");
		JAXBContext jaxbContext = JAXBContext.newInstance(PatientList.class);
		messageContext.setVariable("completed_fun", "2");
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		
		messageContext.setVariable("completed_fun", "3");   
	    PatientList patientList = (PatientList) jaxbUnmarshaller.unmarshal(stringReader);
	    messageContext.setVariable("completed_fun", "4");
	    String fname = patientList.getPatient().get(0).getDemographics().getFname();
		
	    messageContext.setVariable("fname", fname);
		Patient pat = new Patient();
		
		HumanNameDt name = pat.addName();
		messageContext.setVariable("completed_fun", "6");
		name.addFamily(fname).addGiven("Homer");
		
		messageContext.setVariable("given", name.getGivenAsSingleString());
		// Create a context
		FhirContext ctx  = FhirContext.forDstu2();
		
		messageContext.setVariable("completed_fun", "7");
		// Create a XML parser
		IParser parser = ctx.newXmlParser();
		messageContext.setVariable("completed_fun", "8");
		parser.setPrettyPrint(true);
		messageContext.setVariable("completed_fun", "10");
		
		
		result = parser.encodeResourceToString(pat);
		messageContext.setVariable("completed_fun", true);
		return result;
	}
	
	/**
	 * 
	 * Start point when executing from apigee flow.
	 * 
	 * @param messageContext
	 * @param executionContext
	 * @return result of type ExecutionResult to apigee flow.
	 */
	@SuppressWarnings("finally")
	public ExecutionResult execute(MessageContext messageContext, ExecutionContext executionContext) {
		final ExecutionResult er = ExecutionResult.SUCCESS;
		
		// Get the response received from server stored in apigee variable
		String inputDocument = messageContext.getVariable("input_fhir_document");
		inputDocument = inputDocument.trim();

		
		try 
		{
			PatientResource patientResource = new PatientResource();
			result = patientResource.convertToFhir(inputDocument, messageContext);
			
			messageContext.setVariable("ouput_fhir_document", result);
			messageContext.setVariable("RESULT","RESULT_IS_SET" );
			
		} 
		
		catch (Exception e) 
		{
			String error = e.toString();
			messageContext.setVariable("mycallout_exception", error);
			messageContext.setVariable("ouput_fhir_error", ExceptionUtils.getStackTrace(e));
	        return ExecutionResult.ABORT;
		} 
		
		finally{
			messageContext.setVariable("ouput_fhir_document", result);
			messageContext.setVariable("mycallout_exception", "Finally also executed....!");
			return ExecutionResult.SUCCESS;
		}
		
	}
	
	
}
