package com.web10.music.mapper;

import com.web10.music.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 评论表 Mapper 接口
 * </p>
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}
