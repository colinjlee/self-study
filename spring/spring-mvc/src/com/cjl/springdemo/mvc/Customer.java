package com.cjl.springdemo.mvc;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.cjl.springdemo.mvc.validation.CourseCode;

public class Customer {

	private String firstName;
	
	@NotNull(message="Required")
	@Size(min=1, message="Required")
	private String lastName;
	
	@NotNull(message="Required")
	@Min(value=0, message="Must be between 0 and 10")
	@Max(value=10, message="Must be between 0 and 10")
	private Integer freePasses;
	
	@Pattern(regexp="^[a-zA-Z0-9]{5}", message="must be 5 alphanumeric characters")
	private String postalCode;
	
	@CourseCode(value={"CJL", "TEST"}, message="Must start with CJL or TEST")
	private String courseCode;

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
	
	public Integer getFreePasses() {
		return freePasses;
	}
	
	public String getPostalCode() {
		return postalCode;
	}
	
	public String getCourseCode() {
		return courseCode;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setFreePasses(Integer freePasses) {
		this.freePasses = freePasses;
	}
	
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	
}
