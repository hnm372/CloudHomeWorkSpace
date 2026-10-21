package com.workspace.backend.entity;

public enum UserEnum {

    FREE_USER(0, "普通用户"),
    PAID_USER_1(1, "付费等级1"),
    PAID_USER_2(2, "付费等级2"),
    ADMINISTRATOR(100, "管理员"),
    DEVELOPER(1000, "开发者");

    final Integer vipCode;
    final String description;

    UserEnum(Integer vipCode, String description) {
        this.vipCode = vipCode;
        this.description = description;
    }

    public Integer getVipCode() {
        return this.vipCode;
    }

    public String getDescription() {
        return this.description;
    }
}
