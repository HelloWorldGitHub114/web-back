package com.web10.music.controller;


import com.github.pagehelper.PageInfo;
import com.web10.music.commons.result.Result;
import com.web10.music.entity.SongList;
import com.web10.music.entity.User;
import com.web10.music.service.ISongListService;
import com.web10.music.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <p>
 * 歌单表 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/songList")
@Api(tags = "歌单管理控制类")
public class SongListController {
    @Resource
    private ISongListService songListService;

    @Resource
    private IUserService userService;

    /**
     * 添加歌单
     */
    @ApiOperation(value = "添加歌单")
    @PostMapping(value = "add")
    public Result addSongList(@RequestBody SongList songList) {
        Integer userId = songList.getUserId();
        String title = songList.getTitle();
        User user = userService.findUserById(userId);
        if(songListService.existsSongListByTitle(userId,title)){
            return Result.error("400","该歌单已经存在，请换一个标题");
        }
        if(user==null){
            return Result.error("400","该用户不存在");
        }
        boolean addSongList = songListService.addSongList(songList);
        if (addSongList) {
            return Result.ok("添加成功");
        } else {
            return Result.ok("添加失败");
        }
    }

    /**
     * 更新歌单信息
     */
    @ApiOperation(value = "更新歌单信息")
    @PostMapping("update")
    public Result updateSongListMsg(@RequestBody SongList songList) {
        boolean updateSongListMsg = songListService.updateSongListMsg(songList);
        if (updateSongListMsg) {
            return Result.ok("更新成功");
        } else {
            return Result.ok("更新失败");
        }
    }

    /**
     * 根据id删除歌单
     */
    @ApiOperation(value = "根据id删除歌单")
    @GetMapping("delete/{id}")
    public Result deleteSongList(@PathVariable int id) {
        boolean deleteSongList = songListService.deleteSongList(id);
        if (deleteSongList) {
            return Result.ok("删除成功");
        } else {
            return Result.ok("删除失败");
        }
    }

    /**
     * 批量删除歌单
     */
    @ApiOperation(value = "批量删除歌单")
    @PostMapping ("deletes")
    public Result deleteSongLists(@RequestBody List<Integer> idList) {
        boolean deleteSongLists = songListService.deleteSongLists(idList);
        if (deleteSongLists) {
            return Result.ok("删除成功");
        } else {
            return Result.ok("删除失败");
        }
    }

    /**
     * 随机获得n个歌单
     */
    @ApiOperation(value = "随机获得n个歌单")
    @GetMapping("getRandomSongList/{num}")
    public Result getRandomSongList(@PathVariable int num) {

        List<SongList> res = new ArrayList<>();
        //  先查询出所有歌单的id
        PageInfo<SongList> pageInfo = songListService.allSongList(1, 100000);
        List<SongList> list = pageInfo.getList();

        // 获得歌单列表大小，生成索引随机数
        int size = list.size();

        if (size < num) {
            return Result.ok("当前歌单数不足，请调小请求个数");
        }

        // 获得不重复随机数索引
        List<Integer> songListIdList = new ArrayList<>();

        for (int i = 0; i < num; i++) {
            int nextInt = new Random().nextInt(size);
            if (!songListIdList.contains(nextInt)) {
                songListIdList.add(nextInt);
            }else{
                i--;
            }
        }

        for (int i = 0; i < songListIdList.size(); i++) {
            res.add(list.get(songListIdList.get(i)));
        }

        return Result.ok(res);
    }

    /**
     * 分页查询所有歌单
     */
    @ApiOperation(value = "分页查询所有歌单")
    @GetMapping("allSongList/{pageNo}/{pageSize}")
    public Result allSongList(@PathVariable int pageNo, @PathVariable int pageSize) {
        PageInfo<SongList> pageInfo = songListService.allSongList(pageNo, pageSize);
        List<SongList> songLists = pageInfo.getList();

        return Result.ok(songLists);
    }

    /**
     * 根据歌单题目模糊查询歌单
     */
    @ApiOperation(value = "根据歌单题目模糊查询歌单")
    @GetMapping("title/detail/{title}/{pageNo}/{pageSize}")
    public Result findSongListByTitle(@PathVariable String title, @PathVariable int pageNo, @PathVariable int pageSize) {
        PageInfo<SongList> pageInfo = songListService.findSongListByTitle(title, pageNo, pageSize);
        List<SongList> songLists = pageInfo.getList();
        return Result.ok(songLists);
    }

    /**
     * 根据歌单风格模糊查询歌单
     */
    @ApiOperation(value = "根据歌单风格模糊查询歌单")
    @GetMapping("style/detail/{style}/{pageNo}/{pageSize}")
    public Result findSongListByStyle(@PathVariable String style, @PathVariable int pageNo, @PathVariable int pageSize) {
        PageInfo<SongList> pageInfo = songListService.findSongListByStyle(style, pageNo, pageSize);
        List<SongList> songLists = pageInfo.getList();
        return Result.ok(songLists);
    }

    /**
     * 根据userId查询该用户创建的所有歌单,并且以歌单里面的第一首歌曲封面作为歌单封面
     */
    @ApiOperation("根据userId查询该用户创建的所有歌单")
    @GetMapping("detail-userId/{userId}")
    public Result findListSongByUserId(@PathVariable int userId) {
        List<SongList> songLists = songListService.findSongListByUserId(userId);
        return Result.ok(songLists);

    }

    /**
     * 获得歌单的数量
     */
    @ApiOperation("获得歌单的数量")
    @GetMapping("count")
    public Result songListCount() {
        int songListCount = songListService.songListCount();
        return Result.ok(songListCount);
    }

    @ApiOperation(value = "歌单id获取歌单")
    @GetMapping("detail/{id}")
    public Result findSongListById(@PathVariable int id) {
        SongList songList = songListService.findById(id);
        return Result.ok(songList);
    }
}



