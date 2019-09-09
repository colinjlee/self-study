package com.cjl.spring.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CRMLoggingAspect {

	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Pointcut("execution(* com.cjl.spring.controller.*.*(..))")
	private void forControllerPackage() {}
	
	@Pointcut("execution(* com.cjl.spring.service.*.*(..))")
	private void forServicePackage() {}
	
	@Pointcut("execution(* com.cjl.spring.dao.*.*(..))")
	private void forDAOPackage() {}
	
	@Pointcut("forControllerPackage() || forServicePackage() || forDAOPackage()")
	private void forAppFlow() {}
	
	@Before("forAppFlow()")
	public void before(JoinPoint joinPoint) {
		String method = joinPoint.getSignature().toShortString();
		
		logger.info(">> Logging... @Before advice on method: " + method);
		
		// Display arguments of methods
		Object[] args = joinPoint.getArgs();
		
		for (Object arg : args) {
			logger.info("\t>> " + arg);
		}
	}
	
	@AfterReturning(
		pointcut="forAppFlow()",
		returning="result"
		)
	public void afterReturning(JoinPoint joinPoint, Object result) {
		String method = joinPoint.getSignature().toShortString();
		logger.info(">> Logging... @After advice from method: " + method);
		
		logger.info("\t>> Result: " + result);
	}
}
