package com.mrl.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mrl.common.entry.system.DnUser;

/**
 * @author luc
 * @date 2020/7/2214:10
 */
public interface UserMapper extends BaseMapper<DnUser> {
    //通过用户名查询用户信息
    DnUser findByName(String username);
}
