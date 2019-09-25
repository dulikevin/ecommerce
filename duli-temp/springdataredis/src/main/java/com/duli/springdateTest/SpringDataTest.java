package com.duli.springdateTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/*.xml")
public class SpringDataTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void stringTest() throws IllegalAccessException, InstantiationException {
        //String 每次都得确定一个类型
        //String类型，在redis服务器段默认序列化，需要序列化才能存进去
//        redisTemplate.opsForValue().set("spring3","niu");
//        Object spring3 = redisTemplate.opsForValue().get("spring3");
//        System.out.println("spring1-->"+spring3);
//        Student student = new Student();
//        student.setName("duli2");
//        student.setAge(202);
//        redisTemplate.setValueSerializer(JdkSerializationRedisSerializer.class.newInstance());
//        redisTemplate.opsForValue().set("student:3",student);
//        Object o = redisTemplate.opsForValue().get("student:3");
//        System.out.println("student3--->"+o);
        //对于自增的，value为String类型序列化的要转换为String类型
        redisTemplate.opsForValue().set("cunkuan","10");
        redisTemplate.opsForValue().increment("cunkuan",1000l);
        System.out.println(redisTemplate.opsForValue().get("cunkuan"));

    }


    @Test
    public void hashTest() throws IllegalAccessException, InstantiationException {
        //注意hash的key和value，也被序列化了，需要自己设置
        Map<String,String> map = new HashMap<String, String>();
        map.put("name","把时间浪费在美好的事务上");
        map.put("auth","java");
        redisTemplate.opsForHash().putAll("book:101",map);
        Object auth = redisTemplate.opsForHash().get("book:101", "auth");
        System.out.println("auth");
    }

    //事务
    @Test
    public void mutiTest() {
        redisTemplate.execute(new SessionCallback() {
            public Object execute(RedisOperations operations) throws DataAccessException {
                //开启事务
                operations.multi();
                operations.opsForList().leftPush("user:101:follow","6");
                operations.opsForList().leftPush("user:6:fans","1");
                //注意在list的事务操作中，不能获取里面的值，只能在事务执行结束后获取
//                System.out.println(redisTemplate.opsForList().leftPop("user:101:follow")); ，值为null
                operations.exec();
                return null;
            }
        });
    }

    //流水线，用于批量操作
    @Test
    public void noPipeTest() {
        //没有流水线
        long start = System.currentTimeMillis();
        for (int i = 0; i < 5000; i++) {
            redisTemplate.opsForValue().set("k"+i,"v"+i);
        }
        long end = System.currentTimeMillis();
        System.out.println("nopipe-->"+(end-start));

        //有流水线
        redisTemplate.executePipelined(new SessionCallback<Object>() {
            public Object execute(RedisOperations operations) throws DataAccessException {
                long pipeStart = System.currentTimeMillis();
                for (int i = 0; i < 5000; i++) {
                    operations.opsForValue().set("k"+i,"v"+i);
                }
                long pipeEnd = System.currentTimeMillis();
                System.out.println("pipe-->"+(pipeEnd-pipeStart));
                return null;
            };
        });
    }


    //设置过期时间
    @Test
    public void expireTest() {
        redisTemplate.opsForValue().set("expireDate","expireDate");
        redisTemplate.expire("expireDate",20, TimeUnit.SECONDS);
    }




}
