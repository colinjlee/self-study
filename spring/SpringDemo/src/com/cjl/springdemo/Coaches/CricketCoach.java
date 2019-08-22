package com.cjl.springdemo.Coaches;

import com.cjl.springdemo.Services.FortuneService;

public class CricketCoach implements Coach {

    private FortuneService fortuneService;
    private String emailAddress;
    private String team;

    public CricketCoach() {
        System.out.println("Cricket coach no arg constructor.");
    }

    public void setFortuneService(FortuneService fortuneService) {
        System.out.println("Cricket coach set fortune.");
        this.fortuneService = fortuneService;
    }

    public void setEmailAddress(String emailAddress) {
        System.out.println("Cricket coach set email.");
        this.emailAddress = emailAddress;
    }

    public void setTeam(String team) {
        System.out.println("Cricket coach set team.");
        this.team = team;
    }

    public String getDailyWorkout() {
        return "Practice fast bowling for 15 minutes.";
    }

    public String getDailyFortune() {
        return fortuneService.getFortune();
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getTeam() {
        return team;
    }
}
