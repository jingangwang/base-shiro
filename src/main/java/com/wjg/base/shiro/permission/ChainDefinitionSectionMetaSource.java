package com.wjg.base.shiro.permission;

import com.wjg.base.shiro.pojo.SysPermission;
import com.wjg.base.shiro.service.ISysPermissionService;
import org.apache.shiro.config.Ini;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.List;

/**
 * Created by wjg on 2017/5/23.
 * 动态加载数据库中的权限
 */
public class ChainDefinitionSectionMetaSource implements FactoryBean<Ini.Section> {
    private static final Logger logger = LoggerFactory.getLogger(ChainDefinitionSectionMetaSource.class);

    public static final String PERMISSION_STRING = "perms[\"{0}\"]";
    //默认的url过滤定义
    private String filterChainDefinitions = null;
    @Autowired
    private ISysPermissionService sysPermissionService;

    @Override
    public Ini.Section getObject() throws Exception {
        Ini ini = new Ini();
        //加载xml配置的默认url过滤定义
        ini.load(filterChainDefinitions);
        final Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
        //加载数据中配置的url过滤定义
        List<SysPermission> allPermissions = sysPermissionService.findAllPermissions();
        allPermissions
                .stream()
                .filter(per -> !StringUtils.isEmpty(per.getPermissionKey()) && !StringUtils.isEmpty(per.getUrl()))
                .forEach(per -> {
                    logger.info("init permissions : " + MessageFormat.format(PERMISSION_STRING, per.getPermissionKey()));
                    section.put(per.getUrl(), MessageFormat.format(PERMISSION_STRING, per.getPermissionKey()));
                });
        //所有资源的访问权限，必须放在最后
        section.put("/**","user");
        return section;
    }

    @Override
    public Class<?> getObjectType() {
        return this.getClass();
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    public void setFilterChainDefinitions(String filterChainDefinitions) {
        this.filterChainDefinitions = filterChainDefinitions;
    }
}
