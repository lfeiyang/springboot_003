package java.lang;

import lombok.extern.slf4j.Slf4j;

/**
 * 双亲委派机制避免类的冲服加载
 * 双亲委派机制保护程序安全，防止核心API被随意篡改
 *
 * @author lfeiyang
 * @since 2022-06-28 21:33
 */
@Slf4j
public class StringTest {
    static {
        log.error("这是自定义String的静态代码块");
    }

    public static void main(String[] args) {
        log.error("Hello String");
    }
}
