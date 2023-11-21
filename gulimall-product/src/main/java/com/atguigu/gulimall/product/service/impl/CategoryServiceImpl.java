package com.atguigu.gulimall.product.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.gulimall.product.dao.CategoryDao;
import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.atguigu.gulimall.product.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

//    @Autowired
//    private CategoryDao categoryDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {

        //1.查询所有分类
        List<CategoryEntity> categoryEntities = baseMapper.selectList(null);
        List<CategoryEntity> level1 = categoryEntities.stream()
                .filter(x -> x.getParentCid() == 0)
                .map(x -> {
                    x.setChildren(getChildrens(x, categoryEntities));
                    return x;
                })
                .sorted((x1, x2) -> {
                    return (null==x1.getSort())?0:x1.getSort() - ((null==x2.getSort())?0:x2.getSort());
                })
                .collect(Collectors.toList());
        //2.组装成父子树形接口
        return level1;
    }

    @Override
    public void removeMenuByIds(List<Long> list) {
        /**
         * TODO: 1.检查当前删除菜单 是否被其他功能引用
         */
        baseMapper.deleteBatchIds(list);
    }

    /**
     * 递归查找所有菜单的子菜单
     *
     * @param category
     * @param menus
     * @return
     */
    private List<CategoryEntity> getChildrens(CategoryEntity category, List<CategoryEntity> menus) {

        List<CategoryEntity> childs = menus.stream()
                .filter(x -> x.getParentCid() == category.getCatId())
                .map(x -> {
                    x.setChildren(getChildrens(x, menus));
                    return x;
                })
                .sorted((x1, x2) -> {
                    return (null == x1.getSort()) ? 0 : x1.getSort() - ((null == x2.getSort()) ? 0 : x2.getSort());
                })
                .collect(Collectors.toList());
        return childs;
    }

}
