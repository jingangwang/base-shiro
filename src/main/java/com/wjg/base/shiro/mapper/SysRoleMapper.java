package com.wjg.base.shiro.mapper;

import com.wjg.base.shiro.pojo.SysRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wjg on 2017/5/23.
 */
@Repository
public interface SysRoleMapper {

    void insertSysRole(SysRole sysRole);

    void updateSysRole(SysRole sysRole);

    void deleteSysRoleBySid(@Param("sid") Long sid);

    SysRole selectSysRoleBySid(@Param("sid") Long sid);

    SysRole selectSysRoleByRoleKey(@Param("roleKey") String roleKey);

    List<SysRole>  selectAllSysRole();

    List<SysRole> selectRoleByUserSid(@Param("sysUserSid") Long sysUserSid);
}
