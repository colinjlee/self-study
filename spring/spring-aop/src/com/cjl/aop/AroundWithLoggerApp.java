package com.cjl.aop;

import java.util.logging.Logger;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.cjl.aop.service.FortuneService;

// Around advice executes before and after method
public class AroundWithLoggerApp {
	
	private static Logger logger = Logger.getLogger(AroundWithLoggerApp.class.getName());

	public static void main(String[] args) {
		
		// Read spring config class
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
		
		// Get bean from spring container
		FortuneService fortuneService = context.getBean("trafficFortuneService", FortuneService.class);
		
		// Call business logic methods
		boolean exception = true;
		String fortune = fortuneService.getFortune(exception);
		
		logger.info("Starting around app");
		logger.info("Fortune: " + fortune);
		logger.info("Finished");
		
		// Close context
		context.close();
	}

}
