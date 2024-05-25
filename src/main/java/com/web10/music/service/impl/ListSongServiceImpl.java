package com.web10.music.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web10.music.entity.ListSong;
import com.web10.music.mapper.ListSongMapper;
import com.web10.music.service.IListSongService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 歌曲对应歌单表 服务实现类
 * </p>
 */
@Service
public class ListSongServiceImpl extends ServiceImpl<ListSongMapper, ListSong> implements IListSongService {
}
