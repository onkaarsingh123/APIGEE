package openemr.patient;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Patient")
public class Patient {
	
	private Demographics demographics;

	public Patient() {
		// TODO Auto-generated constructor stub
	}

	public Demographics getDemographics() {
		return demographics;
	}

	@XmlElement(name="demographics")
	public void setDemographics(Demographics demographics) {
		this.demographics = demographics;
	}

}
