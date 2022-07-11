package com.sy.java.method;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试设置方法区大小参数的默认值
 *
 * @author lfeiyang
 * @since 2022-07-07 1:46
 */
@Slf4j
public class MethodAreaDemo {
    public static void main(String[] args) {
        log.warn("start...");

        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

            e.printStackTrace();
        }

        log.warn("end...");
    }
}
