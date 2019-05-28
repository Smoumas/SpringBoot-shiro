package com.shiro.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    /**
     * ShiroConfig创建三个Bean
     * ShiroFilterFactoryBean
     * DefaultWebSecurityManager
     * Realm
     */

    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        /*
            Shiro内置过滤器可以实现权限相关的拦截
            常用的过滤器：
                anon：无需认证
                authc：必须认证才可以访问
                user：如果使用了RememberMe的功能，可以访问
                perms：该资源必须资源权限才可以访问
                role：该资源必须得到角色权限才可以访问
         */
        Map<String,String> filterMap = new LinkedHashMap<String,String>();
        filterMap.put("/add","authc");
        filterMap.put("/update","authc");
        filterMap.put("/logout","logout");

        //授权过滤器
        //授权拦截后，shiro会自动跳转到未授权页面
        filterMap.put("/add","perms[user:add]");
        filterMap.put("/update","perms[user:update]");

        //设置退出后返回登录页面
        Map<String,Filter> filters = new LinkedHashMap<String, Filter>();
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setRedirectUrl("/login");
        filters.put("logout",logoutFilter);

        /*
            可以使用通配符进行统一过滤
            如统一拦截user目录下的所有资源
            filterMap.put("/user/*","authc");
         */
        /*
            拦截过后自动跳转login.jps
            修改跳转的页面
         */
        //设置登录页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        //设置未授权提示页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/unAuth");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        shiroFilterFactoryBean.setFilters(filters);
        return shiroFilterFactoryBean;
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联Realm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    //为登录密码添加SHA256校验
    @Bean(name = "userRealm")
    public UserRealm getRealm(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher hashedCredentialsMatcher){
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return userRealm;
    }

    /**
     * 配置ShiroDialect，用于Thymeleaf和shiro标签整合
     * @return
     */
    @Bean(name = "shiroDialect")
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }

    @Bean(name = "hashedCredentialsMatcher")
    public HashedCredentialsMatcher getHashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("SHA-256");
//        hashedCredentialsMatcher.setHashIterations(1024);
        return hashedCredentialsMatcher;
    }
}
