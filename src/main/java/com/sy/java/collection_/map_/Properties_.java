package com.sy.java.collection_.map_;

import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

/**
 * 基本使用
 *
 * @author lfeiyang
 * @since 2022-09-06 22:30
 */
@SuppressWarnings({"all"})
@Slf4j
public class Properties_ {
    public static void main(String[] args) {

        // 秋刀鱼解读
        //1. Properties 继承 Hashtable --> 随机存储
        //2. 可以通过 k-v 存放数据，当然 key 和 value 不能为 null

        Properties properties = new Properties();
        //增
        //properties.put(null,"abc");//抛出 空指针异常
        //properties.put("abc",null);//抛出 空指针异常
        properties.put("john", 100);// k-v
        properties.put("林丹", 99);
        properties.put("李宗伟", 88);
        properties.put("秋刀鱼", 1);
        properties.put("秋刀鱼", 77); //如果有相同的 key， value 被替换

        log.warn("properties=" + properties);

        //删
        properties.remove("秋刀鱼");
        log.warn("properties=" + properties);

        //改
        properties.put("林丹", 123);
        log.warn("properties=" + properties);

        //查：通过 key 获取对应 value 值
        log.warn("==> " + properties.get("林丹")); //123
    }

}
