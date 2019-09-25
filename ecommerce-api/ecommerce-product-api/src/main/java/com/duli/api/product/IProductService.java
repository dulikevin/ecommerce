package com.duli.api.product;

import com.duli.api.product.vo.ProductVO;
import com.duli.entity.TProduct;
import com.dulli.commom.base.IBaseService;
import com.github.pagehelper.PageInfo;

public interface IProductService extends IBaseService<TProduct> {
    PageInfo<TProduct> page(Integer pageIndex, Integer pageSize);

    Long addVo(ProductVO productVo);
}
