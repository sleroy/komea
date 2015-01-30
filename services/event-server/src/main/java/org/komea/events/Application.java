package org.komea.events;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = {"org.komea"})
@EnableTransactionManagement
@EnableAutoConfiguration
public class Application {

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
