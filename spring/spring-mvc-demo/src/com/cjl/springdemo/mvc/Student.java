package com.cjl.springdemo.mvc;

import java.util.List;

public class Student {

	private String firstName;
	private String lastName;
	private String country;
	private String gender;
	private List<String> operatingSystems;
	
	public Student() {
		
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public void setOperatingSystems(List<String> operatingSystems) {
		this.operatingSystems = operatingSystems;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getCountry() {
		return country;
	}

	public String getGender() {
		return gender;
	}
	
	public List<String> getOperatingSystems() {
		return operatingSystems;
	}
	
}
