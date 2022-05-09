package com.sy.model;

import lombok.Data;

/**
 * Es实体类
 *
 * @author lfeiyang
 * @since 2022-05-09 21:56
 */
@Data
public class Hero {
    Integer id;
    String name;
    String country;
    String birthday;
    Integer longevity;
}
