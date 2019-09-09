package com.cjl.springdemoannotations.Coaches;

import com.cjl.springdemoannotations.Services.FortuneService;

public class SoccerCoach implements Coach {

    private FortuneService fortuneService;

    public SoccerCoach(FortuneService fortuneService) {
        this.fortuneService = fortuneService;
    }

    @Override
    public String getDailyWorkout() {
        return "Practice dribbling";
    }

    @Override
    public String getDailyFortune() {
        return fortuneService.getFortune();
    }
}
