package com.mrl.server.system.config;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.mrl.server.system.properties.ServerSystemProperties;
import com.mrl.server.system.properties.SwaggerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * web相关配置
 * @author luc
 * @date 2020/7/2316:42
 */
@Configuration
@EnableSwagger2 //开启swagger功能
public class WebConfigure {
    @Autowired
    private ServerSystemProperties properties;

    /**
     * Mybatis plus 分页插件配置
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor paginationInterceptor=new PaginationInterceptor();
        List<ISqlParser> sqlParserList=new ArrayList<>();
        sqlParserList.add(new BlockAttackSqlParser());
        paginationInterceptor.setSqlParserList(sqlParserList);
        return paginationInterceptor;
    }

    /**
     * swagger配置
     * @return
     */
    @Bean
    public Docket swaggerApi(){
        SwaggerProperties swagger=properties.getSwagger();
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                //将该路径下的controller都添加进去
                .apis(RequestHandlerSelectors.basePackage(swagger.getBasePackage()))
                //Controller中所有方法都纳入
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo(swagger));
    }

    /**
     * 定义API页面信息，比如作者、邮箱、网络连接、开源协议等
     * 此处配置比较多，阿里编程规范称为“魔法值”，我们将它抽取为配置文件
     * @return
     */
    private ApiInfo apiInfo(SwaggerProperties swagger){
        return new ApiInfo(
                swagger.getTitle(),
                swagger.getDescription(),
                swagger.getVersion(),
                null,
                new Contact(swagger.getAuthor(),swagger.getUrl(),swagger.getEmail()),
                swagger.getLicense(),swagger.getLicenseUrl(), Collections.emptyList());
    }
}
