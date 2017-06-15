package com.wjg.base.shiro.controller;

import com.wjg.base.shiro.pojo.SysPermission;
import com.wjg.base.shiro.pojo.SysUser;
import com.wjg.base.shiro.service.ISysPermissionService;
import com.wjg.base.shiro.service.ISysUserService;
import com.wjg.base.shiro.util.PermissionTreeUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

/**
 * Created by wjg on 2017/5/25.
 */
@Controller
public class IndexController {
    @Autowired
    private ISysPermissionService sysPermissionService;
    @Autowired
    private ISysUserService sysUserService;

    @RequestMapping("index")
    public ModelAndView index() {
        ModelAndView model = new ModelAndView();
        Object principal = SecurityUtils.getSubject().getPrincipal();
        SysUser sysUser = sysUserService.findSysUserByUserName(principal.toString());
        List<SysPermission> menuPermissions = sysPermissionService.findMenuPermissions(sysUser.getSid());
        List<SysPermission> list = new PermissionTreeUtil().getTrees(menuPermissions, 0L);
        model.setViewName("index");
        model.addObject("list",list);
        return model;
    }


}
