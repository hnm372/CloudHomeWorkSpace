package com.workspace.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.workspace.backend.entity.UserLog;
import com.workspace.backend.mapper.UserLogMapper;
import com.workspace.backend.service.UserLogService;
import org.springframework.stereotype.Service;

@Service("userLogService")
public class UserLogServiceImpl extends ServiceImpl<UserLogMapper, UserLog> implements UserLogService {
}
