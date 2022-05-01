package com.sy.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

/**
 * Redis工具类
 *
 * @author lfeiyang
 * @since 2022-05-02 3:51
 */
@Component
public class RedisCatchUtil {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * @Author lfeiyang
     * @Description 查找匹配的键集合
     * @Date 4:11 2022/5/2
     * @Param keys
     * @Return java.util.Set<java.lang.String>
     **/
    public Set<String> keys(String keys) {
        try {
            return redisTemplate.keys(keys);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
