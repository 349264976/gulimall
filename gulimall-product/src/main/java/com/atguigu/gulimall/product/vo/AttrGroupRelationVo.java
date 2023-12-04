package com.atguigu.gulimall.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "删除基本属性关系的Vo")
@Data
public class AttrGroupRelationVo {

    @ApiModelProperty("属性Id")
    private Long attrId;

    @ApiModelProperty("属性分组Id")
    private Long attrGroupId;

}
