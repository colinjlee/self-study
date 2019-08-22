package com.cjl.springdemo.mvc.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CourseCodeConstraintValidator implements ConstraintValidator<CourseCode, String> {

	private String[] coursePrefixes;
	
	public void initialize(CourseCode courseCode) {
		coursePrefixes = courseCode.value();
	}
	
	@Override
	// Context can have additional info about error
	public boolean isValid(String code, ConstraintValidatorContext constraintValidatorContext) {
		boolean res = code == null;
		
		if (!res) {
			for (String prefix : coursePrefixes) {
				if (code.startsWith(prefix)) {
					res = true;
					break;
				}
			}
		}
		
		return res;
	}

}
