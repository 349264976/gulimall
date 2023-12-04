package com.atguigu.gulimall.product.service.impl;

import com.atguigu.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.atguigu.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.atguigu.gulimall.product.service.AttrAttrgroupRelationService;
import com.atguigu.gulimall.product.vo.AttrGroupRelationVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.gulimall.product.dao.AttrGroupDao;
import com.atguigu.gulimall.product.entity.AttrGroupEntity;
import com.atguigu.gulimall.product.service.AttrGroupService;
import org.springframework.util.StringUtils;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catalogId) {
        String key = (String) params.get("key");

        LambdaQueryWrapper<AttrGroupEntity> attrGroupEntityLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(key)){
            attrGroupEntityLambdaQueryWrapper.and(wrapper -> {
                wrapper.eq(AttrGroupEntity::getAttrGroupId,key)
                        .or()
                        .like(AttrGroupEntity::getAttrGroupName,key);
            });
        }

        if (catalogId==0){
            IPage<AttrGroupEntity> page = this.page(
                    new Query<AttrGroupEntity>().getPage(params),
                    attrGroupEntityLambdaQueryWrapper);
            return new PageUtils(page);
        }else {
            attrGroupEntityLambdaQueryWrapper.eq(AttrGroupEntity::getCatelogId,catalogId);
//            QueryWrapper<AttrGroupEntity> attrGroupEntityQueryWrapper = new QueryWrapper<>();
//            attrGroupEntityQueryWrapper.eq("catelog_id", catalogId);
//            if (!StringUtils.isEmpty(key)) {
//                attrGroupEntityQueryWrapper.and(wrapper ->
//                        wrapper.eq("attr_group_id", key)
//                                .or()
//                                .like("attr_group_name", key)
//                );
//            }
            IPage<AttrGroupEntity> page = this.page(
                    new Query<AttrGroupEntity>().getPage(params),
                    attrGroupEntityLambdaQueryWrapper);
            return new PageUtils(page);
        }
    }



}
