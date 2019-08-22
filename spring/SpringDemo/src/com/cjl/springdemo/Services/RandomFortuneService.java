package com.cjl.springdemo.Services;

import java.util.Random;

public class RandomFortuneService implements FortuneService {

    private Random random;
    private String[] fortunes;

    public RandomFortuneService() {
        random = new Random();
        fortunes = new String[3];
        fortunes[0] = "You'll soon encounter love!";
        fortunes[1] = "Stay inside. It's a bad day.";
        fortunes[2] = "Today is a good day.";
    }

    public String getFortune() {
        return fortunes[random.nextInt(fortunes.length)];
    }
}
