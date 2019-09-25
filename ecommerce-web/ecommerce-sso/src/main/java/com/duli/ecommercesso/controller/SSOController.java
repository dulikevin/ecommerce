package com.duli.ecommercesso.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.duli.api.user.IUserService;
import com.duli.entity.TUser;
import com.dulli.commom.pojo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("sso")
public class SSOController {

    @Reference
    private IUserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("login")
    public String showLogin(){
        return "login";
    }

    //用户登录
    @RequestMapping("checkLogin")
    public String checkLogin(TUser tuser, HttpServletResponse response){
        TUser user = userService.checkLogin(tuser);
        if(user == null){
            return "login";
        }
        //生成UUID
        String uuid = UUID.randomUUID().toString();
        Cookie cookie = new Cookie("user_token",uuid);
        //不能通过前端来直接获取cookie的信息
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        //跨域问题
//        cookie.setDomain("duli.com");
        StringBuilder redisKey = new StringBuilder("user:token:").append(uuid);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.opsForValue().set(redisKey.toString(),user);
        redisTemplate.expire(redisKey.toString(),30, TimeUnit.MINUTES);
        response.addCookie(cookie);
        System.out.println(redisTemplate.opsForValue().get(redisKey.toString()));
        return "error";
    }

    @RequestMapping("checkIsLoginOld")
    @ResponseBody
    public ResultBean checkIsLoginOld(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies == null){
            return ResultBean.error("该用户登录不成功");
        }
        for (Cookie cookie : cookies) {
            if("user_token".equals(cookie.getName())){
                String uuid = cookie.getValue();
                StringBuilder redisKey = new StringBuilder("user:token:").append(uuid);
                redisTemplate.setKeySerializer(new StringRedisSerializer());
                TUser user = (TUser) redisTemplate.opsForValue().get(redisKey.toString());
                if(user != null){
                    redisTemplate.expire(redisKey.toString(),30,TimeUnit.MINUTES);
                    return ResultBean.success(user.getUsername());
                }
            }
        }
        return ResultBean.error("用户登录不成功");
    }

    @RequestMapping("checkIsLogin")
    @ResponseBody
    public ResultBean checkIsLogin(@CookieValue(name = "user_token",required = false) String uuid){
        if(uuid == null){
            return ResultBean.error("该用户登录不成功");
        }
        StringBuilder redisKey = new StringBuilder("user:token:").append(uuid);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        TUser user = (TUser) redisTemplate.opsForValue().get(redisKey.toString());
        if(user != null){
            redisTemplate.expire(redisKey.toString(),30,TimeUnit.MINUTES);
            return ResultBean.success(user.getUsername());
        }
        return ResultBean.error("用户登录不成功");
    }

    @RequestMapping("logout")
    @ResponseBody
    public ResultBean logout(@CookieValue(name = "user_token",required = false) String uuid,HttpServletResponse response){
        if(uuid == null){
            return ResultBean.error("注销失败");
        }
        Cookie cookie = new Cookie("user_token",uuid);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        StringBuilder redisKey = new StringBuilder("user:token:").append(uuid);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        TUser user = (TUser) redisTemplate.opsForValue().get(redisKey.toString());
        if(user != null){
            redisTemplate.delete(redisKey.toString());
            return ResultBean.success("注销成功");
        }
        return ResultBean.success("注销成功");
    }







}
