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
    <link rel="stylesheet" type="text/css"
          href="${ctx}/assets/global/plugins/jstree/dist/themes/default/style.min.css"/>
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
                <i class="fa fa-globe"></i>角色管理
            </div>
            <div class="actions">
                <a href="javascript:;" class="btn btn-default btn-sm" id="addRole" data-toggle="modal">
                    <i class="fa fa-plus"></i> 新增 </a>

                <a href="javascript:;" class="btn btn-default btn-sm" id="updateRole">
                    <i class="fa fa-pencil"></i> 修改 </a>

                <a href="javascript:;" class="btn btn-default btn-sm" id="deleteRole">
                    <i class="fa fa-trash-o"></i> 删除 </a>

                <a href="javascript:;" class="btn btn-default btn-sm" id="allotPermission">
                    <i class="fa fa-lock"></i> 分配权限 </a>
            </div>
        </div>
        <div class="portlet-body">
                <table class="table table-striped table-bordered table-hover dataTable no-footer" id="roleList" role="grid" aria-describedby="sample_1_info">
                <thead>
                    <tr role="row">
                        <th></th>
                        <th>角色key</th>
                        <th>角色名称</th>
                        <th>创建时间</th>
                        <th>更新时间</th>
                    </tr>
                </thead>
            </table>
        </div>
    </div>
    <!-- END EXAMPLE TABLE PORTLET-->
</div>

<!-- 增加角色模态框（Modal） begin  -->
<div class="modal fade" id="addRoleModal" tabindex="-1" role="dialog" aria-labelledby="addRoleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">新增角色</h4>
            </div>
            <div class="modal-body">
                <!-- BEGIN VALIDATION STATES-->
                <div class="portlet-body form">
                    <!-- BEGIN FORM-->
                    <form action="${ctx}/role/addRole.json" id="role_form" class="form-horizontal" novalidate="novalidate">
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
                                <label class="control-label col-md-3">角色key <span class="required" >
										* </span>
                                </label>
                                <div class="col-md-6">
                                    <div class="input-icon right">
                                        <i class="fa"></i>
                                        <input  class="form-control" name="roleKey" type="text">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3">角色名称<span class="required" >
										* </span>
                                </label>
                                <div class="col-md-6">
                                    <div class="input-icon right">
                                        <i class="fa"></i>
                                        <input class="form-control"  name="roleName" type="text">
                                    </div>
                                </div>
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
<!-- 增加角色模态框（Modal） end  -->

<!-- 修改角色模态框（Modal） begin  -->
<div class="modal fade" id="updateRoleModal" tabindex="-1" role="dialog" aria-labelledby="updateRoleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myUpdateModalLabel">修改角色</h4>
            </div>
            <div class="modal-body">
                <!-- BEGIN VALIDATION STATES-->
                <div class="portlet-body form">
                    <!-- BEGIN FORM-->
                    <form action="${ctx}/role/updateRole.json" id="role-update-form" class="form-horizontal" novalidate="novalidate">
                        <div class="form-body">
                            <input name="sid" type="hidden"/>
                            <div class="alert alert-danger display-hide">
                                <button class="close" data-close="alert"></button>
                                You have some form errors. Please check below.
                            </div>
                            <div class="alert alert-success display-hide">
                                <button class="close" data-close="alert"></button>
                                Your form validation is successful!
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3">角色key <span class="required" >
										* </span>
                                </label>
                                <div class="col-md-6">
                                    <div class="input-icon right">
                                        <i class="fa"></i>
                                        <input  class="form-control" name="roleKey" type="text">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3">角色名称 <span class="required">
										* </span>
                                </label>
                                <div class="col-md-6">
                                    <div class="input-icon right">
                                        <i class="fa"></i>
                                        <input class="form-control" name="roleName" type="text">
                                    </div>
                                </div>
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
<!-- 修改角色模态框（Modal） end  -->

<!-- 修改授权模态框（Modal） begin  -->
<div class="modal fade" id="allotPermissionModal" tabindex="-1" role="dialog" aria-labelledby="allotPermissionModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">角色授权</h4>
            </div>
            <div class="modal-body">
                <!-- BEGIN VALIDATION STATES-->
                <div class="portlet-body form">
                    <!-- BEGIN FORM-->
                    <form  id="allot-form" class="form-horizontal" novalidate="novalidate">
                        <input type="hidden" id="roleSid" />
                        <div class="form-body">
                            <div id="menu_tree" class="tree-demo">
                            </div>
                        </div>
                    </form>
                    <!-- END FORM-->
                </div>
                <!-- END VALIDATION STATES-->
            </div>
            <div class="modal-footer">
                <button type="button" id="allotSaveBtn" class="btn btn-primary">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
<!-- 授权模态框（Modal） end  -->
<script src="${ctx}/assets/global/plugins/jstree/dist/jstree.min.js"></script>
<script src="${ctx}/assets/admin/pages/scripts/ui-tree.js"></script>
<script src="${ctx}/js/system/role/list.js"></script>
<script src="${ctx}/js/system/role/role-form-validation.js"></script>
<script type="text/javascript">
    $(function () {
        RoleFormValidation.init();
    });
</script>
<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>