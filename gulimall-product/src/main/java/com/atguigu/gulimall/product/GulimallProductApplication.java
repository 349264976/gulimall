package com.atguigu.gulimall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 1、整合MyBatis-Plus
 * 1)、导入依赖
 * <dependency>
 * <groupId>com.baomidou</groupId>
 * <artifactId>mybatis-plus-boot-starter</artifactId>
 * <version>3.2.0</version>
 * </dependency>
 * 2)、配置
 * 1、配置数据源;
 * 1)、导入数据库的驱动.https://dev.mysql.com/doc/connector-j/8.e/en/c-j-ver
 * 2)、在application.yml配置数据源相关信息
 * 2、配置MyBatis-Plus;
 * 1)、使用@MapperScan
 * 2)、告诉MyBatis-Plus，sql映射文件位置
 * 逻辑删除
 * 1配置全局的逻辑删除规则(省略)
 * 2)、配置逻辑删除的组件Bean(省略)
 * 3)、给Bean加上逻辑删除注解@TableLogic
 */

@SpringBootApplication
@MapperScan("com.atguigu.gulimall.product.dao")
@EnableSwagger2
public class GulimallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallProductApplication.class, args);
    }

}
