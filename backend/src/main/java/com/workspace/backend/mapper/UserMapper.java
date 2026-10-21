package com.workspace.backend.mapper;

import com.workspace.backend.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Huang
 * @since 2022-11-25
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
