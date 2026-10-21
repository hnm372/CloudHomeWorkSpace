package com.workspace.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "user_log")
public class UserLog {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "user_uuid")
    private String userUuid;

    // 0 account, 1 message code
    private int method;

    @TableField(value = "login_date")
    private LocalDateTime loginDate;

    @TableField(value = "logout_date")
    private LocalDateTime logoutDate;

}
