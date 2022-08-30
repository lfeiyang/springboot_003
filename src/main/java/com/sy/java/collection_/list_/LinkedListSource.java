package com.sy.java.collection_.list_;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

/**
 * LinkedList底层结构和源码分析
 *
 * @author lfeiyang
 * @since 2022-08-30 20:57
 */
@Slf4j
public class LinkedListSource {
    public static void main(String[] args) {
        LinkedList<Object> linkedList = new LinkedList<>();

        linkedList.add("1");
        linkedList.add("2");
        linkedList.add("3");

        log.warn(linkedList.toString());

        Object o = linkedList.remove();

        log.warn("remove ====>" + o);
        log.warn(linkedList.toString());
    }
}
