package com.mrl.server.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mrl.common.entry.QueryRequest;
import com.mrl.common.entry.system.DnUser;

/**
 * @author luc
 * @date 2020/7/2318:00
 */
public interface UserService extends IService<DnUser> {
    /**
     * 查找用户详细信息
     * @param user 用户对象，用于传递查询条件
     * @param request
     * @return
     */
    IPage<DnUser> findUserDetail(DnUser user, QueryRequest request);

    /**
     * 新增用户
     * @param user
     */
    void createUser(DnUser user);

    /**
     * 修改用户
     * @param user
     */
    void updateUser(DnUser user);

    /**
     * 删除用户
     * @param userIds 用户id数组
     */
    void deleteUsers(String[] userIds);
}
