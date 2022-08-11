package com.sy.java.annotation;

import com.sy.annotation.FieldYang;
import com.sy.annotation.TableYang;
import com.sy.model.AnnotationUser;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * 通过注解和表测试类和表映射
 *
 * @author lfeiyang
 * @since 2022-08-11 23:33
 */
@Slf4j
public class TableAnnotation {
    public static void main(String[] args) throws NoSuchFieldException {
        Class<? extends AnnotationUser> userClass = AnnotationUser.class;

        TableYang tableYang = userClass.getDeclaredAnnotation(TableYang.class);
        log.warn(tableYang.value());

        Field id = userClass.getDeclaredField("id");
        FieldYang fieldYang = id.getDeclaredAnnotation(FieldYang.class);
        log.warn(fieldYang.columnName());
        log.warn(fieldYang.type());
        log.warn(String.valueOf(fieldYang.length()));
    }
}
