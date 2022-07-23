package com.sy.java.String;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试  StringTableSize
 * -XX:StringTableSize=1009
 *
 * @author lfeiyang
 * @since 2022-07-21 22:24
 */
@Slf4j
public class StringTest02 {
    public static void main(String[] args) {
        // 测试StringTableSize 参数
        log.warn("我来打个酱油");
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

            e.printStackTrace();
        }
    }
}
