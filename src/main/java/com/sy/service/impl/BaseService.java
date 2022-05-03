package com.sy.service.impl;

import com.sy.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 通用Service业务层层实现
 *
 * @author lfeiyang
 * @since 2022-04-30 21:59
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public abstract class BaseService<T> implements IService<T> {
    @Autowired
    protected Mapper<T> mapper;

    public Mapper<T> getMapper() {
        return mapper;
    }

    @Override
    public T selectByKey(Object key) {
        return mapper.selectByPrimaryKey(key);
    }

    @Override
    @Transactional
    public int save(T entity) {
        return mapper.insert(entity);
    }

    @Override
    @Transactional
    public int updateNotNull(T entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    @Transactional
    public int updateAll(T entity) {
        return mapper.updateByPrimaryKey(entity);
    }

    @Override
    @Transactional
    public int delete(Object key) {
        return mapper.deleteByPrimaryKey(key);
    }

    @Override
    @Transactional
    public int batchDelete(List<String> list, String property, Class<T> clazz) {
        Example example = new Example(clazz);
        example.createCriteria().andIn(property, list);
        return mapper.deleteByExample(example);
    }

    @Override
    public int selectAllCount(T entity) {
        return mapper.selectCount(entity);
    }

    @Override
    public List<T> selectAll() {
        return mapper.selectAll();
    }

    @Override
    public List<T> selectByExample(Object example) {
        return mapper.selectByExample(example);
    }
}
