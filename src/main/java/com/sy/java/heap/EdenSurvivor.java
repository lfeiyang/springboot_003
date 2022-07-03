package com.sy.java.heap;

import lombok.extern.slf4j.Slf4j;

/**
 * 新生代、老年代比例
 *
 * @author lfeiyang
 * @since 2022-07-03 19:47
 */
@Slf4j
public class EdenSurvivor {
    public static void main(String[] args) {
        log.warn("我是来打个酱油的！");

        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

            e.printStackTrace();
        }
    }
}
