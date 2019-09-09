package com.cjl.springbootintro.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class MyRestController {

    @Value("${coach.name}")
    private String coachName;

    @Value("${team.name}")
    private String teamName;

    @GetMapping("/")
    public String sayHello() {
        return "Hello world! Time on server is " + LocalDateTime.now();
    }

    @GetMapping("/team")
    public String getTeam() {
        return "Coach: " + coachName + ", Team: " + teamName;
    }

    @GetMapping("/weather")
    public String getWeather() {
        return "rain";
    }

    @GetMapping("/fortune")
    public String getFortune() {
        return "ok day";
    }
}
