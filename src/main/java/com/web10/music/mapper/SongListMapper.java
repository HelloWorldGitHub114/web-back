package com.web10.music.mapper;

import com.web10.music.config.MybatisRedisCache;
import com.web10.music.entity.SongList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

/**
 * 歌单表 Mapper 接口
 */
@Mapper
@CacheNamespace(implementation= MybatisRedisCache.class,eviction= MybatisRedisCache.class)
public interface SongListMapper extends BaseMapper<SongList> {

}
