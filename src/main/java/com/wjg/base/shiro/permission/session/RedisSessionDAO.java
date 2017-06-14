package com.wjg.base.shiro.permission.session;

import com.wjg.base.shiro.util.PropertiesUtil;
import com.wjg.base.shiro.util.ServletUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * Created by wjg on 2017/6/9.
 */
public class RedisSessionDAO extends EnterpriseCacheSessionDAO {
    private static Logger logger = LoggerFactory.getLogger(RedisSessionDAO.class);
    private RedisTemplate<String, Session> redisTemplate;

    public RedisSessionDAO(RedisTemplate<String, Session> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected Serializable doCreate(Session session) {
        logger.info("创建session。。。");
        Serializable sessionId = super.doCreate(session);
        redisTemplate.opsForValue().set(sessionId.toString(), session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (request != null) {
            String uri = request.getServletPath();
            logger.info("requestURI:" + uri);
            //如果是静态文件，不读取session
        }
        logger.info("读取session。。。");
        return redisTemplate.opsForValue().get(sessionId);
    }

    @Override
    protected void doUpdate(Session session) {
        HttpServletRequest request = ServletUtil.getReqeust();
        if (request != null) {
            String uri = request.getServletPath();
            logger.info("requestURI:" + uri);
            //如果是静态文件，不更新session
            if (ServletUtil.isStaticURI(uri)) {
                return;
            }
            //如果是视图文件，不更新session
            if (StringUtils.startsWith(uri, new PropertiesUtil("common.properties").getProperty(PropertiesUtil.WEB_VIEW_PREFIX))
                    && StringUtils.endsWith(uri, new PropertiesUtil("common.properties").getProperty(PropertiesUtil.WEB_VIEW_SUFFIX))) {
                return;
            }
        }
        logger.info("更新session。。。");
        redisTemplate.opsForValue().set(session.getId().toString(), session);
    }

    @Override
    protected void doDelete(Session session) {
        logger.info("删除session。。。");
        redisTemplate.delete(session.getId().toString());
    }
}
