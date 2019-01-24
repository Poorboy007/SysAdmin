package com.icolsky.service.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icolsky.model.dao.system.PermissionDAO;
import com.icolsky.model.entity.system.Permission;
import com.icolsky.service.common.BaseService;
import com.icolsky.util.common.SimpleMenuUtil;
import com.icolsky.util.common.StringUtil;
import com.icolsky.vo.common.RespResult;
import com.icolsky.vo.common.SimpleMenu;

/**
 * Created by FuChang Liu
 */
@Service
public class PermissionService extends BaseService{

    @Autowired
    private PermissionDAO permissionDao;

    /**
     * 获取权限展示
     * @return
     */
    public RespResult getMenu(){
        List<Permission> alls = permissionDao.getAll();
        for(Permission p: alls){
            p.setSort( p.getSort()!=null ? p.getSort() : 0);
            p.setParentId( p.getParentId()!=null ? p.getParentId() : 0);
            p.setUrl( !StringUtil.isEmpty(p.getUrl()) ? p.getUrl() : "");
            p.setName( !StringUtil.isEmpty(p.getName()) ? p.getName() : "");
            p.setPermission( !StringUtil.isEmpty(p.getPermission()) ? p.getPermission() : "");
            p.setDescription( !StringUtil.isEmpty(p.getDescription()) ? p.getDescription() : "");
            p.setResourceType( !StringUtil.isEmpty(p.getResourceType()) ? p.getResourceType() : "");
        }
        List<SimpleMenu> simpleMenus = SimpleMenuUtil.getMenuTree(alls);
        return new RespResult(RESULT_SUCCESS, simpleMenus);
    }

    /**
     * 权限添加
     * @param menu
     * @return
     */
    public RespResult permissionAdd(SimpleMenu menu){
        if(permissionDao.findPermissionsCountByName(menu.getName()) > 0){
            new RespResult(RESULT_ERROR, "权限名已存在");
        }
        return (permissionDao.insert(menu) > 0)? new RespResult(RESULT_SUCCESS): new RespResult(RESULT_ERROR);
    }

    /**
     * 权限更新
     * @param menu
     * @return
     */
    public RespResult permissionUpdate(SimpleMenu menu){
        return (permissionDao.update(menu) > 0)? new RespResult(RESULT_SUCCESS): new RespResult(RESULT_ERROR);
    }

    /**
     * 权限删除
     * @param permissionId
     * @return
     */
    public RespResult permissionDelete(Integer permissionId){
        return (permissionDao.delete(permissionId) > 0)? new RespResult(RESULT_SUCCESS): new RespResult(RESULT_ERROR);
    }

}
