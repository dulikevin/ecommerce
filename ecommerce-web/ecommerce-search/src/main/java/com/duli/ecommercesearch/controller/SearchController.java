package com.duli.ecommercesearch.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.duli.api.search.ISearchService;
import com.duli.entity.TProduct;
import com.dulli.commom.pojo.PageResultBean;
import com.dulli.commom.pojo.ResultBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
@RequestMapping("search")
public class SearchController {

    @Reference
    private ISearchService isearchService;

    @RequestMapping("initAllData")
    @ResponseBody
    public ResultBean initAllData(){
        return isearchService.initAllData();
    }

    @RequestMapping("searchByKeyword")
    public String searchByKeyword(String keyword, Model model){
        List<TProduct> list =  isearchService.searchByKeyword(keyword);
        model.addAttribute("list",list);
        return "search";
    }

    @RequestMapping("searchByKeywordByPage")
    public String searchByKeyword(String keyword, Model model, int pageIndex,int pageSize){
        PageResultBean<TProduct> tProductPageResultBean = isearchService.searchByKeyword(keyword, pageIndex, pageSize);
        model.addAttribute("list",tProductPageResultBean);
        return "search";
    }






}
