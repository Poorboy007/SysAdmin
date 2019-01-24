package com.icolsky.service.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icolsky.model.dao.system.PermissionDAO;
import com.icolsky.model.dao.system.RoleDAO;
import com.icolsky.model.entity.system.Permission;
import com.icolsky.model.entity.system.Role;
import com.icolsky.service.common.BaseService;
import com.icolsky.util.common.SimpleMenuUtil;
import com.icolsky.util.common.StringUtil;
import com.icolsky.vo.common.PageParam;
import com.icolsky.vo.common.RespResult;
import com.icolsky.vo.common.SimpleMenu;

/**
 * Created by FuChang Liu
 */
@Service
public class RoleService extends BaseService{

    @Autowired
    private RoleDAO roleDao;

    @Autowired
    private PermissionDAO permissionDao;

    /**
     * 角色列表
     * @param role
     * @param page
     * @return
     */
    public RespResult getRoleList(Role role, PageParam page){
        Map params = page.getPageParamMap();
        if(!StringUtil.isEmpty(role.getId())) {
            params.put("id", role.getId());
        }if(!StringUtil.isEmpty(role.getRole())){
            params.put("role", "%" + role.getRole() + "%");
        }if(!StringUtil.isEmpty(role.getDescription())){
            params.put("description", "%" + role.getDescription() + "%");
        }if(!StringUtil.isEmpty(role.getStatus())){
            params.put("status", role.getStatus());
        }

        List<Role> roles = roleDao.findRolePageByMap(params);
        Long count = roleDao.findRoleCountByMap(params);
        return new RespResult(RESULT_SUCCESS, roles, count);
    }

    /**
     * 角色添加
     * @param role
     * @return
     */
    public RespResult roleAdd(Role role){
        Map params = new HashMap<String, Object>();
        params.put("role", role.getRole());
        params.put("status", DEFAULT_VALID_STATUS);
        Long count = roleDao.findRoleCountByMap(params);
        if(count >0){
            return new RespResult(RESULT_ERROR, "角色名已经存在！");
        }
        return roleDao.insertRole(role)>0 ? new RespResult(RESULT_SUCCESS):new RespResult(RESULT_ERROR);
    }

    /**
     * 角色状态
     * @param id
     * @param status
     * @return
     */
    public RespResult roleStatus(Integer id, Integer status){
        Role r = new Role();
        r.setId(id);
        r.setStatus(status);
        return roleDao.updateRole(r)>0 ? new RespResult(RESULT_SUCCESS):new RespResult(RESULT_ERROR);
    }

    /**
     * 角色更新
     * @param role
     * @return
     */
    public RespResult roleUpdate(Role role){
        return roleDao.updateRole(role)>0 ? new RespResult(RESULT_SUCCESS):new RespResult(RESULT_ERROR);
    }

    /**
     * 获取角色权限
     * @param roleId
     * @return
     */
    public RespResult getPermissions(Integer roleId){
        List<Permission> alls = permissionDao.getAll();
        List<Integer> permissionids = permissionDao.findPermissionIdsByRoleId(roleId);
        for(Permission permission : alls){
            permission.setStatus(permissionids.contains(permission.getId()) ? 1 : 0);
        }
        List<SimpleMenu> simpleMenus = SimpleMenuUtil.getMenuTree(alls);
        return new RespResult(RESULT_SUCCESS, simpleMenus);
    }

    /**
     * 设置角色权限
     * @param id
     * @param permissions
     * @return
     */
    public RespResult setPermission(Integer id, String permissions){
        roleDao.deleteRolePermission(id);
        String[] arr = permissions.split("-");
        int insert = 0;
        for(String permission: arr){
            Integer intPermission = Integer.parseInt(permission);
            insert += roleDao.insertRolePermission(id, intPermission);
        }
        return new RespResult(RESULT_SUCCESS);
    }

}
