package com.icolsky.config.shiro;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.icolsky.config.shiro.filter.CustomAuthenticationFilter;
import com.icolsky.config.shiro.realm.SampleRealm;
import com.icolsky.config.shiro.session.CustomSessionManager;


/**
 * Created by FuChang Liu
 */
@Configuration
public class ShiroConfiguration {

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 验证拦截
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/test/**", "anon");
        filterChainDefinitionMap.put("/auth/login", "anon");
        filterChainDefinitionMap.put("/auth/PIN.img", "anon");
  
        filterChainDefinitionMap.put("/**", "authc");
        //filterChainDefinitionMap.put("/**", "anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        // 拦截器
        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("authc" , new CustomAuthenticationFilter());
        shiroFilterFactoryBean.setFilters(filters);
        return shiroFilterFactoryBean;
    }

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

    /**
     * 自定义AuthenticationFilter
     * @return
     */
    @Bean
    public CustomAuthenticationFilter CustomAuthenticationFilter(){
        CustomAuthenticationFilter myAuthenticationFilter = new CustomAuthenticationFilter();
        return myAuthenticationFilter;
    }

    /**
     * SecurityManager
     * @return
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(getSampleRealm());
        securityManager.setCacheManager(getEhCacheManager());   //用户授权、认证信息Cache
        securityManager.setSessionManager(getSessionManager());
        return securityManager;
    }

    /**
     * 身份认证realm;
     * @return
     */
    @Bean
    public SampleRealm getSampleRealm(){
        SampleRealm sampleRealm = new SampleRealm();
        //sampleRealm.setCredentialsMatcher(getHashedCredentialsMatcher());
        return sampleRealm;
    }

    /**
     * 凭证匹配器
     * @return
     */
    @Bean
    public HashedCredentialsMatcher getHashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
        //hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));
        return hashedCredentialsMatcher;
    }

    /**
     * 缓存管理器
     * @return
     */
    @Bean
    public EhCacheManager getEhCacheManager() {
        EhCacheManager em = new EhCacheManager();
        em.setCacheManagerConfigFile("classpath:echcache-shiro.xml");
        return em;
    }

    /**
     * Session管理器
     * @return
     */
    @Bean
    public SessionManager getSessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setCacheManager(getEhCacheManager());
        sessionManager.setSessionDAO(getSessionDao());
        sessionManager.setGlobalSessionTimeout(1200000);     //session的失效时长，单位毫米 60 * 20 * 1000
        sessionManager.setDeleteInvalidSessions(true);      //删除失效的Session
        //sessionManager.setSessionValidationSchedulerEnabled(true);
        return sessionManager;
    }

    /**
     * SessionDAO
     * @return
     */
    @Bean
    public SessionDAO getSessionDao(){
        SessionDAO sessionDao = new MemorySessionDAO();
        return sessionDao;
    }

    /**
     * 个人Session管理器
     * 这个与SessionDAO不同，主要用来查看session
     * @return
     */
    @Bean
    public CustomSessionManager getCustomSessionManager(){
        CustomSessionManager sessionManager = new CustomSessionManager();
        return sessionManager;
    }

    /**
     *  开启shiro aop注解支持.
     *  使用代理方式;所以需要开启代码支持;
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 异常处理
     * @return
     */
     //@Bean
     public SimpleMappingExceptionResolver getSimpleMappingExceptionResolver() {
         SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();

         Properties mappings = new Properties();

         //mappings.setProperty("DatabaseException", "databaseError");//数据库异常处理
         //mappings.setProperty("org.apache.shiro.authz.UnauthenticatedException","403");  // 未认证处理页面
         //mappings.setProperty("org.apache.shiro.authz.UnauthorizedException","403");     // 未授权处理页面
         // mappings.setProperty("org.apache.shiro.authz.UnauthorizedException","redirect:mytest");

         resolver.setExceptionMappings(mappings);  // 定义需要特殊处理的异常，用类名或完全路径名作为key，异常也页名作为值
         //resolver.setDefaultErrorView(null);    // 定义默认的异常处理页面，当该异常类型的注册时使用
         // r.setExceptionAttribute("ex");     // 定义异常处理页面用来获取异常信息的变量名，默认名为exception
         // //r.setWarnLogCategory("example.MvcLogger");     // No default

         return resolver;
     }
}