package com.duli.api.item;

import com.dulli.commom.pojo.ResultBean;

import java.util.List;

public interface ItemService {
    ResultBean createFreeMarkById(Long id);

    ResultBean BatchCreateFreeMark(List<Long> ids);


}
