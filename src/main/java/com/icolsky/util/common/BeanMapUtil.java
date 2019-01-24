package com.icolsky.util.common;


import com.google.common.collect.Maps;
import org.springframework.cglib.beans.BeanMap;

import java.util.*;

/**
 * Created by FuChang Liu
 */
public class BeanMapUtil {


    /**
     * 将 Bean 转换为 Map
     */
    public static <T> Map<String, Object> getMapFromBean(T t) {
        Map<String, Object> map = Maps.newHashMap();
        if (t != null) {
            BeanMap beanMap = BeanMap.create(t);
            for (Object key : beanMap.keySet()) {
                if(beanMap.get(key) != null) map.put(key.toString(), beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * 将 Map 转换为 Bean
     */
    public static <T> T getBeanFormMap(Map<String, Object> map, T t) {
        BeanMap beanMap = BeanMap.create(t);
        beanMap.putAll(map);
        return t;
    }

    /**
     * 将 List<Bean> 转换为 List<Map>
     */
    public static <T> List<Map<String, Object>> getListMapFromListBean(List<T> ts) {
        List<Map<String, Object>> list = new ArrayList<>();
        for(T t : ts){
            Map<String, Object> map = getResultMapFromBean(t);
            list.add(map);
        }
        //list = setSequenceNumber(list);
        return list;
    }

    /**
     * 将 Bean 转换为 结果Map
     */
    public static <T> Map<String, Object> getResultMapFromBean(T t) {
        if(t instanceof Map){
            return (Map<String, Object>)t;
        }

        Map<String, Object> map = Maps.newHashMap();
        if (t != null) {
            BeanMap beanMap = BeanMap.create(t);
            for (Object key : beanMap.keySet()) {
                Object value = beanMap.get(key);
                if(value instanceof Date){
                    map.put(key.toString(), StringUtil.isEmpty(value) ? "" : DateUtil.getStringFromDate((Date)value));
                }else{
                    map.put(key.toString(), StringUtil.isEmpty(value)? "" : value);
                }
            }
        }
        return map;
    }

    public static List<Map<String, Object>> setSequenceNumber(List<Map<String, Object>> list){
        for(int i = 0; i < list.size(); i++){
            Map<String, Object> beanMap = list.get(i);
            beanMap.put("sequence", i + 1);
        }
        return list;
    }

}
