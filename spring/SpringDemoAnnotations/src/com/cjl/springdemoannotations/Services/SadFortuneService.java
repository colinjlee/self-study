package com.cjl.springdemoannotations.Services;

public class SadFortuneService implements FortuneService {
    @Override
    public String getFortune() {
        return "Today is a bad day";
    }
}
