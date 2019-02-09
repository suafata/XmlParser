package com.suafata.xmlParser.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ForkJoinPool;

@Configuration
public class ApplicationConfig {

    @Value("${xml.max.parallel.degree}")
    private Integer degree;

    @Bean
    public ForkJoinPool getPool(){
        if(degree != null) {
            return new ForkJoinPool(degree);
        } else {
            return new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        }
    }

}
