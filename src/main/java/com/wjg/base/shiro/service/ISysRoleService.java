package com.wjg.base.shiro.service;

import com.github.pagehelper.PageInfo;
import com.wjg.base.shiro.pojo.SysRole;
import com.wjg.base.shiro.pojo.SysUser;
import com.wjg.base.shiro.vo.ResultVO;
import com.wjg.base.shiro.vo.SysRoleVO;

import java.util.List;
import java.util.Set;

/**
 * Created by wjg on 2017/5/23.
 */
public interface ISysRoleService {

    List<SysRole> findRolesBySysUserSid(long sysUserSid);

    Set<String> findRoleKeysBySysUserSid(long sysUserSid);

    List<SysRole> findAllSysRoles();

    SysRoleVO findRoleBySid(Long sid);

    void deleteRoleBySid(Long sid);

    PageInfo<SysRole> findPageRole(int pageNo, Integer integer);

    ResultVO addRole(SysRoleVO sysRoleVO);

    ResultVO updateRole(SysRoleVO sysRoleVO);

    Set<String> findRolePersByRoleSid(Long roleSid);

    ResultVO updateRolePermissions(Long roleSid, String perSids);

    void updateCacheAuthInfo(SysUser user);
}
