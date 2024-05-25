package com.web10.music.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *  Shiro配置类
 **/
@Configuration
@Slf4j
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SessionsSecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        // 配置系统受限资源
        // 配置系统公共资源


        // 设置拦截器
        Map<String, String> map = new LinkedHashMap<>();

        // 放行swagger
        map.put("/swagger/**", "anon");
        map.put("/v2/api-docs", "anon");
        map.put("/swagger-ui.html", "anon");
        map.put("/swagger-resources/**", "anon");
        map.put("/webjars/**", "anon");
        map.put("/favicon.ico", "anon");
        map.put("/captcha.jpg", "anon");
        map.put("/csrf", "anon");

        //放行test
        map.put("/test", "anon");

        //其余接口一律拦截
        //主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截
        map.put("/**", "anon");

        factoryBean.setFilterChainDefinitionMap(map);
        System.out.println("shiro拦截器工厂类注入成功");

        //↑ 注意：如果有其他过滤法则要配在/**上，则使用逗号间隔。
        factoryBean.setSecurityManager(securityManager);
        return factoryBean;
    }

    @Bean
    protected Authorizer authorizer() {
        ModularRealmAuthorizer authorizer = new ModularRealmAuthorizer();
        return authorizer;
    }
}
