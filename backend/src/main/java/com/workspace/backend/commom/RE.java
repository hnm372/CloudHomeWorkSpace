package com.workspace.backend.commom;

public enum RE {
    SUCCESS(200, "请求成功"),

    PROCESSING(202, "正在处理"),

    BAD_REQUEST(400, "请求失败"),
    UNAUTHORIZED(401, "未授权"),
    INTERNAL_ERROR(500, "内部错误");

    private int code;
    private String message;

    RE(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public int getCode() {
        return this.code;
    }

}
