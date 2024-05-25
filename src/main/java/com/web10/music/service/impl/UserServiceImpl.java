package com.web10.music.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web10.music.entity.JwtUser;
import com.web10.music.entity.Role;
import com.web10.music.entity.User;
import com.web10.music.mapper.UserMapper;
import com.web10.music.service.IUserService;
import com.web10.music.utils.JwtUtil;
import com.web10.music.utils.SaltUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 用户注册
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean register(User user) {

        try{
            // 1.生成随机盐
            String salt = SaltUtil.getSalt(8);
            // 2. 将随机盐保存到数据库
            user.setSalt(salt);
            // 3. 明文密码进行md5 + salt + hash散列
            Md5Hash md5Hash = new Md5Hash(user.getPassword(), salt, 1024);
            user.setPassword(md5Hash.toHex());
            userMapper.insert(user);
            // 4. 添加用户角色 默认为普通用户
            userMapper.insertUserRole(user.getId(), 3);
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 用户登录
     */
    @Override
    public JSONObject login(String username, String password) {
        // 根据用户名查询数据库中的用户信息
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        subject.login(usernamePasswordToken);//realms会根据UsernamePasswordToken这个类型选用合适的realm来处理登陆

        //生成token
        Map<String,String> res=new HashMap<>();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        JwtUser jwtUser = new JwtUser(user.getUsername(), user.getRolesString(), user.getPermissionsString());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", user);
        String token = JwtUtil.createJwtTokenByUser(jwtUser);
        jsonObject.put("token", token);
        return jsonObject;
    }

    /**
     * 添加用户
     */
    @Override
    public boolean addUser(User user) {
        // 1.生成随机盐
        String salt = SaltUtil.getSalt(8);
        // 2. 将随机盐保存到数据库
        user.setSalt(salt);
        // 3. 明文密码进行md5 + salt + hash散列
        Md5Hash md5Hash = new Md5Hash(user.getPassword(), salt, 1024);
        user.setPassword(md5Hash.toHex());
        return userMapper.insert(user) > 0;
    }


    /**
     * 根据id删除用户
     */
    @Override
    public boolean deleteUserById(int id) {
        return userMapper.deleteById(id) > 0;
    }


    /**
     * 根据id更新用户信息，包括角色信息
     */
    @Override
    public boolean updateUserMsg(User user) {
        return userMapper.updateById(user) > 0;
    }

    /**
     * 根据id查询用户
     */
    @Override
    public User findUserById(int id) {
        return userMapper.findUserById(id);
    }


    /**
     * 查找带权限的用户
     */
    @Override
    @Transactional
    public User findFullUserByUserName(String username) {
        User user = userMapper.findByUserName(username);
        if(user != null) {
            user.setRoles(userMapper.findRolesByUserId(user.getId()));
            for(Role role : user.getRoles()) {
                role.setPerms(userMapper.findPermsByRoleId(role.getId()));
            }
        }

        return user;
    }

}
