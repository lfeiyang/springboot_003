package com.sy.java.collection_.collectionhome;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Objects;

/**
 * set的地址删除测试
 *
 * @author lfeiyang
 * @since 2022-09-08 22:08
 */
@Slf4j
@SuppressWarnings({"all"})
public class RemoveHome {
    public static void main(String[] args) {
        HashSet<Object> hashSet = new HashSet<>();
        Person person = new Person(1, "小明");
        hashSet.add(person);

        person.name = "小红";
        hashSet.remove(person);

        log.warn(String.valueOf(hashSet));
    }
}

class Person {
    int id;
    String name;

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        return id == person.id && Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
