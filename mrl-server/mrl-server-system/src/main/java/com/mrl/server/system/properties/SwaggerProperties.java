package com.mrl.server.system.properties;

import lombok.Data;

/**
 * swagger魔法值配置类
 * @author luc
 * @date 2020/7/2619:02
 */
@Data
public class SwaggerProperties {
    private String basePackage;
    private String title;
    private String description;
    private String version;
    private String author;
    private String url;
    private String email;
    private String license;
    private String licenseUrl;
}
