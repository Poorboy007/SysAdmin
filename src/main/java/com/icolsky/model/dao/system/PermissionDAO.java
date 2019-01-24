package com.icolsky.model.dao.system;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.icolsky.model.dao.common.BaseDAO;
import com.icolsky.model.entity.system.Permission;
import com.icolsky.vo.common.SimpleMenu;

/**
 * Created by FuChang Liu
 */
@Repository
public class PermissionDAO extends BaseDAO {

    public List<Permission> getAll(){
        return sqlSessionTemplate.selectList("Permission.getAll");
    }

    public List<Permission> findMenuByUserName(String userName){
        return sqlSessionTemplate.selectList("Permission.findMenuByUserName", userName);
    }

    public List<String> findFunctionByUserName(String userName){
        return sqlSessionTemplate.selectList("Permission.findFunctionByUserName", userName);
    }

    public Long findPermissionsCountByName(String name){
        return sqlSessionTemplate.selectOne("Permission.findPermissionsCountByName", name);
    }

    public List<Integer> findPermissionIdsByRoleId(Integer roleId){
        return sqlSessionTemplate.selectList("Permission.findPermissionIdsByRoleId", roleId);
    }

    public Integer insert(SimpleMenu menu){
        return sqlSessionTemplate.insert("Permission.insert", menu);
    }

    public Integer update(SimpleMenu menu){
        return sqlSessionTemplate.update("Permission.update", menu);
    }

    public Integer delete(Integer permissionId){
        return sqlSessionTemplate.delete("Permission.delete", permissionId);
    }
}
