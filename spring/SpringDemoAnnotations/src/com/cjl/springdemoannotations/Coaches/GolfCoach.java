package com.cjl.springdemoannotations.Coaches;

import org.springframework.stereotype.Component;

@Component
public class GolfCoach implements Coach {

    public GolfCoach() {
        System.out.println("Golf Coach: inside default constructor");
    }

    @Override
    public String getDailyWorkout() {
        return "Practice putting for 20 minutes.";
    }

    @Override
    public String getDailyFortune() {
        return "Great day to golf";
    }
}
