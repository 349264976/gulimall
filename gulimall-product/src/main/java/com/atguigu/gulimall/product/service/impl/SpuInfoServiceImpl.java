package com.atguigu.gulimall.product.service.impl;

import com.atguigu.common.TO.SkuHasStockVo;
import com.atguigu.common.TO.SkuReductionTo;
import com.atguigu.common.TO.SpuBoundTo;
import com.atguigu.common.TO.es.SkuEsModel;
import com.atguigu.common.utils.R;
import com.atguigu.gulimall.product.entity.*;
import com.atguigu.gulimall.product.fegin.CouponFeignService;
import com.atguigu.gulimall.product.fegin.WareFeignService;
import com.atguigu.gulimall.product.service.*;
import com.atguigu.gulimall.product.vo.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.gulimall.product.dao.SpuInfoDao;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Autowired
    private SpuInfoDescService spuInfoDescService;
    @Autowired
    private SpuImagesService spuImagesService;

    @Autowired
    private AttrService attrService;

    @Autowired
    private ProductAttrValueService productAttrValueService;

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private SkuImagesService skuImagesService;

    @Autowired

    private SkuSaleAttrValueService skuSaleAttrValueService;

    @Autowired
    CouponFeignService couponFeignService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    WareFeignService wareFeignService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void saveSpuInfo(SpuSaveVo spuInfovo) {

        /**
         * 1.保存基本信息 pms_spu_info
         */
        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
        BeanUtils.copyProperties(spuInfovo, spuInfoEntity);
        spuInfoEntity.setCreateTime(new Date());
        spuInfoEntity.setUpdateTime(new Date());
        this.saveBasgeSpuInfo(spuInfoEntity);
        //2. 保存spu的描述图片 pms_spu_info_desc
        List<String> decript = spuInfovo.getDecript();

        SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity();
        spuInfoDescEntity.setSpuId(spuInfoEntity.getId());
        spuInfoDescEntity.setDecript(String.join(",", decript));
        spuInfoDescService.saveSpuInfoDesc(spuInfoDescEntity);
        //3.保存spu的图片集 pms_spu_images

        List<String> images = spuInfovo.getImages();
        spuImagesService.saveImages(spuInfoEntity.getId(), images);
        //4.spu的规格参数 pms_product_attr_value
        List<BaseAttrs> baseAttrs = spuInfovo.getBaseAttrs();

        List<ProductAttrValueEntity> productAttrValueEntityList = baseAttrs.stream().map(attr -> {

            ProductAttrValueEntity productAttrValueEntity = new ProductAttrValueEntity();

            productAttrValueEntity.setAttrId(attr.getAttrId());

            AttrEntity byId = attrService.getById(attr.getAttrId());
            productAttrValueEntity.setAttrName(byId.getAttrName());
            productAttrValueEntity.setAttrValue(attr.getAttrValues());

            productAttrValueEntity.setQuickShow(attr.getShowDesc());
            productAttrValueEntity.setSpuId(spuInfoEntity.getId());

            return productAttrValueEntity;

        }).collect(Collectors.toList());
        productAttrValueService.saveProductAttr(productAttrValueEntityList);


        //5.保存spu的积分信息 gulimall_sms_spu_bounds
        Bounds bounds = spuInfovo.getBounds();
        SpuBoundTo spuBoundTo = new SpuBoundTo();
        BeanUtils.copyProperties(bounds, spuBoundTo);
        spuBoundTo.setSpuId(spuInfoEntity.getId());
        couponFeignService.saveSpuBounds(spuBoundTo);
        //5.保存当前spu对应的sku信息

        //5.1sku的基本信息 pms_sku_info
        List<Skus> skus = spuInfovo.getSkus();
        if (!skus.isEmpty()) {
            skus.forEach(item -> {
                String defaultImage = "";
                for (Images imagess : item.getImages()) {
                    if (imagess.getDefaultImg() == 1) {
                        defaultImage = imagess.getImgUrl();
                    }
                }

                SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
                BeanUtils.copyProperties(item, skuInfoEntity);
                skuInfoEntity.setBrandId(spuInfoEntity.getBrandId());

                skuInfoEntity.setCatalogId(skuInfoEntity.getCatalogId());
                skuInfoEntity.setSaleCount(0L);
                skuInfoEntity.setSpuId(spuInfoEntity.getId());
                skuInfoEntity.setSkuDefaultImg(defaultImage);
                skuInfoService.saveSkuinfo(skuInfoEntity);
                Long skuId = skuInfoEntity.getSkuId();
                List<SkuImagesEntity> imagesEntities = item.getImages().stream().map(image -> {
                            SkuImagesEntity skuImagesEntity = new SkuImagesEntity();

                            skuImagesEntity.setSkuId(skuId);
                            skuImagesEntity.setImgUrl(image.getImgUrl());
                            skuImagesEntity.setDefaultImg(image.getDefaultImg());
                            return skuImagesEntity;
                        }).filter(entity -> !StringUtils.isEmpty(entity.getImgUrl()))
                        .collect(Collectors.toList());
                //5.2保存sku的图片信息 pms_sku_images
                //TODO 空白图片地址不保存
                skuImagesService.saveBatch(imagesEntities);
                ///5.3sku的销售属性信息 pms_sku_sale_attr_value
                List<Attr> attr = item.getAttr();
                List<SkuSaleAttrValueEntity> skuSaleAttrValueEntities = attr.stream().map(x -> {
                            SkuSaleAttrValueEntity skuSaleAttrValueEntity = new SkuSaleAttrValueEntity();
                            BeanUtils.copyProperties(x, skuSaleAttrValueEntity);
                            skuSaleAttrValueEntity.setSkuId(skuId);
                            return skuSaleAttrValueEntity;
                        }
                ).collect(Collectors.toList());
                skuSaleAttrValueService.saveBatch(skuSaleAttrValueEntities);

                //5.4sku的优惠信息 满减等信息gulimall_sms ->sms_sku_ladder\SMS_SKU_full_reduction\sms_member_price

                SkuReductionTo skuReductionTo = new SkuReductionTo();
                BeanUtils.copyProperties(item, skuReductionTo);
                skuReductionTo.setSkuId(skuId);
//                skuReductionTo.setMemberPrice(item.getMemberPrice());
//                skuReductionTo.setSkuId(skuId);
//                skuReductionTo.setSkuId(skuId);
                if (skuReductionTo.getFullCount() > 0 || skuReductionTo.getFullPrice().compareTo(new BigDecimal("0")) == 1) {
                    R r = couponFeignService.saveSkuReduction(skuReductionTo);
                    if (r.getCode() != 0) {
                        log.error("保存积分信息失败 skuReduction remote error");
                    }
                }


            });
        }

    }

    @Override
    public void saveBasgeSpuInfo(SpuInfoEntity spuInfoEntity) {

        this.baseMapper.insert(spuInfoEntity);

    }

    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {

        QueryWrapper<SpuInfoEntity> wrapper = new QueryWrapper<>();

        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            wrapper.and((w) -> {
                w.eq("id", key).or().like("spu_name", key);
            });
        }
        // status=1 and (id=1 or spu_name like xxx)
        String status = (String) params.get("status");
        if (!StringUtils.isEmpty(status)) {
            wrapper.eq("publish_status", status);
        }

        String brandId = (String) params.get("brandId");
        if (!StringUtils.isEmpty(brandId) && !"0".equalsIgnoreCase(brandId)) {
            wrapper.eq("brand_id", brandId);
        }

        String catelogId = (String) params.get("catelogId");
        if (!StringUtils.isEmpty(catelogId) && !"0".equalsIgnoreCase(catelogId)) {
            wrapper.eq("catalog_id", catelogId);
        }

        /**
         * status: 2
         * key:
         * brandId: 9
         * catelogId: 225
         */

        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    @Override
    public void up(Long spuId) {

        //上架数据实体
        List<SkuEsModel> upProductions = new ArrayList<SkuEsModel>();

        //1.组装所需要的数据

        SkuEsModel esModel = new SkuEsModel();

        //1.查询出所有的sku信息

        List<SkuInfoEntity> skuInfoEntities = skuInfoService.getSkusBySpuid(spuId);
        List<Long> skuIds = skuInfoEntities.stream().map(SkuInfoEntity::getSkuId).collect(Collectors.toList());

        //2.封装sku信息
        //TODO 4. 查询所有可以检索的skp的规格属性  Attrs  按照spu
        List<ProductAttrValueEntity> baseAttr = productAttrValueService.baseAttrlistforspu(spuId);

        //过滤出所有的属性id
        List<Long> attrids = baseAttr.stream().map(
                attr -> {
                    return attr.getAttrId();
                }
        ).collect(Collectors.toList());

        //过滤出所有的属性id  查询出可以检索的属性id
        List<Long> searchAttrIds = attrService.selectSearchAttrs(attrids);
        Set<Long> idSet = new HashSet<>(searchAttrIds);
        //过滤出可以检索的属性集合
        //上架数据实体的attrs属性
        List<SkuEsModel.Attrs> attrs = baseAttr.stream().filter(
                item -> {
                    return idSet.contains(item.getAttrId());
                }
        ).map(
                x -> {
                    SkuEsModel.Attrs attrs1 = new SkuEsModel.Attrs();
                    BeanUtils.copyProperties(x, attrs1);

                    return attrs1;
                }
        ).collect(Collectors.toList());
        Map<Long, Boolean> hasStockMap = null;

        R<List<SkuHasStockVo>> skusHasStock=new R<>();
        try {
            //TODO 1.发送远程调用是否有库存
            skusHasStock= wareFeignService.getSkusHasStock(skuIds);
            hasStockMap = skusHasStock.getData().stream()
                    .collect(
                            Collectors.
                                    toMap(SkuHasStockVo::getSkuId, SkuHasStockVo::getHasStock));
        } catch (Exception e) {
            log.error("库存服务查询异常: 原因{}",e);
        }


        Map<Long, Boolean> finalHasStockMap = hasStockMap;
        upProductions = skuInfoEntities.stream().map(
                x -> {
                    SkuEsModel model = new SkuEsModel();

                    BeanUtils.copyProperties(x, model);
                    /**
                     * 名字不一致的单独处理
                     */
                    model.setSkuPrice(x.getPrice());
                    model.setSkuImg(x.getSkuDefaultImg());
                    //设置库存信息
                    if (finalHasStockMap==null){
                        esModel.setHasStock(true);
                    }else {
                        esModel.setHasStock(finalHasStockMap.get(x.getSkuId()));
                    }
                    //TODO 2.热度评分
                    esModel.setHotScore(0L);

                    //TODO 3. 查询品牌和分类的名字信息
                    BrandEntity brandone = brandService.getById(model.getBrandId());

                    esModel.setBrandName(brandone.getName());
                    esModel.setBrandImg(brandone.getLogo());

                    CategoryEntity categoryone = categoryService.getById(model.getCategoryId());

                    esModel.setCatalogImg(categoryone.getIcon());

                    esModel.setAttrs(attrs);
                    return model;
                }
        ).collect(Collectors.toList());
        //TODO 5.数据发送给es保存




    }

}
