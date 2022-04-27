package com.sy.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 配置类
 *
 * @author lfeiyang
 * @since 2022-04-23 21:53
 */
// 扫描包（不然要和其它包在同层级，否则扫描不到）
@SpringBootApplication(scanBasePackages = "com.sy")
@MapperScan(value = {"com.sy.mapper"})
public class SpringConfig {
}
