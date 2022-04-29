package com.sy.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 启动类
 *
 * @author lfeiyang
 * @since 2022-04-23 21:54
 */
@EnableAutoConfiguration
public class SpringBootStarter {
    public static void main(String[] args) {

        SpringApplication.run(SpringConfig.class, args);
    }
}
