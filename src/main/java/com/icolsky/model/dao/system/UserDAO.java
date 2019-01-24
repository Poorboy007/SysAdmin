package com.icolsky.model.dao.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.icolsky.model.dao.common.BaseDAO;
import com.icolsky.model.entity.system.User;

/**
 * Created by FuChang Liu
 */
@Repository
public class UserDAO extends BaseDAO {

    public User getLoginUser(String username){
        return (User) sqlSessionTemplate.selectOne("User.findLoginUser", username);
    }

    public List<User> findUserPageByMap(Map params){
        return sqlSessionTemplate.selectList("User.findUserPageByMap", params);
    }

    public Long findUserCountByMap(Map params){
        return sqlSessionTemplate.selectOne("User.findUserCountByMap", params);
    }

    public Long findUserCountByDept(Integer deptId){
        return sqlSessionTemplate.selectOne("User.findUserCountByDept", deptId);
    }

    public int insertUser(User user){
        return sqlSessionTemplate.insert("User.insert", user);
    }

    public int updateUser(User user){
        return sqlSessionTemplate.update("User.update", user);
    }

    public int delteUserRole(Integer userId){
        return sqlSessionTemplate.delete("User.delteUserRole", userId);
    }

    public int insertUserRole(Integer userId, Integer roleId){
        Map<String, Integer> params = new HashMap<>();
        params.put("userId", userId);
        params.put("roleId", roleId);
        return sqlSessionTemplate.insert("User.insertUserRole", params);
    }

    public List<String> getUserNamesByDeptid(List<Integer> deptids){
        return sqlSessionTemplate.selectList("User.getUserNamesByDeptid", deptids);
    }

    public int delteUserDepts(Integer userId){
        return sqlSessionTemplate.delete("User.delteUserDepts", userId);
    }

    public int insertUserDept(Integer userId, Integer deptId){
        Map<String, Integer> params = new HashMap<>();
        params.put("userId", userId);
        params.put("deptId", deptId);
        return sqlSessionTemplate.insert("User.insertUserDept", params);
    }
}
