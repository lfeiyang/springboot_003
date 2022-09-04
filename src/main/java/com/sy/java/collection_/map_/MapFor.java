package com.sy.java.collection_.map_;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * Map遍历的六种方法
 *
 * @author lfeiyang
 * @since 2022-09-04 12:50
 */
@SuppressWarnings({"all"})
@Slf4j
public class MapFor {
    public static void main(String[] args) {
        Map<Object, Object> map = new HashMap<>();
        map.put("西游记", "施耐庵");
        map.put("红楼梦", "曹雪芹");
        map.put("三国志", "吴承恩");
        map.put("水浒传", "罗贯中");

        log.warn("keySet遍历 --> 增强for");
        Set<Object> keySet = map.keySet();
        for (Object o : keySet) {
            log.warn("key = " + o + ";map = " + map.get(o));
        }

        log.warn("keySet遍历 --> 迭代器");
        Iterator<Object> iterator = keySet.iterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();
            log.warn("key = " + next + ";map = " + map.get(next));
        }

        log.warn("entrySet --> K;V遍历");
        Set<Map.Entry<Object, Object>> entrySet = map.entrySet();
        for (Map.Entry<Object, Object> entry : entrySet) {
            log.warn("key = " + entry.getKey() + ";map = " + entry.getValue());
        }

        log.warn("entrySet --> 迭代器");
        Iterator<Map.Entry<Object, Object>> entrySetIterator = entrySet.iterator();
        while (entrySetIterator.hasNext()) {
            Map.Entry entry = (Map.Entry) entrySetIterator.next();
            log.warn("key = " + entry.getKey() + ";map = " + entry.getValue());
        }

        Collection<Object> values = map.values();
        for (Object value : values) {
            log.warn("value = " + value);
        }

        Iterator<Object> valueIterator = values.iterator();
        while(valueIterator.hasNext()){
            Object next = valueIterator.next();
            log.warn("key = " + next + ";map = " + map.get(next));
        }
    }
}
