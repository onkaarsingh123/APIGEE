package openemr.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import openemr.patient.PatientList;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.api.IDatatype;
import ca.uhn.fhir.model.dstu2.composite.AddressDt;
import ca.uhn.fhir.model.dstu2.composite.CodeableConceptDt;
import ca.uhn.fhir.model.dstu2.composite.HumanNameDt;
import ca.uhn.fhir.model.dstu2.composite.PeriodDt;
import ca.uhn.fhir.model.dstu2.composite.ResourceReferenceDt;
import ca.uhn.fhir.model.dstu2.resource.Bundle;
import ca.uhn.fhir.model.dstu2.resource.Bundle.Entry;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.model.dstu2.resource.Patient.Animal;
import ca.uhn.fhir.model.dstu2.resource.Patient.Link;
import ca.uhn.fhir.model.dstu2.valueset.AdministrativeGenderEnum;
import ca.uhn.fhir.model.dstu2.valueset.BundleTypeEnum;
import ca.uhn.fhir.model.dstu2.valueset.ContactPointSystemEnum;
import ca.uhn.fhir.model.dstu2.valueset.MaritalStatusCodesEnum;
import ca.uhn.fhir.model.primitive.BoundCodeDt;
import ca.uhn.fhir.model.primitive.DateDt;
import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.model.primitive.StringDt;
import ca.uhn.fhir.parser.IParser;

import com.apigee.flow.execution.ExecutionContext;
import com.apigee.flow.execution.ExecutionResult;
import com.apigee.flow.execution.spi.Execution;
import com.apigee.flow.message.MessageContext;

/**
 * 		@version 0.0.1 
 * 		FhirOpenemrConn class reads Patient data from OpenEMR server and converts it to FHIR spec data.
 * 
 */
public class FhirOpenemrConn implements Execution {
	
	boolean flag = false;
	static FHIRMediaType mtOutput;
	
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
	 * This method takes the input from StringReader and Stores Patient data from OpenEMR into patientList object 
	 * 
	 * @param reader
	 *            contains data read from input
	 * @return PatientList2 object.
	 * @throws JAXBException 
	 */
		
	public PatientList getPatientList(final File file) throws JAXBException{
		
		JAXBContext jaxbContext = JAXBContext.newInstance(PatientList.class);
	    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	    PatientList patientList = (PatientList) jaxbUnmarshaller.unmarshal(file);
	    //System.out.println("Patient Data...   " + patientList);
		return patientList;
	}
	

	public PatientList getPatientList(final StringReader stringReader) throws JAXBException{
		
		JAXBContext jaxbContext = JAXBContext.newInstance(PatientList.class);
	    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	    PatientList patientList = (PatientList) jaxbUnmarshaller.unmarshal(stringReader);
	    //System.out.println("Patient Data...   " + patientList);
		return patientList;
	}
	
