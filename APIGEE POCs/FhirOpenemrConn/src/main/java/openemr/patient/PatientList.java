package openemr.patient;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import ca.uhn.fhir.model.api.annotation.ResourceDef;

@XmlRootElement(name="PatientList")
@ResourceDef
public class PatientList {
	
	private List<Patient> patient;


	public List<Patient> getPatient() {
		return patient;
	}

	@XmlElement(name="Patient")
	public void setPatient(List<Patient> patient) {
		this.patient = patient;
	}


	@Override
	public String toString() {
		return "PatientList [patient=" + patient + "]";
	}

	public PatientList() {
		// TODO Auto-generated constructor stub
	}

}
