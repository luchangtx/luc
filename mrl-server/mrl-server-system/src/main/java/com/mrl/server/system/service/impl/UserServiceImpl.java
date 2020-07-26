package com.mrl.server.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrl.common.entry.QueryRequest;
import com.mrl.common.entry.system.DnUser;
import com.mrl.common.entry.system.DnUserRole;
import com.mrl.server.system.mapper.UserMapper;
import com.mrl.server.system.service.UserRoleService;
import com.mrl.server.system.service.UserService;
import com.mrl.server.system.vo.UserRoleVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author luc
 * @date 2020/7/2318:08
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true,rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, DnUser> implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRoleService userRoleService;

    @Override
    public IPage<DnUser> findUserDetail(DnUser user, QueryRequest request) {
        Page<DnUser> page=new Page<>(request.getPageNum(),request.getPageSize());
        return this.baseMapper.findUserDetailPage(page,user);
    }

    @Override
    @Transactional
    public void createUser(DnUser user) {
        LocalDateTime now=LocalDateTime.now();
        user.setPassword(passwordEncoder.encode(DnUser.DEFAULT_PASSWORD));
        user.setInsertTime(now);
        user.setLastLoginTime(now);
        user.setStatus(1);
        save(user);
        //保存用户角色
        String[] roles=user.getRoleId().split(StringPool.COMMA);
        setUserRoles(user,roles);
    }

    @Override
    @Transactional
    public void updateUser(DnUser user) {
        //修改用户表信息
        user.setLastChanged(LocalDateTime.now());
        baseMapper.updateById(user);

        //修改后的角色
        if (!StringUtils.isBlank(user.getRoleId())){
            //拆分修改后角色
            String[] roles=user.getRoleId().split(StringPool.COMMA);

            //判断角色是否修改
            LambdaQueryWrapper wrapper=new LambdaQueryWrapper<DnUserRole>().eq(DnUserRole::getUserId,user.getUserId());
            List<DnUserRole> list =userRoleService.list(wrapper);
            List<String> listRoleId=list.stream().map(x->x.getRoleId()).collect(Collectors.toList());
            //比对修改前后角色 是否有变化

            int unSame=0;
            for (String roleId:roles){
                if (!listRoleId.contains(roleId)){
                    unSame++;break;//只要有一个待新增的角色，就终止循环，直接进行角色修改操作
                }
            }
            if (unSame>0||(unSame==0&&roles.length!=listRoleId.size())){
                //LambdaQueryWrapper 条件查询构造器
                //删除用户当前的角色数据
                userRoleService.remove(wrapper);
                setUserRoles(user,roles);
            }
        }
    }

    @Override
    @Transactional
    public void deleteUsers(String[] userIds) {
        List<String> list=Arrays.asList(userIds);
        removeByIds(list);
        list.forEach(x->{
            userRoleService.deleteByCondition(new UserRoleVo(x,""));
        });
    }

    /**
     * 处理用户角色关联数据
     * @param user
     * @param roles
     */
    private void setUserRoles(DnUser user,String[] roles){
        Arrays.stream(roles).forEach(roleId->{
            DnUserRole userRole=new DnUserRole();
            userRole.setUserId(user.getUserId());
            userRole.setRoleId(roleId);
            userRole.setInsertTime(user.getLastChanged());
            userRole.setLastChanged(user.getLastChanged());
            userRole.setStatus(1);
            userRoleService.save(userRole);
        });
    }
}
