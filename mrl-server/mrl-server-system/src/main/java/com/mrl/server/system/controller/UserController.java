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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "用户管理接口")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('user:view')")
    @ApiOperation(value = "用户列表分页查询", notes = "用户列表分页查询")
    public CResponse userList(QueryRequest queryRequest, DnUser user){
        Map<String,Object> dataTable= ResponseUtil.getDataTable(userService.findUserDetail(user,queryRequest));
        return new CResponse().data(dataTable);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('user:add')")
    @ApiOperation(value = "新增用户", notes = "新增用户")
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
    @ApiOperation(value = "修改用户", notes = "修改用户")
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
    @ApiOperation(value = "删除用户", notes = "删除用户")
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
