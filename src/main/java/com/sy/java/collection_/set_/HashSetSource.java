package com.sy.java.collection_.set_;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;

/**
 * HashSet底层结构和源码分析
 *
 * @author lfeiyang
 * @since 2022-08-31 23:04
 */
@Slf4j
public class HashSetSource {
    @SuppressWarnings({"all"})
    public static void main(String[] args) {
        HashSet hashSet = new HashSet();
        //第 1 次 add
        hashSet.add("java");
        //第 2 次 add
        hashSet.add("php");
        hashSet.add("java");

        log.warn("set=" + hashSet);
    }
}
