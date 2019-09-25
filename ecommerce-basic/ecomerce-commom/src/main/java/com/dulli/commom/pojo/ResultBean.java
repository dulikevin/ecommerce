package com.dulli.commom.pojo;

import java.io.Serializable;

public class ResultBean implements Serializable {

    //返回的状态码
    private Integer statusCode;
    //当成功之后返回的数据
    private String data;
    //当失败之后返回的数据信息
    private String msg;

    public static ResultBean success(String data){
        ResultBean resultBean = new ResultBean();
        resultBean.setStatusCode(200);
        resultBean.setData(data);
        return resultBean;
    }

    public static ResultBean error(String msg){
        ResultBean resultBean = new ResultBean();
        resultBean.setStatusCode(500);
        resultBean.setData(msg);
        return resultBean;
    }


    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
