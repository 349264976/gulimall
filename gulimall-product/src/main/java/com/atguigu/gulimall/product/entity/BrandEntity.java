package com.atguigu.gulimall.product.entity;

import com.atguigu.common.vaild.AddGroup;
import com.atguigu.common.vaild.ListValue;
import com.atguigu.common.vaild.UpdateGroup;
import com.atguigu.common.vaild.UpdateGroupStatus;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import javax.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

/**
 * 品牌
 *
 * @author @lken
 * @email sunlightcs@gmail.com
 * @date 2023-10-22 14:17:18
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 品牌id
	 */
	@TableId
    @Null(message = "新增不能指定id",groups = {AddGroup.class})
	@NotNull(message = "修改必须指定品牌id",groups ={ UpdateGroup.class} )
	private Long brandId;
	/**
	 * 品牌名
	 */
	@NotBlank(message = "brand must not null",groups = {AddGroup.class, UpdateGroup.class})
	private String name;
	/**
	 * 品牌logo地址
	 */
	@NotEmpty(groups = {AddGroup.class})
	@URL(message = "logoURL 必须合法",groups = {AddGroup.class, UpdateGroup.class})
	private String logo;

	/**
	 * 介绍
	 */
	private String descript;
	/**
	 * 显示状态[0-不显示；1-显示]
	 */
	@NotNull(groups = {AddGroup.class, UpdateGroup.class})
	@ListValue(values={0,1},groups = {AddGroup.class, UpdateGroupStatus.class})
	private Integer showStatus;
	/**
	 * 检索首字母
	 */
	@NotEmpty(groups = {AddGroup.class})
	@Pattern(regexp = "^[a-zA-Z]$",message = "检索首字母必须是一个字母",groups = {AddGroup.class, UpdateGroup.class})
	private String firstLetter;
	/**
	 * 排序
	 */
	@Min(value = 0,message = "值必须大于0",groups = {AddGroup.class, UpdateGroup.class})
	@NotNull(groups = {AddGroup.class})
	private Integer sort;

}
