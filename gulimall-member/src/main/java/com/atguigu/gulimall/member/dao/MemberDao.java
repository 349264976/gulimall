package com.atguigu.gulimall.member.dao;

import com.atguigu.gulimall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author @lken
 * @email sunlightcs@gmail.com
 * @date 2023-10-22 00:53:19
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
