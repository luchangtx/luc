package com.mrl.auth.service;

import com.mrl.auth.manager.UserManager;
import com.mrl.common.entry.AuthUser;
import com.mrl.common.entry.system.DnUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 校验用户名密码的类
 * @author luc
 * @date 2020/7/1718:25
 */
@Service
public class AuthUserDetailService implements UserDetailsService {
    //AuthWebSecurityConfigure 类中注册的bean
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserManager userManager;

    //只有获取token或者刷新token才会走到这个方法
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /**
         * 我们模拟一个用户：username=输入的用户名；password=luc，后期再改造成从数据库中获取用户
         * 由于权限参数不能为空，所以我们使用 AuthorityUtils.commaSeparatedStringToAuthorityList 模拟一个user:add权限
         */
        System.err.println("用户名："+username);

        DnUser dnUser=userManager.findByName(username);
        if (dnUser==null){
            throw new UsernameNotFoundException("");
        }
        String permissions=userManager.findUserPermissions(username);
        //设置用户锁定
        boolean notLocked=false;
        if (StringUtils.equals(DnUser.STATUS_VALID,String.valueOf(dnUser.getStatus()))){
            notLocked=true;
        }
        //this.passwordEncoder.encode("luc")
        AuthUser user=new AuthUser(dnUser.getUsername(),dnUser.getPassword(),true,true,true,notLocked,AuthorityUtils.commaSeparatedStringToAuthorityList(permissions));
        //将系统用户转换为权限用户
        BeanUtils.copyProperties(dnUser,user);
        return user;
    }
}