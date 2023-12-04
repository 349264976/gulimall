package com.atguigu.gulimall.product.service.impl;

import com.atguigu.gulimall.product.vo.AttrGroupRelationVo;
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

import com.atguigu.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.atguigu.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.atguigu.gulimall.product.service.AttrAttrgroupRelationService;


@Service("attrAttrgroupRelationService")
public class AttrAttrgroupRelationServiceImpl extends ServiceImpl<AttrAttrgroupRelationDao, AttrAttrgroupRelationEntity> implements AttrAttrgroupRelationService {
    @Autowired
    private AttrAttrgroupRelationDao attrAttrgroupRelationDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrAttrgroupRelationEntity> page = this.page(
                new Query<AttrAttrgroupRelationEntity>().getPage(params),
                new QueryWrapper<AttrAttrgroupRelationEntity>()
        );

        return new PageUtils(page);
    }
    @Override
    public void deleteRelationAttr(List<AttrGroupRelationVo> attrGroupRelationVos) {

        List<AttrAttrgroupRelationEntity> attrAttrgroupRelationEntitys = attrGroupRelationVos.stream().map(x -> {
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(x, attrAttrgroupRelationEntity);
            return attrAttrgroupRelationEntity;
        }).collect(Collectors.toList());
//        relationDao.deleteid(1);
        attrAttrgroupRelationDao.deleteBatch(attrAttrgroupRelationEntitys);
    }

    @Override
    public void saveBatch(List<AttrGroupRelationVo> attrGroupRelations) {
        List<AttrAttrgroupRelationEntity> attrAttrgroupRelationEntitiesadd = attrGroupRelations.stream().map(item -> {
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(item, attrAttrgroupRelationEntity);
            return attrAttrgroupRelationEntity;
        }).collect(Collectors.toList());
        saveBatch(attrAttrgroupRelationEntitiesadd);
    }
}
