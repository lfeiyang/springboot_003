package com.sy.java.annotation;

import com.sy.model.AnnotationUser;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取泛型
 *
 * @author lfeiyang
 * @since 2022-08-21 23:24
 */
@Slf4j
public class GenericsTest {
    public void test01 (Map<String, AnnotationUser> map, List<AnnotationUser> list){
        log.warn("test01");
    }
    public Map<String, AnnotationUser> test02(){
        log.warn("test02");

        return new HashMap<>();
    }

    public static void main(String[] args) throws NoSuchMethodException {
        Method method = GenericsTest.class.getMethod("test01",Map.class,List.class);
        Type[] genericParameterTypes= method.getGenericParameterTypes();
        for (Type genericParameterType : genericParameterTypes) {
            log.warn("参数范型"+genericParameterType);
            if (genericParameterType instanceof ParameterizedType){
                Type[] actualTypeAnguments=((ParameterizedType)genericParameterType).getActualTypeArguments();
                for (Type actualTypeAngument : actualTypeAnguments) {
                    log.warn("返回值泛型"+actualTypeAngument);
                }
            }
        }

        log.warn("====================================================================================================");

        Method method1 = GenericsTest.class.getMethod("test02",null);
        Type getGenericReturnType= method1.getGenericReturnType();
        if (getGenericReturnType instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) getGenericReturnType).getActualTypeArguments();
            for (Type actualTypeArgument : actualTypeArguments) {
                log.warn("返回值泛型" + actualTypeArgument);
            }
        }
    }
}
