package com.web10.music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.web10.music.entity.Song;

/**
 * <p>
 * 歌曲表 服务类
 * </p>
 */
public interface ISongService extends IService<Song> {
    /**
     * 更新歌曲播放次数
     */
    public boolean updateSongPlayCount(int id);

    /**
     * 查询所有歌曲
     */
    PageInfo<Song> allSong(int pageNo, int pageSize);

    /**
     * 根据歌手id查询此歌手的所有歌曲
     */
    PageInfo<Song> findSongBySingerId(String singerId, int pageNo, int pageSize);

    /**
     * 根据歌手名字查找此歌手下面的所有歌曲
     */
    PageInfo<Song> findSongBySingerName(String name, int pageNo, int pageSize);


    /**
     * 根据id查找歌曲
     */
    Song findSongById(Integer id);


    /**
     * 根据歌曲名查找歌曲
     */
    PageInfo<Song> findSongByName(String name, int pageNo, int pageSize);

    /**
     * 查询歌曲总数
     */
    int songCount();
}
