package com.workspace.backend.service.impl;

import com.workspace.backend.entity.User;
import com.workspace.backend.mapper.UserMapper;
import com.workspace.backend.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Huang
 * @since 2022-11-25
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
