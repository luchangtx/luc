package com.mrl.auth.manager;

import com.mrl.auth.mapper.MenuMapper;
import com.mrl.auth.mapper.UserMapper;
import com.mrl.common.entry.system.DnMenu;
import com.mrl.common.entry.system.DnUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author luc
 * @date 2020/7/2215:10
 */
@Service
public class UserManager {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 获取用户信息
     * @param username
     * @return
     */
    public DnUser findByName(String username){
        return userMapper.findByName(username);
    }

    /**
     * 返回用户所有权限，逗号分隔开
     * @param username
     * @return
     */
    public String findUserPermissions(String username){
        List<DnMenu> list=menuMapper.findUserPermissions(username);
        return list.stream().map(DnMenu::getPerms).collect(Collectors.joining(","));
    }
}
