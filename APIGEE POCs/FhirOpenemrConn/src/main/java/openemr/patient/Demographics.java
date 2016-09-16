package openemr.patient;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="demographics")
public class Demographics {
	
//	 private String id;
//     private String title;
//     private String language;
//     private String financial;
//     private String fname;
//     
//     private Date DOB;
//     private String sname;
//     private String status;
//     private String city;
	
	@Override
	public String toString() {
		return "Demographics [\nid=" + id + ", \ntitle=" + title + ", \nlanguage="
				+ language + ", \nfinancial=" + financial + ", \nfname=" + fname
				+ ", \nlname=" + lname + ", \nmname=" + mname + ", \ndob=" + dob
				+ ", \nstreet=" + street + ", \npostalcode=" + postalcode
				+ ", \ncity=" + city + ", \nstate=" + state + ", \ncountrycode="
				+ countrycode + ", \ndriverslicense=" + driverslicense + ", \nss="
				+ ss + ", occupation=" + occupation + ", phonehome="
				+ phonehome + ", phonebiz=" + phonebiz + ", idphonecontract="
				+ idphonecontract + ", phonecell=" + phonecell
				+ ", pharmacyid=" + pharmacyid + ", status=" + status
				+ ", contactrelationship=" + contactrelationship + ", date="
				+ date + ", sex=" + sex + ", referrer=" + referrer
				+ ", referrerid=" + referrerid + ", providerid=" + providerid
				+ ", refproviderid=" + refproviderid + ", email=" + email
				+ ", emaildirect=" + emaildirect + ", ethnoracial="
				+ ethnoracial + ", race=" + race + ", ethnicity=" + ethnicity
				+ ", interpretter=" + interpretter + ", migrantseasonal="
				+ migrantseasonal + ", familysize=" + familysize
				+ ", monthlyincome=" + monthlyincome + ", homeless=" + homeless
				+ ", financialreview=" + financialreview + ", pubpid=" + pubpid
				+ ", pid=" + pid + ", genericname=" + genericname
				+ ", genericval=" + genericval + ", hipaamail=" + hipaamail
				+ ", hipaavoice=" + hipaavoice + ", hipaanotice=" + hipaanotice
				+ ", hipaamessage=" + hipaamessage + ", hipaaallowsms="
				+ hipaaallowsms + ", hipaaallowmail=" + hipaaallowmail
				+ ", squad=" + squad + ", fitness=" + fitness
				+ ", ferralsource=" + ferralsource + ", usertext=" + usertext
				+ ", pricelevel=" + pricelevel + ", regdate=" + regdate
				+ ", contrastart=" + contrastart + ", completead=" + completead
				+ ", adreviewed=" + adreviewed + ", vfc=" + vfc
				+ ", mothersname=" + mothersname + ", guardianname="
				+ guardianname + ", allowimmreguse=" + allowimmreguse
				+ ", allowimminfoshare=" + allowimminfoshare
				+ ", allowhealthinfoex=" + allowhealthinfoex
				+ ", allowpatientportal=" + allowpatientportal
				+ ", deceaseddate=" + deceaseddate + ", deceasedreason="
				+ deceasedreason + ", soapimportstatus=" + soapimportstatus
				+ ", cmsportallogin=" + cmsportallogin + ", dobts=" + dobts
				+ ", ethnicityvalue=" + ethnicityvalue + ", profileimage="
				+ profileimage + ", phonecontract=" + phonecontract + "]";
	}

	private String id ;
	private String title ;
	private String language ;
	private String financial ;
	private String fname ;
	private String lname ;
	private String mname ;
	private Date dob ;
	private String street;
	private String postalcode ;
	private String city ;
	private String state ;
	private String countrycode ;
	private String driverslicense ;
	private String ss ;
	private String occupation ;
	private String phonehome ;
	private String phonebiz ;
	private String idphonecontract ;
	private String phonecell ;
	private String pharmacyid ;
	private String status ;
	private String contactrelationship ;
	private String date ;
	private String sex ;
	private String referrer ;
	private String referrerid ;
	private String providerid ;
	private String refproviderid ;
	private String email ;
	private String emaildirect ;
	private String ethnoracial ;
	private String race ;
	private String ethnicity ;
	private String interpretter ;
	private String migrantseasonal ;
	private String familysize ;
	private String monthlyincome ;
	private String homeless ;
	private String financialreview ;
	private String pubpid ;
	private String pid ;
	private String genericname ;
	private String genericval ;
	private String hipaamail ;
	private String hipaavoice ;
	private String hipaanotice ;
	private String hipaamessage ;
	private String hipaaallowsms ;
	private String hipaaallowmail ;
	private String squad ;
	private String fitness ;
	private String ferralsource ;
	private String usertext ;
	private String pricelevel ;
	private String regdate ;
	private String contrastart ;
	private String completead ;
	private String adreviewed ;
	private String vfc ;
	private String mothersname ;
	private String guardianname ;
	private String allowimmreguse ;
	private String allowimminfoshare ;
	private String allowhealthinfoex ;
	private String allowpatientportal ;
	private Date deceaseddate ;
	private String deceasedreason ;
	private String soapimportstatus ;
	private String cmsportallogin ;
	private String dobts ;
	private String ethnicityvalue ;
	private String profileimage;
	private String phonecontract;
	
