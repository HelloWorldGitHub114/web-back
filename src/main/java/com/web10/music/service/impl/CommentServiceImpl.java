package com.web10.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.web10.music.entity.Comment;
import com.web10.music.entity.User;
import com.web10.music.mapper.CommentMapper;
import com.web10.music.mapper.UserMapper;
import com.web10.music.service.ICommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {
    @Resource
    private CommentMapper commentMapper;

    @Resource
    private UserMapper userMapper;

    /**
     * 添加评论
     */
    @Override
    public boolean addComment(Comment comment) {
        return commentMapper.insert(comment) > 0;
    }

    /**
     * 通过id更新评论信息
     */
    @Override
    public boolean updateCommentMsg(Comment comment) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", comment.getId());
        int update = commentMapper.update(comment, queryWrapper);
        return update > 0;
    }

    /**
     * 通过id删除评论
     */
    @Override
    public boolean deleteComment(Integer id) {
        int delete = commentMapper.deleteById(id);
        return delete > 0;
    }

    /**
     * 批量删除评论
     */
    @Override
    public boolean deleteComments(List<Integer> ids) {
        int deleteBatchIds = commentMapper.deleteBatchIds(ids);
        return deleteBatchIds > 0;
    }

    /**
     * 分页查询所有评论
     */
    @Override
    public List<Comment> allComments(int pageNo, int pageSize) {
        // 设置分页查询参数
        PageHelper.startPage(pageNo, pageSize);
        QueryWrapper<Comment> queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("create_time","id");

        return getFullComments(queryWrapper);
    }

    /**
     * 分页查询指定歌曲id下的评论
     */
    @Override
    public List<Comment> commentOfSongId(Integer songId, int pageNo, int pageSize) {
        // 设置分页查询参数
        PageHelper.startPage(pageNo, pageSize);
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("song_id", songId);
        return getFullComments(queryWrapper);
    }

    /**
     * 分页查询指定歌单id下的评论
     */
    @Override
    public List<Comment> commentOfSongListId(Integer songListId, int pageNo, int pageSize) {
        // 设置分页查询参数
        PageHelper.startPage(pageNo, pageSize);
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("song_list_id", songListId);
        return getFullComments(queryWrapper);
    }

    /**
     * 分页查询指定mv id下的评论
     */
    @Override
    public List<Comment> commentOfMvId(Integer mvId, int pageNo, int pageSize) {
        // 设置分页查询参数
        PageHelper.startPage(pageNo, pageSize);
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mv_id", mvId);
        return getFullComments(queryWrapper);
    }

    private List<Comment> getFullComments(QueryWrapper<Comment> queryWrapper) {
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        for(Comment comment : comments){
            User user = userMapper.findUserNickNameAndAvatarById(comment.getUserId());
            comment.setNickname(user.getNickname());
            comment.setAvatar(user.getAvatar());
        }
        return comments;
    }

    @Override
    public int countOfCommentOfSongId(int songId){
        return commentMapper.selectCount(new QueryWrapper<Comment>().eq("song_id", songId));
    }

    @Override
    public int countOfCommentOfSongListId(int songListId){
        return commentMapper.selectCount(new QueryWrapper<Comment>().eq("song_list_id", songListId));
    }

    @Override
    public int countOfCommentOfMvId(int mvId){
        return commentMapper.selectCount(new QueryWrapper<Comment>().eq("mv_id", mvId));
    }
}
