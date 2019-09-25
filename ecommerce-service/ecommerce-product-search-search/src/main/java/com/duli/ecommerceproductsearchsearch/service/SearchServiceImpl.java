package com.duli.ecommerceproductsearchsearch.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.duli.api.search.ISearchService;
import com.duli.entity.TProduct;
import com.duli.mapper.TProductMapper;
import com.dulli.commom.pojo.PageResultBean;
import com.dulli.commom.pojo.ResultBean;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Service
public class SearchServiceImpl implements ISearchService {

    @Autowired
    private TProductMapper tProductMapper;

    @Autowired
    private SolrClient solrClient;

    @Override
    public ResultBean initAllData() {
        List<TProduct> list = tProductMapper.list();
        List<SolrInputDocument> documentLists = new ArrayList<>(list.size());
        for (TProduct tProduct : list) {
            SolrInputDocument solrInputDocument = new SolrInputDocument();
            solrInputDocument.addField("id",tProduct.getId());
            solrInputDocument.addField("product_name",tProduct.getName());
            solrInputDocument.addField("product_price",tProduct.getPrice());
            solrInputDocument.addField("product_sale_point",tProduct.getSalePoint());
            solrInputDocument.addField("product_images",tProduct.getImage());
            documentLists.add(solrInputDocument);
        }
        if(list.size()>0){
            try {
                solrClient.add(documentLists);
                solrClient.commit();
            } catch (SolrServerException | IOException e) {
                //打印到控制台
                e.printStackTrace();
                return ResultBean.error("数据同步失败");
                //记录日志
                //记录日志到表
                //异常信息管理-索引库异常信息
                //发送一封邮件到相关负责人
            }
        }
        return ResultBean.success("数据同步成功");
    }

    @Override
    public List<TProduct> searchByKeyword(String keyword) {
        SolrQuery solrCondition = new SolrQuery();
        if(keyword != null) {
            solrCondition.setQuery("product_keywords:" + keyword);
        }else{
            solrCondition.setQuery("product_keywords:华为");
        }
        //增加高亮效果
        solrCondition.setHighlight(true);
        solrCondition.addHighlightField("product_name");
        solrCondition.setHighlightSimplePre("<font color='red'>");
        solrCondition.setHighlightSimplePost("</font>");

        List<TProduct> list = null;
        try {
            QueryResponse query = solrClient.query(solrCondition);
            SolrDocumentList documentList = query.getResults();
            //获取高亮信息
            Map<String, Map<String, List<String>>> highlighting =query.getHighlighting();
            list = new ArrayList<>(documentList.size());
            for (SolrDocument document : documentList) {
                TProduct product = new TProduct();
                product.setId(Long.parseLong(document.getFieldValue("id").toString()));
                product.setSalePoint(document.getFieldValue("product_sale_point").toString());
                product.setImage(document.getFieldValue("product_images").toString());
                Map<String, List<String>> map = highlighting.get(document.getFieldValue("id").toString());
                List<String> nameList = map.get("product_name");
                if(nameList != null && nameList.size()>0){
                    product.setName(nameList.get(0));
                }else{
                    product.setName(document.getFieldValue("product_name").toString());
                }
                list.add(product);
            }
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public PageResultBean<TProduct> searchByKeyword(String keyword, int pageNum, int pageSize) {
        PageResultBean pageResultBean = new PageResultBean<TProduct>();
        SolrQuery solrCondition = new SolrQuery();
        if(keyword != null) {
            solrCondition.setQuery("product_keywords:" + keyword);
        }else{
            solrCondition.setQuery("product_keywords:华为");
        }
        //增加高亮效果
        solrCondition.setHighlight(true);
        solrCondition.addHighlightField("product_name");
        solrCondition.setHighlightSimplePre("<font color='red'>");
        solrCondition.setHighlightSimplePost("</font>");
        solrCondition.setStart((pageNum-1)*pageSize);
        solrCondition.setRows(pageSize);
        List<TProduct> list = null;
        long total = 0L;
        try {
            QueryResponse query = solrClient.query(solrCondition);
            SolrDocumentList documentList = query.getResults();
            //获取总记录数
            total = documentList.getNumFound();
            //获取高亮信息
            Map<String, Map<String, List<String>>> highlighting =query.getHighlighting();
            list = new ArrayList<>(documentList.size());
            for (SolrDocument document : documentList) {
                TProduct product = new TProduct();
                product.setId(Long.parseLong(document.getFieldValue("id").toString()));
                product.setSalePoint(document.getFieldValue("product_sale_point").toString());
                product.setImage(document.getFieldValue("product_images").toString());
                Map<String, List<String>> map = highlighting.get(document.getFieldValue("id").toString());
                List<String> nameList = map.get("product_name");
                if(nameList != null && nameList.size()>0){
                    product.setName(nameList.get(0));
                }else{
                    product.setName(document.getFieldValue("product_name").toString());
                }
                list.add(product);
            }
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
        pageResultBean.setPageNum(pageNum);
        pageResultBean.setPageSize(pageSize);
        pageResultBean.setList(list);
        pageResultBean.setTotal(total);
        pageResultBean.setPages((int) (total%pageSize==0?(total/pageSize):(total/pageSize)+1));
        return pageResultBean;
    }

    @Override
    public ResultBean updateById(Long id) {
        TProduct tProduct = tProductMapper.selectByPrimaryKey(id);
        List<SolrInputDocument> documentLists = new ArrayList<>();
        SolrInputDocument solrInputDocument = new SolrInputDocument();
        solrInputDocument.addField("id",tProduct.getId());
        solrInputDocument.addField("product_name",tProduct.getName());
        solrInputDocument.addField("product_price",tProduct.getPrice());
        solrInputDocument.addField("product_sale_point",tProduct.getSalePoint());
        solrInputDocument.addField("product_images",tProduct.getImage());
        documentLists.add(solrInputDocument);
        try {
            solrClient.add(documentLists);
            solrClient.commit();
        } catch (SolrServerException | IOException e) {
                //打印到控制台
            e.printStackTrace();
            return ResultBean.error("数据同步失败");
            //记录日志
            //记录日志到表
            //异常信息管理-索引库异常信息
            //发送一封邮件到相关负责人
        }
    return ResultBean.success("数据同步成功");
    }
}
