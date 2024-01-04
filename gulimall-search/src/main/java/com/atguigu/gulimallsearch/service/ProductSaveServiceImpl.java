package com.atguigu.gulimallsearch.service;


import com.atguigu.common.TO.es.SkuEsModel;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSaveServiceImpl implements ProductSaveService{
    @Autowired
    RestHighLevelClient client;


    @Override
    public void productStatusUp(List<SkuEsModel> skuEsModels) {

        //保存包到es

        //1.给es中简历索引，product 建立好映射关系

        //





    }
}
