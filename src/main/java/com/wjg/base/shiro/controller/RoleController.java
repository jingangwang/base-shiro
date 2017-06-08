package com.wjg.base.shiro.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.wjg.base.shiro.pojo.SysRole;
import com.wjg.base.shiro.pojo.SysUser;
import com.wjg.base.shiro.service.ISysRoleService;
import com.wjg.base.shiro.service.ISysUserService;
import com.wjg.base.shiro.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.transform.Result;
import java.util.List;
import java.util.Set;

/**
 * Created by wjg on 2017/5/26.
 */
@Controller
@RequestMapping("role")
public class RoleController {
    private static Logger logger = LoggerFactory.getLogger(RoleController.class);
    @Autowired
    private ISysRoleService sysRoleService;

    @RequestMapping("list")
    public String listUI(Model model, Integer id) throws Exception {
        return "/system/role/list";
    }

    /**
     * 查询指定角色
     * @param sid
     * @return
     */
    @ResponseBody
    @RequestMapping("get")
    public SysRoleVO getRole(@RequestParam Long sid){
        return sysRoleService.findRoleBySid(sid);
    }

    /**
     * 根据当前角色查询角色下的权限
     * @param roleSid
     * @return
     */
    @ResponseBody
    @RequestMapping("getRolePers")
    public Set<String> getRolePers(@RequestParam Long roleSid){
        return sysRoleService.findRolePersByRoleSid(roleSid);
    }
    /**
     * 删除指定角色
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("del")
    public JSONObject deleteRole(@RequestParam String ids){
        JSONObject obj = new JSONObject();
        String[] sids = ids.split(",");
        for (String str:sids
             ) {
            sysRoleService.deleteRoleBySid(Long.valueOf(str));
        }
        obj.put("code","success");
        return obj;
    }


    /**
     * 获取分页角色
     * @param tableParams
     * @return
     */
    @ResponseBody
    @RequestMapping("getPageRole")
    public DataTableResult findUserByPage(DataTableParams tableParams){
        //计算要显示的当前页数
        int pageNo = tableParams.getiDisplayStart()/tableParams.getiDisplayLength()+1;
        PageInfo<SysRole> pageInfo = sysRoleService.findPageRole(pageNo, tableParams.getiDisplayLength());
        DataTableResult<SysRole>  dataTableResult = new DataTableResult<>();
        dataTableResult.setsEcho(tableParams.getsEcho());
        dataTableResult.setAaData(pageInfo.getList());
        dataTableResult.setiTotalRecords(pageInfo.getTotal());
        dataTableResult.setiTotalDisplayRecords(pageInfo.getTotal());
        return dataTableResult;
    }

    /**
     * 新增角色
     * @param sysRoleVO
     * @return
     */
    @ResponseBody
    @RequestMapping("addRole")
    public ResultVO addRole(SysRoleVO sysRoleVO){
        logger.info("接到增加角色的报文:"+sysRoleVO.toString());
        ResultVO result = sysRoleService.addRole(sysRoleVO);
        return result;
    }

    /**
     * 更新用户
     * @param sysRoleVO
     * @return
     */
    @ResponseBody
    @RequestMapping("updateRole")
    public ResultVO updateUser(SysRoleVO sysRoleVO){
        logger.info("接到更新角色的报文："+sysRoleVO.toString());
        ResultVO result = sysRoleService.updateRole(sysRoleVO);
        return result;
    }

    /**
     * 更新角色权限
     * @param roleSid
     * @param perSids
     * @return
     */
    @ResponseBody
    @RequestMapping("saveAllotPers")
    public ResultVO saveAllotPers(Long roleSid,String perSids){
        logger.info("接到更新角色权限的报文：roleSid={},perSids={}",roleSid,perSids);
        return sysRoleService.updateRolePermissions(roleSid,perSids);
    }

}
