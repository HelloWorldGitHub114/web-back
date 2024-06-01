package com.web10.music.service;

import com.web10.music.entity.Mv;

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
}
