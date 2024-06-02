package com.web10.music.mapper;

import com.web10.music.config.MybatisRedisCache;
import com.web10.music.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

/**
 * 权限表 Mapper 接口
 */
@Mapper
@CacheNamespace(implementation= MybatisRedisCache.class,eviction= MybatisRedisCache.class)
public interface PermissionMapper extends BaseMapper<Permission> {

}
