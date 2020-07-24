package com.mrl.common.selector;

import com.mrl.common.config.AuthExceptionConfigure;
import com.mrl.common.config.OAuth2FeignConfigure;
import com.mrl.common.config.ServerProtectConfigure;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 打包导入，使用统一注解驱动
 * @author luc
 * @date 2020/7/2118:18
 */
public class CloudApplicationSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{
                //访问资源服务器 权限异常翻译处理（token无效、无权限）
                AuthExceptionConfigure.class.getName(),
                //oauth2认证，通过feign方式访问微服务手动拦截请求，处理请求头 token信息
                OAuth2FeignConfigure.class.getName(),
                //在网关转发前拦截请求处理zuul token，避免客户端绕过网关直接请求微服务
                ServerProtectConfigure.class.getName()
        };
    }
}
