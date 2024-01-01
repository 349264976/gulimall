package com.atguigu.gulimall.ware.service;

import com.atguigu.gulimall.ware.entity.PurchaseDetailEntity;
import com.atguigu.gulimall.ware.vo.MergeVo;
import com.atguigu.gulimall.ware.vo.PurchaseDoneVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.ware.entity.PurchaseEntity;

import java.util.List;
import java.util.Map;

/**
 * 采购信息
 *
 * @author @lken
 * @email sunlightcs@gmail.com
 * @date 2023-10-22 01:02:37
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);
    public void done(PurchaseDoneVo doneVo);
    public void received(List<Long> ids);
    public void mergePurchase(MergeVo mergeVo);
    public PageUtils queryPageUnreceivePurchase(Map<String, Object> params);
}

