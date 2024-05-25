package com.web10.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web10.music.entity.Song;
import com.web10.music.mapper.SongMapper;
import com.web10.music.service.ISongService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 歌曲表 服务实现类
 * </p>
 */
@Service
public class SongServiceImpl extends ServiceImpl<SongMapper, Song> implements ISongService {

    @Resource
    private SongMapper songMapper;

    /**
     * 更新歌曲播放次数
     */
    @Override
    public boolean updateSongPlayCount(int id) {
        UpdateWrapper<Song> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id).setSql("play_count = play_count + 1");
        return songMapper.update(null, updateWrapper) > 0;
    }

    /**
     * 查询所有歌曲
     */
    @Override
    public PageInfo<Song> allSong(int pageNo, int pageSize) {
        // 设置分页查询参数
        PageHelper.startPage(pageNo, pageSize);
        QueryWrapper<Song> queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("create_time");
        List<Song> songs = songMapper.selectList(queryWrapper);

        PageInfo<Song> pageInfo = new PageInfo(songs);
        return pageInfo;
    }

    /**
     * 根据歌手id查询此歌手的所有歌曲
     */
    @Override
    public PageInfo<Song> findSongBySingerId(String singerId, int pageNo, int pageSize) {
        // 设置分页查询参数
        PageHelper.startPage(pageNo, pageSize);
        QueryWrapper<Song> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("singer_id", singerId);
        List<Song> songs = songMapper.selectList(queryWrapper);
        PageInfo<Song> pageInfo = new PageInfo(songs);
        return pageInfo;
    }

    /**
     * 根据歌手名字查找此歌手下面的所有歌曲
     */
    @Override
    public PageInfo<Song> findSongBySingerName(String name, int pageNo, int pageSize) {
        // 设置分页查询参数
        PageHelper.startPage(pageNo, pageSize);
        QueryWrapper<Song> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("singer_name", name);
        List<Song> songs = songMapper.selectList(queryWrapper);
        PageInfo<Song> pageInfo = new PageInfo(songs);
        return pageInfo;
    }

    /**
     * 根据id查找歌曲
     */
    @Override
    public Song findSongById(Integer id) {
        return songMapper.selectById(id);
    }

    /**
     * 根据歌曲名查找歌曲
     */
    @Override
    public PageInfo<Song> findSongByName(String name, int pageNo, int pageSize) {
        // 设置分页查询参数
        PageHelper.startPage(pageNo, pageSize);

        QueryWrapper<Song> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", name);
        List<Song> songs = songMapper.selectList(queryWrapper);
        PageInfo<Song> pageInfo = new PageInfo(songs);
        return pageInfo;
    }

    /**
     * 查询歌曲总数
     */
    @Override
    public int songCount() {
        return songMapper.selectCount(null);
    }
}
