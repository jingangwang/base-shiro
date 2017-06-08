<%--
  Created by IntelliJ IDEA.
  User: wjg
  Date: 2017/5/26
  Time: 11:09
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<head>
    <link rel="stylesheet" type="text/css" href="${ctx}/assets/global/plugins/bootstrap-select/bootstrap-select.min.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/assets/global/plugins/select2/select2.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/assets/global/plugins/jquery-multi-select/css/multi-select.css"/>
</head>
<body>
<div class="col-md-12">
    <!-- BEGIN EXAMPLE TABLE PORTLET-->
    <div class="portlet box grey-cascade">
        <div class="portlet-title">
            <div class="caption">
                <i class="fa fa-globe"></i>用户管理
            </div>
            <div class="actions">
                <a href="javascript:;" class="btn btn-default btn-sm" id="addUser" data-toggle="modal">
                    <i class="fa fa-plus"></i> 新增 </a>

                <a href="javascript:;" class="btn btn-default btn-sm" id="updateUser">
                    <i class="fa fa-pencil"></i> 修改 </a>

                <a href="javascript:;" class="btn btn-default btn-sm" id="deleteUser">
                    <i class="fa fa-trash-o"></i> 删除 </a>

                <a href="javascript:;" class="btn btn-default btn-sm" id="lockUser">
                    <i class="fa fa-lock"></i> 锁定 </a>
            </div>
        </div>
        <div class="portlet-body">
                <table class="table table-striped table-bordered table-hover dataTable no-footer" id="userList" role="grid" aria-describedby="sample_1_info">
                <thead>
                    <tr role="row">
                        <th></th>
                        <th>用户名</th>
                        <th>邮箱</th>
                        <th>电话</th>
                        <th>创建时间</th>
                        <th>最后登录时间</th>
                        <th>状态</th>
                    </tr>
                </thead>
            </table>
        </div>
    </div>
    <!-- END EXAMPLE TABLE PORTLET-->
</div>

<!-- 增加用户模态框（Modal） begin  -->
<div class="modal fade" id="addUserModal" tabindex="-1" role="dialog" aria-labelledby="addUserModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">新增用户</h4>
            </div>
            <div class="modal-body">
                <!-- BEGIN VALIDATION STATES-->
                <div class="portlet-body form">
                    <!-- BEGIN FORM-->
                    <form action="${ctx}/user/addUser.json" id="user_form" class="form-horizontal" novalidate="novalidate">
                        <div class="form-body">
                            <div class="alert alert-danger display-hide">
                                <button class="close" data-close="alert"></button>
                                You have some form errors. Please check below.
                            </div>
                            <div class="alert alert-success display-hide">
                                <button class="close" data-close="alert"></button>
                                Your form validation is successful!
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3">用户名 <span class="required" >
										* </span>
                                </label>
                                <div class="col-md-6">
                                    <div class="input-icon right">
                                        <i class="fa"></i>
                                        <input  class="form-control" name="username" type="text">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3">密码 <span class="required" >
										* </span>
                                </label>
                                <div class="col-md-6">
                                    <div class="input-icon right">
                                        <i class="fa"></i>
                                        <input class="form-control" id="password" name="password" type="password">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3">重复密码 <span class="required" >
										* </span>
                                </label>
                                <div class="col-md-6">
                                    <div class="input-icon right">
                                        <i class="fa"></i>
                                        <input class="form-control" name="password2" type="password">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3">邮箱 <span class="required">
										* </span>
                                </label>
                                <div class="col-md-6">
                                    <div class="input-icon right">
                                        <i class="fa"></i>
                                        <input class="form-control" name="email" type="text">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3">电话 <span class="required">
										* </span>
                                </label>
                                <div class="col-md-6">
                                    <div class="input-icon right">
                                        <i class="fa"></i>
                                        <input class="form-control" name="phone" type="text">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-3">角色</label>
                            <div class="col-md-9">
                                <select multiple="multiple" class="multi-select" id="roleMulti" name="roles">
                                    <c:forEach items="${allRoles}" var="role">
                                        <option value="${role.sid}">${role.roleName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </form>
                    <!-- END FORM-->
                </div>
                <!-- END VALIDATION STATES-->
            </div>
            <div class="modal-footer">
                <button type="button" id="saveBtn" class="btn btn-primary">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
<!-- 增加用户模态框（Modal） end  -->

<!-- 修改用户模态框（Modal） begin  -->
<div class="modal fade" id="updateUserModal" tabindex="-1" role="dialog" aria-labelledby="updateUserModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myUpdateModalLabel">修改用户</h4>
            </div>
            <div class="modal-body">
                <!-- BEGIN VALIDATION STATES-->
                <div class="portlet-body form">
                    <!-- BEGIN FORM-->
                    <form action="${ctx}/user/updateUser.json" id="user-update-form" class="form-horizontal" novalidate="novalidate">
                        <div class="form-body">
                            <input name="sid" type="hidden" id="userSid" />
                            <div class="alert alert-danger display-hide">
                                <button class="close" data-close="alert"></button>
                                You have some form errors. Please check below.
                            </div>
                            <div class="alert alert-success display-hide">
                                <button class="close" data-close="alert"></button>
                                Your form validation is successful!
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3">用户名 <span class="required" >
										* </span>
                                </label>
                                <div class="col-md-6">
                                    <div class="input-icon right">
                                        <i class="fa"></i>
                                        <input  class="form-control" name="username" type="text" disabled>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3">邮箱 <span class="required">
										* </span>
                                </label>
                                <div class="col-md-6">
                                    <div class="input-icon right">
                                        <i class="fa"></i>
                                        <input class="form-control" name="email" type="text">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3">电话 <span class="required">
										* </span>
                                </label>
                                <div class="col-md-6">
                                    <div class="input-icon right">
                                        <i class="fa"></i>
                                        <input class="form-control" name="phone" type="text">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-3">角色</label>
                            <div class="col-md-9">
                                <select multiple="multiple" class="multi-select" id="updateRoleMulti" name="roles">
                                    <c:forEach items="${allRoles}" var="role">
                                        <option value="${role.sid}">${role.roleName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </form>
                    <!-- END FORM-->
                </div>
                <!-- END VALIDATION STATES-->
            </div>
            <div class="modal-footer">
                <button type="button" id="updateBtn" class="btn btn-primary">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
<!-- 修改用户模态框（Modal） end  -->
<script src="${ctx}/js/system/user/list.js"></script>
<script src="${ctx}/js/system/user/user-form-validation.js"></script>
<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>