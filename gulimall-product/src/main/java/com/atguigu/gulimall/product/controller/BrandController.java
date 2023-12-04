package com.atguigu.gulimall.product.controller;

import com.atguigu.common.vaild.AddGroup;
import com.atguigu.common.vaild.UpdateGroup;
import com.atguigu.common.vaild.UpdateGroupStatus;
import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.gulimall.product.entity.BrandEntity;
import com.atguigu.gulimall.product.service.BrandService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.R;



/**
 * 品牌
 *
 * @author @lken
 * @email sunlightcs@gmail.com
 * @date 2023-10-22 14:17:18
 */
@RestController
@RequestMapping("product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = brandService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{brandId}")
    public R info(@PathVariable("brandId") Long brandId){
		BrandEntity brand = brandService.getById(brandId);

        return R.ok().put("brand", brand);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@Validated(value = {AddGroup.class}) @RequestBody BrandEntity brand/*BindingResult result */){
//        if (  result.hasErrors()){
//            Map<String, Object> params = new HashMap<String, Object>();            //1.获取娇艳的错误结果
//            result.getFieldErrors().forEach(
//                    (item)->{
//                        //FieldError
//                        String defaultMessage = item.getDefaultMessage();
//                        //FieldErrorName获取错误的属性名字
//                        String field = item.getField();
//                        params.put(field, defaultMessage);
//                    }
//            );
//            R.error(400,"提交的数据不合法").put("data",params);
//        }else {
            brandService.save(brand);
//        }
        return R.ok();
    }

    /**
     * 修改
     */
    @Transactional
    @RequestMapping("/update")
    public R update(@Validated(UpdateGroup.class)@RequestBody BrandEntity brand){
		brandService.updateDetail(brand);
        return R.ok();
    }

    /**
     * 修改状态
     * @param brand
     * @return
     */
    @RequestMapping("/update/status")
    public R updateStatus(@Validated(UpdateGroupStatus.class)@RequestBody BrandEntity brand){
        brandService.updateById(brand);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] brandIds){
		brandService.removeByIds(Arrays.asList(brandIds));

        return R.ok();
    }

}
