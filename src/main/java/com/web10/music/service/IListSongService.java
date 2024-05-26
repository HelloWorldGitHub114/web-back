package com.web10.music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.web10.music.entity.ListSong;
import com.web10.music.entity.Song;

/**
 * <p>
 * 歌曲对应歌单表 服务类
 * </p>
 */
public interface IListSongService extends IService<ListSong> {

    /**
     * 添加歌曲到歌单，并更新当前歌曲pic为歌单封面
     */
    boolean addListSong(ListSong listSong);

    /**
     * 更新歌单里面的歌曲信息
     */
    boolean updateListSongMsg(ListSong listSong);

    /**
     * 删除指定歌单id里面的指定歌曲id
     */
    boolean deleteListSongBySongIdAndSongListId(Integer songId,Integer songListId);


    /**
     * 分页查询歌单里指定歌单ID的歌曲
     */
    PageInfo<Song> findSongsBySongListId(int songListId, int pageNo, int pageSize);


    /**
     * 查询指定歌单里面的最后一首歌曲id
     */
    int findLastSongIdBySongListId(int songListId);
}
