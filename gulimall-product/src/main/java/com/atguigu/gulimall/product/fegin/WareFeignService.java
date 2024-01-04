package com.atguigu.gulimall.product.fegin;

import com.atguigu.common.TO.SkuHasStockVo;
import com.atguigu.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("gulimall-ware")
public interface WareFeignService {
    /**
     * R设计的啥时候可以加上泛型
     * 2.直接返回我们想要的结果
     * 3.自己封装解析的结果
     * @param skuids
     * @return
     */
    @PostMapping("/hasstock")
    R<List<SkuHasStockVo>> getSkusHasStock(@RequestBody List<Long> skuids);

}
