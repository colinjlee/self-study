package com.cjl.aop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.cjl.aop.service.FortuneService;

// Around advice executes before and after method
// Can handle exceptions so main app doesn't deal with any
public class AroundApp {

	public static void main(String[] args) {
		
		// Read spring config class
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
		
		// Get bean from spring container
		FortuneService fortuneService = context.getBean("trafficFortuneService", FortuneService.class);
		
		// Call business logic methods
		String fortune = fortuneService.getFortune();
		
		System.out.println("Starting around app");
		System.out.println("Fortune: " + fortune);
		System.out.println("Finished");
		
		// Close context
		context.close();
	}

}
