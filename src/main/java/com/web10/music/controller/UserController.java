package com.web10.music.controller;


import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.web10.music.commons.result.Result;
import com.web10.music.entity.JwtUser;
import com.web10.music.entity.User;
import com.web10.music.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户管理控制器")
@Slf4j
public class UserController {

    @Resource
    private IUserService userService;


    /**
     * 用户注册
     */
    @ApiOperation("用户注册")
    @PostMapping("register")
    public Result register(@RequestBody User user) {
        boolean register = userService.register(user);
        if (register) {
            return Result.ok("注册成功");
        } else {
            return Result.ok("注册失败");
        }
    }

    /**
     * 用户登录
     */
    @ApiOperation("用户登录")
    @PostMapping("login")
    public Result login(@RequestParam String username, @RequestParam String password) {
        JSONObject jsonObject;
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return Result.ok("请输入用户名和密码！");
        }
        jsonObject = userService.login(username, password);
        return Result.ok(jsonObject);
    }

    /**
     * 退出登录
     */
    @ApiOperation("退出登录")
    @GetMapping("/logout")
    @RequiresPermissions("user")
    public Result logout() {
        Subject subject = SecurityUtils.getSubject();
        JwtUser jwtUser = (JwtUser) subject.getPrincipal();
        if (subject.isAuthenticated()) {
            subject.logout();
        }
        return Result.ok("用户" + jwtUser.getId() + "退出登录");
    }

    /**
     * 添加用户
     */
    @ApiOperation("添加用户")
    @PostMapping("add")
    @RequiresPermissions(value = "admin")
    public Result addUser(@RequestBody User user) {
        boolean addUser = userService.addUser(user);
        if (addUser) {
            return Result.ok("添加成功");
        } else {
            return Result.ok("添加失败");
        }
    }

    /**
     * 更新用户信息
     */
    @ApiOperation("更新用户信息")
    @PostMapping("update")
    @RequiresPermissions(value = "user")
    public Result updateUser(@RequestBody User user) {
        Subject subject = SecurityUtils.getSubject();
        JwtUser jwtUser = (JwtUser) subject.getPrincipal();
        if(user.getId() != null && user.getId().equals(jwtUser.getId())){
            boolean updateUserMsg = userService.updateUser(user);
            if (updateUserMsg) {
                return Result.ok("更新成功");
            } else {
                return Result.ok("更新失败");
            }
        }else
            return Result.ok("更新失败，只能更新自己的信息");
    }


    /**
     * 更新用户信息
     */
    @ApiOperation("更新用户信息")
    @PostMapping("update-admin")
    @RequiresPermissions(value = "admin")
    public Result updateUserAdmin(@RequestBody User user) {
        boolean updateUserMsg = userService.updateUserAdmin(user);
        if (updateUserMsg) {
            return Result.ok("更新成功");
        } else {
            return Result.ok("更新失败");
        }
    }

    /**
     * 根据id删除用户
     */
    @ApiOperation("根据id删除用户")
    @GetMapping("delete/{id}")
    @RequiresPermissions("root")
    public Result deleteById(@PathVariable int id) {
        boolean deleteUserById = userService.deleteUserById(id);
        if (deleteUserById) {
            return Result.ok("删除成功");
        } else {
            return Result.ok("删除失败");
        }
    }

    /**
     * 根据id查找用户
     */
    @ApiOperation("根据id查找用户")
    @GetMapping("/detail/{id}")
    public Result findUserById(@PathVariable int id) {
        User user = userService.findUserById(id);
        return Result.ok(user);
    }

    @ApiOperation("判断用户名是否存在")
    @GetMapping("/check/{username}")
    public Result checkUsername(@PathVariable String username) {
        boolean existUsername = userService.checkUsername(username);
        return Result.ok(existUsername);
    }
}

