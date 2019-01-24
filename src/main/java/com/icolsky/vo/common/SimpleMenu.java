package com.icolsky.vo.common;

import lombok.Data;

import java.util.List;

/**
 * Created by FuChang Liu
 */
@Data
public class SimpleMenu {

    private Integer id;
    private String name;
    private String icon;
    private String type;
    private String url;
    private String description;
    private String permission;
    private Integer parentId;
    private String parentName;
    private Integer sortnum;
    private Integer status;
    private List<SimpleMenu> menu;

}
