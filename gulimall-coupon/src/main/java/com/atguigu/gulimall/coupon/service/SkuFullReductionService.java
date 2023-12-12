package com.atguigu.gulimall.coupon.service;

import com.atguigu.common.TO.SkuReductionTo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.coupon.entity.SkuFullReductionEntity;

import java.util.Map;

/**
 * 商品满减信息
 *
 * @author @lken
 * @email sunlightcs@gmail.com
 * @date 2023-10-22 14:12:29
 */
public interface SkuFullReductionService extends IService<SkuFullReductionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    Boolean saveSkuReduction(SkuReductionTo skuReductionTo);
}

