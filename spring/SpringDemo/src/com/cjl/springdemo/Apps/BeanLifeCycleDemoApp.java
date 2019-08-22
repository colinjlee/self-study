package com.cjl.springdemo.Apps;

import com.cjl.springdemo.Coaches.Coach;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanLifeCycleDemoApp {

    public static void main(String[] args) {

        // Load config file
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("beanLifeCycle-applicationContext.xml");

        // Retrieve bean from spring container
        Coach coach = context.getBean("myCoach", Coach.class);

        // Get workout
        System.out.println(coach.getDailyWorkout());

        // Close context
        context.close();
    }
}
