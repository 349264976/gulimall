package com.atguigu.gulimall.member.feiclient;

import com.atguigu.common.utils.R;
import java.util.Arrays;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("gulimall-coupon")
public interface CouponFeignService {

    /**
     * 测试openfeign
     * @return ok
     */
    @GetMapping("coupon/coupon/member/list")
    public R membercoupons();


}
