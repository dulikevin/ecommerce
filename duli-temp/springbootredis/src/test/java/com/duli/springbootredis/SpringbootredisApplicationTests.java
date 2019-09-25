package com.duli.springbootredis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootredisApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void contextLoads() {
        redisTemplate.opsForValue().set("k1","v1");
        System.out.println(redisTemplate.opsForValue().get("k1"));
    }


    //短信验证码
    @Test
    public void saveCodeToRedisTest() {
        //手机号注册，发送验证码
        //获取到手机号
        String phone = "18086436486";
        //随机生成一个验证码
        String code = "6686";
        //生成其中的key的值
        StringBuilder redisKey = new StringBuilder("register:code").append(phone);
        redisTemplate.opsForValue().set(redisKey.toString(),"6686");
        redisTemplate.expire(redisKey.toString(),300, TimeUnit.SECONDS);
        //发送短信验证码
        System.out.println("短信验证码发送成功");
    }

    //短信验证码
    @Test
    public void checkCodeToRedisTest() {
        //获取到手机号和验证码
        String phone = "18086436486";
        String code = "6686";
        StringBuilder redisKey = new StringBuilder("register:code").append(phone);
        Object currentCode = redisTemplate.opsForValue().get(redisKey.toString());
        if(code.equals(currentCode.toString())){
            System.out.println("验证码发送成功");
        }else{
            System.out.println("短信验证码发送失败");
        }
    }

    //前提，用户注册成功，要发送激活邮件
    public void saveActivateCode(){
        //1.用户注册成功，获取当前用户记录id
        long userId = 1l;
        //2.生成激活码
        String activateCode = UUID.randomUUID().toString();
        //保存激活码和用户id之间的关系，方便后续的激活
        StringBuilder redisKey = new StringBuilder("register:activate:").append(activateCode);
        redisTemplate.opsForValue().set(redisKey.toString(),userId);
        redisTemplate.expire(redisKey.toString(),300, TimeUnit.DAYS);
        //发送激活邮件
        System.out.println("发送激活邮件");
    }


    //前提，用户注册成功，要发送激活邮件
    public void checkActivateCode(){
        //2.获取激活码
        String activateCode = "";
        //保存激活码和用户id之间的关系，方便后续的激活
        StringBuilder redisKey = new StringBuilder("register:activate:").append(activateCode);
        Object o = redisTemplate.opsForValue().get(redisKey.toString());
        if(o!=null){
            System.out.println("激活成功");
            //更新状态UsetId
            //删除掉Redis中的key值
        }
    }







}
