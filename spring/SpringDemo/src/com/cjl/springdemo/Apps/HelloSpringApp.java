package com.cjl.springdemo.Apps;

import com.cjl.springdemo.Coaches.Coach;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HelloSpringApp {

    public static void main(String[] args) {
        // Load spring config file
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // Retrieve bean from spring container
        Coach coach = context.getBean("myCoach", Coach.class);

        // Call methods on bean
        System.out.println(coach.getDailyWorkout());

        // Call fortune method
        System.out.println(coach.getDailyFortune());

        // Close context
        context.close();
    }

}
