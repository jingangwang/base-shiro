package com.wjg.base.shiro.mapper;

import com.wjg.base.shiro.pojo.SysPermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wjg on 2017/5/23.
 */
@Repository
public interface SysPermissionMapper {

    void insertSysPermission(SysPermission sysPermission);

    void updateSysPermission(SysPermission sysPermission);

    void deleteSysPermissionBySid(@Param("sid") Long sid);

    SysPermission selectSysPermissionBySid(@Param("sid") Long sid);

    SysPermission selectSysPermissionByKey(@Param("permissionKey") String permissionKey);

    List<SysPermission> selectAllSysPermission();

    List<SysPermission> selectSysPermissionBySysRoleSid(@Param("sysRoleSid") Long sysRoleSid);

    List<SysPermission> selectMenuPermissionsBySysUserSid(@Param("sysUserSid") Long sysUserSid);

    List<SysPermission> selectPermissionByParentSid(@Param("parentSid") Long parentSid);
}
