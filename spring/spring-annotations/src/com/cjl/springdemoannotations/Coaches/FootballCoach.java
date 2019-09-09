package com.cjl.springdemoannotations.Coaches;

import com.cjl.springdemoannotations.Services.FortuneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
// @Scope("prototype")
public class FootballCoach implements Coach {

    @Autowired
    @Qualifier("readFortuneService")
    private FortuneService fortuneService;

    @Value("${football.email}")
    private String emailAddress;

    @Value("${football.team}")
    private String teamName;

    public FootballCoach() {
        System.out.println("Football Coach: inside default constructor");
    }

    @Override
    public String getDailyWorkout() {
        return "Run 10 laps.";
    }

    @Override
    public String getDailyFortune() {
        return fortuneService.getFortune();
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getTeamName() {
        return teamName;
    }
}