     //incremental changes starts here
    
	public Demographics() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	@XmlElement(name="id")
	public void setId(String id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	@XmlElement(name="title")
	public void setTitle(String title) {
		this.title = title;
	}

	public String getLanguage() {
		return language;
	}
	
	@XmlElement(name="language")
	public void setLanguage(String language) {
		this.language = language;
	}

	public String getFinancial() {
		return financial;
	}
	
	@XmlElement(name="financial")
	public void setFinancial(String financial) {
		this.financial = financial;
	}

	public String getFname() {
		return fname;
	}
	
	@XmlElement(name="fname")
	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}
	
	@XmlElement(name="lname")
	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getMname() {
		return mname;
	}
	
	@XmlElement(name="mname")
	public void setMname(String mname) {
		this.mname = mname;
	}

	public Date getDob() {
		return dob;
	}

	@XmlElement(name="DOB")
	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getStreet() {
		return street;
	}
	
	@XmlElement(name="street")
	public void setStreet(String street) {
		this.street = street;
	}

	public String getPostalcode() {
		return postalcode;
	}

	@XmlElement(name="postalcode")
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public String getCity() {
		return city;
	}

	@XmlElement(name="city")
	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	@XmlElement(name="state")
	public void setState(String state) {
		this.state = state;
	}

	public String getCountrycode() {
		return countrycode;
	}

