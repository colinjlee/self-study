package com.cjl.aop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration					// Spring pure java config
@EnableAspectJAutoProxy 		// Spring AOP proxy support
@ComponentScan("com.cjl.aop")	// Component scanning for components/aspects
public class Config {
	
	

}
