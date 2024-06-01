package com.web10.music.controller;

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
            return Result.error("400", "该mv不存在");
        }
        return Result.ok(mv);
    }
}
