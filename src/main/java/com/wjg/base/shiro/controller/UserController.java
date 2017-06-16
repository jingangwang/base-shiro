package com.wjg.base.shiro.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.wjg.base.shiro.pojo.SysRole;
import com.wjg.base.shiro.pojo.SysUser;
import com.wjg.base.shiro.service.ISysRoleService;
import com.wjg.base.shiro.service.ISysUserService;
import com.wjg.base.shiro.vo.DataTableParams;
import com.wjg.base.shiro.vo.DataTableResult;
import com.wjg.base.shiro.vo.ResultVO;
import com.wjg.base.shiro.vo.SysUserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.transform.Result;
import java.util.List;

/**
 * Created by wjg on 2017/5/26.
 */
@Controller
@RequestMapping("user")
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysRoleService sysRoleService;

    @RequestMapping("list")
    public String listUI(Model model, Integer id) throws Exception {
        model.addAttribute("allRoles",sysRoleService.findAllSysRoles());
        return "/system/user/list";
    }



    /**
     * 查询指定用户
     * @param sid
     * @return
     */
    @ResponseBody
    @RequestMapping("get")
    public SysUserVO getUser(@RequestParam Long sid){
        return sysUserService.findSysUserBySid(sid);
    }

    /**
     * 删除指定用户
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("del")
    public JSONObject deleteUser(@RequestParam String ids){
        JSONObject obj = new JSONObject();
        String[] sids = ids.split(",");
        for (String str:sids
             ) {
            sysUserService.deleteUserBySid(Long.valueOf(str));
        }
        obj.put("code","success");
        return obj;
    }

    /**
     * 锁定指定用户
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("lock")
    public JSONObject lockUser(@RequestParam String ids){
        JSONObject obj = new JSONObject();
        String[] sids = ids.split(",");
        for (String str:sids
                ) {
            sysUserService.lockUserBySid(Long.valueOf(str));
        }
        obj.put("code","success");
        return obj;
    }

    /**
     * 获取分页用户
     * @param tableParams
     * @return
     */
    @ResponseBody
    @RequestMapping("getPageUser")
    public DataTableResult findUserByPage(DataTableParams tableParams){
        //计算要显示的当前页数
        int pageNo = tableParams.getiDisplayStart()/tableParams.getiDisplayLength()+1;
        PageInfo<SysUser> pageInfo = sysUserService.findPageUser(pageNo, tableParams.getiDisplayLength());
        DataTableResult<SysUser>  dataTableResult = new DataTableResult<>();
        dataTableResult.setsEcho(tableParams.getsEcho());
        dataTableResult.setAaData(pageInfo.getList());
        dataTableResult.setiTotalRecords(pageInfo.getTotal());
        dataTableResult.setiTotalDisplayRecords(pageInfo.getTotal());
        return dataTableResult;
    }

    /**
     * 新增用户
     * @param sysUserVO
     * @return
     */
    @ResponseBody
    @RequestMapping("addUser")
    public ResultVO addUser(SysUserVO sysUserVO){
        logger.info("接受到新增用户的报文-->"+sysUserVO.toString());
        ResultVO result = sysUserService.addUser(sysUserVO);
        return result;
    }

    /**
     * 更新用户
     * @param sysUserVO
     * @return
     */
    @ResponseBody
    @RequestMapping("updateUser")
    public ResultVO updateUser(SysUserVO sysUserVO){
        logger.info("接受到修改用户的报文-->"+sysUserVO.toString());
        ResultVO result = sysUserService.updateUser(sysUserVO);
        return result;
    }

    /**
     * 修改
     * @param sysUserVO
     * @return
     */
    @ResponseBody
    @RequestMapping("updatePassword")
    public ResultVO updatePassword(SysUserVO sysUserVO){
        logger.info("接受到修改密码的报文-->"+sysUserVO.toString());
        ResultVO result = sysUserService.updatePassword(sysUserVO);
        return  result;
    }

}
