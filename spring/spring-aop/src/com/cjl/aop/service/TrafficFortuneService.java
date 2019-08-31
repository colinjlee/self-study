package com.cjl.aop.service;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

@Component
public class TrafficFortuneService implements FortuneService {

	@Override
	public String getFortune() {
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "Heavy traffic today";
	}

	@Override
	public String getFortune(boolean exception) {
		if (exception) {
			throw new RuntimeException("Traffic exception");
		}
		
		return "Heavy traffic today";
	}

	
}
