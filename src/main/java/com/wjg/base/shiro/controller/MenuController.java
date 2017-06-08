package com.wjg.base.shiro.controller;

import com.wjg.base.shiro.pojo.SysPermission;
import com.wjg.base.shiro.service.ISysPermissionService;
import com.wjg.base.shiro.vo.JsTreeDataVO;
import com.wjg.base.shiro.vo.ResultVO;
import com.wjg.base.shiro.vo.SysPermissionVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by wjg on 2017/6/2.
 */
@Controller
@RequestMapping("menu")
public class MenuController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private ISysPermissionService sysPermissionService;

    /**
     * 返回菜单列表页面
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("list")
    public String listUI(Model model) throws Exception {
        return "/system/menu/list";
    }

    /**
     * ajax异步加载树，根据父节点查找子节点
     * @param parent
     * @return
     */
    @ResponseBody
    @RequestMapping("getTreeNode")
    public List<JsTreeDataVO> getTreeNode(String parent){
        return sysPermissionService.findPermissionsByParentSid(parent);
    }

    /**
     * 获取单个菜单的详情
     * @param sid
     * @return
     */
    @ResponseBody
    @RequestMapping("getNodeData")
    public SysPermissionVO getNodeData(Long sid){
        return sysPermissionService.findPermissionBySid(sid);
    }


    /**
     * 获取整个树的数据，用于分配权限
     * @return
     */
    @ResponseBody
    @RequestMapping("getAllTreeNode")
    public List<JsTreeDataVO> getAllTreeNode(){
        return sysPermissionService.findAllPermissionsTree();
    }
    /**
     * 增加菜单
     * @param sysPermissionVO
     * @return
     */
    @ResponseBody
    @RequestMapping("addMenu")
    public ResultVO addMenu(SysPermissionVO sysPermissionVO){
        logger.info("接到增加菜单的报文："+sysPermissionVO.toString());
        return  sysPermissionService.addPermission(sysPermissionVO);
    }

    /**
     * 更新菜单
     * @param sysPermissionVO
     * @return
     */
    @ResponseBody
    @RequestMapping("updateMenu")
    public ResultVO updateMenu(SysPermissionVO sysPermissionVO){
        logger.info("接到更新菜单的报文："+sysPermissionVO.toString());
        return  sysPermissionService.updatePermission(sysPermissionVO);
    }

    /**
     * 删除菜单
     * @param sid
     * @return
     */
    @ResponseBody
    @RequestMapping("deleteMenu")
    public ResultVO deleteMenu(Long sid){
        logger.info("接到删除菜单的报文："+sid);
        return sysPermissionService.deletePermission(sid);
    }
}