	public Patient getSinglePatient(PatientList patientList, int index){
		
		String id = patientList.getPatient().get(index).getDemographics().getId();
	    String title = patientList.getPatient().get(index).getDemographics().getTitle();
	    String language = patientList.getPatient().get(index).getDemographics().getLanguage();
	    String financial = patientList.getPatient().get(index).getDemographics().getFinancial();
	    String fname = patientList.getPatient().get(index).getDemographics().getFname();	// Mapping Done
	    String lname = patientList.getPatient().get(index).getDemographics().getLname();	//Mapping Done
	    String mname = patientList.getPatient().get(index).getDemographics().getMname();
	    Date dob = patientList.getPatient().get(index).getDemographics().getDob();	//Mapping Done
	    String street = patientList.getPatient().get(index).getDemographics().getStreet(); // Mapping Done
	    String postalcode = patientList.getPatient().get(index).getDemographics().getPostalcode(); // Mapping Done
	    String city = patientList.getPatient().get(index).getDemographics().getCity(); // Mapping Done
	    String state = patientList.getPatient().get(index).getDemographics().getState(); // Mapping Done
	    String countrycode = patientList.getPatient().get(index).getDemographics().getCountrycode(); // Mapping Done
	    String driverslicense = patientList.getPatient().get(index).getDemographics().getDriverslicense(); 
	    String ss = patientList.getPatient().get(index).getDemographics().getSs();
	    String occupation = patientList.getPatient().get(index).getDemographics().getOccupation();
	    String phonehome = patientList.getPatient().get(index).getDemographics().getPhonehome();
	    String phonebiz = patientList.getPatient().get(index).getDemographics().getPhonebiz();
	    String phonecontract = patientList.getPatient().get(index).getDemographics().getPhonecontract();
	    String phonecell = patientList.getPatient().get(index).getDemographics().getPhonecell(); // Mapping Done
	    String pharmacyid = patientList.getPatient().get(index).getDemographics().getPharmacyid();
	    String status = patientList.getPatient().get(index).getDemographics().getStatus(); // Mapping Done
	    String relationship = patientList.getPatient().get(index).getDemographics().getContractrelationship();
	    String date = patientList.getPatient().get(index).getDemographics().getDate();
	    String sex = patientList.getPatient().get(index).getDemographics().getSex(); // Mapping Done
	    String referrer = patientList.getPatient().get(index).getDemographics().getReferrer();
	    String referrerid = patientList.getPatient().get(index).getDemographics().getReferrerid();
	    String providerid = patientList.getPatient().get(index).getDemographics().getProviderid();
	    String refproviderid = patientList.getPatient().get(index).getDemographics().getRefproviderid();
	    String email = patientList.getPatient().get(index).getDemographics().getEmail(); // Mapping Done
	    String emaildirect = patientList.getPatient().get(index).getDemographics().getEmaildirect();
	    String ethnoracial = patientList.getPatient().get(index).getDemographics().getEthnoracial();
	    String race = patientList.getPatient().get(index).getDemographics().getRace();
	    String ethnicity = patientList.getPatient().get(index).getDemographics().getEthnicity();
	    String interpretter = patientList.getPatient().get(index).getDemographics().getInterpretter();
	    String migrantseasonal = patientList.getPatient().get(index).getDemographics().getMigrantseasonal();
	    String familysize = patientList.getPatient().get(index).getDemographics().getFamilysize();
	    String monthlyincome = patientList.getPatient().get(index).getDemographics().getMonthlyincome();
	    String homeless = patientList.getPatient().get(index).getDemographics().getHomeless();
	    String financialreview = patientList.getPatient().get(index).getDemographics().getFinancialreview();
	    String pubpid = patientList.getPatient().get(index).getDemographics().getPubpid();
	    String pid = patientList.getPatient().get(index).getDemographics().getPid();
	    String genericname = patientList.getPatient().get(index).getDemographics().getGenericname();
	    String genericval = patientList.getPatient().get(index).getDemographics().getGenericval();
	    String hipaamail = patientList.getPatient().get(index).getDemographics().getHipaamail();
	    String hipaavoice = patientList.getPatient().get(index).getDemographics().getHipaavoice();
	    String hipaanotice = patientList.getPatient().get(index).getDemographics().getHipaanotice();
	    String hipaamessage = patientList.getPatient().get(index).getDemographics().getHipaamessage();
	    String hipaaallowsms = patientList.getPatient().get(index).getDemographics().getHipaaallowsms();
	    String hipaaallowmail = patientList.getPatient().get(index).getDemographics().getHipaaallowmail();
	    String squad = patientList.getPatient().get(index).getDemographics().getSquad();
	    String fitness = patientList.getPatient().get(index).getDemographics().getFitness();
	    String ferralsource = patientList.getPatient().get(index).getDemographics().getFerralsource();
	    String usertext = patientList.getPatient().get(index).getDemographics().getUsertext();
	    String pricelevel = patientList.getPatient().get(index).getDemographics().getPricelevel();
	    String regdate = patientList.getPatient().get(index).getDemographics().getRegdate();
	    String contrastart = patientList.getPatient().get(index).getDemographics().getContrastart();
	    String completead = patientList.getPatient().get(index).getDemographics().getCompletead();
	    String adreviewed = patientList.getPatient().get(index).getDemographics().getAdreviewed();
	    String vfc = patientList.getPatient().get(index).getDemographics().getVfc();
	    String mothersname = patientList.getPatient().get(index).getDemographics().getMothersname();
	    String guardianname = patientList.getPatient().get(index).getDemographics().getGuardianname();
	    String allowimmreguse = patientList.getPatient().get(index).getDemographics().getAllowimmreguse();
	    String allowimminfoshare = patientList.getPatient().get(index).getDemographics().getAllowimminfoshare();
	    String allowhealthinfoex = patientList.getPatient().get(index).getDemographics().getAllowhealthinfoex();
	    String allowpatientportal = patientList.getPatient().get(index).getDemographics().getAllowpatientportal();
	    Date deceaseddate = patientList.getPatient().get(index).getDemographics().getDeceaseddate(); // Mapping Done
	    String deceasedreason = patientList.getPatient().get(index).getDemographics().getDeceasedreason();
	    String soapimportstatus = patientList.getPatient().get(index).getDemographics().getSoapimportstatus();
	    String cmsportallogin = patientList.getPatient().get(index).getDemographics().getCmsportallogin();
	    String dobts = patientList.getPatient().get(index).getDemographics().getDobts();
	    String ethnicityvalue = patientList.getPatient().get(index).getDemographics().getEthnicityvalue();
	    String profileimage  = patientList.getPatient().get(index).getDemographics().getProfileimage(); // Mapping Done
	   	 			
	   	// Set object values into FHIR model
		// Create a resource instance
	    Patient patient = new Patient();
		IdDt dt = new IdDt(id);
		
		patient.setId(dt);
		
				//An identifier for this patient
		//patient.addIdentifier().setSystem("urn:fake:mrns").setValue(""); // No mapping parameter found, hence hard coded
		//patient.addIdentifier().setSystem("urn:fake:otherids").setValue("");// No mapping parameter found, hence hard coded
		
		//Whether this patient's record is in active use
		/*boolean activeFlag = false;	//No mapping parameter found, hence hard coded
		if (activeFlag == true ) {
			patient.setActive(true);
		}
		else {
			patient.setActive(false);
		}	*/
		
	    //A name associated with the patient.
		//patient.addName().addPrefix(title).addGiven(fname).addFamily(lname);
		
		HumanNameDt nameDt = new HumanNameDt();
		nameDt.addPrefix(title);
		nameDt.addGiven(fname);
		nameDt.addFamily(lname);
		
		List<HumanNameDt> humanNameDts = new ArrayList<HumanNameDt>();
		humanNameDts.add(nameDt);
		
		patient.setName(humanNameDts);
		
		//A contact detail for the individual.
		if (phonecell != null)	{
			patient.addTelecom().setSystem(ContactPointSystemEnum.PHONE).setValue(phonecell);
		}
		
		if (email != null)	{
			patient.addTelecom().setSystem(ContactPointSystemEnum.EMAIL).setValue(email);
		}
		
		/*if ("FAX No" != null)  { // No mapping parameter found, hence hard coded
			patient.addTelecom().setSystem(ContactPointSystemEnum.FAX).setValue("FAX No.");
		}
		
		if ("Pager" != null)  { // No mapping parameter found, hence hard coded
			patient.addTelecom().setSystem(ContactPointSystemEnum.PAGER).setValue("Pager");
		}
		
		if ("URL" != null)  {// No mapping parameter found, hence hard coded
			patient.addTelecom().setSystem(ContactPointSystemEnum.URL).setValue("URL");
		}*/
				
		// Patient gender
		if(sex.equalsIgnoreCase("Male")) {
			patient.setGender(AdministrativeGenderEnum.MALE);
		}
		else if(sex.equalsIgnoreCase("Female")) {
			patient.setGender(AdministrativeGenderEnum.FEMALE);
		}
		else {
			patient.setGender(AdministrativeGenderEnum.OTHER);
		}
		
		//The date of birth for the individual
		DateDt fhir_dob = new DateDt();
		fhir_dob.setValue(dob);
		patient.setBirthDate(fhir_dob);
		
		//Indicates if the individual is deceased or not
		DateDt fhir_deceaseddate = new DateDt();
		fhir_deceaseddate.setValue(deceaseddate);
		patient.setDeceased(fhir_deceaseddate);
		
		//Addresses for the individual
		List<StringDt> fhir_line = new ArrayList<StringDt>();
		fhir_line.add(0, new StringDt(street));
		patient.addAddress().setLine(fhir_line).setCity(city).setState(state).setPostalCode(postalcode).setCountry(countrycode);
		
		//Marital status of a patient.  // Check input value is numeric or characters 
		if (status.equals("Annulled")){	
			patient.setMaritalStatus(MaritalStatusCodesEnum.A);
		}
		else if (status.equals("Divorced")){
			patient.setMaritalStatus(MaritalStatusCodesEnum.D);
		}
		else if (status.equals("Interlocutory")){
			patient.setMaritalStatus(MaritalStatusCodesEnum.I);
		}
		else if (status.equals("Legally Separated")){
			patient.setMaritalStatus(MaritalStatusCodesEnum.L);
		}
		else if (status.equals("Married")){
			patient.setMaritalStatus(MaritalStatusCodesEnum.M);
		}
		else if (status.equals("Polygamous")){
			patient.setMaritalStatus(MaritalStatusCodesEnum.P);
		}
		else if (status.equals("Never Married")){
			patient.setMaritalStatus(MaritalStatusCodesEnum.S);
		}
		else if (status.equals("Domestic partner")){
			patient.setMaritalStatus(MaritalStatusCodesEnum.T);
		}
		else if (status.equals("Widowed")){
			patient.setMaritalStatus(MaritalStatusCodesEnum.W);
		}
		
		//Whether patient is part of a multiple birth
		IDatatype fhir_multipleBirth=null; // No mapping parameter found, hence set to null
		patient.setMultipleBirth(fhir_multipleBirth);
		
		//Image of the patient
		patient.addPhoto().setUrl(profileimage); 
		
		//A contact party for the patient
		List<CodeableConceptDt> fhir_relationship = new ArrayList<CodeableConceptDt>();
		fhir_relationship.add(null); 	// No mapping parameter found, hence set to null
		patient.addContact().setRelationship(fhir_relationship);   
		HumanNameDt fhir_contactname = null;	// No mapping parameter found, hence set to null
		patient.addContact().setName(fhir_contactname );	
		BoundCodeDt<ContactPointSystemEnum> fhir_telecom =null;	// No mapping parameter found, hence set to null
		patient.addTelecom().setSystem(fhir_telecom);	
		AddressDt fhir_address = null;  // No mapping parameter found, hence set to null
		patient.addContact().setAddress(fhir_address);
		BoundCodeDt<AdministrativeGenderEnum> fhir_gender=null;// No mapping parameter found, hence set to null
		patient.addContact().setGender(fhir_gender);
		ResourceReferenceDt fhir_organization = null;// No mapping parameter found, hence set to null
		patient.addContact().setOrganization(fhir_organization);
		PeriodDt fhir_period = null; // No mapping parameter found, hence set to null
		patient.addContact().setPeriod(fhir_period);
		
		//This patient is known to be an animal (non-human)
		Animal fhir_animal= new Animal(); 
		CodeableConceptDt codeableConceptDt = new CodeableConceptDt();
		codeableConceptDt.setText("DOG");
		fhir_animal.setBreed(codeableConceptDt);
		
		// A list of Languages which may be used to communicate with the patient about his or her health.
		List<Patient.Communication> comm = new ArrayList<Patient.Communication>();
		Patient.Communication com = new Patient.Communication();
		CodeableConceptDt lang;
		//lang.setText("acb");
		//com.setLanguage(lang);
		com.setPreferred(false);
		comm.add(com);
		
		//Patient's nominated primary care provider
		/*patient.addCareProvider().setReference("Practioner/1769493");
		patient.addCareProvider().setReference("Practioner/1769503");
		patient.addCareProvider().setReference("Practioner/1767209");
		*/
		
		//Organization that is the custodian of the patient record
		ResourceReferenceDt fhir_managingOrg = null; // No mapping parameter found, hence set to null
		patient.setManagingOrganization(fhir_managingOrg);
		
		//Link to another patient resource that concerns the same actual person.
		List<Link> fhir_links = null; // No mapping parameter found, hence set to null
		patient.setLink(fhir_links);
		
		return patient;
		
	}
		
	
	public Bundle getAllPatients(PatientList patientList){
		
		Bundle bundle = new Bundle();
		bundle.setType(BundleTypeEnum.SEARCH_RESULTS);
		int patientSize = patientList.getPatient().size();
		for(int i=0; i<patientSize; i++)
		{
			Patient patient = new Patient();
			String id = patientList.getPatient().get(i).getId();
			String firstname = patientList.getPatient().get(i).getFirstname();
			String lastname = patientList.getPatient().get(i).getLastname();
			Date dob = patientList.getPatient().get(i).getDob();
			String middlename = patientList.getPatient().get(i).getMiddlename(); // No mapping parameters found
			String phone = patientList.getPatient().get(i).getPhone();
			String gender = patientList.getPatient().get(i).getGender();
			
			IdDt dt = new IdDt(id);
			patient.setId(dt);
			
			HumanNameDt nameDt = new HumanNameDt();
			nameDt.addGiven(firstname);
			nameDt.addFamily(lastname);
			
			List<HumanNameDt> humanNameDts = new ArrayList<HumanNameDt>();
			humanNameDts.add(nameDt);
			
			patient.setName(humanNameDts);
			
			// Patient gender
			if(gender.equalsIgnoreCase("Male")) {
				patient.setGender(AdministrativeGenderEnum.MALE);
			}
			else if(gender.equalsIgnoreCase("Female")) {
				patient.setGender(AdministrativeGenderEnum.FEMALE);
			}
			else {
				patient.setGender(AdministrativeGenderEnum.OTHER);
			}
			
			//The date of birth for the individual
			DateDt fhir_dob = new DateDt();
			fhir_dob.setValue(dob);
			patient.setBirthDate(fhir_dob);
			
			//A contact detail for the individual.
			if (phone != null)	{
				patient.addTelecom().setSystem(ContactPointSystemEnum.PHONE).setValue(phone);
			}
			
			Entry entry = new Entry();
			entry.setResource(patient);
			bundle.addEntry(entry);
		}
	return bundle;
		
	}
	
	
 
