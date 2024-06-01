package com.web10.music.service;

import com.web10.music.entity.Mv;

import java.util.List;

/**
 * <p>
 * Mv 服务类
 * </p>
 */
public interface IMvService {
    /**
     * 根据mvid查询mv属性
     */
    Mv selectMvByMvId(Integer mvid);

    /**
     * 根据artistName查询mv属性
     */
    List<Mv> selectMvsByartistName(String artist_name, Integer mvid);

    /**
     * 随机选取除了mvid的5个mv
     */
    List<Mv> randomSelectMvs(Integer mvid);

    /**
     * 随机limit个mv
     */
    List<Mv> randomlySelectMvs(Integer limit);

    /**
     * 按照publish_time或者play_count降序查询mv
     */
    List<Mv> getAllMvsOrderBy(String order);
}
