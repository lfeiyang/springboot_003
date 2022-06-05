package com.sy.java.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 初始化实战分析
 *
 * @author lfeiyang
 * @since 2022-05-31 21:54
 */
@Slf4j
@RestController
public class Book {
    @GetMapping("/book")
    public void book() {
        staticFunction();
    }

    static Book book = new Book();

    static {
        log.info("书的静态代码块");
    }

    {
        log.info("书的普通代码块");
    }

    Book() {
        log.info("书的构造方法");
        log.info("price=" + price + ",amount=" + amount);
    }

    public static void staticFunction() {
        log.info("书的静态方法");
    }

    int price = 110;
    static int amount = 112;
}
