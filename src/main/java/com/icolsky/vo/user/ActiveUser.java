package com.icolsky.vo.user;

import java.util.List;

import com.icolsky.model.entity.system.User;
import com.icolsky.vo.common.SimpleMenu;

import lombok.Data;

/**
 * Created by FuChang Liu
 */
@Data
public class ActiveUser extends User{

    private List<SimpleMenu> menus;     // 菜单
    private List<String> funcs;         // 功能

    public ActiveUser(User user, List<SimpleMenu> menus, List<String> funcs){
        this.setId(user.getId());
        this.setUsername(user.getUsername());
        this.setName(user.getName());
        this.setTel(user.getTel());
        this.setEmail(user.getEmail());
        this.setZone(user.getZone());
        this.setStrDept(user.getStrDept());
        this.setDescription(user.getDescription());
        this.setMenus(menus);
        this.setFuncs(funcs);
    }

}
