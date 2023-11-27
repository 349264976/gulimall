package com.atguigu.gulimall.gulimaillthirdparty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GulimaillThirdPartyApplication {

	public static void main(String[] args) {
		SpringApplication.run(GulimaillThirdPartyApplication.class, args);
	}

}
