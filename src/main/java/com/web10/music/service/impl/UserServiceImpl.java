package com.web10.music.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web10.music.entity.User;
import com.web10.music.mapper.UserMapper;
import com.web10.music.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
}
