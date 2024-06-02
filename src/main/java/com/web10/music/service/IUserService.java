package com.web10.music.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.web10.music.entity.User;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户表 服务类
 * </p>
 */
public interface IUserService extends IService<User> {

    /**
     * 用户注册
     */
    boolean register(User user);

    /**
     * 用户登录
     */
    JSONObject login(String username, String password);

    /**
     * 通过用户名获取用户
     */
    User findFullUserByUserName(String username);

    /**
     * 添加用户
     */
    boolean addUser(User user);


    /**
     * 根据id删除用户
     */
    boolean deleteUserById(int id);

    @Transactional(rollbackFor = Exception.class)
    boolean updateUser(User user);

    /**
     * 根据id更新用户信息，包括角色信息
     */
    boolean updateUserAdmin(User user);


    /**
     * 根据id查询用户
     */
    User findUserById(int id);

    boolean checkUsername(String username);
}