	/**
	 * Starting point for normal execution
	 */
	public static final void main(String[] args) {
		
		FhirContext context = new FhirContext();
		final FhirOpenemrConn pg = new FhirOpenemrConn();
		Bundle bundle;
		Patient patient;
		final String outputMediaType = "application/json+fhir";
		final String req="get_all";
		String result = "";
		IParser parser = getParser(context, FHIRMediaType.getType(outputMediaType) );
		parser.setPrettyPrint(true);
		
		try {
			
			PatientList patientList=null;
			File f = new File("D:/AllPatients.txt");
			patientList = pg.getPatientList(f);
			
			if(req.equalsIgnoreCase("get_all")){
				
				System.out.println("Patient List... " + patientList);
				bundle = pg.getAllPatients(patientList);
				result = parser.encodeResourceToString(bundle);	
			}
			/*else if(req.equalsIgnoreCase("get_single")){
				patient = pg.getSinglePatient(patientList,0);
				System.out.println("Patient in main.... " + patient.getId());
				result = parser.encodeResourceToString(patient);	
			}
			else
				result = null;*/
			
			System.out.println("Result....   "+result);
			
			
		} catch (JAXBException e) {
			e.printStackTrace();
		  }	 
		catch (Exception e) {
			e.printStackTrace();
		}  
		
		
		
		
		
		
		
		/*int status_code = 200;
		String reason = "OK";
		String errorMessage = null;
		String result = null;
		PatientList2 patientList;
		final FhirOpenemrConn conn = new FhirOpenemrConn();
		//String req = messageContext.getVariable("req");
		// Get the response received from server stored in apigee variable
		
		
		// Get the actual Content type of response and required content type.
		String outputMediaType = "application/json+fhir";

		
		
		FhirContext context = new FhirContext();
		try 
		{
			File f = new File("D:/patient.xml");;
			patientList = conn.getPatientList(f);
		    Bundle bundle;
			Patient patient;
			
			IParser parser = getParser(context, FHIRMediaType.getType("application/json+fhir"));
			parser.setPrettyPrint(true);
			
			patient = conn.getSinglePatient(patientList,0);
			result = parser.encodeResourceToString(patient);
			
			bundle = conn.getAllPatients(patientList);
			result = parser.encodeResourceToString(bundle);
			
			System.out.println("Result...." + result);

		}
		catch (UnsupportedOperationException e) {
			status_code = 415;
			reason = "Unsupported media type";
			errorMessage = "Media type not supported.";
		} 
		catch (Throwable e) {
			status_code = 500;
			reason = "Internal Server Error";
			errorMessage = e.getMessage();
			System.out.println("Error... " + e);
		}
		
		if (status_code == 200) {
			// Set result and message variables for apigee flow if response converted successfully
			System.out.println("Valid Result");
		} 
		else {
			System.out.println("Error..!!");
		}
		*/
		
		
		
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
		int status_code = 200;
		String reason = "OK";
		String errorMessage = "";
		String result = "";
		PatientList patientList = new PatientList();
		final FhirOpenemrConn conn = new FhirOpenemrConn();
		FhirContext context = new FhirContext();
		
		// Get the response received from server stored in apigee variable
		String inputDocument = messageContext.getVariable("input_openemr_response");
		String id = messageContext.getVariable("id");
		inputDocument = inputDocument.trim();
		StringReader stringReader = new StringReader(inputDocument);
		//messageContext.setVariable("inside_fun", 1);
		// Check and remove any BOM character present in response.
		if (inputDocument.charAt(0) != '<' && inputDocument.charAt(0) != '{') {
			int start = inputDocument.indexOf("<");
			inputDocument = inputDocument.substring(start);
		}

		String output_MT = messageContext.getVariable("outputMediaType");
		messageContext.setVariable("outputTypeFromCallout", output_MT );
		// Get the actual Content type of response and required content type.
		String outputMediaType = "application/json+fhir";

		//messageContext.setVariable("inside_fun", 2);
		if(outputMediaType == "" || outputMediaType == null)
		{
			outputMediaType = "application/json+fhir";
		}
		
		try {
			Bundle bundle;
			Patient patient;
			
			//messageContext.setVariable("inside_fun", 4);
			patientList = getPatientList(stringReader);
		    
		    //messageContext.setVariable("inside_fun1", patientList);
		    IParser parser = getParser(context, FHIRMediaType.getType(outputMediaType) );
			parser.setPrettyPrint(true);
			
			messageContext.setVariable("inside_fun", 5);
			if(id =="" || id == null){
				bundle = getAllPatients(patientList);
				result = parser.encodeResourceToString(bundle);
			}
			else{
				patient = getSinglePatient(patientList,0);
				result = parser.encodeResourceToString(patient);	
			}
			
			messageContext.setVariable("inside_fun", 6);
			
		} catch (UnsupportedOperationException e) {
			status_code = 415;
			reason = "Unsupported media type";
			errorMessage = "Media type not supported.";
		} catch (Throwable e) {
			messageContext.setVariable("inside_fun", e);
			status_code = 500;
			reason = "Internal Server Error";
			errorMessage = e.getMessage();
			messageContext.setVariable("output_openemr_document", errorMessage);
			
		} finally {
			if (stringReader != null)
				IOUtils.closeQuietly(stringReader);
		}
		
		if (status_code == 200) {
			// Set result and message variables for apigee flow if response converted successfully
			messageContext.setVariable("inside_fun", "STATUS IS 200");
			messageContext.setVariable("output_openemr_document", result);
			messageContext.setVariable("output_media_type", outputMediaType);
		} 
		else {
			// Set error message variables for apigee flow if any error occur in process.
			messageContext.setVariable("output_openemr_status", Integer.toString(status_code));
			messageContext.setVariable("output_openemr_reason", reason);
			if (!StringUtils.isEmpty(errorMessage))
				messageContext.setVariable("output_openemr_error", errorMessage);
		}
		return er;
	}

}
