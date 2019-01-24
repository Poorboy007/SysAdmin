package com.icolsky.model.dao.common;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by FuChang Liu
 */
public class BaseDAO {

    @Autowired
    protected SqlSessionTemplate sqlSessionTemplate;

}
