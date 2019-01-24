package com.icolsky.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.icolsky.controller.common.BaseController;
import com.icolsky.model.entity.system.User;
import com.icolsky.service.system.UserService;
import com.icolsky.vo.common.PageParam;
import com.icolsky.vo.common.RespResult;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by FuChang Liu
 */
@Slf4j
@RestController
@RequestMapping("/system/user")
public class UserController extends BaseController {

    @Autowired
    protected UserService userServer;

    /**
     * 登录提交
     */
    @RequestMapping(value="list")
    public RespResult userList(User user, PageParam page){
        return userServer.getUserList(user, page);
    }

    /**
     * 新增
     * @param user,
     * @return
     */
    @RequestMapping(value="add")
    public RespResult userAdd(User user){
        return userServer.userAdd(user);
    }


    /**
     * 密码修改
     * @param id
     * @param password
     * @return
     */
    @RequestMapping(value="password")
    public RespResult userPassword(Integer id, String password){
        return userServer.userPassWord(id, password);

    }

    /**
     * 状态修改
     * @param id
     * @param status
     * @return
     */
    @RequestMapping(value="status")
    public RespResult userStatus(Integer id, Integer status){
        return userServer.userStatus(id, status);
    }

    /**
     * 修改
     * @param user,
     * @return
     */
    @RequestMapping(value="update")
    public RespResult userUpdate(User user){
        return userServer.userUpdate(user);
    }

    /**
     * 修改个人信息
     * @param user,
     * @return
     */
    @RequestMapping(value="myself")
    public RespResult userMyself(User user){
        return userServer.userMyself(user);
    }

    /**
     * 获取所有角色信息
     * @return
     */
    @RequestMapping(value="roles")
    public RespResult roles(Integer id){
        return userServer.getUserRoles(id);
    }

    /**
     * 设置该用户角色信息
     * @return
     */
    @RequestMapping(value="setRoles")
    public RespResult setRoles(Integer id, String roles){
        return userServer.setUserRoles(id, roles);
    }


}
