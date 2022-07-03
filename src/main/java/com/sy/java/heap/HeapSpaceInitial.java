package com.sy.java.heap;

import lombok.extern.slf4j.Slf4j;

/**
 * 堆内存演示
 *
 * @author lfeiyang
 * @since 2022-07-03 17:31
 */
@Slf4j
public class HeapSpaceInitial {
    public static void main(String[] args) {
        // 返回Java虚拟机中的堆内存总量
        long initialMemory = Runtime.getRuntime().totalMemory() / 1024 / 1024;
        // 返回Java虚拟机试图使用的最大堆内存量
        long maxMemory = Runtime.getRuntime().maxMemory() / 1024 / 1024;

        log.warn("-Xms：" + initialMemory + "M");
        log.warn("-Xmx：" + maxMemory + "M");

        log.warn("系统内存大小为：" + initialMemory * 64.0 / 1024 + "G");
        log.warn("系统内存大小为：" + maxMemory  * 4.0 / 1024 +  "G");

        //try {
        //    Thread.sleep(1000000);
        //} catch (Exception e) {
        //    Thread.currentThread().interrupt();
        //
        //    e.printStackTrace();
        //}
    }
}
