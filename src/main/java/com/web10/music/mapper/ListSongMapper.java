package com.web10.music.mapper;

import com.web10.music.entity.ListSong;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 歌曲对应歌单表 Mapper 接口
 * </p>
 */
@Mapper
public interface ListSongMapper extends BaseMapper<ListSong> {

}
