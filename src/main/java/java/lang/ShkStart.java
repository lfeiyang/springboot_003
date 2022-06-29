package java.lang;

import lombok.extern.slf4j.Slf4j;

/**
 * 双亲委派机制保护程序安全，防止核心API被随意篡改
 *
 * @author lfeiyang
 * @since 2022-06-28 21:22
 */
@Slf4j
public class ShkStart {
    public static void main(String[] args) {
        log.error("Hello");
    }
}
