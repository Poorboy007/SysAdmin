package com.icolsky.config.shiro.realm;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.icolsky.model.entity.system.User;
import com.icolsky.service.system.UserService;


/**
 * Created by FuChang Liu
 */
public class SampleRealm extends AuthorizingRealm {

    @Autowired
    private UserService userServer;

    /**
     * 认证信息(身份验证)
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 获取用户的输入帐号
        String username = (String) token.getPrincipal();
        User user = userServer.getLoginUser(username);
        if (user == null) return null;
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
        return authenticationInfo;
    }

    /**
     * 权限控制
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = (User) principals.getPrimaryPrincipal();
//        List<Permission> permissions = permissionServer.getPermissionList(user.getUsername());
//        for(Permission permission : permissions){
//            if(permission.getPermission() != null && !permission.getPermission().isEmpty())
//                authorizationInfo.addStringPermission(permission.getPermission());
//            if(permission.getUrl() != null && !permission.getUrl().isEmpty())
//                authorizationInfo.addStringPermission(permission.getUrl());
//        }
        return authorizationInfo;
    }

    /**
     * 清空当前用户权限信息
     */
    public void clearCachedAuthorizationInfo() {
        PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
        SimplePrincipalCollection principals = new SimplePrincipalCollection(
                principalCollection, getName());
        super.clearCachedAuthorizationInfo(principals);
    }


    /**
     * 指定principalCollection 清除
     * @param principalCollection
     */
    public void clearCachedAuthorizationInfo(PrincipalCollection principalCollection) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(principalCollection, getName());
        super.clearCachedAuthorizationInfo(principals);
    }

}