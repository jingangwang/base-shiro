package com.wjg.base.shiro.pojo;

/**
 * Created by wjg on 2017/5/22.
 */
public class SysRoleUser {
    private Long sid;
    private Long userSid;
    private Long roleSid;

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public Long getUserSid() {
        return userSid;
    }

    public void setUserSid(Long userSid) {
        this.userSid = userSid;
    }

    public Long getRoleSid() {
        return roleSid;
    }

    public void setRoleSid(Long roleSid) {
        this.roleSid = roleSid;
    }

    public SysRoleUser(Long userSid, Long roleSid) {
        this.userSid = userSid;
        this.roleSid = roleSid;
    }

    public SysRoleUser() {
    }
}
