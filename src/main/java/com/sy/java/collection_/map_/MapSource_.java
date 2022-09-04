package com.sy.java.collection_.map_;

import java.util.HashMap;
import java.util.Map;

/**
 * Map底层结构和源码分析
 *
 * @author lfeiyang
 * @since 2022-09-04 11:33
 */
@SuppressWarnings({"all"})
public class MapSource_ {
    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("no1", "hello");
        map.put("no2", "张无忌");
        map.put(new Car(), new Person());
    }
}

@SuppressWarnings({"all"})
class Car {

}

@SuppressWarnings({"all"})
class Person {

}