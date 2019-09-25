package com.duli.ecommercebackground.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.duli.api.product.IProductTypeService;
import com.duli.entity.TProductType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@RequestMapping("productType")
@Controller
public class ProductTypeController {

    @Reference
    private IProductTypeService iProductTypeService;

    @ResponseBody
    @RequestMapping("list")
    public List<TProductType> list(){
        return iProductTypeService.list();
    }

}
