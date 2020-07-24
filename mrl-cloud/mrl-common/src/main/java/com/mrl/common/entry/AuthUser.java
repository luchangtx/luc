package com.mrl.common.entry;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author luc
 * @date 2020/7/2013:13
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AuthUser extends User implements Serializable {
    //用户ID
    private String userId;

    //用户名
    private String username;

    //姓名
    private String name;

    //部门ID
    private String deptId;

    //邮箱
    private String email;

    //联系电话
    private String mobile;

    //性别 0保密 1男 2女
    private int sex;

    //头像
    private String avatar;

    //小程序用户ID 小程序用户唯一标识
    private String openid;

    //最近访问时间
    private LocalDateTime lastLoginTime;

    /**
     * 非数据库字典——做拓展查询
     */
    //角色编号
    private String roleId;

    //角色标识
    private String roleCode;

    //角色名称
    private String roleName;

    //部门名称
    private String deptName;

    public AuthUser(String username, String password, Collection<? extends GrantedAuthority> authorities){
        super(username,password,authorities);
    }
    public AuthUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities){
        super(username,password,enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    /**
     * getAuthorities：获取用户权限集合 权限是继承了GrantedAuthority的对象
     * getPassword / getUsername：获取用户名和密码
     * isAccountNonExpired：判断账户是否未过期，tru未过期，false已过期
     * isAccountNonLocked：判断账户是否未锁定
     * isCredentialsNonExpired：判断账户凭证是否未过期，即密码是否未过期
     * isEnabled：判断用户是否可用
     */
}
