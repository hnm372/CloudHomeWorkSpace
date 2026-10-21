package com.workspace.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.workspace.backend.entity.GameUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GameUserMapper extends BaseMapper<GameUser> {
}
