package com.workspace.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.workspace.backend.entity.GameUser;
import com.workspace.backend.mapper.GameUserMapper;
import com.workspace.backend.service.GameUserService;
import org.springframework.stereotype.Service;

@Service
public class GameUserServiceImpl extends ServiceImpl<GameUserMapper, GameUser> implements GameUserService {
}
