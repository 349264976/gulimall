package com.atguigu.gulimall.ware.service.impl;

import com.atguigu.common.utils.R;
import com.atguigu.gulimall.ware.feign.ProductFeignService;
import com.atguigu.gulimall.ware.vo.SkuHasStockVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.gulimall.ware.dao.WareSkuDao;
import com.atguigu.gulimall.ware.entity.WareSkuEntity;
import com.atguigu.gulimall.ware.service.WareSkuService;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;


@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    @Autowired

    private WareSkuDao wareSkuDao;

    @Autowired
    ProductFeignService productFeignService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        /**
         * skuId: 1
         * wareId: 2
         */
        QueryWrapper<WareSkuEntity> queryWrapper=new QueryWrapper<>();
        String skuId = (String) params.get("skuId");

        if (!StringUtils.isEmpty(skuId)){
            queryWrapper.eq("sku_id",skuId);
        }

        String wareId = (String)params.get("wareId");
        if (!StringUtils.isEmpty(wareId)){
            queryWrapper.eq("ware_id",wareId);
        }
        IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public void addStock(Long skuId, Long wareId, Integer skuNum) {

        //1.判断是不是还没有这个条库存记录

        List<WareSkuEntity> wareSkuEntities = wareSkuDao.selectList(new LambdaQueryWrapper<WareSkuEntity>()
                .eq(WareSkuEntity::getSkuId, skuId)
                .eq(WareSkuEntity::getWareId, wareId)
        );
        if (CollectionUtils.isEmpty(wareSkuEntities)){
            WareSkuEntity wareSkuEntity = new WareSkuEntity();
            wareSkuEntity.setSkuId(skuId);
            wareSkuEntity.setWareId(wareId);
            wareSkuEntity.setStock(skuNum);
            wareSkuEntity.setStockLocked(0);
            R info = productFeignService.info(skuId);
            try {
                if(info.getCode()==0){
                    Map<String,Object> data = (Map<String, Object>) info.get("skuInfo");
                    wareSkuEntity.setSkuName((String) data.get("skuName"));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            wareSkuDao.insert(wareSkuEntity);
        }else {
            wareSkuDao.addStock(skuId,wareId,skuNum);
        }
    }

    @Override
    public List<SkuHasStockVo> getSkusHasStock(List<Long> skuids) {

        List<SkuHasStockVo> skuStockVos = skuids.stream().map(skuid -> {

            SkuHasStockVo vo = new SkuHasStockVo();

            //数据库查询出数据封装    //查新当前sku的总库存量
          Long count= wareSkuDao.getSkuStock(skuid);

          vo.setHasStock(count==null?false:count>0);

          vo.setSkuId(skuid);

          vo.setSkuCount(count);

            return vo;
        }).collect(Collectors.toList());

        return skuStockVos;
    }

}
