package com.wjg.base.shiro.pojo;

/**
 * Created by wjg on 2017/5/22.
 */
public class SysRolePermission {
    private Long sid;
    private Long roleSid;
    private Long permissionSid;

    public SysRolePermission(Long roleSid, Long permissionSid) {
        this.roleSid = roleSid;
        this.permissionSid = permissionSid;
    }

    public SysRolePermission() {
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public Long getRoleSid() {
        return roleSid;
    }

    public void setRoleSid(Long roleSid) {
        this.roleSid = roleSid;
    }

    public Long getPermissionSid() {
        return permissionSid;
    }

    public void setPermissionSid(Long permissionSid) {
        this.permissionSid = permissionSid;
    }
}
