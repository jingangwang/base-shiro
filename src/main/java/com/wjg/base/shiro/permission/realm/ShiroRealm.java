package com.wjg.base.shiro.permission.realm;

import com.wjg.base.shiro.pojo.SysUser;
import com.wjg.base.shiro.service.ISysPermissionService;
import com.wjg.base.shiro.service.ISysRoleService;
import com.wjg.base.shiro.service.ISysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * Created by wjg on 2017/5/18.
 */
public class ShiroRealm extends AuthorizingRealm {
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysRoleService sysRoleUserService;
    @Autowired
    private ISysPermissionService sysPermissionService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1、获取用户登录用户名，密码
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //2、获取登录用户名
        String username = token.getUsername();
        //3、去数据库中根据username查询用户对象
        SysUser sysUser = sysUserService.findSysUserByUserName(username);
        if (null == sysUser) {
            throw new UnknownAccountException("'" + username + "'" + " the username not exist");
        }
        //2代表状态被锁定
        if ("2".equals(sysUser.getStatus())) {
            throw new LockedAccountException("'" + username + "'" + " the username is locked ");
        }
        //4、创建SimpleAuthenticationInfo
        //此处盐值没有单独设计，直接用了username，因为username就是唯一的
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(sysUser.getUsername(), sysUser.getPassword(), ByteSource.Util.bytes(sysUser.getUsername()), getName());

        //5、验证通过之后，将用户信息放入session中
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("user",sysUser);
        session.setAttribute("userId",sysUser.getSid());
        return info;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //1、获取登录用户信息
        String  username = principals.getPrimaryPrincipal().toString();
        SysUser sysUser = sysUserService.findSysUserByUserName(username);
        //2、查询当前用户的角色和权限
        Set<String> roleKeys = sysRoleUserService.findRoleKeysBySysUserSid(sysUser.getSid());
        Set<String> permissionKeys = sysPermissionService.findUserPermissions(sysUser.getSid());
        //3、创建SimpleAuthorizationInfo,设置roles 和 permission
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(roleKeys);
        info.addStringPermissions(permissionKeys);
        return info;
    }
}
