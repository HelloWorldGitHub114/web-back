package com.web10.music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.web10.music.entity.Singer;

import java.util.List;

/**
 * <p>
 * 歌手表 服务类
 * </p>
 */
public interface ISingerService extends IService<Singer> {
    /**
     * 查询所有歌手
     */
    PageInfo<Singer> allSinger(int pageNo, int pageSize);

    /**
     * 根据歌手id获得歌手信息
     */
    Singer findBySingerId(String singerId);


    /**
     * 添加歌手
     */
    boolean addSinger(Singer singer);

    /**
     * 搜索框根据歌手名字模糊搜索
     */
    List<Singer> findByNameLike(String name);

//    /**
//     * 根据不同条件查询歌手
//     * @param keyWord
//     * @return
//     */
//     List<Singer> findByCondition(String keyWord);

    /**
     * 通过id删除歌手
     */
    boolean deleteSinger(String id);

    /**
     * 批量删除歌手
     */
    boolean deleteSingers(List<Integer> ids);

    /**
     * 通过主键id更新歌手信息
     */
    boolean updateSingerMsg(Singer singer);


    /**
     * 更新歌手头像
     */
    boolean updateSingerPic();


    /**
     * 根据姓名查询歌手
     */
    PageInfo<Singer> findSingerByName(String name, int pageNo, int pageSize);


    /**
     * 根据性别查找歌手
     */
    PageInfo<Singer> findSingerBySex(int sex, int pageNo, int pageSize);

    /**
     * 获得歌手总数量
     */
    int singerCount();

    /**
     * 根据性别获得歌曲数量
     * @return
     */
    int singerCountOfSex(int sex);


    /**
     * 根据歌手地区获得歌手数量
     * @return
     */
    int singerCountOfLocation(String location);
}
