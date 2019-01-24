package com.icolsky.model.entity.system;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * Created by FuChang Liu
 */
@Data
public class Role {

    private Integer id;             // ID
    private String role;            // 角色标识程序中判断使用，如"admin"，这个是唯一的:
    private String description;     // 角色描述，UI界面显示使用
    private Integer status;         // 0:不可用 1:可用

    public String toString(){
        return JSON.toJSONString(this);
    }
}
