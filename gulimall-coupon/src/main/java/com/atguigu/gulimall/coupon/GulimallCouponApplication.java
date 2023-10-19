package com.atguigu.gulimall.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 1.如何使用通过Nacos作为配置中心
 * 	1）.引入依赖 nacos-config  引入依赖 bootstrap.yaml
 * 	2）.创建yaml文件 等级优先于application.yml
 * 	3）.在nacos中配置 配置文件
 * 	4）.设置动态刷新 nacos2.2版本对应的需要开启
 * 	5).动态获取值
 *
 */
@SpringBootApplication
public class GulimallCouponApplication {

	public static void main(String[] args) {
		SpringApplication.run(GulimallCouponApplication.class, args);
	}

}
