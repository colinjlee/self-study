package com.cjl.aop.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(3)
public class AnalyticsAspect {
	
	@Before("com.cjl.aop.aspect.AopExpressions.packageWithoutGettersAndSetters()")
	public void performAPIAnalytics() {
		System.out.println(">> Performing API Analytics... @Before advice");
	}

}
