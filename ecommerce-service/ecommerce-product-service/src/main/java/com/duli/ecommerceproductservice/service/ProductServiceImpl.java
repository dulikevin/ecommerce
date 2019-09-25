package com.duli.ecommerceproductservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.duli.api.product.IProductService;
import com.duli.api.product.vo.ProductVO;
import com.duli.entity.TProduct;
import com.duli.entity.TProductDesc;
import com.duli.mapper.TProductDescMapper;
import com.duli.mapper.TProductMapper;
import com.dulli.commom.base.BaseServiceImpl;
import com.dulli.commom.base.IBaseDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Service
public class ProductServiceImpl extends BaseServiceImpl<TProduct> implements IProductService {

    @Autowired
    private TProductMapper tProductMapper;
    @Autowired
    private TProductDescMapper tProductDescMapper;

    @Override
    public IBaseDao<TProduct> getBaseDao() {
        return tProductMapper;
    }

    @Override
    public PageInfo<TProduct> page(Integer pageIndex, Integer pageSize) {
        PageHelper.startPage(pageIndex,pageSize);
        List<TProduct> list = tProductMapper.list();
        PageInfo<TProduct> pageInfo = new PageInfo<TProduct>(list,3);
        return pageInfo;
    }

    @Override
    public Long addVo(ProductVO productVo) {
        TProduct product = productVo.getProduct();
        product.setFlag(true);
        tProductMapper.insertSelective(product);
        TProductDesc tProductDesc = new TProductDesc();
        tProductDesc.setpDesc(productVo.getpDesc());
        tProductDesc.setProductId(product.getId());
        tProductDescMapper.insertSelective(tProductDesc);
        return product.getId();
    }

}
