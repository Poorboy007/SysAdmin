package com.icolsky.model.dao.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.icolsky.model.dao.common.BaseDAO;
import com.icolsky.model.entity.system.Role;

/**
 * Created by FuChang Liu
 */
@Repository
public class RoleDAO extends BaseDAO {

    public List<Role> getAll(){
        return sqlSessionTemplate.selectList("Role.getAll");
    }

    public List<Role> findRolePageByMap(Map param){
        return sqlSessionTemplate.selectList("Role.findRolePageByMap", param);
    }

    public Long findRoleCountByMap(Map param){
        return sqlSessionTemplate.selectOne("Role.findRoleCountByMap", param);
    }

    public List<Integer> findRoleIdsByUserId(Integer userId){
        return sqlSessionTemplate.selectList("Role.findRoleIdsByUserId", userId);
    }

    public int insertRole(Role role){
        return sqlSessionTemplate.insert("Role.insert", role);
    }

    public int updateRole(Role role){
        return sqlSessionTemplate.update("Role.update", role);
    }

    public void deleteRolePermission(Integer roleId){
        sqlSessionTemplate.delete("Role.deleteRolePermission", roleId);
    }

    public int insertRolePermission(Integer roleId, Integer permissionId){
        Map<String, Integer> params = new HashMap<>();
        params.put("roleId", roleId);
        params.put("permissionId", permissionId);
        return sqlSessionTemplate.insert("Role.insertRolePermission", params);
    }

}
