package com.atguigu.gulimall.product.service.impl;

import com.atguigu.gulimall.product.entity.CategoryBrandRelationEntity;
import com.atguigu.gulimall.product.service.CategoryBrandRelationService;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.gulimall.product.dao.CategoryDao;
import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.atguigu.gulimall.product.service.CategoryService;
import org.springframework.util.StringUtils;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

//    @Autowired
//    private CategoryDao categoryDao;
    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;

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
     * 找到完整路径
     * @param catelogId
     * @return
     */
    @Override
    public Long[] findCateLogPath(Long catelogId) {
        ArrayList<Long> paths = new ArrayList<>();
        List<Long> parentPath = findParentPath(catelogId, paths);
        Collections.reverse(parentPath);
        return paths.toArray(new Long[parentPath.size()]);
    }

    /**
     * 级联更新所有关联的数据
     * @param category
     */
    @Override
    public void updateCascade(CategoryEntity category) {

        boolean b = updateById(category);
        /**
         * 保证关联表的 相同id的字段修改一起修改一致性问题
         */
        if (!StringUtils.isEmpty(category.getName())){
            /**
             * 更新所有表的品牌名字
             */
            LambdaUpdateWrapper<CategoryBrandRelationEntity> setwrapper = new LambdaUpdateWrapper<CategoryBrandRelationEntity>().eq(CategoryBrandRelationEntity::getCatelogId, category.getCatId())
                    .set(CategoryBrandRelationEntity::getCatelogName, category.getName());
            categoryBrandRelationService.update(setwrapper);
            // TODO更新其他关联表
        }
}

    private  List<Long> findParentPath(Long catelogId,List<Long> paths) {
            //手机当前节点id
        CategoryEntity byId = this.getById(catelogId);
        paths.add(catelogId);
        if (byId.getParentCid()!=0){
                findParentPath(byId.getParentCid(),paths);
        }
        return paths;
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
