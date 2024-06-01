package com.web10.music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.web10.music.entity.Comment;

import java.util.List;

/**
 * <p>
 * 评论表 服务类
 * </p>
 */
public interface ICommentService extends IService<Comment> {
    /**
     * 添加评论
     */
    boolean addComment(Comment comment);

    /**
     * 通过id更新评论信息
     */
    boolean updateCommentMsg(Comment comment);

    /**
     * 通过id删除评论
     */
    boolean deleteComment(Integer id);

    /**
     * 批量删除评论
     */
    boolean deleteComments(List<Integer> ids);


    /**
     * 分页查询所有评论
     */
    List<Comment> allComments(int pageNo, int pageSize);

    /**
     * 分页查询指定歌曲id下的评论
     */
    List<Comment> commentOfSongId(Integer songId, int pageNo, int pageSize);

    /**
     * 分页查询指定歌单id下的评论
     */
    List<Comment> commentOfSongListId(Integer songListId, int pageNo, int pageSize);

    /**
     * 分页查询指定mv id下的评论
     */
    List<Comment> commentOfMvId(Integer mvId, int pageNo, int pageSize);

    int countOfCommentOfSongId(int songId);

    int countOfCommentOfSongListId(int songListId);

    int countOfCommentOfMvId(int mvId);
}
