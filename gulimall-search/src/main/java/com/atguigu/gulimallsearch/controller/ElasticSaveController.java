package com.atguigu.gulimallsearch.controller;

import com.atguigu.common.utils.R;
import com.atguigu.gulimallsearch.service.ProductSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search/save")
public class ElasticSaveController {

    @Autowired
    ProductSaveService productSaveService;
    //上架商品

    @PostMapping ("product")
    public R productStatusUp(){

//        productSaveService.productStatusUp();
       return R.ok();
    }



}
