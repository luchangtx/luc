package com.mrl.server.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mrl.common.entry.system.DnUserRole;
import com.mrl.server.system.vo.UserRoleVo;

/**
 * @author luc
 * @date 2020/7/2410:23
 */
public interface UserRoleService extends IService<DnUserRole> {
    /**
     * 根据 条件（用户id、角色id） 删除用户角色信息（改造为假删）
     * @param userRoleVo
     * @return
     */
    void deleteByCondition(UserRoleVo userRoleVo);
}
