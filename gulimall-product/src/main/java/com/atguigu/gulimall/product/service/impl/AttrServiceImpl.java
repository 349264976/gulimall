package com.atguigu.gulimall.product.service.impl;

import com.atguigu.common.constant.ProductConstant;
import com.atguigu.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.atguigu.gulimall.product.dao.AttrGroupDao;
import com.atguigu.gulimall.product.dao.CategoryDao;
import com.atguigu.gulimall.product.entity.*;
import com.atguigu.gulimall.product.service.AttrAttrgroupRelationService;
import com.atguigu.gulimall.product.service.CategoryService;
import com.atguigu.gulimall.product.vo.AttrGroupRelationVo;
import com.atguigu.gulimall.product.vo.AttrRespVo;
import com.atguigu.gulimall.product.vo.AttrVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
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

import com.atguigu.gulimall.product.dao.AttrDao;
import com.atguigu.gulimall.product.service.AttrService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    AttrAttrgroupRelationDao relationDao;

    @Autowired
    AttrGroupDao attrGroupDao;

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    AttrDao attrDao;

    @Autowired
    CategoryService categoryService;

    @Autowired
    AttrAttrgroupRelationService attrAttrgroupRelationService;



    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );
        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void saveAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        /**
         * 1.保存基本信息
         */
        save(attrEntity);

        /**
         * 2.保存关联关系
         */
        AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
        if (attr.getAttrType()== ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()
        &&attr.getAttrGroupId()!=null){
            attrAttrgroupRelationEntity.setAttrGroupId(attr.getAttrGroupId());
        }
        attrAttrgroupRelationEntity.setAttrId(attrEntity.getAttrId());
        relationDao.insert(attrAttrgroupRelationEntity);
    }

    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, Long categoryId, String attrType) {
        LambdaQueryWrapper<AttrEntity> attrEntityLambdaQueryWrapper = new LambdaQueryWrapper<AttrEntity>()
                .eq(AttrEntity::getAttrType,"base".equalsIgnoreCase(attrType)?ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode():ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());
        if (categoryId!=0){
            attrEntityLambdaQueryWrapper.eq(AttrEntity::getAttrId,categoryId);

        }
        String key = (String)params.get("key");
        if (!StringUtils.isEmpty(key)){
            attrEntityLambdaQueryWrapper.and(LambdaQueryWrapper -> {
                        LambdaQueryWrapper.eq(AttrEntity::getCatelogId,key).or().like(AttrEntity::getAttrName,key);
                    } );
        }
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                attrEntityLambdaQueryWrapper
        );
        PageUtils pageUtils = new PageUtils(page);
        List<AttrEntity> records = page.getRecords();
        List<AttrRespVo> AttrRespVos = records.stream().map(x -> {
            AttrRespVo attrRespVo = new AttrRespVo();
            BeanUtils.copyProperties(x, attrRespVo);

            if ("base".equalsIgnoreCase(attrType)){
                AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = relationDao.selectOne(new LambdaQueryWrapper<AttrAttrgroupRelationEntity>()
                        .eq(AttrAttrgroupRelationEntity::getAttrId, x.getAttrId())
                );

                if (attrAttrgroupRelationEntity != null) {
                    AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrAttrgroupRelationEntity.getAttrGroupId());
                    if (attrGroupEntity != null) {
                        attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
                    }
                }
            }
            CategoryEntity categoryEntity = categoryDao.selectById(x.getCatelogId());
            if (categoryEntity != null) {
                attrRespVo.setCatelogName(categoryEntity.getName());
            }
            return attrRespVo;
        }).collect(Collectors.toList());
        pageUtils.setList(AttrRespVos);
        return pageUtils;
    }

    @Override
    public AttrRespVo getAttrInfo(Long attrId) {

        AttrRespVo attrRespVo = new AttrRespVo();

        AttrEntity attrEntity = getById(attrId);

        BeanUtils.copyProperties(attrEntity, attrRespVo);

        if (attrEntity.getAttrType()==ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()){
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = relationDao.selectOne(new LambdaQueryWrapper<AttrAttrgroupRelationEntity>()
                    .eq(AttrAttrgroupRelationEntity::getAttrId, attrId)
            );
            /**
             * 设置分组信息
             */

            if (attrAttrgroupRelationEntity!=null) {
                attrRespVo.setAttrGroupId(attrAttrgroupRelationEntity.getAttrGroupId());
                AttrGroupEntity attrGroupEntity = attrGroupDao.selectOne(new LambdaQueryWrapper<AttrGroupEntity>()
                        .eq(AttrGroupEntity::getAttrGroupId, attrAttrgroupRelationEntity.getAttrGroupId())
                );
                if (attrGroupEntity!=null){
                    attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
                }
            }
        }

        /**
         * 设置分类信息
         */
        Long[] cateLogPath = categoryService.findCateLogPath(attrEntity.getCatelogId());
        attrRespVo.setCatelogPath(cateLogPath);

        CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCatelogId());
        if (categoryEntity!=null)
        attrRespVo.setCatelogName(categoryEntity.getName());

        return attrRespVo;
    }

    @Override
    public void updateAttr(AttrVo attrvo) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attrvo,attrEntity);
        boolean b = updateById(attrEntity);

        if (attrEntity.getAttrType()==ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()){
            /**
             * 修改分组管理按
             */
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            attrAttrgroupRelationEntity.setAttrGroupId(attrvo.getAttrGroupId());
            attrAttrgroupRelationEntity.setAttrId(attrvo.getAttrId());

            Integer count = relationDao.selectCount(new LambdaUpdateWrapper<AttrAttrgroupRelationEntity>()
                    .eq(AttrAttrgroupRelationEntity::getAttrId, attrvo.getAttrId())
            );
            if (count>0){
                relationDao.update(attrAttrgroupRelationEntity,new LambdaUpdateWrapper<AttrAttrgroupRelationEntity>()
                        .eq(AttrAttrgroupRelationEntity::getAttrId,attrvo.getAttrId()));

            }else {
                relationDao.insert(attrAttrgroupRelationEntity);
            }
        }

    }

    /**
     * 根据分组iD查找关联的基本属性
     * @param attrgroupId
     * @return
     */

    @Override
    public List<AttrEntity> getRelationAttr(Long attrgroupId) {

        List<AttrAttrgroupRelationEntity> attrAttrgroupRelationEntities = relationDao.selectList(
                new LambdaQueryWrapper<AttrAttrgroupRelationEntity>()
                        .eq(AttrAttrgroupRelationEntity::getAttrGroupId,attrgroupId)
        );
        List<Long> attrIds = attrAttrgroupRelationEntities.stream().map(x -> {
            return x.getAttrId();
        }).collect(Collectors.toList());
        if (attrIds==null || attrIds.size()<1) return null;
        List<AttrEntity> attrEntities = listByIds(attrIds);
        return attrEntities;
    }

    /**
     * 获取当前分组没有货关联的所有属性
     * @param params
     * @param attrgroupId
     * @return
     */
    @Override
    public PageUtils getNoRelationAttr(Map<String, Object> params, Long attrgroupId) {

        //1.当前分组只能关联自己所属分类里面的所有属性

        AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrgroupId);

        Long catelogId = attrGroupEntity.getCatelogId();

        //2.当前分组只能关联别的分组没有引用的属性
            //2.1当前分类下其他分组
        List<AttrGroupEntity> attrGroupEntities = attrGroupDao.selectList(new LambdaQueryWrapper<AttrGroupEntity>()
                .eq(AttrGroupEntity::getCatelogId, catelogId));
        List<Long> groupIds = attrGroupEntities.stream().map(AttrGroupEntity::getAttrGroupId).collect(Collectors.toList());
        //2.2这些分组的关联属性
        LambdaQueryWrapper<AttrAttrgroupRelationEntity> inotherattrLqw = new LambdaQueryWrapper<AttrAttrgroupRelationEntity>()
                .in(AttrAttrgroupRelationEntity::getAttrGroupId, groupIds);
        List<AttrAttrgroupRelationEntity> attrAttrgroupRelationEntities = relationDao.selectList(inotherattrLqw);
        List<Long> attrIds = attrAttrgroupRelationEntities.stream().map(AttrAttrgroupRelationEntity::getAttrId).collect(Collectors.toList());
        //2.3从大能签分类的所有属性移除这写属性
        LambdaQueryWrapper<AttrEntity> attrEntityLambdaQueryWrapper = new LambdaQueryWrapper<AttrEntity>()
                .eq(AttrEntity::getCatelogId, catelogId)
                .eq(AttrEntity::getAttrType,ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode());
        if (attrIds!=null && attrIds.size()>0){
            attrEntityLambdaQueryWrapper.notIn(AttrEntity::getAttrId, attrIds);
        }
        String key = (String)params.get("key");
        if (StringUtils.isEmpty(key)){
            attrEntityLambdaQueryWrapper.and(
                   x->{
                       x.eq(AttrEntity::getAttrId,key).or()
                               .like(AttrEntity::getAttrName,key);
                   }
            );
        }
        IPage<AttrEntity> page = page(new Query<AttrEntity>().getPage(params)
                , attrEntityLambdaQueryWrapper);
        //2.4
        PageUtils pageUtils=new PageUtils(page);
        return pageUtils;
    }


}
