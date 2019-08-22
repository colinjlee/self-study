package com.cjl.springdemo.Apps;

import com.cjl.springdemo.Coaches.Coach;
import com.cjl.springdemo.Coaches.TrackCoach;

public class MyApp {

    public static void main(String[] args) {
        // Create object
        Coach baseballCoach = new TrackCoach();

        // Use object
        System.out.println(baseballCoach.getDailyWorkout());
    }

}
