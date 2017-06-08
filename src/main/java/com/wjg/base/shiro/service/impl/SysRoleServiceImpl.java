package com.wjg.base.shiro.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wjg.base.shiro.mapper.SysRoleMapper;
import com.wjg.base.shiro.mapper.SysRolePermissionMapper;
import com.wjg.base.shiro.mapper.SysRoleUserMapper;
import com.wjg.base.shiro.pojo.SysRole;
import com.wjg.base.shiro.pojo.SysRolePermission;
import com.wjg.base.shiro.service.ISysRoleService;
import com.wjg.base.shiro.vo.ResultVO;
import com.wjg.base.shiro.vo.SysRoleVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by wjg on 2017/5/23.
 */
@Service
public class SysRoleServiceImpl implements ISysRoleService {
    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;

    @Override
    public List<SysRole> findRolesBySysUserSid(long sysUserSid) {
        return sysRoleMapper.selectRoleByUserSid(sysUserSid);
    }

    @Override
    public Set<String> findRoleKeysBySysUserSid(long sysUserSid) {
        return sysRoleUserMapper.selectRoleKeysByUserSid(sysUserSid);
    }

    @Override
    public List<SysRole> findAllSysRoles() {
        return sysRoleMapper.selectAllSysRole();
    }

    @Override
    public SysRoleVO findRoleBySid(Long sid) {
        SysRoleVO sysRoleVO = new SysRoleVO();
        SysRole sysRole = sysRoleMapper.selectSysRoleBySid(sid);
        BeanUtils.copyProperties(sysRole, sysRoleVO);
        return sysRoleVO;
    }

    @Override
    @Transactional
    public void deleteRoleBySid(Long sid) {
        //删除角色表
        sysRoleMapper.deleteSysRoleBySid(sid);
        //删除角色用户关联表
        sysRoleUserMapper.deleteSysRoleUserByRoleSid(sid);
        //删除角色权限关联表
        sysRolePermissionMapper.deleteBySysRoleSid(sid);
    }

    @Override
    public PageInfo<SysRole> findPageRole(int pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return new PageInfo(sysRoleMapper.selectAllSysRole());
    }

    @Override
    @Transactional
    public ResultVO addRole(SysRoleVO sysRoleVO) {
        ResultVO result = new ResultVO();
        SysRole sysRole = sysRoleMapper.selectSysRoleByRoleKey(sysRoleVO.getRoleKey());
        if (null != sysRole) {
            result.setCode("error");
            result.setMsg("角色key不能重复");
            return result;
        }
        sysRole = new SysRole();
        BeanUtils.copyProperties(sysRoleVO, sysRole);
        sysRole.setCreateTime(new Timestamp(System.currentTimeMillis()));
        sysRole.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        sysRoleMapper.insertSysRole(sysRole);
        result.setCode("success");
        result.setMsg("新增角色成功");
        return result;
    }

    @Override
    @Transactional
    public ResultVO updateRole(SysRoleVO sysRoleVO) {
        ResultVO result = new ResultVO();
        SysRole sysRole = sysRoleMapper.selectSysRoleByRoleKey(sysRoleVO.getRoleKey());
        if (null != sysRole && !sysRole.getSid().equals(sysRoleVO.getSid())) {
            result.setCode("error");
            result.setMsg("角色key不能重复");
            return result;
        }
        sysRole = new SysRole();
        BeanUtils.copyProperties(sysRoleVO, sysRole);
        sysRole.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        sysRoleMapper.updateSysRole(sysRole);
        result.setCode("success");
        result.setMsg("修改角色成功");
        return result;
    }

    @Override
    public Set<String> findRolePersByRoleSid(Long roleSid) {
        return sysRolePermissionMapper.selectSysPermissionsBySysRoleSid(roleSid);
    }

    @Override
    @Transactional
    public ResultVO updateRolePermissions(Long roleSid, String perSids) {
        ResultVO result = new ResultVO();
        if (roleSid != null) {
            //删除之前该角色下的所有权限
            sysRolePermissionMapper.deleteBySysRoleSid(roleSid);
            if (!StringUtils.isEmpty(perSids)) {
                String[] permissionSids = perSids.split(",");
                Arrays.asList(permissionSids).stream().filter(sid -> !sid.equals("0")).map(sid -> Long.valueOf(sid)).forEach(sid -> {
                    sysRolePermissionMapper.insertSysRolePermission(new SysRolePermission(roleSid, sid));
                });
            }
            result.setCode("success");
            result.setMsg("OK");
        } else {
            result.setCode("error");
            result.setMsg("保存权限失败");
        }
        return result;
    }
}
