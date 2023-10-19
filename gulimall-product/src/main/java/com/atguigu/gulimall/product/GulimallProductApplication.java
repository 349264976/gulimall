package com.atguigu.gulimall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 1.整合mp
 * 导入以来
 *
 * 2.配置
 * 1.配置数据源
 *  1.导入数据库·驱动
 *  2.yaml配置数据源 相关信息
 * 2.配置mp
 *  1.
 *  * 配置mapperscan
 *  2.告诉mp sql映射文件的位置
 *  mybatis-plus:
 *  */
 //  mapper-locations: casspath:/mapper/**/*.xml

/**
 * 设置数据id自增
  */

@SpringBootApplication
@MapperScan("com.atguigu.gulimall.product.dao")
@EnableSwagger2
public class GulimallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallProductApplication.class, args);
    }

}
