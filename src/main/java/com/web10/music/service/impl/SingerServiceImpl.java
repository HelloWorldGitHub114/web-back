package com.web10.music.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web10.music.entity.Singer;
import com.web10.music.mapper.SingerMapper;
import com.web10.music.service.ISingerService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 歌手表 服务实现类
 * </p>
 */
@Service
public class SingerServiceImpl extends ServiceImpl<SingerMapper, Singer> implements ISingerService {
}
