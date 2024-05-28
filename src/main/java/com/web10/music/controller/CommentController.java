package com.web10.music.controller;


import com.github.pagehelper.PageInfo;
import com.web10.music.commons.result.Result;
import com.web10.music.entity.Comment;
import com.web10.music.entity.Song;
import com.web10.music.entity.SongList;
import com.web10.music.entity.User;
import com.web10.music.service.ICommentService;
import com.web10.music.service.ISongListService;
import com.web10.music.service.ISongService;
import com.web10.music.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 评论表 前端控制器
 * </p>
 */
@RestController
@Api(tags = "评论管理控制器")
@RequestMapping("/comment")
public class CommentController {
    @Resource
    private ICommentService commentService;

    @Resource
    private IUserService userService;

    @Resource
    private ISongService songService;

    @Resource
    private ISongListService songListService;

    /**
     * 添加评论
     */
    @ApiOperation("添加评论")
    @PostMapping("add")
    public Result addComment(@RequestBody Comment comment) {
        //不需要进行去重，同一个用户可以对同一个歌单的同一首歌发表内容一模一样的评论
        int userId = comment.getUserId();
        int songId = comment.getSongId();
        int songListId = comment.getSongListId();
        User user = userService.findUserById(userId);
        Song song = songService.findSongById(songId);
        SongList songList = songListService.findById(songListId);
        if(user==null||song==null||songList==null){
            return Result.error("400","非法操作，请检查您输入的账号状态，歌单状态是否正常");
        }
        boolean addComment = commentService.addComment(comment);
        if (addComment) {
            return Result.ok("添加成功");
        } else {
            return Result.ok("添加失败");
        }
    }

    /**
     * 分页查询所有评论
     */
    @ApiOperation("分页查询所有评论")
    @GetMapping("all/{pageNo}/{pageSize}")
    public Result allComments(@PathVariable int pageNo, @PathVariable int pageSize) {
        PageInfo<Comment> pageInfo = commentService.allComments(pageNo, pageSize);
        List<Comment> comments = pageInfo.getList();
        return Result.ok(comments);
    }

    /**
     * 分页查询指定歌曲id下的评论
     */
    @ApiOperation("分页查询指定歌曲id下的评论")
    @GetMapping("detail-songId/{songId}/{pageNo}/{pageSize}")
    public Result commentOfSongId(@PathVariable int songId, @PathVariable int pageNo, @PathVariable int pageSize) {
        PageInfo<Comment> pageInfo = commentService.commentOfSongId(songId, pageNo, pageSize);
        List<Comment> comments = pageInfo.getList();
        return Result.ok(comments);
    }

    /**
     * 分页查询指定歌单id下的评论
     */
    @ApiOperation("分页查询指定歌单id下的评论")
    @GetMapping("detail-songListId/{songListId}/{pageNo}/{pageSize}")
    public Result commentOfSongListId(@PathVariable int songListId, @PathVariable int pageNo, @PathVariable int pageSize) {
        PageInfo<Comment> pageInfo = commentService.commentOfSongListId(songListId, pageNo, pageSize);
        List<Comment> comments = pageInfo.getList();
        return Result.ok(comments);
    }

    /**
     * 通过id更新评论
     */
    @ApiOperation("通过id更新评论")
    @PostMapping("update")
    public Result updateCommentMsg(@RequestBody Comment comment) {
        int userId = comment.getUserId();
        int songId = comment.getSongId();
        int songListId = comment.getSongListId();
        User user = userService.findUserById(userId);
        Song song = songService.findSongById(songId);
        SongList songList = songListService.findById(songListId);
        if(user==null||song==null||songList==null){
            return Result.error("400","更新失败，请检查当前歌单和歌曲的状态");
        }
        boolean update = commentService.updateCommentMsg(comment);
        if (update) {
            return Result.ok("更新成功");
        } else {
            return Result.ok("更新失败");
        }
    }

    /**
     * 通过id删除评论
     */
    @ApiOperation("通过id删除评论")
    @GetMapping("delete/{id}")
    public Result deleteComment(@PathVariable int id){
        boolean deleteComment = commentService.deleteComment(id);
        if (deleteComment) {
            return Result.ok("删除成功");
        } else {
            return Result.ok("删除失败");
        }
    }

    /**
     * 批量删除评论
     */
    @ApiOperation("批量删除评论")
    @PostMapping("deletes")
    public Result deleteComments(@RequestBody List<Integer> idList){
        boolean deleteComment = commentService.deleteComments(idList);
        if (deleteComment) {
            return Result.ok("删除成功");
        } else {
            return Result.ok("删除失败");
        }
    }
}
