package com.web10.music.controller;


import com.github.pagehelper.PageInfo;
import com.web10.music.commons.result.Result;
import com.web10.music.entity.ListSong;
import com.web10.music.entity.Song;
import com.web10.music.service.IListSongService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 歌曲对应歌单表 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/listSong")
@Api(tags = "歌曲对应歌单控制器")
public class ListSongController {
    @Resource
    private IListSongService listSongService;

    /**
     * 添加歌曲到歌单中，并更新当前歌曲pic为歌单封面
     */
    @ApiOperation("添加歌曲到歌单中 注意只传song的id（是表的主键，int）和songlist的id")
    @PostMapping("add")
    public Result addListSong(@RequestBody ListSong listSong) {
        boolean addListSong = listSongService.addListSong(listSong);
        if (addListSong) {
            return Result.ok("添加成功");
        } else {
            return Result.ok("该歌曲已在歌单中");
        }
    }

    /**
     * 更新歌单里面的歌曲信息
     */
    @ApiOperation("更新歌单里面的歌曲信息")
    @PostMapping("update")
    public Result updateListSongMsg(@RequestBody ListSong listSong) {
        boolean updateListSongMsg = listSongService.updateListSongMsg(listSong);
        if (updateListSongMsg) {
            return Result.ok("更新成功");
        } else {
            return Result.ok("更新失败");
        }
    }

    /**
     * 删除指定歌单id里面的指定歌曲id,并更新歌单封面
     */
    @ApiOperation("删除指定歌单id里面的指定歌曲id,并更新歌单封面")
    @PostMapping("delete/{songId}/{songListId}")
    public Result deleteListSongBySongIdAndSongListId(@PathVariable int songId, @PathVariable int songListId) {
        boolean deleteListSong = listSongService.deleteListSongBySongIdAndSongListId(songId, songListId);
        if (deleteListSong) {
            return Result.ok("删除成功");
        } else {
            return Result.ok("删除失败");
        }
    }


    /**
     * 分页查询歌单表里指定歌单ID的所有歌曲
     */
    @ApiOperation("分页查询歌单表里指定歌单ID的所有歌曲")
    @GetMapping("detail/{songListId}/{pageNo}/{pageSize}")
    public Result findSongsBySongListId(@PathVariable int songListId, @PathVariable int pageNo, @PathVariable int pageSize) {
        PageInfo<Song> pageInfo = listSongService.findSongsBySongListId(songListId, pageNo, pageSize);
        return Result.ok(pageInfo);
    }

    /**
     * 查询指定歌单里面的最后一首歌曲id
     */
    @ApiOperation("查询指定歌单里面的最后一首歌曲id")
    @GetMapping("lastSongId/{songListId}")
    public Result findLastSongIdBySongListId(@PathVariable int songListId) {
        int lastSongId = listSongService.findLastSongIdBySongListId(songListId);
        return Result.ok(lastSongId);
    }

}
