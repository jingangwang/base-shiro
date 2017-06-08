package com.wjg.base.shiro.mapper;

import com.wjg.base.shiro.pojo.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wjg on 2017/5/22.
 */
@Repository
public interface SysUserMapper {

    void insertSysUser(SysUser user);

    SysUser selectByUserName(@Param("userName") String userName);

    SysUser selectBySid(@Param("sid") Long sid);

    void updateSysUser(SysUser user);

    void deleteBySid(@Param("sid") Long sid);

    List<SysUser> selectUserByRoleSid(@Param("sysRoleSid") Long sysRoleSid);

    List<SysUser> selectAllUser();
}
