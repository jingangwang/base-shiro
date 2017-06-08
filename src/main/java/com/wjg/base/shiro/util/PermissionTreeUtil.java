package com.wjg.base.shiro.util;

import com.wjg.base.shiro.pojo.SysPermission;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wjg on 2017/5/25.
 */
public class PermissionTreeUtil {
    /**
     * 获取菜单的tree结构
     * @param list
     * @param parentSid
     * @return
     */
    public List<SysPermission> getTrees(List<SysPermission> list, Long parentSid) {
        List<SysPermission> returnList = new ArrayList<>();
        for (SysPermission permission : list
                ) {
            if(permission.getParentSid().equals(parentSid)){
                recursionFn(list,permission);
                returnList.add(permission);
            }
        }
        return returnList;
    }

    /**
     * 递归算法
     * @param list
     * @param permission
     */
    private void recursionFn(List<SysPermission> list,SysPermission permission){
        List<SysPermission> childList = getChildList(list, permission);
        permission.setChildren(childList);
        for (SysPermission child: childList
                ) {
            if(getChildList(list,child)!=null && getChildList(list,child).size()>0){
                recursionFn(list,child);
            }
        }
    }

    /**
     * 获取子菜单的集合
     * @param list
     * @param permission
     * @return
     */
    private List<SysPermission> getChildList(List<SysPermission> list,SysPermission permission){
        List<SysPermission> tList = new ArrayList<>();
        for (SysPermission per: list
                ) {
            if(per.getParentSid().equals(permission.getSid())){
                tList.add(per);
            }
        }
        return tList;
    }
}
