package com.atguigu.gulimall.product.fegin;

import com.atguigu.common.TO.es.SkuEsModel;
import com.atguigu.common.utils.R;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("gulimall-search")
public interface SearchFeignService {

    @PostMapping("/search/save/product")
    public R prodductStatusUp(@RequestBody List<SkuEsModel> skuEsModels);
}
