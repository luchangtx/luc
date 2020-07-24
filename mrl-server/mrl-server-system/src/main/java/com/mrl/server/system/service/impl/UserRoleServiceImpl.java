package com.mrl.server.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrl.common.entry.system.DnUserRole;
import com.mrl.server.system.mapper.UserRoleMapper;
import com.mrl.server.system.service.UserRoleService;
import com.mrl.server.system.vo.UserRoleVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author luc
 * @date 2020/7/2410:43
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true,rollbackFor = Exception.class)
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, DnUserRole> implements UserRoleService {
    @Override
    public void deleteByCondition(UserRoleVo userRoleVo) {
        baseMapper.deleteByCondition(userRoleVo);
    }
}
