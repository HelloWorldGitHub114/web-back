package com.web10.music.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web10.music.entity.Comment;
import com.web10.music.mapper.CommentMapper;
import com.web10.music.service.ICommentService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {
}
