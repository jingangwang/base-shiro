package com.wjg.base.shiro.permission.session;

import com.wjg.base.shiro.util.PropertiesUtil;
import com.wjg.base.shiro.util.ServletUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by wjg on 2017/6/14.
 */
public class RedisSessionDAO extends AbstractSessionDAO {
    private static Logger logger = LoggerFactory.getLogger(RedisSessionDAO.class);
    private RedisTemplate<String, Session> redisTemplate;

    public RedisSessionDAO(RedisTemplate<String, Session> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected Serializable doCreate(Session session) {
        HttpServletRequest request = ServletUtil.getReqeust();
        if (request != null) {
            String uri = request.getServletPath();
            logger.info("requestURI:" + uri);
            ////如果是静态资源，不生成session
            if(ServletUtil.isStaticURI(uri)){
                return null;
            }
        }
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session,sessionId);
        logger.info("创建session,sessionId:[{}]",sessionId);
        redisTemplate.opsForValue().set(sessionId.toString(), session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        Session session = null;
        HttpServletRequest request = ServletUtil.getReqeust();
        if (request != null) {
            String uri = request.getServletPath();
            logger.info("requestURI:" + uri);
            session= (Session)request.getAttribute("session_"+sessionId);
        }
        if(session!=null){
            return session;
        }
        logger.info("读取session,sessionId:[{}]",sessionId);
        session =  redisTemplate.opsForValue().get(sessionId);
        if(request!= null && session!=null){
            request.setAttribute("session_"+sessionId,session);
        }
        return session;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
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
        logger.info("更新session,sessionId:[{}]",session.getId());
        redisTemplate.opsForValue().set(session.getId().toString(), session);
    }

    @Override
    public void delete(Session session) {
        logger.info("删除session,sessionId:[{}]",session.getId());
        redisTemplate.delete(session.getId().toString());
    }

    @Override
    public Collection<Session> getActiveSessions() {
        return null;
    }
}
