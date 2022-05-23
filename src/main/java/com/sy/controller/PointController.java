package com.sy.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * [一句话注释]
 *
 * @author lfeiyang
 * @since 2022-05-21 17:11
 */
@Slf4j
@RestController
@Scope(value = "singleton")
public class PointController {
    @GetMapping("/epoint")
    public void epoint() {
        for (int i = 0; i < 1000; i++) {
            System.out.println(i);
        }
    }

    @GetMapping("/method1")
    public void method1() {
        for (int i = 0; i < 100; i++) {
            method2(i);
        }
    }

    public void method2(int i) {
        System.out.println(i);
    }

    @GetMapping("/multiThread")
    public void multiThread() {
        new Thread(() -> {
            System.out.println("哈哈哈！");
        }, "菩提树下的杨过").start();

        new Thread(() -> {
            System.out.println("嘿嘿嘿！");
        }, "天空中的飞鸟").start();

        System.out.println("天下第一");
    }
}
