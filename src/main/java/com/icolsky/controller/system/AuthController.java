package com.icolsky.controller.system;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icolsky.config.shiro.token.TokenManager;
import com.icolsky.controller.common.BaseController;
import com.icolsky.model.entity.system.User;
import com.icolsky.service.system.AuthService;
import com.icolsky.util.common.ValidateCode;
import com.icolsky.vo.common.RespResult;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by FuChang Liu
 */
@Slf4j
@Controller
@RequestMapping("/auth")
public class AuthController extends BaseController {

    @Autowired
    private AuthService authServer;

    private final static String INVALID_LOGIN_MSG       = "用户名或密码错误";
    private final static String INVALID_FAILURE_MSG     = "用户有效时间已过";
    private final static String INVALID_VALIDATE_CODE   = "验证码错误或过期";

    /**
     * 登录提交
     * @param user,
     * @return
     */
    @ResponseBody
    @RequestMapping(value="login")
    public RespResult submitLogin(User user, HttpServletRequest request){
        if(!inspectPIN(request)) {
            return new RespResult(RESULT_ERROR, INVALID_VALIDATE_CODE);
        }

        try {
            TokenManager.login(user);
        }catch (AuthenticationException ae){
            return new RespResult(RESULT_ERROR, INVALID_LOGIN_MSG);
        }

        if(isFailureUser()){
            return new RespResult(RESULT_ERROR, INVALID_FAILURE_MSG);
        }

        return new RespResult(RESULT_SUCCESS);
    }

    /**
     * 退出
     * @return
     */
    @ResponseBody
    @RequestMapping(value="logout")
    public RespResult logout(){
        try {
            TokenManager.logout();
        } catch (Exception e) {
            return new RespResult(RESULT_ERROR);
        }
        return new RespResult(RESULT_SUCCESS);
    }

    /**
     * 修改个人信息
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value="personal")
    public RespResult personal(User user){
        return authServer.updatePersonal(user);
    }

    /**
     * 重置密码
     * @param password
     * @param newPassword
     * @return
     */
    @ResponseBody
    @RequestMapping(value="resetPass")
    public RespResult resetPass(String password, String newPassword){
        return authServer.resetPassword(password, newPassword);
    }

    /**
     * 获取登陆信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value="info")
    public RespResult getAuthInfo(){
        return authServer.getAuthInfo();
    }

    /**
     * 验证码
     * @param response
     * @throws IOException
     */
    @RequestMapping("/PIN.img")
    public void getValiDateCode(HttpServletResponse response) throws IOException {
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "no-cache");           // 禁止图像缓存
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        ValidateCode vCode = new ValidateCode(120, 31, 4, 5);
        Cookie cookie = new Cookie("VALIDATE_CODE", vCode.getCode());
        response.addCookie(cookie);
        vCode.write(response.getOutputStream());
    }

    /**
     * 检验验证码
     * @param request
     * @return
     */
    public boolean inspectPIN(HttpServletRequest request){
        boolean inspect = false;
        String PIN = request.getParameter("validateCode");
        Cookie[] cookies = request.getCookies();
        if(cookies == null) return inspect;
        for(Cookie cookie : cookies){
            if("VALIDATE_CODE".equalsIgnoreCase(cookie.getName()) && PIN.equalsIgnoreCase(cookie.getValue())){
                cookie.setMaxAge(0);
                inspect = true;
                continue;
            }
        }
        return inspect;
    }

    /**
     * 验证用户是否失效
     * @return
     */
    public boolean isFailureUser(){
        boolean isFailure = false;
        Date failDate = TokenManager.getToken().getFailureDate();
        if(failDate == null) return isFailure;
        if(failDate.getTime() < new Date().getTime()){
            isFailure = true;
            TokenManager.logout();
        }
        return isFailure;
    }

}
