package com.sy.java.collection_.map_;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * HashMap底层机制和源码剖析
 *
 * @author lfeiyang
 * @since 2022-09-04 14:12
 */
@SuppressWarnings({"all"})
@Slf4j
public class HashMapSource {
    public static void main(String[] args) {
        HashMap map = new HashMap();
        map.put("java", 10);
        map.put("php", 10);
        map.put("java", 20);

        log.warn(map.toString());
    }
}
