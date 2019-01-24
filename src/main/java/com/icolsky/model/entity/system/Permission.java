package com.icolsky.model.entity.system;


import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * Created by FuChang Liu
 */
@Data
public class Permission {

    private Integer id;             // 权限ID
    private String name;            // 权限名称
    private String icon;            // 权限图标
    private String resourceType;    // 资源类型
    private String description;     // 描述
    private String permission;      // 权限标识符
    private String url;             // 权限URL
    private Integer parentId;       // 父项ID
    private Integer sort;           // 排序号
    private Integer status;         // 0:不可用 1:可用

    public String toString(){
        return JSON.toJSONString(this);
    }
}
