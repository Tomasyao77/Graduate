package com.whut.tomasyao.base.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-05-02 16:52
 */
@EnableWebMvc//必须存在
@EnableSwagger2//必须存在
@Configuration//必须存在
//必须存在 扫描的API Controller package name 也可以直接扫描class (basePackageClasses)
@ComponentScan(basePackages = {"edu.whut.pocket.*.controller"})
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                ;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("口袋光谷-区块链商城")
                .description("用户子系统(设置,权限等)接口文档")
                .termsOfServiceUrl("http://localhost:8080/v2/api-docs")
                .version("1.0.0")
                .build();
    }

}
