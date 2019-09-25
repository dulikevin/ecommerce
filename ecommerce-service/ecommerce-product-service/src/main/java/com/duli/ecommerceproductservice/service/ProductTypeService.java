package com.duli.ecommerceproductservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.duli.api.product.IProductTypeService;
import com.duli.entity.TProductType;
import com.duli.mapper.TProductTypeMapper;
import com.dulli.commom.base.BaseServiceImpl;
import com.dulli.commom.base.IBaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Service
public class ProductTypeService extends BaseServiceImpl<TProductType> implements IProductTypeService {


    @Autowired
    private TProductTypeMapper tProductTypeMapper;


    @Override
    public IBaseDao<TProductType> getBaseDao() {
        return (IBaseDao<TProductType>) tProductTypeMapper;
    }


}
