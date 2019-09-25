package com.duli.api.user;

import com.duli.entity.TUser;
import com.dulli.commom.base.IBaseService;

public interface IUserService extends IBaseService<TUser> {
    TUser checkLogin(TUser tuser);
}
