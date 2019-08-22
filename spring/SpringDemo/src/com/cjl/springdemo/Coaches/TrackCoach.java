package com.cjl.springdemo.Coaches;

import com.cjl.springdemo.Services.FortuneService;

public class TrackCoach implements Coach {

    private FortuneService fortuneService;

    public TrackCoach() {

    }

    public TrackCoach(FortuneService fortuneService) {
        this.fortuneService = fortuneService;
    }

    public String getDailyWorkout() {
        return "Spend an hour jogging.";
    }

    public String getDailyFortune() {
        return "Just do it! " + fortuneService.getFortune();
    }

    // Init method
    public void doMyStartupStuff() {
        System.out.println("Track Coach: inside startup stuff");
    }

    // Destroy method
    public void doMyCleanupStuff() {
        System.out.println("Track Coach: inside destroy stuff");
    }

}
