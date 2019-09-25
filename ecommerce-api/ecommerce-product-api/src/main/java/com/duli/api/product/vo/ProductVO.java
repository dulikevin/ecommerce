package com.duli.api.product.vo;

import com.duli.entity.TProduct;

import java.io.Serializable;

public class ProductVO implements Serializable {

    private TProduct product;

    private String pDesc;

    public TProduct getProduct() {
        return product;
    }

    public void setProduct(TProduct product) {
        this.product = product;
    }

    public String getpDesc() {
        return pDesc;
    }

    public void setpDesc(String pDesc) {
        this.pDesc = pDesc;
    }
}
