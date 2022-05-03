package com.sy.controller;

import com.sy.util.RedisCatchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 接口幂等控制层
 *
 * @author lfeiyang
 * @since 2022-05-03 23:33
 */
@RestController
public class TokenController {
    @Autowired
    private RedisCatchUtil redisCatchUtil;

    /**
     * 获取token
     *
     * @param url 请求地址
     * @return java.util.Map<java.lang.String, java.lang.String>
     **/
    @GetMapping("/getToken")
    public Map<String, String> getToken(@RequestParam("url") String url) {
        Map<String, String> tokenMap = new HashMap<>();

        String tokenValue = UUID.randomUUID().toString();
        tokenMap.put(url + tokenValue, tokenValue);

        //把token放到redis中，使用分布式锁的方式
        redisCatchUtil.set(url + tokenValue, tokenValue);

        return tokenMap;
    }
}
