package com.mrl.server.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrl.common.entry.QueryRequest;
import com.mrl.common.entry.system.DnUser;
import com.mrl.server.system.mapper.UserMapper;
import com.mrl.server.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author luc
 * @date 2020/7/2318:08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, DnUser> implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public IPage<DnUser> findUserDetail(DnUser user, QueryRequest request) {
        Page<DnUser> page=new Page<>(request.getPageNum(),request.getPageSize());
        return this.baseMapper.findUserDetailPage(page,user);
    }

    @Override
    public void createUser(DnUser user) {

    }

    @Override
    public void updateUser(DnUser user) {

    }

    @Override
    public void deleteUser(String[] userIds) {

    }
}
