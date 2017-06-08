package com.wjg.base.shiro.vo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wjg on 2017/5/22.
 */
public class SysPermissionVO {
    private Long sid;
    private String permissionKey;
    private String permissionName;
    private String iconName;
    private String url;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Long parentSid;
    private Integer sort;
    private String type;

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public String getPermissionKey() {
        return permissionKey;
    }

    public void setPermissionKey(String permissionKey) {
        this.permissionKey = permissionKey;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Long getParentSid() {
        return parentSid;
    }

    public void setParentSid(Long parentSid) {
        this.parentSid = parentSid;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }



    @Override
    public String toString() {
        return "SysPermission{" +
                "sid=" + sid +
                ", permissionKey='" + permissionKey + '\'' +
                ", permissionName='" + permissionName + '\'' +
                ", iconName='" + iconName + '\'' +
                ", url='" + url + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", parentSid=" + parentSid +
                ", sort=" + sort +
                ", type='" + type + '\'' +
                '}';
    }
}
