package com.atguigu.gulimallsearch.service;


import com.atguigu.common.TO.es.SkuEsModel;

import java.util.List;

public interface ProductSaveService {

    void productStatusUp(List<SkuEsModel> skuEsModels);
}
