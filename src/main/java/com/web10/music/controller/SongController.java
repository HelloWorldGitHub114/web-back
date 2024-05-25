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
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import javax.servlet.MultipartConfigElement;
import javax.websocket.server.PathParam;
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


    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大10M,DataUnit提供5中类型B,KB,MB,GB,TB
        factory.setMaxFileSize(DataSize.of(10, DataUnit.MEGABYTES));
        /// 设置总上传数据总大小10M
        factory.setMaxRequestSize(DataSize.of(10, DataUnit.MEGABYTES));
        return factory.createMultipartConfig();
    }

    @Configuration
    public class MyPicConfig implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/img/songPic/**").addResourceLocations("file:D:\\IDE_Project\\JavaLearning\\SpringBoot-VUE-Music\\img\\songPic");
            registry.addResourceHandler("/song/**").addResourceLocations("file:D:\\IDE_Project\\JavaLearning\\SpringBoot-VUE-Music\\song");
        }
    }


    /**
     * 查询所有歌曲
     */
    @ApiOperation(value = "查询所有歌曲")
    @GetMapping("allSong/{pageNo}/{pageSize}")
    public Result<List<Song>> allSong(@PathVariable int pageNo, @PathVariable int pageSize) {
        PageInfo<Song> pageInfo = songService.allSong(pageNo, pageSize);
        List<Song> songs = pageInfo.getList();
        return Result.ok(songs);
    }

    /**
     * 根据歌曲id查找歌曲，返回歌曲详细信息，
     */
    @ApiOperation(value = "根据id查找歌曲歌曲")
    @GetMapping("detail/{id}")
    public Result<Song> findSongById(@PathVariable("id") int id) {
        Song song = songService.findSongById(id);
        return Result.ok(song);
    }

    /**
     * 查询指定歌手ID的所有歌曲
     */
    @ApiOperation(value = "查询指定歌手ID的所有歌曲")
    @GetMapping("/singer-id/detail/{singerId}/{pageNo}/{pageSize}")
    public Result<Song> findSongsBySingerId(@PathVariable("singerId") String singerId, @PathVariable int pageNo, @PathVariable int pageSize) {
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
    public Result<List<Song>> findSongsBySingerName(@PathVariable String singerName, @PathVariable int pageNo, @PathVariable int pageSize) {
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
    public Result<List<Song>> findSongBySongName(@PathVariable String songName, @PathVariable int pageNo, @PathVariable int pageSize) {
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
