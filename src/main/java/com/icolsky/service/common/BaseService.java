package com.icolsky.service.common;

import org.apache.shiro.crypto.hash.Md5Hash;

import com.icolsky.common.constants.BaseConstant;
 
/**
 * Created by FuChang Liu
 */
public class BaseService {

    protected static final Integer DEFAULT_PAGE_NUM  = 1;
    protected static final Integer DEFAULT_PAGE_SIZE = 15;
    protected static final Integer DEFAULT_VALID_STATUS = 1;

    protected static final Integer RESULT_ERROR     =  BaseConstant.ResultCode.ERROR;
    protected static final Integer RESULT_SUCCESS   =  BaseConstant.ResultCode.SUCCESS;
    protected static final Integer RESULT_NOT_LOGIN =  BaseConstant.ResultCode.NOT_LOGIN;

    protected static final Integer IMPORT_ERROR     =  BaseConstant.ResultCode.IMPORT_ERROR;
    protected static final Integer FREQUENT_OPERATION= BaseConstant.ResultCode.FREQUENT_OPERATION;
    protected static final String DEFAULT_EMTRY = "";


    /**
     * 获取FILE UID唯一码
     * @return
     */
    protected static String getFileUid(){
        long random = (long) ((Math.random() * 9 + 1) * 10000);
        String strValue = System.currentTimeMillis() + "_fileUid_" + random;
        Md5Hash hash = new Md5Hash(strValue);
        return hash.toString();
    }
  
}
