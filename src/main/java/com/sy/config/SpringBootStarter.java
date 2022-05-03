package com.sy.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 启动类
 *
 * @author lfeiyang
 * @since 2022-04-23 21:54
 */
@EnableAutoConfiguration
@EnableAsync
public class SpringBootStarter {
    public static void main(String[] args) {
        SpringApplication.run(SpringConfig.class, args);
    }
}
