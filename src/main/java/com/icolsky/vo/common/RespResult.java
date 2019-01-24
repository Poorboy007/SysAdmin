package com.icolsky.vo.common;

import java.util.List;
import java.util.Map;

import com.icolsky.util.common.BeanMapUtil;

import lombok.Data;

/**
 * Created by FuChang Liu
 */
@Data
public class RespResult {

    private Integer code;

    private String msg;

    private Object data;

    private Long count;

    private Map support;


    public RespResult(Integer code){
        this.code = code;
    }

    public RespResult(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public RespResult(Integer code, Object data){
        this.code = code;
        this.data = data instanceof List? BeanMapUtil.getListMapFromListBean((List)data): data;
    }

	public RespResult(Integer code, Object data, String msg){
        this.code = code;
        this.msg = msg;
        this.data = data instanceof List? BeanMapUtil.getListMapFromListBean((List)data): data;
    }

    public RespResult(Integer code, Object data, Map support){
        this.code = code;
        this.support = support;
        this.data = data instanceof List? BeanMapUtil.getListMapFromListBean((List)data): data;
    }

    public RespResult(Integer code, List lists, Long count){
        List<Map<String, Object>> datas = BeanMapUtil.getListMapFromListBean(lists);
        this.code = code;
        this.data = datas;
        this.count = count;
    }

    public RespResult(Integer code, List lists, Long count, Map support){
        List<Map<String, Object>> datas = BeanMapUtil.getListMapFromListBean(lists);
        this.code = code;
        this.data = datas;
        this.count = count;
        this.support = support;
    }


}
