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
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<head>
    <link rel="stylesheet" type="text/css"
          href="${ctx}/assets/global/plugins/jstree/dist/themes/default/style.min.css"/>
</head>
<body>
<div class="col-md-4">
    <div class="portlet grey-cascade box">
        <div class="portlet-title">
            <div class="caption">
                <i class="fa fa-cogs"></i>菜单管理
            </div>
            <div class="actions">
                <a href="javascript:" class="btn btn-default btn-sm" id="addMenu" data-toggle="modal">
                    <i class="fa fa-plus"></i> 新增 </a>

                <a href="javascript:" class="btn btn-default btn-sm" id="updateMenu">
                    <i class="fa fa-pencil"></i> 修改 </a>
                <shiro:hasPermission name="menu-delete">
                <a href="javascript:" class="btn btn-default btn-sm" id="deleteMenu">
                    <i class="fa fa-trash-o"></i> 删除 </a></shiro:hasPermission>
            </div>
        </div>
        <div class="portlet-body">
            <div id="menu_tree" class="tree-demo">
            </div>
        </div>
    </div>
</div>

<div class="col-md-6">
    <div class="portlet grey-cascade box">
        <div class="portlet-title">
            <div class="caption">
                <i class="fa fa-cogs"></i>菜单详情
            </div>
            <%--<div class="tools">--%>
            <%--<a href="javascript:;" class="collapse">--%>
            <%--</a>--%>
            <%--<a href="#portlet-config" data-toggle="modal" class="config">--%>
            <%--</a>--%>
            <%--<a href="javascript:;" class="reload">--%>
            <%--</a>--%>
            <%--<a href="javascript:;" class="remove">--%>
            <%--</a>--%>
            <%--</div>--%>
        </div>
        <div class="portlet-body form">
            <form class="form-horizontal">
                <div class="form-wizard">
                    <div class="form-body" id="form_view">
                        <div class="form-group">
                            <label class="control-label col-md-3">菜单名称</label>
                            <div class="col-md-6">
                                <div class="input-icon right">
                                    <i class="fa"></i>
                                    <input class="form-control" name="permissionName" type="text" disabled/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-3">权限key</label>
                            <div class="col-md-6">
                                <div class="input-icon right">
                                    <i class="fa"></i>
                                    <input class="form-control" name="permissionKey" type="text" disabled/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-3">图标</label>
                            <div class="col-md-6">
                                <div class="input-icon right">
                                    <i class="fa"></i>
                                    <input class="form-control" name="iconName" type="text" disabled/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-3">资源url地址</label>
                            <div class="col-md-6">
                                <div class="input-icon right">
                                    <i class="fa"></i>
                                    <input class="form-control" name="url" type="text" disabled/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-3">类型</label>
                            <div class="col-md-6">
                                <div class="input-icon right">
                                    <i class="fa"></i>
                                    <input class="form-control" name="type" type="text" disabled/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-3">父级菜单ID</label>
                            <div class="col-md-6">
                                <div class="input-icon right">
                                    <i class="fa"></i>
                                    <input class="form-control" name="parentSid" type="text" disabled/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-3">排序</label>
                            <div class="col-md-6">
                                <div class="input-icon right">
                                    <i class="fa"></i>
                                    <input class="form-control" name="sort" type="text" disabled/>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- 增加菜单模态框（Modal） begin  -->
