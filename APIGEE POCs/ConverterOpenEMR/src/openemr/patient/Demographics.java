package openemr.patient;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="demographics")
public class Demographics {
	
	 private int id;
     private String title;
     private Date DOB;
     private String fname;
     private String sname;
     private String status;
     private String city;

	public Demographics() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	@XmlElement(name="id")
	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	@XmlElement(name="title")
	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDOB() {
		return DOB;
	}

	@XmlElement(name="DOB")
	public void setDOB(Date dOB) {
		DOB = dOB;
	}

	public String getFname() {
		return fname;
	}

	@XmlElement(name="fname")
	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getSname() {
		return sname;
	}

	@XmlElement(name="sname")
	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getStatus() {
		return status;
	}

	@XmlElement(name="status")
	public void setStatus(String status) {
		this.status = status;
	}

	public String getCity() {
		return city;
	}
	
	@XmlElement(name="cit")
	public void setCity(String city) {
		this.city = city;
	}

}
