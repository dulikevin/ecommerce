package com.duli.ecommerceproductitemservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.duli.api.item.ItemService;
import com.duli.entity.TProduct;
import com.duli.mapper.TProductMapper;
import com.dulli.commom.pojo.ResultBean;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Component
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TProductMapper tProductMapper;

    @Autowired
    private Configuration Configuration;

    private int  initPoolCount = Runtime.getRuntime().availableProcessors();

    //单例线程池 00p  队列 Integer.MAX_value
    private ExecutorService ExecutorService = Executors.newSingleThreadExecutor();
    //定长线程池 00p  队列 Integer.MAX_value
    private ExecutorService fixExecutorService = Executors.newFixedThreadPool(10);
    //内存线程池 00p  线程数量 Integer.MAX_value
    private ExecutorService cacheExecutorService = Executors.newCachedThreadPool();

    private ExecutorService pool = new ThreadPoolExecutor(initPoolCount,initPoolCount*2,0l, TimeUnit.SECONDS,new LinkedBlockingQueue<>(100));

    @Value("${location.html}")
    private String locationHtml;
    @Override
    public ResultBean createFreeMarkById(Long id) {

        TProduct tProduct = tProductMapper.selectByPrimaryKey(id);
        try {
            Template template = Configuration.getTemplate("goods.ftl");
            Map<String,Object> data = new HashMap<>();
            data.put("product",tProduct);
            FileWriter fileWriter = new FileWriter(locationHtml+tProduct.getId()+".html");
            template.process(data,fileWriter);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
            return ResultBean.error("生成页面失败");
        }
        return ResultBean.success("生成页面成功");
    }

    @Override
    public ResultBean BatchCreateFreeMark(List<Long> ids) {
        //初始化线程的数量
        List<Future> list = new ArrayList<>(ids.size());
        for (Long id : ids) {
            Future<Boolean> submit = pool.submit(new BatchCreateHtml(id));
            list.add(submit);
        }
        for (Future future : list) {
            try {
                System.out.println(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return ResultBean.success("批量生成页面成功");
    }


    private class BatchCreateHtml implements Callable<Boolean>{
        private Long id;
        private BatchCreateHtml(Long id){
            this.id = id;
        }
        @Override
        public Boolean call() throws Exception {

            TProduct tProduct = tProductMapper.selectByPrimaryKey(id);
            try {
                Template template = Configuration.getTemplate("goods.ftl");
                Map<String,Object> data = new HashMap<>();
                data.put("product",tProduct);
                FileWriter fileWriter = new FileWriter(locationHtml+tProduct.getId()+".html");
                template.process(data,fileWriter);
            } catch (IOException | TemplateException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }



}
