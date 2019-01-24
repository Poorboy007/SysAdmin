package com.icolsky.service.system;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icolsky.manager.UserManager;
import com.icolsky.model.dao.system.RoleDAO;
import com.icolsky.model.dao.system.UserDAO;
import com.icolsky.model.entity.system.Role;
import com.icolsky.model.entity.system.User;
import com.icolsky.service.common.BaseService;
import com.icolsky.util.common.StringUtil;
import com.icolsky.vo.common.PageParam;
import com.icolsky.vo.common.RespResult;

import lombok.extern.java.Log;

/**
 * Created by FuChang Liu
 */
@Service
@Log
public class UserService extends BaseService{

    @Autowired
    private UserDAO userDao;
    @Autowired
    private RoleDAO roleDao;


    /**
     * 用户名查找登录用户
     * @param username
     * @return
     */
    public User getLoginUser(String username){
        return userDao.getLoginUser(username);
    }

    /**
     * 用户列表
     * @param user
     * @param page
     * @return
     */
    public RespResult getUserList(User user, PageParam page){
        Map params = page.getPageParamMap();
        if(!StringUtil.isEmpty(user.getUsername())){
            params.put("username", "%" + user.getUsername() + "%");
        }if(!StringUtil.isEmpty(user.getName())){
            params.put("name", "%" + user.getName() + "%");
        }if(!StringUtil.isEmpty(user.getTel())){
            params.put("tel", "%" + user.getTel() + "%");
        }if(!StringUtil.isEmpty(user.getEmail())){
            params.put("email", "%" + user.getEmail() + "%");
        }if(!StringUtil.isEmpty(user.getStatus())){
            params.put("status", user.getStatus());
        }
         
        List<User> users = userDao.findUserPageByMap(params); 
        Long count = userDao.findUserCountByMap(params);
        return new RespResult(RESULT_SUCCESS, users, count);
    }

    /**
     * 用户添加
     * @param user
     * @return
     */
    public RespResult userAdd(User user){
        HashMap params = new HashMap<String, Object>();
        params.put("username", user.getUsername());
        params.put("status", DEFAULT_VALID_STATUS);
        if(userDao.findUserCountByMap(params) > 0){
            return new RespResult(RESULT_ERROR, "用户名已经存在！");
        }

        if(StringUtil.isEmpty(user.getFailureDate())){
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.YEAR,3);
            user.setFailureDate(cal.getTime());
        }
        user = UserManager.MD5UserPassword(user);
        return userDao.insertUser(user) > 0 ? new RespResult(RESULT_SUCCESS):new RespResult(RESULT_ERROR);
    }

    /**
     * 用户密码修改
     * @param id
     * @param password
     * @return
     */
    public RespResult userPassWord(Integer id, String password){
        User user = new User();
        user.setId(id);
        user.setPassword(password);
        return userDao.updateUser(UserManager.MD5UserPassword(user))>0 ? new RespResult(RESULT_SUCCESS):new RespResult(RESULT_ERROR);
    }

    /**
     * 用户状态修改
     * @param id
     * @param status
     * @return
     */
    public RespResult userStatus(Integer id, Integer status){
        User u = new User();
        u.setId(id);
        u.setStatus(status);
        return userDao.updateUser(u)>0 ? new RespResult(RESULT_SUCCESS):new RespResult(RESULT_ERROR);
    }

    /**
     * 用户状态修改
     * @param user
     * @return
     */
    public RespResult userUpdate(User user){
        return userDao.updateUser(user)>0 ? new RespResult(RESULT_SUCCESS):new RespResult(RESULT_ERROR);
    }

    /**
     * 用户自己信息修改
     * @param user
     * @return
     */
    public RespResult userMyself(User user){
        if(!StringUtil.isEmpty(user.getPassword())){
            user = UserManager.MD5UserPassword(user);
        }else{
            user.setPassword(null);
        }
        return userDao.updateUser(user)>0 ? new RespResult(RESULT_SUCCESS):new RespResult(RESULT_ERROR);
    }

    /**
     * 获取用户角色
     * @param id
     * @return
     */
    public RespResult getUserRoles(Integer id){
        List<Integer> roleids = roleDao.findRoleIdsByUserId(id);
        List<Role> alls = roleDao.getAll();
        for(Role role : alls){
            role.setStatus(roleids.contains(role.getId()) ? 1 : 0);
        }
        return new RespResult(RESULT_SUCCESS, alls);
    }

    /**
     * 设置用户角色
     * @param id
     * @param roles
     * @return
     */
    public RespResult setUserRoles(Integer id, String roles){
        userDao.delteUserRole(id);
        if(StringUtil.isEmpty(roles)){
            return new RespResult(RESULT_SUCCESS);
        }
        String[] arrs = roles.split("-");
        int insert = 0;
        for(String role: arrs){
            Integer intRole = Integer.parseInt(role);
            insert += userDao.insertUserRole(id, intRole);
        }
        return new RespResult(RESULT_SUCCESS);
    }


}
