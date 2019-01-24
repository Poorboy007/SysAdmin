package com.icolsky.model.entity.system;


import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by FuChang Liu
 */
@Data
public class User {

    private Integer id;             // 用户ID
    private String username;        // 用户名
    private String name;            // 名称
    private String password;        // 密码 
    private String tel;             // 手机号码
    private String email;           // 电子邮箱
    private String zone;            // 所属地区
    private String description;     // 简要描述
    private Date createDate;        // 创建时间
    private Date failureDate;       // 失效时间
    private String salt;            // 盐
    private Integer status;         // 0:不可用 1:可用

    private String strDept;         // 部门名称
    private List<Integer> rules;    // 管辖部门


    public String toString() {
        return JSON.toJSONString(this);
    }

}
