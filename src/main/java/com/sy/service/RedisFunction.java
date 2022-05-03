package com.sy.service;

/**
 * Jedis接口
 *
 * @author lfeiyang
 * @since 2022-05-04 1:47
 */
public interface RedisFunction<T, E> {
    Object callback(E jedis);
}
