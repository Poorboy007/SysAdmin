package com.icolsky.vo.common;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.icolsky.common.constants.BaseConstant;

import lombok.Data;

/**
 * Created by FuChang Liu
 */
@Data
public class PageParam {

    private Integer intPage     = BaseConstant.Page.PAGE_NUM;
    private Integer intPageSize = BaseConstant.Page.PAGE_SIZE;

    public String toString(){
        return JSON.toJSONString(this);
    }

    /**
     * 创建分页参数MAP
     */
    public Map<String, Object> getPageParamMap(){
        Map params = new HashMap<String, Object>();
        params.put("offsetNum", (intPage - 1) * intPageSize );
        params.put("pageSize", intPageSize);
        return params;
    }


}
