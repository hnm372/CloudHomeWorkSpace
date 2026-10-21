package com.workspace.backend.vo;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AccountLoginVO {

    @NotEmpty(message = "{common.userAccount.message}")
    String userAccount;
    @NotEmpty(message = "{common.password.message}")
    String password;
    @NotEmpty(message = "{common.kaptcha.message}")
    String kaptcha;
}
