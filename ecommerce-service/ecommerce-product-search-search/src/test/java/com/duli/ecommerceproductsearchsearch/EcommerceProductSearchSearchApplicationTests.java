package com.duli.ecommerceproductsearchsearch;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EcommerceProductSearchSearchApplicationTests {

    @Autowired
    private SolrClient solrClient;

    @Test
    public void addOrUpdateTest() throws IOException, SolrServerException {
        //作用于数据的同步操作上，源头数据库发生了变更，索引库也要变更
        //solr里面操作的记录，document
        SolrInputDocument document = new SolrInputDocument();
        //需要有一个唯一的标识
        document.setField("id",500);
        document.setField("product_name","103杜力比刘德华帅");
        document.setField("product_price",9999);
        document.setField("product_sale_point","帅得一塌糊涂");
        document.setField("product_images","暂无");
        //提交
        //solrClient.add(document);
        solrClient.add("collection2",document);
        solrClient.commit("collection2");

    }
    //查询操作
    @Test
    public void queryTest() throws IOException, SolrServerException {
        SolrQuery solrQuery = new SolrQuery();
        //查询所有
//        solrQuery.setQuery("*:*");
//        QueryResponse query = solrClient.query(solrQuery);
        //一般的查询
        solrQuery.setQuery("product_name:杜力");
        QueryResponse query = solrClient.query(solrQuery);
        SolrDocumentList results = query.getResults();
        System.out.println("query--->");
        for (SolrDocument result : results) {
            System.out.println(result.get("product_name"));
            System.out.println(result.get("product_sale_point"));
            System.out.println(result.get("product_sale_price"));
            System.out.println("------");
        }
    }

    @Test
    public void deleteTest() throws IOException, SolrServerException {
        //solrClient.deleteById("12");
        solrClient.deleteByQuery("product_name:杜力");
        solrClient.commit();





    }



}
