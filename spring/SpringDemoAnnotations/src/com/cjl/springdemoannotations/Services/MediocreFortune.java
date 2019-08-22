package com.cjl.springdemoannotations.Services;

public class MediocreFortune implements FortuneService {
    @Override
    public String getFortune() {
        return "Your day will be ok";
    }
}
