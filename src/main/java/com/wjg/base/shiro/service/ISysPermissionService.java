package com.wjg.base.shiro.service;

import com.wjg.base.shiro.pojo.SysPermission;
import com.wjg.base.shiro.vo.JsTreeDataVO;
import com.wjg.base.shiro.vo.ResultVO;
import com.wjg.base.shiro.vo.SysPermissionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * Created by wjg on 2017/5/23.
 */
public interface ISysPermissionService {

    List<SysPermission> findAllPermissions();

    List<JsTreeDataVO> findAllPermissionsTree();

    Set<String> findUserPermissions(Long sysUserSid);

    List<SysPermission> findMenuPermissions(Long sysUserSid);

    List<JsTreeDataVO> findPermissionsByParentSid(String parentSid);

    SysPermissionVO findPermissionBySid(Long sid);

    ResultVO addPermission(SysPermissionVO sysPermissionVO);

    ResultVO updatePermission(SysPermissionVO sysPermissionVO);

    ResultVO deletePermission(Long sid);
}
