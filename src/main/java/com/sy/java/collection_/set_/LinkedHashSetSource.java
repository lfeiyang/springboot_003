package com.sy.java.collection_.set_;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * LinkedHashSet底层结构和源码分析
 *
 * @author lfeiyang
 * @since 2022-09-04 10:16
 */
@SuppressWarnings({"all"})
@Slf4j
public class LinkedHashSetSource {
    public static void main(String[] args) {
        //分析一下LinkedHashSet的底层机制
        Set set = new LinkedHashSet();
        set.add(new String("AA"));
        set.add(456);
        set.add(456);
        set.add(new Customer("刘", 1001));
        set.add(123);
        set.add("SP");

        log.warn("set=" + set);
    }
}

@SuppressWarnings({"all"})
class Customer {
    private String name;
    private int no;

    public Customer(String name, int no) {
        this.name = name;
        this.no = no;
    }
}
