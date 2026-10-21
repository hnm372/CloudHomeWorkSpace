package com.workspace.backend.entity;

public enum UserLogEnum {
    ACCOUNT_LOGIN(0, "账号密码登录"),
    MESSAGE_CODE_LOGIN(1, "短信验证码登录"),
    QRCODE_LOGIN(2, "二维码登录");

    private Integer code;
    private String description;

    UserLogEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }
}
