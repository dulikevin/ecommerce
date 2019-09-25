package com.duli.ecommercebackground.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.duli.api.item.ItemService;
import com.duli.api.product.IProductService;
import com.duli.api.product.vo.ProductVO;
import com.duli.api.search.ISearchService;
import com.duli.entity.TProduct;
import com.dulli.commom.pojo.ResultBean;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Reference
    private IProductService iProductService;

    @Reference
    private ISearchService isearchService;

    @Reference
    private ItemService itemService;


    @RequestMapping("/list")
    public String list(Model model){
        List<TProduct> list = iProductService.list();
        model.addAttribute("list",list);
        return "/product/list" ;
    }

    @RequestMapping("/page/{pageIndex}/{pageSize}")
    public String page(@PathVariable("pageIndex") Integer pageIndex,@PathVariable("pageSize") Integer pageSize, Model model){
        PageInfo<TProduct> pageInfo = iProductService.page(pageIndex,pageSize);
        model.addAttribute("pageInfo",pageInfo);
        return "/product/list" ;
    }

    @PostMapping("addVo")
    public String add(ProductVO productVo){
        Long id = iProductService.addVo(productVo);
        //添加商品时，同时更新索引库
        isearchService.updateById(id);
        //生成freemark页面
        ResultBean resultBean = itemService.createFreeMarkById(id);
        return "redirect:/product/page/1/2";

    }
}
