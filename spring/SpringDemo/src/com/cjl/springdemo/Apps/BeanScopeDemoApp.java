package com.cjl.springdemo.Apps;

import com.cjl.springdemo.Coaches.Coach;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanScopeDemoApp {

    public static void main(String[] args) {

        // Load config file
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("beanScope-applicationContext.xml");

        // Retrieve bean from spring container
        Coach coach = context.getBean("myCoach", Coach.class);
        Coach coach2 = context.getBean("myCoach", Coach.class);

        // Check if they are the same
        boolean result = (coach == coach2);

        System.out.println("Same object? - " + result);
        System.out.println("Coach1: " + coach);
        System.out.println("Coach2: " + coach2);
    }
}
