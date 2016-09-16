package openemr.patient;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Patient")
public class Patient {
	
	private Demographics demographics;
	private String id ;
	private String firstname ;
	private String lastname ;
	private String middlename ;
	private String phone ;
	private Date dob ;
	private String gender ;
	
	public String getId() {
		return id;
	}

	@XmlElement(name="id")
	public void setId(String id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	@XmlElement(name="firstname")
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	@XmlElement(name="lastname")
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getMiddlename() {
		return middlename;
	}

	@XmlElement(name="middlename")
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getPhone() {
		return phone;
	}

	@XmlElement(name="phone")
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getDob() {
		return dob;
	}

	@XmlElement(name="dob")
	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	@XmlElement(name="gender")
	public void setGender(String gender) {
		this.gender = gender;
	}

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
	
	@Override
	public String toString() {
		return "Patient [demographics=" + demographics + ", id=" + id
				+ ", firstname=" + firstname + ", lastname=" + lastname
				+ ", middlename=" + middlename + ", phone=" + phone + ", dob="
				+ dob + ", gender=" + gender + "]";
	}

}
