package com.mrl.server.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mrl.common.entry.system.DnUserRole;
import com.mrl.server.system.vo.UserRoleVo;

/**
 * @author luc
 * @date 2020/7/2410:05
 */
public interface UserRoleMapper extends BaseMapper<DnUserRole> {
    /**
     * 根据 条件（用户id、角色id） 删除用户角色信息（改造为假删）
     * @param userRoleVo
     * @return
     */
    Boolean deleteByCondition(UserRoleVo userRoleVo);
}
