package com.duli.redis;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JavaRedis {

    @Test
    public void StringTest(){
        Jedis jedis = new Jedis("192.168.253.128",6379);
        jedis.auth("123456");
        jedis.set("k1","redis");
        System.out.println(jedis.get("k1"));
        //mset
        jedis.mset("a1","v1","a2","v2");
        List<String> mget = jedis.mget(new String[]{"ai", "a2"});
        for (String s : mget) {
            System.out.println("mget--->"+s);
        }
        //incr
        jedis.incrBy("book:1:zan",1000);
        System.out.println(jedis.get("book:1:zan"));

    }

    @Test
    public void hashTest(){
        Jedis jedis = new Jedis("192.168.253.128",6379);
        jedis.auth("123456");
        Map<String,String> map = new HashMap<String, String>();
        map.put("name","c++");
        map.put("price","999");
        map.put("auth","ali");
        jedis.hmset("book:3",map);
        Map<String, String> book3 = jedis.hgetAll("book:3");
        Set<Map.Entry<String, String>> entries = book3.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            System.out.println("key--->"+entry.getKey());
            System.out.println("value--->"+entry.getValue());
        }
    }

    @Test
    public void listTest(){
        Jedis jedis = new Jedis("192.168.253.128",6379);
        jedis.auth("123456");
        jedis.lpush("iphone","a","b","c","d","e");
        List<String> iphone = jedis.lrange("iphone", 0, -1);
        for (String s : iphone) {
            System.out.println("iphone---》"+s);
        }
    }

    @Test
    public void setTest(){
        Jedis jedis = new Jedis("192.168.253.128",6379);
        jedis.auth("123456");
        jedis.sadd("luckymans","1001","1003","1005");
        if(!jedis.sismember("luckymans","1001")){
            jedis.sadd("luckymans","1001");
        }else{
            System.out.println("1001用户已经得到了抢购资格，请勿重复抢购");
        }
    }

    @Test
    public void zsetTest(){
        Jedis jedis = new Jedis("192.168.253.128",6379);
        jedis.auth("123456");
        Map<String,Double>  map = new HashMap<String, Double>();
        map.put("国家召开两会",1000.0);
        map.put("科比来深圳",2000.0);
        jedis.zadd("hot:search",map);



    }








}
