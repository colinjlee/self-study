package com.cjl.springdemo.Coaches;

import com.cjl.springdemo.Services.FortuneService;

public class BaseballCoach implements Coach {
    private FortuneService fortuneService;

    public BaseballCoach(FortuneService fortuneService) {
        this.fortuneService = fortuneService;
    }

    public String getDailyWorkout() {
        return "Spend 30 minutes on batting practice.";
    }

    public String getDailyFortune() {
        return fortuneService.getFortune();
    }
}
