package com.cjl.springdemoannotations.Coaches;

import com.cjl.springdemoannotations.Services.FortuneService;
import org.springframework.beans.factory.annotation.Value;

public class SwimCoach implements Coach {

    private FortuneService fortuneService;

    @Value("${swim.email}")
    private String email;

    @Value("${swim.team}")
    private String team;

    public SwimCoach(FortuneService fortuneService) {
        System.out.println("Swim Coach: inside default constructor");
        this.fortuneService = fortuneService;
    }

    @Override
    public String getDailyWorkout() {
        return "Swim 10 laps";
    }

    @Override
    public String getDailyFortune() {
        return fortuneService.getFortune();
    }

    public String getEmail() {
        return email;
    }

    public String getTeam() {
        return team;
    }
}
