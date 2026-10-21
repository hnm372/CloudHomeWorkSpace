package com.workspace.backend.commom;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/***
 * 统一返回结果的类
 * Usage:
 * R.ok().code().message().data();
 */
@Data
public class R {

    private Boolean success;

    private Integer code;

    private String message;


    private Map<String, Object> data = new HashMap<>();

    //把构造方法私有
    private R() {
    }

    //成功静态方法
    public static R ok() {
        R r = new R();
        r.setSuccess(true);
        r.setCode(RE.SUCCESS.getCode());
        r.setMessage(RE.SUCCESS.getMessage());
        return r;
    }

    //失败静态方法
    public static R error() {
        R r = new R();
        r.setSuccess(false);
        r.setCode(RE.BAD_REQUEST.getCode());
        r.setMessage(RE.BAD_REQUEST.getMessage());
        return r;
    }

    public static R error(RE re) {
        R r = new R();
        r.setSuccess(false);
        r.setCode(re.getCode());
        r.setMessage(re.getMessage());
        return r;
    }

    public R success(Boolean success) {
        this.setSuccess(success);
        return this;   //返回this  实现链式编程
    }

    public static R success(RE re) {
        R r = new R();
        r.setSuccess(true);
        r.setCode(re.getCode());
        r.setMessage(re.getMessage());
        return r;
    }

    public R message(String message) {
        this.setMessage(message);
        return this;
    }

    public R code(Integer code) {
        this.setCode(code);
        return this;
    }


    public R data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public R data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }

}

