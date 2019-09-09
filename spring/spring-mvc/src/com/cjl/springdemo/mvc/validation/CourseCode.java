package com.cjl.springdemo.mvc.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy=CourseCodeConstraintValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD } )
@Retention(RetentionPolicy.RUNTIME)
public @interface CourseCode {
	// Define default course code
	public String[] value() default {"CJL"};
	
	// Define default error message
	public String message() default "Must start with CJL";
	
	// Define default groups
	// Groups related constraints
	public Class<?>[] groups() default {};
	
	// Define default payloads
	// Provide custom details about validation failure, severity, error code, etc
	public Class<? extends Payload>[] payload() default {};
}
