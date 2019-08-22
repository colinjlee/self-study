package com.cjl.springdemoannotations.Coaches;

import com.cjl.springdemoannotations.Services.FortuneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

// Bean ID defaults to class name with lowercase first letter
// Special case of first 2 or more characters being uppercase is left alone
@Component("theTennisCoach")
// @Scope("prototype")
public class TennisCoach implements Coach {

    @Autowired
    @Qualifier("happyFortuneService")
    private FortuneService fortuneService;

    public TennisCoach() {
        System.out.println("Tennis Coach: inside default constructor");
    }

    @PostConstruct
    public void doStartupStuff() {
        System.out.println("Startup stuff");
    }

    @PreDestroy
    public void doCleanupStuff() {
        System.out.println("Cleanup stuff");
    }

    @Override
    public String getDailyWorkout() {
        return "Practice serving for 30 minutes.";
    }

    @Override
    public String getDailyFortune() {
        return fortuneService.getFortune();
    }
}
