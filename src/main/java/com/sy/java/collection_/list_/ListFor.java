package com.sy.java.collection_.list_;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 遍历
 *
 * @author lfeiyang
 * @since 2022-08-29 21:00
 */
@Slf4j
public class ListFor {
    @SuppressWarnings({"all"})
    public static void main(String[] args) {
        List<Object> list = new ArrayList<>();
        list.add("鸡");
        list.add("鸭");
        list.add("鱼");
        list.add("肉");

        // 迭代器
        log.warn("====================迭代器=================================");
        Iterator<Object> iterator = list.iterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();

            log.warn(next.toString());
        }

        // 增强for循环
        log.warn("====================增强for循环=================================");
        for (Object o : list) {
            log.warn(o.toString());
        }

        // 普通for循环
        log.warn("====================普通for循环=================================");
        for (int i = 0; i < list.size(); i++) {
            log.warn(list.get(i).toString());
        }
    }
}
