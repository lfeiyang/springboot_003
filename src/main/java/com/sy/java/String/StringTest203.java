package com.sy.java.String;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * -XX:StringTableSize=1009
 * 测试  StringTableSize 不同大小的性能
 *
 * @author lfeiyang
 * @since 2022-07-21 22:53
 */
@Slf4j
public class StringTest203 {
    public static void main(String[] args) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("words.txt"));
            long start = System.currentTimeMillis();
            String data;
            while ((data = br.readLine()) != null) {
                data.intern(); // 如果字符串常量池中没有对应 data 的字符串的话，则在常量池中生成
            }
            long end = System.currentTimeMillis();
            log.warn("花费的时间为：" + (end - start));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
