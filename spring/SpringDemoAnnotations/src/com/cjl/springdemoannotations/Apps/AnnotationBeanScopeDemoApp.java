package com.cjl.springdemoannotations.Apps;

import com.cjl.springdemoannotations.Coaches.Coach;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AnnotationBeanScopeDemoApp {

    public static void main(String[] args) {
        // Load spring config file
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // Retrieve bean
        Coach coach = context.getBean("theTennisCoach", Coach.class);
        Coach coach2 = context.getBean("theTennisCoach", Coach.class);

        // Check if they are the same object
        boolean res = (coach == coach2);

        System.out.println("Same object? " + res);
        System.out.println("Coach 1: " + coach);
        System.out.println("Coach 2: " + coach2);

        // Close context
        context.close();
    }
}
