package com.atguigu.gulimall.product.service.impl;

import com.atguigu.gulimall.product.dao.CategoryBrandRelationDao;
import com.atguigu.gulimall.product.entity.CategoryBrandRelationEntity;
import com.atguigu.gulimall.product.service.CategoryBrandRelationService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.gulimall.product.dao.BrandDao;
import com.atguigu.gulimall.product.entity.BrandEntity;
import com.atguigu.gulimall.product.service.BrandService;
import org.springframework.util.StringUtils;


@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {

    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        //1.0获取key
         String key = (String)params.get("key");

         LambdaQueryWrapper<BrandEntity> queryWrapper=new LambdaQueryWrapper<BrandEntity>();

         if (!StringUtils.isEmpty(key)) {
             queryWrapper.eq(BrandEntity::getBrandId,key).or().like(BrandEntity::getName,key);
         }

        IPage<BrandEntity> page = this.page(
                new Query<BrandEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public void updateDetail(BrandEntity brand) {
        /**
         * 保证关联表的 相同id的字段修改一起修改一致性问题
         */
        updateById(brand);

        if (!StringUtils.isEmpty(brand.getName())){
            /**
             * 更新所有表的品牌名字
             */
            LambdaUpdateWrapper<CategoryBrandRelationEntity> setwrapper = new LambdaUpdateWrapper<CategoryBrandRelationEntity>().eq(CategoryBrandRelationEntity::getBrandId, brand.getBrandId())
                    .set(CategoryBrandRelationEntity::getBrandName, brand.getName());
            categoryBrandRelationService.update(setwrapper);
            // TODO更新其他关联表
        }
    }

}
