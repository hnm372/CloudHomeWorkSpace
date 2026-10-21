package com.workspace.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author Huang
 * @since 2022-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "user")
public class User implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String uuid;

    @TableField(value = "user_account")
    private String userAccount;

    private String password;

    @TableField(value = "nick_name")
    private String nickName;

    private String email;

    private String phone;

    @TableField(value = "avatar_url")
    private String avatarUrl;

    private Integer vip;

    @TableField(value = "reg_date")
    private LocalDateTime regDate;

    @TableField(value = "v_date")
    private LocalDateTime vDate;

    private Integer version;


}
