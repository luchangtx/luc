package com.mrl.server.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrl.common.entry.system.DnUser;
import org.springframework.data.repository.query.Param;

/**
 * 要让Mapper注册到IOC容器，可以使用@Mapper注解，更推荐再启动类增加
 * MapperScan("com.mrl.system.mapper")
 * @author luc
 * @date 2020/7/2317:43
 */
public interface UserMapper extends BaseMapper<DnUser> {
    /**
     * 查找用户详细信息
     * @param page 分页对象
     * @param user 用户对象，用于传递查询条件
     * @return
     */
    IPage<DnUser> findUserDetailPage(Page page, @Param("user") DnUser user);
}
