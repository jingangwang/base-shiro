package com.wjg.base.shiro.mapper;

import com.wjg.base.shiro.pojo.SysPermission;
import com.wjg.base.shiro.pojo.SysRolePermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Created by wjg on 2017/5/23.
 */
@Repository
public interface SysRolePermissionMapper {

    void insertSysRolePermission(SysRolePermission sysRolePermission);

    void deleteSysRolePermission(@Param("sid") Long sid);

    void deleteBySysPermissionSid(@Param("sysPermissionSid") Long sysPermissionSid);

    void deleteBySysRoleSid(@Param("sysRoleSid") Long sysRoleSid);

    Set<String> selectSysPermissionsBySysUserSid(@Param("sysUserSid") Long sysUserSid);

    Set<String> selectSysPermissionsBySysRoleSid(@Param("sysRoleSid") Long sysRoleSid);
}
