package com.wjg.base.shiro.util;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.codec.Base64;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by wjg on 2017/6/13.
 */
public class PropertiesUtil {

    public static final String WEB_STATIC_FILE_SUFFIX = "web.static.file.suffix";

    public static final String WEB_VIEW_PREFIX="web.view.prefix";

    public static final String WEB_VIEW_SUFFIX="web.view.suffix";

    private String propertiesName;

    private HashMap<String,String> map = Maps.newHashMap();

    public PropertiesUtil(String propertiesName) {
        this.propertiesName = propertiesName;
    }

    public String getProperty(String key) {
        String value = map.get(key);
        if(!StringUtils.isEmpty(value)){
            return value;
        }
        InputStream is = PropertiesUtil.class.getClassLoader().getResourceAsStream(propertiesName);
        Properties properties = new Properties();
        try {
            properties.load(is);
            value =  properties.get(key).toString();
            map.put(key,value);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return value;

    }
}
