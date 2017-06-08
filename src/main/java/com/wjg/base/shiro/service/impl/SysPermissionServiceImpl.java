package com.wjg.base.shiro.service.impl;

import com.wjg.base.shiro.mapper.SysPermissionMapper;
import com.wjg.base.shiro.mapper.SysRolePermissionMapper;
import com.wjg.base.shiro.pojo.SysPermission;
import com.wjg.base.shiro.service.ISysPermissionService;
import com.wjg.base.shiro.vo.JsTreeDataVO;
import com.wjg.base.shiro.vo.ResultVO;
import com.wjg.base.shiro.vo.SysPermissionVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Created by wjg on 2017/5/23.
 */
@Service
public class SysPermissionServiceImpl implements ISysPermissionService {
    @Autowired
    private SysPermissionMapper sysPermissionMapper;
    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;

    @Override
    public List<SysPermission> findAllPermissions() {
        return sysPermissionMapper.selectAllSysPermission();
    }

    @Override
    public List<JsTreeDataVO> findAllPermissionsTree() {
        List<SysPermission> allPermissions = findAllPermissions();
        List<JsTreeDataVO> collect = allPermissions.stream().map(per -> {
            JsTreeDataVO vo = new JsTreeDataVO();
            vo.setId(per.getSid().toString());
            vo.setIcon(per.getIconName());
            vo.setText(per.getPermissionName());
            vo.setParent(per.getParentSid().toString());
            return vo;
        }).collect(toList());
        JsTreeDataVO topNode = new JsTreeDataVO();
        topNode.setId("0");
        topNode.setParent("#");
        topNode.setText("菜单");
        collect.add(topNode);
        return collect;
    }

    @Override
    public Set<String> findUserPermissions(Long sysUserSid) {
        return sysRolePermissionMapper.selectSysPermissionsBySysUserSid(sysUserSid);
    }

    @Override
    public List<SysPermission> findMenuPermissions(Long sysUserSid) {
        return sysPermissionMapper.selectMenuPermissionsBySysUserSid(sysUserSid);
    }

    @Override
    public List<JsTreeDataVO> findPermissionsByParentSid(String parentSid) {
        List<JsTreeDataVO> list = null;
        if ("#".equals(parentSid)) {
            JsTreeDataVO dataVO = new JsTreeDataVO();
            dataVO.setId("0");
            dataVO.setChildren(true);
            dataVO.setParent("#");
            dataVO.setText("菜单");
            list = new ArrayList<>();
            list.add(dataVO);
            return list;
        } else {
            List<SysPermission> sysPermissions = sysPermissionMapper.selectPermissionByParentSid(Long.valueOf(parentSid));
            list = sysPermissions.stream().map(per -> {
                JsTreeDataVO vo = new JsTreeDataVO();
                vo.setId(per.getSid().toString());
                vo.setParent(per.getParentSid().toString());
                vo.setIcon(per.getIconName());
                vo.setText(per.getPermissionName());
                vo.setChildren(!sysPermissionMapper.selectPermissionByParentSid(per.getSid()).isEmpty());
                return vo;
            }).collect(toList());
        }

        return list;

    }

    @Override
    public SysPermissionVO findPermissionBySid(Long sid) {
        SysPermission sysPermission = sysPermissionMapper.selectSysPermissionBySid(sid);
        SysPermissionVO vo = new SysPermissionVO();
        BeanUtils.copyProperties(sysPermission, vo);
        return vo;
    }

    @Override
    @Transactional
    public ResultVO addPermission(SysPermissionVO sysPermissionVO) {
        ResultVO result = new ResultVO();
        SysPermission sysPermission = sysPermissionMapper.selectSysPermissionByKey(sysPermissionVO.getPermissionKey());
        if(sysPermission!=null){
            result.setCode("error");
            result.setMsg("资源key不能重复");
            return result;
        }
        sysPermission = new SysPermission();
        BeanUtils.copyProperties(sysPermissionVO,sysPermission);
        sysPermission.setCreateTime(new Timestamp(System.currentTimeMillis()));
        sysPermission.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        sysPermissionMapper.insertSysPermission(sysPermission);
        result.setCode("success");
        result.setMsg("增加菜单成功");
        return result;
    }

    @Override
    @Transactional
    public ResultVO updatePermission(SysPermissionVO sysPermissionVO) {
        ResultVO result = new ResultVO();
        SysPermission sysPermission = sysPermissionMapper.selectSysPermissionByKey(sysPermissionVO.getPermissionKey());
        if(sysPermission!=null && !sysPermission.getSid().equals(sysPermissionVO.getSid())){
            result.setCode("error");
            result.setMsg("资源key不能重复");
            return result;
        }
        sysPermission = new SysPermission();
        BeanUtils.copyProperties(sysPermissionVO,sysPermission);
        sysPermission.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        sysPermissionMapper.updateSysPermission(sysPermission);
        result.setCode("success");
        result.setMsg("增加菜单成功");
        return result;
    }

    @Override
    @Transactional
    public ResultVO deletePermission(Long sid) {
        ResultVO result = new ResultVO();
        //判断该节点下是否还有子菜单，如果有返回错误
        List<SysPermission> sysPermissions = sysPermissionMapper.selectPermissionByParentSid(sid);
        if(!sysPermissions.isEmpty()){
            result.setCode("error");
            result.setMsg("该菜单下还包含子菜单,不能删除");
            return  result;
        }
        //删除该菜单
        sysPermissionMapper.deleteSysPermissionBySid(sid);
        //删除拥有该菜单的所有角色关联关系
        sysRolePermissionMapper.deleteBySysPermissionSid(sid);
        result.setCode("success");
        result.setMsg("删除菜单成功");
        return result;
    }
}
