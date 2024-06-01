package com.web10.music.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web10.music.commons.result.Result;
import com.web10.music.entity.Mv;
import com.web10.music.service.IMvService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * Mv控制器
 * </p>
 */
@RestController
@RequestMapping("/mv")
@Api(tags = "Mv控制控制器")
public class MvController {
    @Resource
    private IMvService mvService;

    /**
     * 根据mvid查询mv属性
     */
    @ApiOperation("根据mvid查询mv属性")
    @GetMapping("detail")
    public Result getMvByMvId(@RequestParam Integer mvid){
        Mv mv = mvService.selectMvByMvId(mvid);
        if (mv == null) {
            return Result.ok("该mv不存在");
        }
        return Result.ok(mv);
    }

    /**
     * 根据mvid推荐相似mv(mv详情页)
     */
    @ApiOperation("根据mvid推荐相似mv")
    @GetMapping("simi")
    public Result recommendMvByMvId(@RequestParam Integer mvid){
        Mv mv = mvService.selectMvByMvId(mvid);
        String artist_name = mv.getArtistName();
        List<Mv> mvs = mvService.selectMvsByartistName(artist_name, mvid);
        if (mvs.isEmpty()) {
            mvs = mvService.randomSelectMvs(mvid);
            return Result.ok(mvs);
        }
        return Result.ok(mvs);
    }

    /**
     * 随机推荐mv
     */
    @ApiOperation("随机推荐mv")
    @GetMapping("personalized")
    public Result randomRecommendMv(@RequestParam Integer limit) {
        List<Mv> mvs = mvService.randomlySelectMvs(limit);
        return Result.ok(mvs);
    }

    /**
     * 根据mvid查询mv的url，并自增播放量
     */
    @ApiOperation("根据mvid查询mv的url，并自增播放量")
    @GetMapping("detail")
    public Result getMvUrlByMvId(@RequestParam Integer mvid){
        Mv mv = mvService.selectMvByMvId(mvid);
        if (mv == null) {
            return Result.ok("该mv不存在");
        }
        // 自增播放量
        mv.setPlayCount(mv.getPlayCount() + 1);
        // 更新播放量到数据库
        mvService.updateMv(mv);
        return Result.ok(mv.getUrl());
    }

    /**
     * 获取所有的mv，按照条件进行排序
     */
    @ApiOperation("获取所有的mv，按照条件进行排序")
    @GetMapping("all")
    public Result getAllMvs(@RequestParam String order, @RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        // 开启分页
        PageHelper.startPage(pageNo, pageSize);

        // 按条件查询
        List<Mv> mvs;
        if ("NEW".equalsIgnoreCase(order)) {
            mvs = mvService.getAllMvsOrderBy("publish_time");
        } else if ("HOT".equalsIgnoreCase(order)) {
            mvs = mvService.getAllMvsOrderBy("play_count");
        } else {
            return Result.ok("Invalid order parameter");
        }

        // 使用PageInfo封装结果
        PageInfo<Mv> pageInfo = new PageInfo<>(mvs);

        return Result.ok(pageInfo);
    }
}