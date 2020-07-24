package com.mrl.common.entry.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mrl.common.annotation.IsMobile;
import com.mrl.common.entry.BasePojo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户表
 * @author luc
 * @date 2020/7/2213:33
 */
@Data
@TableName("dn_user")
@EqualsAndHashCode(callSuper=true)
public class DnUser extends BasePojo implements Serializable {
    private static final long serialVersionUID = 3221846968477322994L;

    //用户状态：有效
    public static final String STATUS_VALID="1";
    //用户状态：锁定
    public static final String STATUS_LOCK="-1";
    // 默认头像
    public static final String DEFAULT_AVATAR = "default.jpg";
    // 默认密码
    public static final String DEFAULT_PASSWORD = "1234qwer";
    // 性别男
    public static final String SEX_MALE = "1";
    // 性别女
    public static final String SEX_FEMALE = "2";
    // 性别保密
    public static final String SEX_UNKNOW = "0";

    //用户ID
    @TableId(value = "USER_ID",type = IdType.UUID)
    private String userId;

    //用户名
    @TableField("USERNAME")
    @Size(min = 4,max = 15,message = "{range}")
    private String username;

    //姓名
    @TableField("NAME")
    @NotBlank(message = "{required}")
    private String name;

    //密码
    @TableField("PASSWORD")
    private String password;

    //部门ID
    @TableField("DEPT_ID")
    private String deptId;

    //邮箱
    @TableField("EMAIL")
    @Size(max =50,message = "{noMoreThan}")
    @Email(message = "{email}")
    private String email;

    //联系电话
    @TableField("MOBILE")
    @IsMobile(message = "{mobile}")
    private String mobile;

    //性别 0保密 1男 2女
    @TableField("SEX")
    @Min(value = 0,message = "{sexMin}")
    @Max(value = 2,message = "{sexMax}")
    private int sex;

    //头像
    @TableField("AVATAR")
    private String avatar;

    //小程序用户ID 小程序用户唯一标识
    @TableField("OPENID")
    private String openid;

    //最近访问时间
    @TableField("LAST_LOGIN_TIME")
    private LocalDateTime lastLoginTime;

    /**
     * 非数据库字典——做拓展查询
     */
    //角色编号
    @TableField(exist = false)
    private String roleId;

    //角色标识
    @TableField(exist = false)
    private String roleCode;

    //角色名称
    @TableField(exist = false)
    private String roleName;

    //部门名称
    @TableField(exist = false)
    private String deptName;
}
