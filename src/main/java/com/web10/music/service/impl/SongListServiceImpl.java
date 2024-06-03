package com.web10.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web10.music.entity.SongList;
import com.web10.music.mapper.SongListMapper;
import com.web10.music.service.ISongListService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 歌单表 服务实现类
 * </p>
 */
@Service
public class SongListServiceImpl extends ServiceImpl<SongListMapper, SongList> implements ISongListService {
    @Resource
    private SongListMapper songListMapper;

    /**
     * 添加歌单
     */
    @Override
    public boolean addSongList(SongList songList) {
        return songListMapper.insert(songList) > 0;
    }

    @Override
    public boolean existsSongListByTitle(Integer userId, String title){
        QueryWrapper<SongList> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("title", title);
        return songListMapper.selectCount(queryWrapper) > 0;
    }

    /**
     * 更新歌单信息
     */
    @Override
    public boolean updateSongListMsg(SongList songList) {
        QueryWrapper<SongList> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", songList.getId());
        return songListMapper.update(songList, queryWrapper) > 0;
    }

    /**
     * 更新歌单封面
     */
    @Override
    public boolean updateSongListImg(SongList songList) {

        return true;
    }

    /**
     * 删除歌单
     */
    @Override
    public boolean deleteSongList(Integer id) {
        return songListMapper.deleteById(id) > 0;
    }

    /**
     * 批量删除歌单
     */
    @Override
    public boolean deleteSongLists(List<Integer> ids) {
        return songListMapper.deleteBatchIds(ids) > 0;
    }

    /**
     * 通过id获取歌单信息
     */
    @Override
    public SongList findById(int id) {
        // 在list_song表中查询所有对应歌单的所有歌曲
        return songListMapper.selectById(id);
    }


    /**
     * 分页查询所有歌单
     */
    @Override
    public PageInfo<SongList> allSongList(int pageNo, int pageSize) {
        // 设置分页查询参数
        PageHelper.startPage(pageNo, pageSize);
        QueryWrapper<SongList> queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("create_time");
        List<SongList> songLists = songListMapper.selectList(queryWrapper);
        PageInfo<SongList> pageInfo = new PageInfo(songLists);
        return pageInfo;
    }

    /**
     * 根据歌单题目模糊查询歌单
     */
    @Override
    public PageInfo<SongList> findSongListByTitle(String title, int pageNo, int pageSize) {
        // 设置分页查询参数
        PageHelper.startPage(pageNo, pageSize);
        QueryWrapper<SongList> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("title", title);
        List<SongList> songLists = songListMapper.selectList(queryWrapper);
        PageInfo<SongList> pageInfo = new PageInfo(songLists);
        return pageInfo;
    }

    /**
     * 根据歌单风格查询歌单
     */
    @Override
    public PageInfo<SongList> findSongListByStyle(String style, int pageNo, int pageSize) {
        // 设置分页查询参数
        PageHelper.startPage(pageNo, pageSize);
        QueryWrapper<SongList> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("style", style);
        List<SongList> songLists = songListMapper.selectList(queryWrapper);
        PageInfo<SongList> pageInfo = new PageInfo(songLists);
        return pageInfo;
    }

    /**
     * 根据userId查询该用户创建的所有歌单
     */
    @Override
    public List<SongList> findSongListByUserId(int userId) {
        QueryWrapper<SongList> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<SongList> songLists = songListMapper.selectList(queryWrapper);
        return songLists;
    }

    /**
     * 获得歌单的数量
     */
    @Override
    public int songListCount() {
        return songListMapper.selectCount(null);
    }

}
