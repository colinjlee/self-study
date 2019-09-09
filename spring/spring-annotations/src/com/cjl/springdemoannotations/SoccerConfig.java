package com.cjl.springdemoannotations;

import com.cjl.springdemoannotations.Coaches.SoccerCoach;
import com.cjl.springdemoannotations.Services.FortuneService;
import com.cjl.springdemoannotations.Services.MediocreFortune;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SoccerConfig {

    @Bean
    public FortuneService mediocreFortune() {
        return new MediocreFortune();
    }

    @Bean
    public SoccerCoach soccerCoach() {
        return new SoccerCoach(mediocreFortune());
    }
}
