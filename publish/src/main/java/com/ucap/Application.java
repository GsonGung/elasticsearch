package com.ucap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * Create with IntelliJ IDEA
 * User:曹立华
 * Date:2017/9/19
 */
@SpringBootApplication
@PropertySource("param.properties")
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