<div class="modal fade" id="addMenuModal" tabindex="-1" role="dialog" aria-labelledby="addMenuModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">新增菜单</h4>
            </div>
            <div class="modal-body">
                <!-- BEGIN VALIDATION STATES-->
                <div class="portlet-body form">
                    <!-- BEGIN FORM-->
                    <form action="${ctx}/menu/addMenu.json" id="menu_form" class="form-horizontal" novalidate="novalidate">
                        <div class="form-body">
                            <div class="alert alert-danger display-hide">
                                <button class="close" data-close="alert"></button>
                                You have some form errors. Please check below.
                            </div>
                            <div class="alert alert-success display-hide">
                                <button class="close" data-close="alert"></button>
                                Your form validation is successful!
                            </div>
                            <input type="hidden" name="parentSid" />
                            <div class="form-group">
                                <label class="control-label col-md-3">菜单名称<span class="required" >
										* </span>
                                </label>
                                <div class="col-md-6">
                                    <div class="input-icon right">
                                        <i class="fa"></i>
                                        <input  class="form-control" name="permissionName" type="text">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3">资源key<span class="required" >
										* </span>
                                </label>
                                <div class="col-md-6">
                                    <div class="input-icon right">
                                        <i class="fa"></i>
                                        <input class="form-control"  name="permissionKey" type="text">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3">资源url
                                </label>
                                <div class="col-md-6">
                                    <div class="input-icon right">
                                        <i class="fa"></i>
                                        <input class="form-control"  name="url" type="text">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3">图标</label>
                                <div class="col-md-6">
                                    <div class="input-icon right">
                                        <i class="fa"></i>
                                        <input class="form-control" name="iconName" type="text">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3">类型 <span class="required">
										* </span>
                                </label>
                                <div class="col-md-6">
                                    <select class="form-control select2me" name="type">
                                        <option value="1">------菜单------</option>
                                        <option value="2">------按钮------</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3">排序 <span class="required">
										* </span>
                                </label>
                                <div class="col-md-6">
                                    <div class="input-icon right">
                                        <i class="fa"></i>
                                        <input class="form-control" name="sort" type="text">
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
<!-- 增加菜单模态框（Modal） end  -->

<!-- 修改菜单模态框（Modal） begin  -->
<div class="modal fade" id="updateMenuModal" tabindex="-1" role="dialog" aria-labelledby="updateMenuModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myUpdateModalLabel">修改菜单</h4>
            </div>
            <div class="modal-body">
                <!-- BEGIN VALIDATION STATES-->
                <div class="portlet-body form">
                    <!-- BEGIN FORM-->
                    <form action="${ctx}/menu/updateMenu.json" id="menu-update-form" class="form-horizontal" novalidate="novalidate">
                        <div class="form-body">
                            <div class="alert alert-danger display-hide">
                                <button class="close" data-close="alert"></button>
                                You have some form errors. Please check below.
                            </div>
                            <div class="alert alert-success display-hide">
                                <button class="close" data-close="alert"></button>
                                Your form validation is successful!
                            </div>
                            <input type="hidden" name="sid" />
                            <div class="form-group">
                                <label class="control-label col-md-3">菜单名称<span class="required" >
										* </span>
                                </label>
                                <div class="col-md-6">
                                    <div class="input-icon right">
                                        <i class="fa"></i>
                                        <input  class="form-control" name="permissionName" type="text">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3">资源key<span class="required" >
										* </span>
                                </label>
                                <div class="col-md-6">
                                    <div class="input-icon right">
                                        <i class="fa"></i>
                                        <input class="form-control"  name="permissionKey" type="text">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3">资源url
                                </label>
                                <div class="col-md-6">
                                    <div class="input-icon right">
                                        <i class="fa"></i>
                                        <input class="form-control"  name="url" type="text">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3">图标</label>
                                <div class="col-md-6">
                                    <div class="input-icon right">
                                        <i class="fa"></i>
                                        <input class="form-control" name="iconName" type="text">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3">类型 <span class="required">
										* </span>
                                </label>
                                <div class="col-md-6">
                                    <select class="form-control select2me" name="type">
                                        <option value="1">------菜单------</option>
                                        <option value="2">------按钮------</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3">排序 <span class="required">
										* </span>
                                </label>
                                <div class="col-md-6">
                                    <div class="input-icon right">
                                        <i class="fa"></i>
                                        <input class="form-control" name="sort" type="text">
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
<!-- 修改菜单模态框（Modal） end  -->
</body>
<script src="${ctx}/assets/global/plugins/jstree/dist/jstree.min.js"></script>
<script src="${ctx}/assets/admin/pages/scripts/ui-tree.js"></script>
<script src="${ctx}/js/system/menu/menu.js" type="text/javascript"></script>
<script src="${ctx}/js/system/menu/menu-form-validation.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
        UITree.init();
        MenuFormValidation.init();
    });
</script>
</html>
