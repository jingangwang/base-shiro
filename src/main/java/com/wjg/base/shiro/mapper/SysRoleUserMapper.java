package com.wjg.base.shiro.mapper;

import com.wjg.base.shiro.pojo.SysRole;
import com.wjg.base.shiro.pojo.SysRoleUser;
import com.wjg.base.shiro.pojo.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Created by wjg on 2017/5/23.
 */
@Repository
public interface SysRoleUserMapper {

    void insertSysRoleUser(SysRoleUser sysRoleUser);

    void deleteSysRoleUser(@Param("sid") Long sid);

    void deleteSysRoleUSerByUserSid(@Param("sysUserSid") Long sysUserSid);

    void deleteSysRoleUserByRoleSid(@Param("sysRoleSid") Long sysRoleSid);

    Set<String> selectRoleKeysByUserSid(@Param("sysUserSid") Long sysUserSid);
}
