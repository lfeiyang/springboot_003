package com.sy.java.collection_.list_;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

/**
 * ArrayList注意事项
 *
 * @author lfeiyang
 * @since 2022-08-29 21:58
 */
@Slf4j
public class ArrayListDetail {
    public static void main(String[] args) {
        ArrayList<Object> list = new ArrayList<>();
        list.add(null);
        list.add("lFeiYang");
        list.add(null);

        for (Object o : list) {
            log.warn(o == null ? null : o.toString());
        }
    }
}
