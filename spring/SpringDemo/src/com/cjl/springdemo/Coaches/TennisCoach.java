package com.cjl.springdemo.Coaches;

import com.cjl.springdemo.Services.FortuneService;

public class TennisCoach implements Coach {

    private FortuneService fortuneService;

    public TennisCoach(FortuneService fortuneService) {
        this.fortuneService = fortuneService;
    }

    public String getDailyWorkout() {
        return "Practice serving 20 sets.";
    }

    public String getDailyFortune() {
        return "Here's your fortune: " + fortuneService.getFortune();
    }
}
