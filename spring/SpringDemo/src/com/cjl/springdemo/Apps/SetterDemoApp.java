package com.cjl.springdemo.Apps;

import com.cjl.springdemo.Coaches.CricketCoach;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SetterDemoApp {

    public static void main(String[] args) {
        // Load spring config file
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // Retrieve bean from spring container
        CricketCoach coach = context.getBean("myCricketCoach", CricketCoach.class);

        // Call methods on bean
        System.out.println(coach.getDailyWorkout());
        System.out.println(coach.getDailyFortune());
        System.out.println(coach.getEmailAddress());
        System.out.println(coach.getTeam());

        // Close context
        context.close();

    }
}
