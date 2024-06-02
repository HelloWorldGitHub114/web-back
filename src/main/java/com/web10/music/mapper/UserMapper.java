package com.web10.music.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.web10.music.config.MybatisRedisCache;
import com.web10.music.entity.Permission;
import com.web10.music.entity.Role;
import com.web10.music.entity.User;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户表 Mapper 接口
 */
@Mapper
@CacheNamespace(implementation= MybatisRedisCache.class,eviction= MybatisRedisCache.class)
public interface UserMapper extends BaseMapper<User> {

    User findByUserName(String username);

    /**
     * 根据用户名获取所有角色
     */
    List<Role> findRolesByUserId(int userId);


    /**
     * 根据角色id查询权限集合
     */
    List<Permission> findPermsByRoleId(int id);


    /**
     * 根据用户id查找用户信息
     */
    User findUserById(int id);

    /**
     * 根据用户id获取用户昵称
     */
    User findUserNickNameAndAvatarById(int id);

    List<User> findAllUsers();

    Integer insertUserRole(int userId, int roleId);

    Integer deleteUserRole(int userId);
}
