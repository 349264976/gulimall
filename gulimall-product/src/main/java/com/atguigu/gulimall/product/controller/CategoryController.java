package com.atguigu.gulimall.product.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.atguigu.gulimall.product.service.CategoryService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.R;



/**
 * 商品三级分类
 *
 * @author @lken
 * @email sunlightcs@gmail.com
 * @date 2023-10-22 14:17:18
 */
@RestController
@RequestMapping("product/category")
@Api(tags = "产品分类模块")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 列表
     *
     * 查出所有分类以及子分类以树形结构封装起来
     */
    @RequestMapping("/list/tree")
    @ApiOperation(value = "获取树形菜单")
    public R list(){
        List<CategoryEntity> categoryEntitiestree = categoryService.listWithTree();
        return R.ok().put("data", categoryEntitiestree);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    public R info(@PathVariable("catId") Long catId){
		CategoryEntity category = categoryService.getById(catId);

        return R.ok().put("data", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryEntity category){
		categoryService.save(category);

        return R.ok();
    }

    /**
     * 修改
     */
    @Transactional
    @RequestMapping("/update")
    public R update(@RequestBody CategoryEntity category){
		categoryService.updateCascade(category);

        return R.ok();
    }
    /**
     * 修改
     */
    @RequestMapping("/update/sort")
    public R updatesort(@RequestBody CategoryEntity[] category){
        categoryService.updateBatchById(Arrays.asList(category));
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] catIds){
//		categoryService.removeByIds(Arrays.asList(catIds));
        categoryService.removeMenuByIds(Arrays.asList(catIds));
        return R.ok();
    }
}
