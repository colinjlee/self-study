package com.cjl.aop.aspect;

import java.util.List;
import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.cjl.aop.Account;

@Aspect
@Component
@Order(2)
public class LoggingAspect {
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Before("com.cjl.aop.aspect.AopExpressions.packageWithoutGettersAndSetters()")
	public void beforeAddAccount(JoinPoint joinPoint) {
		logger.info(">> Logging... @Before advice");
		
		// Display method signature
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		logger.info(">> Logging... Method: " + methodSignature);
		
		// Display method arguments
		Object[] args = joinPoint.getArgs();
		
		logger.info(">> Logging... Args: ");
		for (Object arg : args) {
			logger.info("\t" + arg);
			
			if (arg instanceof Account) {
				Account account = (Account) arg;
				
				logger.info("\t\tAccount name: " + account.getName());
				logger.info("\t\tAccount level: " + account.getLevel());
			}
		}
	}
	
	@AfterReturning(
			pointcut="execution(* com.cjl.aop.dao.AccountDAO.findAccounts(..))",
			returning="result"
			)
	public void afterReturningFindAccounts(JoinPoint joinPoint, List<Account> result) {
		String method = joinPoint.getSignature().toShortString();
		
		logger.info((">> Logging... @AfterReturning advice on method: " + method));
		logger.info("\tResult: " + result);
		
		// Process data
		logger.info("\t>> Converting account names to uppercase... <<");
		convertAccountNamesToUpperCase(result);
		
		logger.info("\tNew Result: " + result);
	}
	
	@AfterThrowing(
			pointcut="execution(* com.cjl.aop.dao.AccountDAO.findAccounts(..))",
			throwing="exceptionThrown"
			)
	public void afterThrowingException(JoinPoint joinPoint, Exception exceptionThrown) {
		String method = joinPoint.getSignature().toShortString();
		
		logger.info(">> Logging... @AfterThrowing advice on method: " + method);
		logger.info("\tThrown exception: " + exceptionThrown);
	}
	
	@After("execution(* com.cjl.aop.dao.AccountDAO.findAccounts(..))")
	public void afterFinallyFindAccounts(JoinPoint joinPoint) {
		String method = joinPoint.getSignature().toShortString();
		
		logger.info(">> Logging... @After advice on method: " + method);
	}
	
	// Can rethrow exceptions to main app or handle them
	@Around("execution(* com.cjl.aop.service.*.getFortune(..))")
	public Object aroundGetFortune(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		String method = proceedingJoinPoint.getSignature().toShortString();
		logger.info(">> Logging... @Around advice on method: " + method);
		
		// Time method
		long start = System.currentTimeMillis();
		Object res = null;
		
		try {
			res = proceedingJoinPoint.proceed();
		} catch (Throwable e) {
			logger.warning(">> Logging @Around advice exception: " + e);
			
			// Rethrow exception
			throw e;
		}
		
		long end = System.currentTimeMillis();
		logger.info(">> Method duration: " + (end - start) / 1000.0 + "s");
		
		return res;
	}

	private void convertAccountNamesToUpperCase(List<Account> result) {
		for (Account acc : result) {
			acc.setName(acc.getName().toUpperCase());
		}
	}
}
