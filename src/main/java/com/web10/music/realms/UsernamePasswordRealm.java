package com.web10.music.realms;

import com.web10.music.entity.JwtUser;
import com.web10.music.entity.User;
import com.web10.music.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

//Username Password Realm，用户名密码登陆专用Realm
@Slf4j
@Component
public class UsernamePasswordRealm extends AuthenticatingRealm {//只管验证不管鉴权

    @Resource
    private IUserService userService;

    /*构造器里配置Matcher*/
    public UsernamePasswordRealm() {
        super();
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(1024);//密码保存策略一致，1024次md5加密
        this.setCredentialsMatcher(hashedCredentialsMatcher);
    }

    /**
     * 通过该方法来判断是否由本realm来处理login请求
     *
     * 调用{@code doGetAuthenticationInfo(AuthenticationToken)}之前会shiro会调用{@code supper.supports(AuthenticationToken)}
     * 来判断该realm是否支持对应类型的AuthenticationToken,如果相匹配则会走此realm
     *
     * @return
     */
    @Override
    public Class getAuthenticationTokenClass() {
        log.info("UsernamePasswordRealm getAuthenticationTokenClass");
        return UsernamePasswordToken.class;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        //继承但啥都不做就为了打印一下info
        boolean res = super.supports(token);//会调用↑getAuthenticationTokenClass来判断
        log.info("[UsernamePasswordRealm is supports]" + res);
        return res;
    }

    /**
     * 用户名和密码验证，login接口专用。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {

        UsernamePasswordToken usernamePasswordToken=(UsernamePasswordToken) token;

        User userFromDB = userService.findFullUserByUserName(usernamePasswordToken.getUsername());
        if (userFromDB == null) {
            return null;
        }else{
            log.info("userFromDB: "+userFromDB);
        }

        String passwordFromDB = userFromDB.getPassword();
        String salt = userFromDB.getSalt();

        //在使用jwt访问时，shiro中能拿到的用户信息只能是token中携带的jwtUser，所以此处保持统一。
        JwtUser jwtUser=new JwtUser(userFromDB.getId(),userFromDB.getRoles(),userFromDB.getPermissions());
        log.info("jwtUser: "+jwtUser);
        SimpleAuthenticationInfo res = new SimpleAuthenticationInfo(userFromDB, passwordFromDB, ByteSource.Util.bytes(salt),
                getName());
        return res;
    }
}
