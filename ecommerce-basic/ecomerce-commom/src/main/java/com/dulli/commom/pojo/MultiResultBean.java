package com.dulli.commom.pojo;

import java.io.Serializable;

public class MultiResultBean implements Serializable {

    private String errno;

    private String[] data;

    public String getErrno() {
        return errno;
    }

    public void setErrno(String errno) {
        this.errno = errno;
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }
}
