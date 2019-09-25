package com.duli.ecommerceproductitemservice;

import com.duli.api.item.ItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EcommerceProductItemServiceApplicationTests {

    @Autowired
    private ItemService itemService;


    @Test
    public void contextLoads() {
        List<Long> list = new ArrayList<>(9);
        for (long i = 1; i < 9 ; i++) {
            list.add(i);
        }
        itemService.BatchCreateFreeMark(list);
    }

}
