package com.web10.music.controller;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.web10.music.commons.result.Result;
import com.web10.music.entity.Singer;
import com.web10.music.entity.Song;
import com.web10.music.service.ISingerService;
import com.web10.music.service.ISongService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 歌曲表 前端控制器
 * </p>
 */
@RestController
@Api(tags = "歌曲管理控制类")
@RequestMapping("/song")
@Slf4j
public class SongController {

    @Resource
    private ISongService songService;

    @Resource
    private ISingerService singerService;

    /**
     * 查询所有歌曲
     */
    @ApiOperation(value = "查询所有歌曲")
    @GetMapping("allSong/{pageNo}/{pageSize}")
    public Result allSong(@PathVariable int pageNo, @PathVariable int pageSize) {
        PageInfo<Song> pageInfo = songService.allSong(pageNo, pageSize);
        List<Song> songs = pageInfo.getList();
        return Result.ok(songs);
    }

    /**
     * 根据歌曲id查找歌曲，返回歌曲详细信息，
     */
    @ApiOperation(value = "根据id查找歌曲歌曲")
    @GetMapping("detail/{id}")
    public Result findSongById(@PathVariable("id") int id) {
        Song song = songService.findSongById(id);
        return Result.ok(song);
    }

    /**
     * 查询指定歌手ID的所有歌曲
     */
    @ApiOperation(value = "查询指定歌手ID的所有歌曲")
    @GetMapping("/singer-id/detail/{singerId}/{pageNo}/{pageSize}")
    public Result findSongsBySingerId(@PathVariable("singerId") String singerId, @PathVariable int pageNo, @PathVariable int pageSize) {
        log.info("singerId = " + singerId + " pageNo = " + pageNo + " pageSize = " + pageSize);
        JSONObject jsonObject = new JSONObject();

        // 查询歌手的所有歌曲
        PageInfo<Song> pageInfo = songService.findSongBySingerId(singerId, pageNo, pageSize);
        List<Song> songs = pageInfo.getList();

        // 根据歌手id查询歌手信息
        Singer singer = singerService.findBySingerId(singerId);

        jsonObject.put("songs", songs);
        jsonObject.put("singer", singer);

        return Result.ok(jsonObject);
    }


    /**
     * 返回指定歌手名的所有歌曲
     */
    @ApiOperation(value = "查询指定歌手名的所有歌曲")
    @GetMapping("/singer-name/detail/{singerName}/{pageNo}/{pageSize}")
    public Result findSongsBySingerName(@PathVariable String singerName, @PathVariable int pageNo, @PathVariable int pageSize) {
        log.info("singerName = " + singerName);
        PageInfo<Song> pageInfo = songService.findSongBySingerName(singerName, pageNo, pageSize);
        List<Song> songs = pageInfo.getList();
        return Result.ok(songs);
    }

    /**
     * 返回指定歌曲名的歌曲
     */
    @ApiOperation(value = "查询指定歌曲名的歌曲")
    @GetMapping("/song-name/detail/{songName}/{pageNo}/{pageSize}")
    public Result findSongBySongName(@PathVariable String songName, @PathVariable int pageNo, @PathVariable int pageSize) {
        PageInfo<Song> pageInfo = songService.findSongByName(songName, pageNo, pageSize);
        List<Song> songs = pageInfo.getList();
        return Result.ok(songs);
    }

    /**
     * 获得歌曲总数
     */
    @ApiOperation(value = "获得歌曲总数")
    @GetMapping("count")
    public Result songCount() {
        int songCount = songService.songCount();
        return Result.ok(songCount);
    }

    /**
     * 播放并更新播放次数  次数+1
     */
    @ApiOperation(value = "播放并更新播放次数 次数+1")
    @GetMapping("/play/{id}")
    public Result playAndUpdatePlayCount(@PathVariable("id") int id) {
        // 更新播放次数
        if(songService.updateSongPlayCount(id)){
            // 查询更新后的数据并返回
            Song songById = songService.findSongById(id);
            return Result.ok(songById);
        }
        else{
            return Result.ok("播放量更新失败");
        }
    }
}
