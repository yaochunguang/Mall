package com.company.entity;

import java.io.Serializable;

/**
 * @description: 结果对象
 * @author: chunguang.yao
 * @date: 2019-07-04 0:54
 */
public class Result implements Serializable {

    // 结果标志：成功/失败
    private  boolean success;

    // 提示信息：返回给页面
    private  String message;

    public Result(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
