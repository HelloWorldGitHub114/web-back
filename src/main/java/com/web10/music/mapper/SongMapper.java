package com.web10.music.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.web10.music.entity.Song;
import org.apache.ibatis.annotations.Mapper;

/**
 * 歌曲表 Mapper 接口
 */
@Mapper
public interface SongMapper extends BaseMapper<Song> {

}
