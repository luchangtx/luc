package com.mrl.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mrl.common.entry.system.DnMenu;

import java.util.List;

/**
 * @author luc
 * @date 2020/7/2214:11
 */
public interface MenuMapper extends BaseMapper<DnMenu> {
    //根据用户名查询用户权限
    List<DnMenu> findUserPermissions(String username);
}
