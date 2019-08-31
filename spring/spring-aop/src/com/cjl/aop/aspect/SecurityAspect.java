package com.cjl.aop.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class SecurityAspect {

	@Before("com.cjl.aop.aspect.AopExpressions.packageWithoutGettersAndSetters()")
	public void checkSecurityClearance() {
		System.out.println(">> Checking security clearances... @Before advice");
	}
}
