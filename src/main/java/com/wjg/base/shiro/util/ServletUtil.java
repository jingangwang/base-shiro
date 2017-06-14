package com.wjg.base.shiro.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wjg on 2017/6/13.
 */
public class ServletUtil {

    private static String[] staticFileSuffixs = new PropertiesUtil("common.properties").getProperty(PropertiesUtil.WEB_STATIC_FILE_SUFFIX).split(",");

    /**
     * 随时随地获取request
     * 在web.xml里配置了此监听org.springframework.web.context.request.RequestContextListener
     * 后就可以使用此方法
     *
     * @return
     */
    public static HttpServletRequest getReqeust() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 判断是否是静态资源的url
     * 此方法用作过滤uri，防止多次调用redisSessionDAO的update和read方法
     *
     * @param uri
     * @return
     */
    public static boolean isStaticURI(String uri) {
        if (StringUtils.endsWithAny(uri, staticFileSuffixs)) {
            return true;
        }
        return false;
    }
}
