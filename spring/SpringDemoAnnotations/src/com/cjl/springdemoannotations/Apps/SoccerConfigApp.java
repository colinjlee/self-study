package com.cjl.springdemoannotations.Apps;

import com.cjl.springdemoannotations.Coaches.Coach;
import com.cjl.springdemoannotations.SoccerConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SoccerConfigApp {

    public static void main(String[] args) {

        // Load java config class
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SoccerConfig.class);

        // Retrieve bean
        Coach coach = context.getBean("soccerCoach", Coach.class);

        // Bean methods
        System.out.println(coach.getDailyWorkout());
        System.out.println(coach.getDailyFortune());

        // Close context
        context.close();
    }
}
