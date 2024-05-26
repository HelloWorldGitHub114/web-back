package com.web10.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web10.music.entity.ListSong;
import com.web10.music.entity.Song;
import com.web10.music.entity.SongList;
import com.web10.music.mapper.ListSongMapper;
import com.web10.music.mapper.SongListMapper;
import com.web10.music.mapper.SongMapper;
import com.web10.music.service.IListSongService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 歌曲对应歌单表 服务实现类
 * </p>
 */
@Service
public class ListSongServiceImpl extends ServiceImpl<ListSongMapper, ListSong> implements IListSongService {
    @Resource
    private ListSongMapper listSongMapper;

    @Resource
    private SongListMapper songListMapper;

    @Resource
    private SongMapper songMapper;

    /**
     * 添加歌曲到歌单，并更新当前歌曲pic为歌单封面
     */
    @Override
    public boolean addListSong(ListSong listSong) {
        // 查询当前歌曲是否存在该歌单中

        QueryWrapper queryWrapper2 = new QueryWrapper();
        queryWrapper2.eq("song_id", listSong.getSongId());
        queryWrapper2.eq("song_list_id", listSong.getSongListId());

        ListSong listSong1 = listSongMapper.selectOne(queryWrapper2);
        if (listSong1 != null) {
            return false;
        }

        int insert = listSongMapper.insert(listSong);

        // 通过 songListId 查询歌单信息
        Integer songListId = listSong.getSongListId();
        SongList songList = songListMapper.selectById(songListId);


        // 通过songId查询歌曲信息
        Integer songId = listSong.getSongId();
        QueryWrapper<Song> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", songId);
        Song song = songMapper.selectById(songId);

        if (song != null) {
            String pic = song.getPic();
            songList.setPic(pic);
            // 通过songList的id更新他的pic
            QueryWrapper<SongList> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("id", songListId);
            int update = songListMapper.update(songList, queryWrapper1);
        }
        return (insert > 0);

    }

    /**
     * 更新歌单里面的歌曲信息
     */
    @Override
    public boolean updateListSongMsg(ListSong listSong) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", listSong.getId());
        return listSongMapper.update(listSong, queryWrapper) > 0;
    }

    /**
     * 删除指定歌单id里面的指定歌曲id,并更新歌单封面
     */
    @Override
    public boolean deleteListSongBySongIdAndSongListId(Integer songId, Integer songListId) {

        QueryWrapper<ListSong> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("song_id", songId).eq("song_list_id", songListId);

        int delete = listSongMapper.delete(queryWrapper);

        // 通过歌单id查询歌单信息,查询到歌单里面有歌曲，更新当前歌单封面
        SongList songList = songListMapper.selectById(songListId);

        // 在list_song表中查询当前歌单的最后一首歌的id
        QueryWrapper<ListSong> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("song_list_id", songListId);

        List<ListSong> listSongs = listSongMapper.selectList(queryWrapper1);
        int lastSongId = -1;
        if (listSongs.size() >= 1) {
            // 将最后一首歌的id返回
            lastSongId = listSongs.get(listSongs.size() - 1).getSongId();
        }

        // 如果歌单里还有歌曲
        if (lastSongId != -1) {
            // 查询当前歌曲的pic，更新到歌单封面
            QueryWrapper<Song> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("song_id", lastSongId);

            Song song = songMapper.selectOne(queryWrapper3);

            String pic = song.getPic();
            songList.setPic(pic);
        } else {
            songList.setPic("");
        }

        // 更新当前歌单的封面为此歌单里面最后一首歌的图片
        // 通过songList的id更新他的pic
        QueryWrapper<SongList> queryWrapper4 = new QueryWrapper<>();
        queryWrapper4.eq("id", songListId);
        int update = songListMapper.update(songList, queryWrapper4);
        return delete > 0;
    }


    /**
     * 分页查询歌单里指定歌单ID的歌曲
     */
    @Override
    public PageInfo<Song> findSongsBySongListId(int songListId, int pageNo, int pageSize) {
        // 设置分页查询参数
        PageHelper.startPage(pageNo, pageSize);

        QueryWrapper<ListSong> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("song_list_id", songListId);
        List<ListSong> listSongList = listSongMapper.selectList(queryWrapper);

        // 得到歌单里面的所有歌曲id，再根据歌曲id查询歌曲信息，返回给前端
        List<Song> songs = new ArrayList<>();
        for (ListSong listSong : listSongList) {
            Song song = songMapper.selectById(listSong.getSongId());
            songs.add(song);
        }
        PageInfo<Song> pageInfo = new PageInfo(songs);
        return pageInfo;
    }

    /**
     * 查询指定歌单里面的最后一首歌曲id
     */
    @Override
    public int findLastSongIdBySongListId(int songListId) {
        QueryWrapper<ListSong> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("song_list_id", songListId);
        List<ListSong> listSongs = listSongMapper.selectList(queryWrapper);
        if (listSongs.size() >= 1) {
            // 将最后一首歌的id返回
            return listSongs.get(listSongs.size() - 1).getSongId();
        }
        // 歌单里面一首歌都没，用默认封面
        return -1;
    }
}
