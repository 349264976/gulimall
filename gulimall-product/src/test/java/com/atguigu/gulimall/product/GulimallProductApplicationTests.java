package com.atguigu.gulimall.product;

import com.atguigu.gulimall.product.entity.BrandEntity;
import com.atguigu.gulimall.product.service.BrandService;
import com.atguigu.gulimall.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
//@RequiredArgsConstructor
class GulimallProductApplicationTests {
    @Autowired
    private  BrandService brandService;


    @Autowired
    CategoryService categoryService;
    @Test
    public void testFindPat() {
        Long[] cateLogPath = categoryService.findCateLogPath(225L);
        log.info("完整路径：{}" ,cateLogPath);
    }



    @Test
    void contextLoads() {
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setName("Brand1");
        brandService.save(brandEntity);
        System.out.println("Successfully saved");
    }

}
