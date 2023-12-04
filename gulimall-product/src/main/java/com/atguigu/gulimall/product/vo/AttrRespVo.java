package com.atguigu.gulimall.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class AttrRespVo extends AttrVo {
    /**
     * 分类名字
     *
     *
     * 所属分组
     */
    private  String catelogName;

    private String groupName;

    @ApiModelProperty(value = "分类路径信息")
    private Long[] catelogPath;
}
