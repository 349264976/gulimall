package com.atguigu.gulimall.coupon.controller;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = "xuser.test")
//@RefreshScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CouponsProperties {
    private String name;
    private String age;

}
