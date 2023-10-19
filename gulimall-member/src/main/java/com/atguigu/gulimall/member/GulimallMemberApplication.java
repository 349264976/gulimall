package com.atguigu.gulimall.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author @lken
 * 1.使用rpc远程调用
 * 	1).引入open-feign
 * 	2）.编写远程调用接口 遵循openfeign规则 并且告知springcloud
 * 	3).编写一个接口 接口之上注解标识 nacos注册的远程服务名称 方法声明为被调用者方法签名
 * 	4）.开启远程调用功能 开启远程调用包扫描包主街道启动类
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.atguigu.gulimall.member.feiclient")
public class GulimallMemberApplication {

	public static void main(String[] args) {
		SpringApplication.run(GulimallMemberApplication.class, args);
	}

}
