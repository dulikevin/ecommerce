package com.duli.api.search;

import com.duli.entity.TProduct;
import com.dulli.commom.pojo.PageResultBean;
import com.dulli.commom.pojo.ResultBean;

import java.util.List;

public interface ISearchService {
    ResultBean initAllData();
    List<TProduct> searchByKeyword(String keyword);
    PageResultBean<TProduct> searchByKeyword(String keyword,int pageNum,int pageSize);
    ResultBean updateById(Long id);
}
