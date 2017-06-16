package com.wjg.base.shiro.util;

import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * Created by wjg on 2017/6/15.
 */
public class CredentialsUtil {
    /**
     * 对密码进行盐值加密
     * @param password
     * @param salt
     * @param hashIterations
     * @return
     */
    public static String getPasswordCredentials(String password,String salt,Integer hashIterations){
        return new SimpleHash("MD5", password,
                salt, hashIterations).toString();
    }
}
