package com.sy.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * spring工具类 方便在非spring管理环境中获取bean
 *
 * @author lfeiyang
 * @since 2022-05-03 2:01
 */
@Component
public class SpringUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringUtil.applicationContext == null) {
            SpringUtil.applicationContext = applicationContext;
        }
    }

    /**
     * 通过字节码获取
     *
     * @param beanClass 类字节码
     * @return T
     */
    public static <T> T getBean(Class<T> beanClass) {
        return applicationContext.getBean(beanClass);
    }

    /***
     * 通过BeanName获取
     *
     * @param beanName 类名
     * @return T
     **/
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

    /**
     * 通过beanName和字节码获取
     *
     * @param name      类名
     * @param beanClass 类字节码
     * @return T
     */
    public static <T> T getBean(String name, Class<T> beanClass) {
        return applicationContext.getBean(name, beanClass);
    }

}
