package com.atguigu.gulimall.product.service;

import com.atguigu.gulimall.product.vo.AttrGroupRelationVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.product.entity.AttrAttrgroupRelationEntity;

import java.util.List;
import java.util.Map;

/**
 * 属性&属性分组关联
 *
 * @author @lken
 * @email sunlightcs@gmail.com
 * @date 2023-10-22 14:17:18
 */
public interface AttrAttrgroupRelationService extends IService<AttrAttrgroupRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void deleteRelationAttr(List<AttrGroupRelationVo> attrGroupRelationVos);

    void saveBatch(List<AttrGroupRelationVo> attrGroupRelations);
}

