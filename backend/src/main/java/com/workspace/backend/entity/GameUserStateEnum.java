package com.workspace.backend.entity;

public enum GameUserStateEnum {
    NOTHING(0, "啥都不干"),
    PRACTICE_LEVEL(1, "打怪练级");
    final Integer stateCode;
    final String description;

    GameUserStateEnum(Integer stateCode, String description) {
        this.stateCode = stateCode;
        this.description = description;
    }

    public Integer getStateCode() {
        return this.stateCode;
    }

    public String getDescription() {
        return this.description;
    }
}
