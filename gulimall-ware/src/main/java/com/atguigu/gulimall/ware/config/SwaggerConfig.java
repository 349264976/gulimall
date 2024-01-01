package com.atguigu.gulimall.ware.config;//package com.atguigu.gulimall.product.config;
//
//import java.util.ArrayList;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//@EnableSwagger2
//@Configuration
//public class SwaggerConfig {
//    @Bean
//    public Docket docket() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .select()// 通过.select()方法，去配置扫描接口,RequestHandlerSelectors配置如何扫描接口
//                .apis(RequestHandlerSelectors.basePackage("com.atguigu.gulimall.product"))
//                .build();
//    }
//    //配置文档信息
//    private ApiInfo apiInfo() {
//        Contact contact = new Contact("lken","https:","349264976@qq.com");
//        return new ApiInfo(
//                "谷粒", // 标题
//                "商城", // 描述
//                "v1", // 版本
//                "apq", // 组织链接
//                contact, // 联系人信息
//                "license0.1", // 许可
//                "", // 许可连接
//                new ArrayList<>()// 扩展
//        );
//    }
//}
//
