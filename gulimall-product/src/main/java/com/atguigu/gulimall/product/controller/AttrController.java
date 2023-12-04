package com.atguigu.gulimall.product.controller;

import com.atguigu.gulimall.product.vo.AttrRespVo;
import com.atguigu.gulimall.product.vo.AttrVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tags;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.atguigu.gulimall.product.entity.AttrEntity;
import com.atguigu.gulimall.product.service.AttrService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.R;



/**
 * 商品属性
 *
 * @author @lken
 * @email sunlightcs@gmail.com
 * @date 2023-10-22 14:17:17
 */
@RestController
@RequestMapping("product/attr")
//@Tags
@Tag(name = "规格参数分类",description = "规格参数控制器")
public class AttrController {
    @Autowired
    private AttrService attrService;


    /**
     *信息
     */
    @RequestMapping("/info/{attrId}")
    public R info(@PathVariable Long attrId){

        AttrRespVo respVo= attrService.getAttrInfo(attrId);

        return R.ok().put("attr",respVo);
    }

    @GetMapping("/{attrType}/list/{categoryId}")
    @ApiOperation(value = "基础查询分页" )
    public R baseAttrList(@RequestParam Map<String, Object> params,@PathVariable
    Long categoryId
    ,@PathVariable String attrType){

        PageUtils page= attrService.queryBaseAttrPage(params,categoryId,attrType);

        return R.ok().put("page", page);
    }



    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attrService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
//    @RequestMapping("/info/{attrId}")
//    public R info(@PathVariable("attrId") Long attrId){
//		AttrEntity attr = attrService.getById(attrId);
//
//        return R.ok().put("attr", attr);
//    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrVo attr){
		attrService.saveAttr(attr);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrVo attrvo){
		attrService.updateAttr(attrvo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrIds){
		attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}
