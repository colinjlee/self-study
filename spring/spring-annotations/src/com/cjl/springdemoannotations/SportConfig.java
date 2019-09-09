package com.cjl.springdemoannotations;

import com.cjl.springdemoannotations.Coaches.Coach;
import com.cjl.springdemoannotations.Coaches.SwimCoach;
import com.cjl.springdemoannotations.Services.FortuneService;
import com.cjl.springdemoannotations.Services.HappyFortuneService;
import com.cjl.springdemoannotations.Services.SadFortuneService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
// @ComponentScan("com.cjl.springdemoannotations")
@PropertySource("sport.properties")
public class SportConfig {

    @Bean
    public FortuneService happyFortuneService() {
        return new HappyFortuneService();
    }

    @Bean
    public FortuneService sadFortuneService() {
        return new SadFortuneService();
    }

    @Bean
    public Coach swimCoach() {
        return new SwimCoach(sadFortuneService());
    }
}
