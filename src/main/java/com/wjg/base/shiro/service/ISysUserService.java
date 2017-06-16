package com.wjg.base.shiro.service;

import com.github.pagehelper.PageInfo;
import com.wjg.base.shiro.pojo.SysUser;
import com.wjg.base.shiro.vo.ResultVO;
import com.wjg.base.shiro.vo.SysUserVO;
import org.apache.shiro.authz.AuthorizationInfo;

import java.util.List;

/**
 * Created by wjg on 2017/5/23.
 */
public interface ISysUserService {

    SysUser findSysUserByUserName(String username);

    PageInfo<SysUser> findPageUser(Integer pageNo, Integer pageSize);

    void deleteUserBySid(Long sysUserSid);

    void lockUserBySid(Long sysUserSid);

    ResultVO addUser(SysUserVO userVO);

    ResultVO updateUser(SysUserVO userVO);

    SysUserVO findSysUserBySid(Long sysUserSid);

    SysUser findSysUserPOBySid(Long sysUserSid);

    AuthorizationInfo findAuthInfoByUserSid(Long sysUserSid);

    ResultVO updatePassword(SysUserVO sysUserVO);
}
