package com.workspace.backend.vo;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CodeLoginVO {
    @NotEmpty(message = "{common.phone.notEmpty}")
    String phone;
    @NotEmpty(message = "{common.code.notEmpty}")
    String code;
}
