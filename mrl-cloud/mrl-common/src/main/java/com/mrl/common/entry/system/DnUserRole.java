package com.mrl.common.entry.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mrl.common.entry.BasePojo;
import lombok.Data;

import java.io.Serializable;

/**
 * @author luc
 * @date 2020/7/2318:36
 */
@Data
@TableName("dn_user_role")
public class DnUserRole extends BasePojo implements Serializable {

    private static final long serialVersionUID = 5911635743235757021L;

    //用户角色ID
    @TableId(value = "USER_ROLE_ID",type = IdType.ASSIGN_UUID)
    private String userRoleId;

    //用户名
    @TableField("USER_ID")
    private String userId;

    //姓名
    @TableField("ROLE_ID")
    private String roleId;
}
