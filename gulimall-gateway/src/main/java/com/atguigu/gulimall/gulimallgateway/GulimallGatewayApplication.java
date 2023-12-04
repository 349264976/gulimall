package com.atguigu.gulimall.gulimallgateway;

import com.baomidou.mybatisplus.autoconfigure.IdentifierGeneratorAutoConfiguration;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 开启注册发现
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, MybatisPlusAutoConfiguration.class
, IdentifierGeneratorAutoConfiguration.class})
@EnableDiscoveryClient
public class GulimallGatewayApplication {
	public static void main(String[] args) {
		SpringApplication.run(GulimallGatewayApplication.class, args);
	}

}
