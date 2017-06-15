package com.wjg.base.shiro.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xml.internal.utils.StringToStringTable;
import com.wjg.base.shiro.controller.UserController;
import com.wjg.base.shiro.mapper.SysRoleMapper;
import com.wjg.base.shiro.mapper.SysRoleUserMapper;
import com.wjg.base.shiro.mapper.SysUserMapper;
import com.wjg.base.shiro.pojo.SysRole;
import com.wjg.base.shiro.pojo.SysRoleUser;
import com.wjg.base.shiro.pojo.SysUser;
import com.wjg.base.shiro.service.ISysPermissionService;
import com.wjg.base.shiro.service.ISysRoleService;
import com.wjg.base.shiro.service.ISysUserService;
import com.wjg.base.shiro.vo.ResultVO;
import com.wjg.base.shiro.vo.SysUserVO;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by wjg on 2017/5/23.
 */
@Service
public class SysUserServiceImpl implements ISysUserService {
    private static Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private  ISysRoleService sysRoleService;
    @Autowired
    private ISysPermissionService sysPermissionService;

    @Override
    public SysUser findSysUserByUserName(String username) {
        return sysUserMapper.selectByUserName(username);
    }

    @Override
    public PageInfo<SysUser> findPageUser(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return new PageInfo(sysUserMapper.selectAllUser());
    }

    @Override
    @Transactional
    public void deleteUserBySid(Long sysUserSid) {
        //1、删除用户
        sysUserMapper.deleteBySid(sysUserSid);
        //2、删除角色与用户的关系表
        sysRoleUserMapper.deleteSysRoleUSerByUserSid(sysUserSid);
    }

    @Override
    @Transactional
    public void lockUserBySid(Long sysUserSid) {
        SysUser sysUser = sysUserMapper.selectBySid(sysUserSid);
        sysUser.setStatus("2");
        sysUser.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        sysUserMapper.updateSysUser(sysUser);
    }

    @Override
    @Transactional
    public ResultVO addUser(SysUserVO userVO) {
        ResultVO resultVO = new ResultVO();
        SysUser sysUser = sysUserMapper.selectByUserName(userVO.getUsername());
        if(sysUser!=null){
            resultVO.setCode("error");
            resultVO.setMsg("增加用户失败，用户名已存在");
            return  resultVO;
        }
        SysUser user = new SysUser();
        BeanUtils.copyProperties(userVO, user);
        user.setCreateTime(new Timestamp(System.currentTimeMillis()));
        user.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        user.setStatus("1");
        //密码md5加密，盐值用username
        user.setPassword(new SimpleHash("MD5", user.getPassword(),
                user.getUsername(), 2).toString());
        sysUserMapper.insertSysUser(user);

        userVO.getRoles().forEach(sid -> sysRoleUserMapper.insertSysRoleUser(new SysRoleUser(user.getSid(), sid)));
        resultVO.setCode("success");
        resultVO.setMsg("增加用户成功");
        return resultVO;
    }

    @Override
    @Transactional
    public ResultVO updateUser(SysUserVO userVO) {
        ResultVO resultVO = new ResultVO();
        if(userVO.getSid()!=null){
            SysUser user = new SysUser();
            BeanUtils.copyProperties(userVO,user);
            user.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            sysUserMapper.updateSysUser(user);

            sysRoleUserMapper.deleteSysRoleUSerByUserSid(userVO.getSid());

            userVO.getRoles().forEach(sid -> sysRoleUserMapper.insertSysRoleUser(new SysRoleUser(user.getSid(), sid)));

            //更新缓存中的权限信息
            sysRoleService.updateCacheAuthInfo(this.findSysUserPOBySid(user.getSid()));
        }
        resultVO.setCode("success");
        resultVO.setMsg("OK");
        return resultVO;
    }

    @Override
    public SysUserVO findSysUserBySid(Long sysUserSid) {
        SysUserVO sysUserVO = new SysUserVO();
        SysUser sysUser = findSysUserPOBySid(sysUserSid);
        BeanUtils.copyProperties(sysUser,sysUserVO);
        sysUserVO.setRoles(sysRoleMapper.selectRoleByUserSid(sysUserSid).stream().map(SysRole::getSid).collect(Collectors.toSet()));
        return sysUserVO;
    }

    @Override
    public SysUser findSysUserPOBySid(Long sysUserSid) {
        return  sysUserMapper.selectBySid(sysUserSid);
    }

    @Override
    public AuthorizationInfo findAuthInfoByUserSid(Long sysUserSid) {
        //1、查询当前用户的角色和权限
        Set<String> roleKeys = sysRoleService.findRoleKeysBySysUserSid(sysUserSid);
        Set<String> permissionKeys = sysPermissionService.findUserPermissions(sysUserSid);
        //2、创建SimpleAuthorizationInfo,设置roles 和 permission
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(roleKeys);
        info.addStringPermissions(permissionKeys);
        return info;
    }



}
