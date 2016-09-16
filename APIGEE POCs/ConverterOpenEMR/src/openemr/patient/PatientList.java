package openemr.patient;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="PatientList")
public class PatientList {
	
	private List<Patient> patient;


	public List<Patient> getPatient() {
		return patient;
	}

	@XmlElement(name="Patient")
	public void setPatient(List<Patient> patient) {
		this.patient = patient;
	}


	public PatientList() {
		// TODO Auto-generated constructor stub
	}
	
	
	/*public static void main(String args[]){
		
		try{
		JAXBContext jaxbContext = JAXBContext.newInstance(PatientList.class);
	    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	    PatientList patientList = (PatientList) jaxbUnmarshaller.unmarshal( new File("D:/patient.xml") );
	    System.out.println("unmarshalling Done!");
	    System.out.println(patientList.getPatient().get(0).getDemographics().getFname());
		}
		catch (JAXBException e) {
			e.printStackTrace();
		  }
		
	}*/

}
