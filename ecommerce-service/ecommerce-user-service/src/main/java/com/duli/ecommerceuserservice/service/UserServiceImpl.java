package com.duli.ecommerceuserservice.service;


import com.alibaba.dubbo.config.annotation.Service;
import com.duli.api.user.IUserService;
import com.duli.entity.TUser;
import com.duli.mapper.TUserMapper;
import com.dulli.commom.base.BaseServiceImpl;
import com.dulli.commom.base.IBaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Service
public class UserServiceImpl extends BaseServiceImpl<TUser> implements IUserService {

    @Autowired
    private BCryptPasswordEncoder bcryptPasswordEncoder;

    @Autowired
    private TUserMapper userMapper;

    @Override
    public IBaseDao<TUser> getBaseDao() {
        return userMapper;
    }


    @Override
    public TUser checkLogin(TUser tuser) {
        TUser user = userMapper.selectByName(tuser.getUsername());
        if(user != null){
            if(bcryptPasswordEncoder.matches(tuser.getPassword(),user.getPassword())){
                return user;
            }
        }
        return null;
    }
}
