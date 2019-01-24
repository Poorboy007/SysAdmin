package com.icolsky.config.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.icolsky.common.constants.BaseConstant;
import com.icolsky.util.common.ResponseUtil;
import com.icolsky.vo.common.RespResult;

/**
 * Created by FuChang Liu
 */
 

@CrossOrigin
public class CustomAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        ResponseUtil.sendResult(httpRequest, httpResponse, new RespResult(BaseConstant.ResultCode.NOT_LOGIN));
        return false;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String SID = httpServletRequest.getParameter("test");
        if(BaseConstant.System.SID_KEY.equals(SID)){
            return Boolean.TRUE;
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }

}
