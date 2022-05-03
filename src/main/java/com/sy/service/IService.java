package com.sy.service;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 通用Service业务接口层
 *
 * @author lfeiyang
 * @since 2022-04-30 21:55
 */
@Service
public interface IService<T> {
    T selectByKey(Object key);

    int save(T entity);

    int updateNotNull(T entity);

    int updateAll(T entity);

    int delete(Object key);

    int batchDelete(List<String> list, String property, Class<T> clazz);

    int selectAllCount(T entity);

    List<T> selectAll();

    List<T> selectByExample(Object example);
}
