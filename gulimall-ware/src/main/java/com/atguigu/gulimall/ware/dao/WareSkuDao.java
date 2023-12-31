package com.atguigu.gulimall.ware.dao;

import com.atguigu.gulimall.ware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 商品库存
 *
 * @author @lken
 * @email sunlightcs@gmail.com
 * @date 2023-10-22 01:02:37
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {

    void addStock(@Param("skuId") Long skuId, @Param("wareId") Long wareId, @Param("skuNum") Integer skuNum);


    @Select("select SUM(stock-stock_Locked) from `wms_ware_sku` where sku_id=#{skuid}")
    Long getSkuStock(Long skuid);
}
