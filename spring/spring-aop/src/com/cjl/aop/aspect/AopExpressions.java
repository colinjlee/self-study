package com.cjl.aop.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AopExpressions {
	
	// * is wildcard for pointcut expressions
	// Modifiers (public, private), delcaring type (class name), 
	// throws (exceptions) are optional
	
	// Pointcut expression for entire dao package
	@Pointcut("execution(* com.cjl.aop.dao.*.*(..))")
	public void forDAOPackage() {}
	
	// Pointcut expression for getters
	@Pointcut("execution(* com.cjl.aop.dao.*.get*(..))")
	public void getters() {}
	
	// Pointcut expression for setters
	@Pointcut("execution(* com.cjl.aop.dao.*.set*(..))")
	public void setters() {}
	
	// Pointcut expression for package and exclude getters/setters
	@Pointcut("forDAOPackage() && !(getters() || setters())")
	public void packageWithoutGettersAndSetters() {}

}
