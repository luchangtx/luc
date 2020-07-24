package com.mrl.server.system.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luc
 * @date 2020/7/2410:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleVo {
    //用户id
    private String userId;

    //角色id
    private String roleId;
}
