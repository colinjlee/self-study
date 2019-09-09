package com.cjl.springdemoannotations.Apps;

import com.cjl.springdemoannotations.Coaches.Coach;
import com.cjl.springdemoannotations.Coaches.FootballCoach;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AnnotationDemoApp {

    public static void main(String[] args) {

        // Read spring config file
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        // Note: Beans are created when the context is started up
        // No defined order of creating beans
        // Dependencies are created first then injected
        // Get bean from spring container
        Coach coach = context.getBean("theTennisCoach", Coach.class);
        FootballCoach coach2 = context.getBean("footballCoach", FootballCoach.class);
        Coach coach3 = context.getBean("golfCoach", Coach.class);

        // Call bean methods
        System.out.println("\n" + coach.getDailyWorkout());
        System.out.println(coach.getDailyFortune() + "\n");

        System.out.println(coach2.getDailyWorkout());
        System.out.println(coach2.getDailyFortune());
        System.out.println(coach2.getEmailAddress());
        System.out.println(coach2.getTeamName() + "\n");

        // Close context
        context.close();
    }
}
