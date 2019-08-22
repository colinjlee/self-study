package com.cjl.springdemoannotations.Services;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomFortuneService implements FortuneService {

    private Random random;
    private String[] fortunes = {"Good fortune", "Bad fortune", "Great fortune"};

    public RandomFortuneService() {
        random = new Random();
    }

    @Override
    public String getFortune() {
        return fortunes[random.nextInt(fortunes.length)];
    }
}
