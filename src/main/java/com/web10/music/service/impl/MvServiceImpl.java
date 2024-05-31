package com.web10.music.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web10.music.entity.Comment;
import com.web10.music.entity.Mv;
import com.web10.music.mapper.CommentMapper;
import com.web10.music.mapper.MvMapper;
import com.web10.music.service.ICommentService;
import com.web10.music.service.IMvService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * Mv 服务实现类
 * </p>
 */
@Service
public class MvServiceImpl extends ServiceImpl<MvMapper, Mv> implements IMvService {
    @Resource
    private MvMapper mvMapper;

    /**
     * 根据mvid查询mv属性
     */
    public Mv selectMvByMvId(Integer mvid){
        return mvMapper.selectById(mvid);
    }
}
