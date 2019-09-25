package com.dulli.commom.base;

import java.util.List;

public interface IBaseDao<T> {

    int insert(T t);

    int insertSelective(T t);

    int deleteByPrimaryKey(Integer id);

    T selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(T t);

    int updateByPrimaryKey(T t);

    List<T> list();

}
