package com.web10.music.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web10.music.entity.SongList;
import com.web10.music.mapper.SongListMapper;
import com.web10.music.service.ISongListService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 歌单表 服务实现类
 * </p>
 */
@Service
public class SongListServiceImpl extends ServiceImpl<SongListMapper, SongList> implements ISongListService {

}
