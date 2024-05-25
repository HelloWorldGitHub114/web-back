package com.web10.music.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web10.music.entity.Song;
import com.web10.music.mapper.SongMapper;
import com.web10.music.service.ISongService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 歌曲表 服务实现类
 * </p>
 */
@Service
public class SongServiceImpl extends ServiceImpl<SongMapper, Song> implements ISongService {
}