	@XmlElement(name="countrycode")
	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}

	public String getDriverslicense() {
		return driverslicense;
	}

	@XmlElement(name="driverslicense")
	public void setDriverslicense(String driverslicense) {
		this.driverslicense = driverslicense;
	}

	public String getSs() {
		return ss;
	}

	@XmlElement(name="ss")
	public void setSs(String ss) {
		this.ss = ss;
	}

	public String getOccupation() {
		return occupation;
	}

	@XmlElement(name="occupation")
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getPhonehome() {
		return phonehome;
	}

	@XmlElement(name="phonehome")
	public void setPhonehome(String phonehome) {
		this.phonehome = phonehome;
	}

	public String getPhonebiz() {
		return phonebiz;
	}

	@XmlElement(name="phonebiz")
	public void setPhonebiz(String phonebiz) {
		this.phonebiz = phonebiz;
	}

	public String getPhonecontract() {
		return phonecontract;
	}

	@XmlElement(name="phonecontract")
	public void setPhonecontract(String phonecontract) {
		this.phonecontract = phonecontract;
	}

	public String getPhonecell() {
		return phonecell;
	}

	@XmlElement(name="phonecell")
	public void setPhonecell(String phonecell) {
		this.phonecell = phonecell;
	}

	public String getPharmacyid() {
		return pharmacyid;
	}

	@XmlElement(name="pharmacyid")
	public void setPharmacyid(String pharmacyid) {
		this.pharmacyid = pharmacyid;
	}

	public String getStatus() {
		return status;
	}
	
	@XmlElement(name="status")
	public void setStatus(String status) {
		this.status = status;
	}

	public String getContractrelationship() {
		return contactrelationship;
	}

	@XmlElement(name="contactrelationship")
	public void setContactrelationship(String relationship) {
		this.contactrelationship = relationship;
	}

	public String getDate() {
		return date;
	}

	@XmlElement(name="date")
	public void setDate(String date) {
		this.date = date;
	}

	public String getSex() {
		return sex;
	}


	@XmlElement(name="sex")
	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getReferrer() {
		return referrer;
	}

	@XmlElement(name="referrer")
	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}

	public String getReferrerid() {
		return referrerid;
	}

	@XmlElement(name="referrerID")
	public void setReferrerid(String referrerid) {
		this.referrerid = referrerid;
	}

	public String getProviderid() {
		return providerid;
	}

	@XmlElement(name="providerID")
	public void setProviderid(String providerid) {
		this.providerid = providerid;
	}

	public String getRefproviderid() {
		return refproviderid;
	}

	@XmlElement(name="refproviderID")
	public void setRefproviderid(String refproviderid) {
		this.refproviderid = refproviderid;
	}

	public String getEmail() {
		return email;
	}

	@XmlElement(name="email")
	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmaildirect() {
		return emaildirect;
	}

	@XmlElement(name="emaildirect")
	public void setEmaildirect(String emaildirect) {
		this.emaildirect = emaildirect;
	}

	public String getEthnoracial() {
		return ethnoracial;
	}

	@XmlElement(name="ethnoracial")
	public void setEthnoracial(String ethnoracial) {
		this.ethnoracial = ethnoracial;
	}

	public String getRace() {
		return race;
	}

	@XmlElement(name="race")
	public void setRace(String race) {
		this.race = race;
	}

	public String getEthnicity() {
		return ethnicity;
	}

	@XmlElement(name="ethnicity")
	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity;
	}

	public String getInterpretter() {
		return interpretter;
	}

	@XmlElement(name="interpretter")
	public void setInterpretter(String interpretter) {
		this.interpretter = interpretter;
	}

	public String getMigrantseasonal() {
		return migrantseasonal;
	}

	@XmlElement(name="migrantseasonal")
	public void setMigrantseasonal(String migrantseasonal) {
		this.migrantseasonal = migrantseasonal;
	}

	public String getFamilysize() {
		return familysize;
	}

	@XmlElement(name="familysize")
	public void setFamilysize(String familysize) {
		this.familysize = familysize;
	}

	public String getMonthlyincome() {
		return monthlyincome;
	}

	@XmlElement(name="monthlyincome")
	public void setMonthlyincome(String monthlyincome) {
		this.monthlyincome = monthlyincome;
	}

	public String getHomeless() {
		return homeless;
	}

	@XmlElement(name="homeless")
	public void setHomeless(String homeless) {
		this.homeless = homeless;
	}

	public String getFinancialreview() {
		return financialreview;
	}

	@XmlElement(name="financialreview")
	public void setFinancialreview(String financialreview) {
		this.financialreview = financialreview;
	}

	public String getPubpid() {
		return pubpid;
	}

	@XmlElement(name="pubpid")
	public void setPubpid(String pubpid) {
		this.pubpid = pubpid;
	}

	public String getPid() {
		return pid;
	}

	@XmlElement(name="pid")
	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getGenericname() {
		return genericname;
	}

	@XmlElement(name="genericname")
	public void setGenericname(String genericname) {
		this.genericname = genericname;
	}

	public String getGenericval() {
		return genericval;
	}

	@XmlElement(name="genericval")
	public void setGenericval(String genericval) {
		this.genericval = genericval;
	}

	public String getHipaamail() {
		return hipaamail;
	}

	@XmlElement(name="hipaamail")
	public void setHipaamail(String hipaamail) {
		this.hipaamail = hipaamail;
	}

	public String getHipaavoice() {
		return hipaavoice;
	}

	@XmlElement(name="hipaavoice")
	public void setHipaavoice(String hipaavoice) {
		this.hipaavoice = hipaavoice;
	}

	public String getHipaanotice() {
		return hipaanotice;
	}

	@XmlElement(name="hipaanotice")
	public void setHipaanotice(String hipaanotice) {
		this.hipaanotice = hipaanotice;
	}

	public String getHipaamessage() {
		return hipaamessage;
	}

	@XmlElement(name="hipaamessage")
	public void setHipaamessage(String hipaamessage) {
		this.hipaamessage = hipaamessage;
	}

	public String getHipaaallowsms() {
		return hipaaallowsms;
	}

	@XmlElement(name="hipaaallowsms")
	public void setHipaaallowsms(String hipaaallowsms) {
		this.hipaaallowsms = hipaaallowsms;
	}

	public String getHipaaallowmail() {
		return hipaaallowmail;
	}

	@XmlElement(name="hipaaallowmail")
	public void setHipaaallowmail(String hipaaallowmail) {
		this.hipaaallowmail = hipaaallowmail;
	}

	public String getSquad() {
		return squad;
	}

	@XmlElement(name="squad")
	public void setSquad(String squad) {
		this.squad = squad;
	}

	public String getFitness() {
		return fitness;
	}

	@XmlElement(name="fitness")
	public void setFitness(String fitness) {
		this.fitness = fitness;
	}

	public String getFerralsource() {
		return ferralsource;
	}

	@XmlElement(name="ferralsource")
	public void setFerralsource(String ferralsource) {
		this.ferralsource = ferralsource;
	}

	public String getUsertext() {
		return usertext;
	}
	
	@XmlElement(name="usertext")
	public void setUsertext(String usertext) {
		this.usertext = usertext;
	}

	public String getPricelevel() {
		return pricelevel;
	}
	
	@XmlElement(name="pricelevel")
	public void setPricelevel(String pricelevel) {
		this.pricelevel = pricelevel;
	}

	public String getRegdate() {
		return regdate;
	}
	
	@XmlElement(name="regdate")
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	public String getContrastart() {
		return contrastart;
	}

	@XmlElement(name="contrastart")
	public void setContrastart(String contrastart) {
		this.contrastart = contrastart;
	}

	public String getCompletead() {
		return completead;
	}

	@XmlElement(name="completead")
	public void setCompletead(String completead) {
		this.completead = completead;
	}

	public String getAdreviewed() {
		return adreviewed;
	}
	
	@XmlElement(name="adreviewed")
	public void setAdreviewed(String adreviewed) {
		this.adreviewed = adreviewed;
	}

	public String getVfc() {
		return vfc;
	}

	@XmlElement(name="vfc")
	public void setVfc(String vfc) {
		this.vfc = vfc;
	}

	
	public String getMothersname() {
		return mothersname;
	}

	@XmlElement(name="mothersname")
	public void setMothersname(String mothersname) {
		this.mothersname = mothersname;
	}

	public String getGuardianname() {
		return guardianname;
	}

	@XmlElement(name="guardianname")
	public void setGuardianname(String guardianname) {
		this.guardianname = guardianname;
	}

	
	public String getAllowimmreguse() {
		return allowimmreguse;
	}

	@XmlElement(name="allowimmreguse")
	public void setAllowimmreguse(String allowimmreguse) {
		this.allowimmreguse = allowimmreguse;
	}

	public String getAllowimminfoshare() {
		return allowimminfoshare;
	}
	
	@XmlElement(name="allowimminfoshare")
	public void setAllowimminfoshare(String allowimminfoshare) {
		this.allowimminfoshare = allowimminfoshare;
	}

	public String getAllowhealthinfoex() {
		return allowhealthinfoex;
	}

	@XmlElement(name="allowhealthinfoex")
	public void setAllowhealthinfoex(String allowhealthinfoex) {
		this.allowhealthinfoex = allowhealthinfoex;
	}

	public String getAllowpatientportal() {
		return allowpatientportal;
	}

	@XmlElement(name="allowpatientportal")
	public void setAllowpatientportal(String allowpatientportal) {
		this.allowpatientportal = allowpatientportal;
	}

	public Date getDeceaseddate() {
		return deceaseddate;
	}

	@XmlElement(name="deceaseddate")
	public void setDeceaseddate(Date deceaseddate) {
		this.deceaseddate = deceaseddate;
	}

	public String getDeceasedreason() {
		return deceasedreason;
	}

	@XmlElement(name="deceasedreason")
	public void setDeceasedreason(String deceasedreason) {
		this.deceasedreason = deceasedreason;
	}

	
	public String getSoapimportstatus() {
		return soapimportstatus;
	}

	@XmlElement(name="soapimportstatus")
	public void setSoapimportstatus(String soapimportstatus) {
		this.soapimportstatus = soapimportstatus;
	}

	public String getCmsportallogin() {
		return cmsportallogin;
	}
	
	@XmlElement(name="cmsportallogin")
	public void setCmsportallogin(String cmsportallogin) {
		this.cmsportallogin = cmsportallogin;
	}

	public String getDobts() {
		return dobts;
	}

	@XmlElement(name="DOBTS")
	public void setDobts(String dobts) {
		this.dobts = dobts;
	}

	public String getEthnicityvalue() {
		return ethnicityvalue;
	}

	@XmlElement(name="ethnicityvalue")
	public void setEthnicityvalue(String ethnicityvalue) {
		this.ethnicityvalue = ethnicityvalue;
	}

	
	public String getProfileimage() {
		return profileimage;
	}

	@XmlElement(name="profileimage")
	public void setProfileimage(String profileimage ) {
		this.profileimage = profileimage;
	}

	
	
	// ***********************************************************************
/*	public String getId() {
		return id;
	}

	@XmlElement(name="id")
	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	@XmlElement(name="title")
	public void setTitle(String title) {
		this.title = title;
	}

	public String getLanguage() {
		return language;
	}
	
	@XmlElement(name="language")
	public void setLanguage(String language) {
		this.language = language;
	}

	public String getFinancial() {
		return financial;
	}
	
	@XmlElement(name="financial")
	public void setFinancial(String financial) {
		this.financial = financial;
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

	public Date getDOB() {
		return DOB;
	}

	@XmlElement(name="DOB")
	public void setDOB(Date dOB) {
		DOB = dOB;
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
	} */
//******************************************************************************************
	
	
}
