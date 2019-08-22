package com.cjl.springdemoannotations.Apps;

import com.cjl.springdemoannotations.Coaches.Coach;
import com.cjl.springdemoannotations.Coaches.SwimCoach;
import com.cjl.springdemoannotations.SportConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class JavaConfigDemoApp {

    public static void main(String[] args) {

        // Read java spring config class
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(SportConfig.class);

        // Get bean from spring container
        SwimCoach coach = context.getBean("swimCoach", SwimCoach.class);

        // Call bean methods
        System.out.println("\n" + coach.getDailyWorkout());
        System.out.println(coach.getDailyFortune() + "\n");
        System.out.println(coach.getEmail());
        System.out.println(coach.getTeam());

        // Close context
        context.close();
    }
}
