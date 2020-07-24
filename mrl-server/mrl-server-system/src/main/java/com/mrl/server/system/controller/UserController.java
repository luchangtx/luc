package com.mrl.server.system.controller;

import com.mrl.common.entry.CResponse;
import com.mrl.common.entry.QueryRequest;
import com.mrl.common.entry.system.DnUser;
import com.mrl.common.utils.ResponseUtil;
import com.mrl.server.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping
    public CResponse userList(QueryRequest queryRequest, DnUser user){
        Map<String,Object> dataTable= ResponseUtil.getDataTable(userService.findUserDetail(user,queryRequest));
        return new CResponse().data(dataTable);
    }
}
