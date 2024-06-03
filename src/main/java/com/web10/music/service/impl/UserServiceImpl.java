package com.web10.music.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web10.music.entity.JwtUser;
import com.web10.music.entity.Permission;
import com.web10.music.entity.Role;
import com.web10.music.entity.User;
import com.web10.music.mapper.RoleMapper;
import com.web10.music.mapper.UserMapper;
import com.web10.music.service.IUserService;
import com.web10.music.utils.JwtUtil;
import com.web10.music.utils.SaltUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;

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
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        JwtUser jwtUser = new JwtUser(user.getId(), user.getRoles(), user.getPermissions());
        user.setPassword(null);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", user);
        String token = JwtUtil.createJwtTokenByUser(jwtUser);
        jsonObject.put("token", "Bearer "+token);
        return jsonObject;
    }

    /**
     * 添加用户
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addUser(User user) {
        try {
            // 1.生成随机盐
            String salt = SaltUtil.getSalt(8);
            // 2. 将随机盐保存到数据库
            user.setSalt(salt);
            // 3. 明文密码进行md5 + salt + hash散列
            Md5Hash md5Hash = new Md5Hash(user.getPassword(), salt, 1024);
            user.setPassword(md5Hash.toHex());
            userMapper.insert(user);
            // 4. 添加用户角色
            QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("id").in("name", user.getRoles());
            List<Role> rolesList = roleMapper.selectList(queryWrapper);
            for(Role role : rolesList) {
                userMapper.insertUserRole(user.getId(), role.getId());
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * 根据id删除用户
     */
    @Override
    public boolean deleteUserById(int id) {
        return userMapper.deleteById(id) > 0;
    }

    /**
     * 用户根据id更新用户信息，不包括角色信息，只能更自己的
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    
    public boolean updateUser(User user) {
        try {
            if(user.getId() == null) {
                return false;
            } else if (user.getUsername() != null && checkUsername(user.getUsername())) {
                return false;
            }
            User userDB = userMapper.findUserById(user.getId());
            if(user.getPassword() != null) {
                Md5Hash md5Hash = new Md5Hash(user.getPassword(), userDB.getSalt(), 1024);
                user.setPassword(md5Hash.toHex());
            }
            userMapper.updateById(user);
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据id更新用户信息，包括角色信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    
    public boolean updateUserAdmin(User user) {
        try {
            if(user.getId() == null) {
                return false;
            } else if (user.getUsername() != null && checkUsername(user.getUsername())) {
                return false;
            }
            User userDB = userMapper.findUserById(user.getId());
            if(user.getPassword() != null) {
                Md5Hash md5Hash = new Md5Hash(user.getPassword(), userDB.getSalt(), 1024);
                user.setPassword(md5Hash.toHex());
            }
            userMapper.updateById(user);
            if(user.getRoles() != null) {
                QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
                queryWrapper.select("id").in("name", user.getRoles());
                List<Role> rolesList = roleMapper.selectList(queryWrapper);
                userMapper.deleteUserRole(user.getId());
                for(Role role : rolesList) {
                    userMapper.insertUserRole(user.getId(), role.getId());
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据id查询用户
     */
    @Override
    public User findUserById(int id) {
        User user = userMapper.selectById(id);
        if(user != null){
            //不传密码
            user.setPassword(null);
        }
        return user;
    }


    /**
     * 查找带权限的用户
     */
    @Override
    public User findFullUserByUserName(String username) {
        log.info("findFullUserByUserName: " + username);
        User user = userMapper.findByUserName(username);
        if(user != null) {
            user.setRoles(new ArrayList<>());
            //不重复地把用户的角色和权限加入到用户对象中（角色不会重复，权限可能会，所以用set）
            List<Role> roleList = userMapper.findRolesByUserId(user.getId());
            Set<String> permissions = new HashSet<>();
            for(Role role : roleList) {
                user.getRoles().add(role.getName());
                List<Permission> permissionList = userMapper.findPermsByRoleId(role.getId());
                for(Permission permission : permissionList) {
                    permissions.add(permission.getName());
                }
            }
            user.setPermissions(new ArrayList<>(permissions));
        }
        return user;
    }

    @Override
    public boolean checkUsername(String username){
        return userMapper.selectCount(new QueryWrapper<User>().select("username").eq("username",username)) > 0;
    }

}
