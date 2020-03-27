package org.example.configBean;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
@EnableAsync
public class CustomBeanThread {

    @Bean
    public ScheduledExecutorService httpAsync(){
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(20);
        return executor;
    }
}
