package com.sy.model;

import com.sy.annotation.FieldYang;
import com.sy.annotation.TableYang;

/**
 * 映射表注解类
 *
 * @author lfeiyang
 * @since 2022-08-11 23:25
 */
@TableYang(value = "user")
public class AnnotationUser {
    @FieldYang(columnName = "id", type = "String", length = 50)
    private String id;

    @FieldYang(columnName = "name", type = "String", length = 50)
    private String name;

    @FieldYang(columnName = "age", type = "int", length = 50)
    private int age;

    public AnnotationUser() {
    }

    public AnnotationUser(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "AnnotationUser{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
