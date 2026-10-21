package com.workspace.backend.vo;

import com.workspace.backend.entity.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class UserInfoModifyVO {
    User user;
    @NotEmpty
    List<String> modifyList;
}
