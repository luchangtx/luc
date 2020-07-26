package com.mrl.server.system.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.mrl.common.entry.CResponse;
import com.mrl.common.entry.QueryRequest;
import com.mrl.common.entry.system.DnUser;
import com.mrl.common.exception.ErrorType;
import com.mrl.common.utils.ResponseUtil;
import com.mrl.server.system.service.UserRoleService;
import com.mrl.server.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author luc
 * @date 2020/7/2318:15
 */
@RestController
@Slf4j
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('user:view')")
    public CResponse userList(QueryRequest queryRequest, DnUser user){
        Map<String,Object> dataTable= ResponseUtil.getDataTable(userService.findUserDetail(user,queryRequest));
        return new CResponse().data(dataTable);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('user:add')")
    public CResponse addUser(@Valid DnUser user){
        try {
            userService.createUser(user);
            return CResponse.ok();
        }catch (Exception e){
            log.error("UserController:addUser:"+e.getMessage());
            return new CResponse().message(ErrorType.SYSTEM_ERROR.toString());
        }
    }

    @PostMapping("/update")
    @PreAuthorize("hasAnyAuthority('user:update')")
    public CResponse updateUser(@Valid DnUser user){
        try {
            userService.updateUser(user);
            return CResponse.ok();
        }catch (Exception e){
            log.error("UserController:updateUser:"+e.getMessage());
            return CResponse.fail();
        }
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('user:delete')")
    public CResponse deleteUser(@Validated String userIds){
        try {
            String[] userArray= StringUtils.split(userIds, StringPool.COMMA);
            userService.deleteUsers(userArray);
            return CResponse.ok();
        }catch (Exception e){
            log.error("UserController:deleteUser:"+e.getMessage());
            return CResponse.fail();
        }
    }
}
