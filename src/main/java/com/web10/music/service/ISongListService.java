package com.web10.music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.web10.music.entity.SongList;

import java.util.List;

/**
 * <p>
 * 歌单表 服务类
 * </p>
 */
public interface ISongListService extends IService<SongList> {
    /**
     * 添加歌单
     */
    boolean addSongList(SongList songList);

    boolean existsSongListByTitle(Integer userId, String title);

    /**
     * 更新歌单信息
     */
    boolean updateSongListMsg(SongList songList);

    /**
     * 更新歌单封面
     */
    boolean updateSongListImg(SongList songList);

    /**
     * 根据主键id删除歌单
     */
    boolean deleteSongList(Integer id);

    /**
     * 批量删除歌单
     */
    boolean deleteSongLists(List<Integer> ids);

    /**
     * 通过id获取歌单信息
     */
    SongList findById(int id);



    /**
     * 分页查询所有歌单
     */
    PageInfo<SongList> allSongList(int pageNo, int pageSize);

    /**
     * 根据歌单题目模糊查询歌单
     */
    PageInfo<SongList> findSongListByTitle(String title, int pageNo, int pageSize);

    /**
     * 根据歌单风格查询歌单
     */
    PageInfo<SongList> findSongListByStyle(String style, int pageNo, int pageSize);

    /**
     * 根据userId查询该用户创建的所有歌单
     */
    List<SongList> findSongListByUserId(int userId);

    /**
     * 获得歌单的数量
     */
    int songListCount();

}
