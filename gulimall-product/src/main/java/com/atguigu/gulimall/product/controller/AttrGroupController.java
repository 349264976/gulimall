package com.atguigu.gulimall.product.controller;

import com.atguigu.gulimall.product.entity.AttrEntity;
import com.atguigu.gulimall.product.service.AttrAttrgroupRelationService;
import com.atguigu.gulimall.product.service.AttrService;
import com.atguigu.gulimall.product.service.CategoryService;
import com.atguigu.gulimall.product.vo.AttrGroupRelationVo;
import com.atguigu.gulimall.product.vo.AttrGroupWithAttrsVo;
import com.atguigu.gulimall.product.vo.AttrVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.atguigu.gulimall.product.entity.AttrGroupEntity;
import com.atguigu.gulimall.product.service.AttrGroupService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.R;



/**
 * 属性分组
 *
 * @author @lken
 * @email sunlightcs@gmail.com
 * @date 2023-10-22 14:17:18
 */
@RestController
@RequestMapping("product/attrgroup")
@RequiredArgsConstructor
@Api(value = "关联分组控制器")
public class AttrGroupController {

    private final AttrGroupService attrGroupService;

    private final AttrAttrgroupRelationService attrAttrgroupRelationService;

    private final CategoryService categoryService;

    private final AttrService attrService;

    /**
     * 列表
     */
    @RequestMapping("/list/{catelogId}")
    public R list(@RequestParam Map<String, Object> params,@PathVariable Long catelogId){
//        PageUtils page = attrGroupService.queryPage(params);
        PageUtils page = attrGroupService.queryPage(params,catelogId);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    public R info(@PathVariable("attrGroupId") Long attrGroupId){
		AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        Long catelogId = attrGroup.getCatelogId();
        Long[] path= categoryService.findCateLogPath(catelogId);
        attrGroup.setCatelogPath(path);
        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.save(attrGroup);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrGroupIds){
		attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

    @GetMapping("{attrgroupId}/attr/relation")
    @ApiOperation(value = "获取指定分组关联的所有属性")
    public R getRelation(@PathVariable Long attrgroupId){

       List<AttrEntity> attrEntities= attrService.getRelationAttr(attrgroupId);

        return R.ok().put("data",attrEntities);
    }

    @GetMapping("{attrgroupId}/noattr/relation")
    @ApiOperation(value = "获取指定分组关联的所有属性")
    public R getNoRelation(@PathVariable Long attrgroupId
       ,@RequestParam Map<String,Object> params){

       PageUtils page= attrService.getNoRelationAttr(params,attrgroupId);

        return R.ok().put("page",page);
    }
    /**
     * /product/attrgroup/attr/relation/delete
     */
    @PostMapping("/attr/relation/delete")
    @ApiOperation(value = "删除指定分组关联的所有属性")
    public R deleteRelationAttr(@RequestBody List<AttrGroupRelationVo> attrGroupRelationVos){
        attrAttrgroupRelationService.deleteRelationAttr(attrGroupRelationVos);
        return R.ok();
    }
    /**
     * 新增关联关系
     */

    @PostMapping("/attr/relation")
    public R addRelation(@RequestBody List<AttrGroupRelationVo> attrGroupRelations ){

        attrAttrgroupRelationService.saveBatch(attrGroupRelations);
        return R.ok();
    }

    /**
     * /product/attrgroup/{catelogId}/withattr
     */
    @GetMapping("/{catelogId}/withattr")
    public R getAttrGroupWithAttrs(@PathVariable Long catelogId){

        /**
         * 1.查出当前分类下的所有属性分组
         */

        /**
         * 2.查出当前每个属性分组的所有属性
         */
      List<AttrGroupWithAttrsVo> attrGroupWithAttrsVoList=  attrGroupService.getAttrGroupWithAttrsByCatelogId(catelogId);

      return R.ok().put("data", attrGroupWithAttrsVoList);
    }

}
