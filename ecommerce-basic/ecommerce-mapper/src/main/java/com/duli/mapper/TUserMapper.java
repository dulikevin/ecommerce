package com.duli.mapper;

import com.duli.entity.TUser;
import com.dulli.commom.base.IBaseDao;

public interface TUserMapper extends IBaseDao<TUser> {
    TUser selectByName(String username);
}