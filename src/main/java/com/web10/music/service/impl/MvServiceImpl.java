package com.web10.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web10.music.entity.Mv;
import com.web10.music.mapper.MvMapper;
import com.web10.music.service.IMvService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    public Mv selectMvByMvId(Integer mvid) {
        return mvMapper.selectById(mvid);
    }

    /**
     * 根据artistName查询mv属性
     */
    public List<Mv> selectMvsByartistName(String artist_name, Integer mvid) {
        QueryWrapper<Mv> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("artist_name", artist_name).ne("id", mvid);
        return getFullMvs(queryWrapper);
    }

    private List<Mv> getFullMvs(QueryWrapper<Mv> queryWrapper) {
        return mvMapper.selectList(queryWrapper);
    }

    /**
     * 随机选取5个Mv，如果Mv的数量不足5个，则返回数据库中所有的Mv
     */
    public List<Mv> randomSelectMvs(Integer mvid) {
        QueryWrapper<Mv> queryWrapper = Wrappers.query();
        queryWrapper.ne("id", mvid);
        List<Mv> allMvs = mvMapper.selectList(queryWrapper);
        if (allMvs.size() <= 5) {
            return allMvs;
        } else {
            // 随机选择5个
            Random random = new Random();
            List<Mv> randomMvs = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                int randomIndex = random.nextInt(allMvs.size());
                randomMvs.add(allMvs.get(randomIndex));
                allMvs.remove(randomIndex); // 移除已选择的，避免重复
            }
            return randomMvs;
        }
    }

    /**
     * 随机选取limit个Mv，如果Mv的数量不足limit个，则返回数据库中所有的Mv
     */
    public List<Mv> randomlySelectMvs(Integer limit) {
        // 先获取 MV 的总数
        int count = mvMapper.selectCount(null);

        // 如果总数小于或等于 limit，直接返回所有的 MV
        if (count <= limit) {
            return mvMapper.selectList(null);
        }

        // 使用 QueryWrapper 并利用 ORDER BY RAND() 实现随机选取
        QueryWrapper<Mv> queryWrapper = Wrappers.query();
        queryWrapper.orderBy(true, false, "RAND()").last("LIMIT " + limit);

        return mvMapper.selectList(queryWrapper);
    }

    /**
     * 按照publish_time或者play_count降序查询mv
     */
    public List<Mv> getAllMvsOrderBy(String order) {
        QueryWrapper<Mv> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc(order);
        return mvMapper.selectList(queryWrapper);
    }

    /**
     * 更新mv信息
     */
    public boolean updateMv(Mv mv) {
        return mvMapper.updateById(mv) > 0;
    }

    /**
     * 根据title模糊搜索mv
     */
    public PageInfo<Mv> findMvListByTitle(String title, Integer pageNo, Integer pageSize) {
        // 设置分页查询参数
        PageHelper.startPage(pageNo, pageSize);
        QueryWrapper<Mv> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", title);
        List<Mv> mvs = mvMapper.selectList(queryWrapper);
        PageInfo<Mv> pageInfo = new PageInfo(mvs);
        return pageInfo;
    }

    /**
     * 增加mv
     */
    @Override
    public boolean addMv(Mv mv){
        return mvMapper.insert(mv) > 0;
    }

    /**
     * 删除mv
     */
    @Override
    public boolean deleteMv(Mv mv){
        return mvMapper.deleteById(mv.getId()) > 0;
    }
}
