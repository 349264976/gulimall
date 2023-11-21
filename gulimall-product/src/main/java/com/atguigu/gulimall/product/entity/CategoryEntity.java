package com.atguigu.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Data;

/**
 * 商品三级分类
 *
 * @author @lken
 * @email sunlightcs@gmail.com
 * @date 2023-10-22 14:17:18
 */
@Data
@TableName("pms_category")
@ApiModel(value = "CategoryEntity", description = "分类实体")
public class CategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 分类id
	 */
	@TableId
	@ApiModelProperty(value = "主键")
	private Long catId;
	/**
	 * 分类名称
	 */
	@ApiModelProperty(value = "分类名称")
	private String name;
	/**
	 * 父分类id
	 */
	@ApiModelProperty(value = "父分类id")
	private Long parentCid;
	/**
	 * 层级
	 */
		@ApiModelProperty(value = "层级")
	private Integer catLevel;
	/**
	 * 是否显示[0-不显示，1显示]
	 */
	@ApiModelProperty(value = "是否显示[0-不显示，1显示]")
	@TableLogic(value = "1",delval = "0")
	private Integer showStatus;
	/**
	 * 排序
	 */
	@ApiModelProperty(value = "排序")
	private Integer sort;
	/**
	 * 图标地址
	 */
	@ApiModelProperty(value = "图标地址")
	private String icon;
	/**
	 * 计量单位
	 */
	@ApiModelProperty(value = "计量单位")
	private String productUnit;
	/**
	 * 商品数量
	 */
	@ApiModelProperty(value = "商品数量")
	private Integer productCount;

	/**
	 * 子分类
	 */
	@TableField(exist = false)
	@ApiModelProperty(value = "子分类")
	private List<CategoryEntity> children;

}
