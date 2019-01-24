package com.icolsky.config.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.servlet.AdviceFilter;

import com.icolsky.common.constants.BaseConstant;
import com.icolsky.config.shiro.token.TokenManager;
import com.icolsky.util.common.ResponseUtil;
import com.icolsky.vo.common.RespResult;

/**
 * Created by FuChang Liu
 */
public class CustomAuthFilter extends AdviceFilter {

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        if(!TokenManager.isLogin()){
            ResponseUtil.sendResult(httpRequest, httpResponse, new RespResult(BaseConstant.ResultCode.NOT_LOGIN));
            return false;
        }
        return true;
    }



}
