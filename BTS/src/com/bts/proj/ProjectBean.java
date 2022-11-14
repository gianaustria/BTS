package com.bts.proj;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class ProjectBean {
	private int id;
	private String fName;
	private String lName;
	private String email;
	private String password;
	private String mobileNum;
	private String bday;
	private String gender;
	
	public ProjectBean(int id, String fName, String lName, String email, String password, String mobileNum, String bday, String gender) {
		this.id = id;
		this.fName = fName;
		this.lName = lName;
		this.email = email;
		this.password = password;
		this.mobileNum = mobileNum;
		this.bday = bday;
		this.gender = gender;
		
	}
	
	public ProjectBean () {
		
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBday() {
		return bday;
	}
	public void setBday(String bday) {
		this.bday = bday;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMobileNum() {
		return mobileNum;
	}
	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	

}
