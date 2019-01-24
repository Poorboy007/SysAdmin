package com.icolsky.util.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.icolsky.model.entity.system.Permission;
import com.icolsky.vo.common.SimpleMenu;

/**
 * Created by FuChang Liu
 */
public class SimpleMenuUtil {

    /**
     * 通过权限列表获取菜单树
     * @param permissions 用户权限列表
     * @return
     */
    public static List<SimpleMenu> getMenuTree(List<Permission> permissions) {
        List<SimpleMenu> rootMenu = getMenuList(permissions);
        List<SimpleMenu> menutree = new ArrayList<SimpleMenu>();
        //先找到所有的一级菜单
        for (SimpleMenu menu : rootMenu) {
            if (menu.getParentId() == 0) {
                menutree.add(menu);
            }
        }
        sortMenuList(menutree);
        //为所有一级菜单建造子菜单，通过叠归方式
        for (SimpleMenu menu : menutree) {
            menu.setMenu(getChildMenuTree(menu.getId(), rootMenu));
        }
        return menutree;
    }

    /**
     * 获取子菜单树
     * @param parentId 父菜单ID
     * @param rootMenu 未整理菜单
     * @return
     */
    private static List<SimpleMenu> getChildMenuTree(Integer parentId, List<SimpleMenu> rootMenu) {
        List<SimpleMenu> childMenutree = new ArrayList<SimpleMenu>();
        for (SimpleMenu menu : rootMenu) {
            if (menu.getParentId() == parentId) {
                childMenutree.add(menu);
            }
        }
        for (SimpleMenu menu : childMenutree) {
            menu.setMenu(getChildMenuTree(menu.getId(), rootMenu));
        }
        if (childMenutree.size() == 0) return null;
        sortMenuList(childMenutree);
        return childMenutree;
    }

    /**
     * 权限转化菜单
     * @param permisssions 权限集
     * @return
     */
    private static List<SimpleMenu> getMenuList(List<Permission> permisssions){
        List<SimpleMenu> mens = new ArrayList<SimpleMenu>();
        Map<Integer, String> permissionMap = new HashMap<>();
        for(Permission permission : permisssions){
            permissionMap.put(permission.getId(), permission.getName());
        }
        for(Permission permission : permisssions){
            SimpleMenu menu = new SimpleMenu();
            menu.setId(permission.getId());
            menu.setName(permission.getName());
            menu.setIcon(permission.getIcon());
            menu.setType(permission.getResourceType());
            menu.setDescription(permission.getDescription());
            menu.setPermission(permission.getPermission());
            menu.setParentId(permission.getParentId());
            menu.setParentName(permission.getParentId() != 0 ? permissionMap.get(permission.getParentId()) : "根目录");
            menu.setUrl(permission.getUrl());
            menu.setSortnum(permission.getSort());
            menu.setStatus(permission.getStatus());
            mens.add(menu);
        }
        return mens;
    }


    /**
     * 对菜单进行排序
     * @param menus 菜单集
     * @return
     */
    private static List<SimpleMenu> sortMenuList(List<SimpleMenu> menus){
        Collections.sort(menus, new Comparator<SimpleMenu>(){
            /*
             * int compare(Menu o1, Menu o2) 返回一个基本类型的整型，
             * 返回: 负数:o1小于o2, 0表:o1等于o2, 正数:o1大于o2。
             */
            public int compare(SimpleMenu o1, SimpleMenu o2) {
                if(o1.getSortnum() != null && o2.getSortnum() != null){
                    return o1.getSortnum() - o2.getSortnum();
                    //return o2.getSortnum() - o1.getSortnum();
                }
                return 0;
            }
        });
        return menus;
    }

}
