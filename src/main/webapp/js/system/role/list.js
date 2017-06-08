
$(function () {
    roleList();
    loadMenuTree();

    $("#addRole").click("click", function () {
        addRole();
    });

    $("#updateRole").click("click", function () {
        updateRole();
    });
    $("#deleteRole").click("click", function () {
        deleteRole();
    });
    $("#allotPermission").click("click", function () {
        allotPermission();
    });

    $("#saveBtn").click("click",function(){
        $("#role_form").submit();
    });

    $("#updateBtn").click("click",function(){
        $("#role-update-form").submit();
    });

    $("#allotSaveBtn").click("click",function(){
        saveAllotPers();
    });
});

function addRole(){
    //去除校验标示
    $('#role_form .form-group').removeClass('has-success').removeClass('has-error');
    $('#role_form .input-icon i').removeClass('fa-check').removeClass('fa-fail');
    $('.alert-danger').hide();
    $('.alert-success').hide();
    //重置表单
    $('#role_form').resetForm();
    //显示弹出框
    $('#addRoleModal').modal('show');
}

function updateRole() {
    var ids = [];
    $("input.checkboxes[name='sid']:checkbox").each(function () {
        if ($(this).attr("checked")) {
            ids.push($(this).val());
        }
    });
    if(ids==""){
        bootbox.alert("请选择要修改的角色！！");
        return;
    }
    if (ids.length > 1) {
        bootbox.alert("只能选择一个角色！！");
        return;
    }
    //去除校验标示
    $('#role-update-form .form-group').removeClass('has-success').removeClass('has-error');
    $('#role-update-form .input-icon i').removeClass('fa-check').removeClass('fa-fail');
    $('.alert-danger').hide();
    $('.alert-success').hide();
    //重置表单
    $('#role-update-form').resetForm();
    var url = rootPath + '/role/get.json';
    $.ajax({
        type:"POST",
        url:url,
        data:"sid="+ids[0],
        dataType:"json",
        success:function(data){
            if(data){
                $("#role-update-form input[name='sid']").val(data.sid);
                $("#role-update-form input[name='roleKey']").val(data.roleKey);
                $("#role-update-form input[name='roleName']").val(data.roleName);
                //显示弹出框
                $('#updateRoleModal').modal('show');
            }else{
                bootbox.alert('获取角色数据失败');
            }
        },error:function (data) {
            bootbox.alert('获取角色数据失败');
        }
    });

}

function deleteRole() {
    var ids = [];
    $("input.checkboxes[name='sid']:checkbox").each(function () {
        if ($(this).attr("checked")) {
            ids.push($(this).val());
        }
    });
    /*var cbox = grid.getSelectedCheckbox();*/
    if (ids == "") {
        bootbox.alert("请选择要删除的角色！！");
        return;
    }
    bootbox.confirm('是否删除选定的角色？删除角色之后，将会删除该角色下的绑定的用户以及权限。', function (result) {
        var url = rootPath + '/role/del.json';
        if(result){
            $.ajax({
                type:"POST",
                url:url,
                data:"ids="+ids.join(","),
                dataType:"json",
                success:function(data){
                    if(data.code=="success"){
                        bootbox.alert('删除成功');
                        roleList();
                    }else{
                        bootbox.alert('删除失败');
                    }
                },error:function (data) {
                    bootbox.alert('删除失败');
                }
            });
        }
    });
}

function saveAllotPers() {
    var roleSid = $("#roleSid").val();
    var perSids = $("#menu_tree").jstree(true).get_checked();
    $.ajax({
        type:"POST",
        url:rootPath+"/role/saveAllotPers.json",
        data:"perSids="+perSids.join(",")+"&roleSid="+roleSid,
        dataType:"json",
        success:function(data){
            if(data.code=="success"){
                bootbox.alert('权限修改成功');
                $('#allotPermissionModal').modal('hide');
            }else{
                bootbox.alert('权限修改失败');
            }
        },error:function (data) {
            bootbox.alert('权限修改失败');
        }
    });
}

function loadMenuTree() {
    $("#menu_tree").jstree({
        "core": {
            "themes": {
                "responsive": false
            },
            // so that create works
            "check_callback": true,
            'data': {
                'url': function (node) {
                    return rootPath + '/menu/getAllTreeNode.json';
                },
                'dataType':"JSON"

            }
        },
        "types": {
            "default": {
                "icon": "fa fa-folder icon-state-warning icon-lg"
            },
            "file": {
                "icon": "fa fa-file icon-state-warning icon-lg"
            }
        },
        "state": {"key": "allot_menu"},
        "checkbox" : {
            "keep_selected_style" : false,
            "three_state":false
        },
        "plugins": ["dnd", "state", "types","checkbox"]
    }).on("ready.jstree",function(e,data){
       data.instance.open_all();
    }).on("select_node.jstree",function(e,data){
        var parent = data.node.parent;
        $("#menu_tree").jstree(true).select_node(parent);
    }).on("deselect_node.jstree",function (e,data){
        var parent = data.node.id;
        var children = $("#menu_tree").jstree(true).get_children_dom(parent);
        for(var i=0;i<children.length;i++){
           var childId = $("#menu_tree").jstree(true).get_node(children[i]).id;
            $("#menu_tree").jstree(true).deselect_node(childId);
        }
    });
}
function allotPermission() {

    var ids = [];
    $("input.checkboxes[name='sid']:checkbox").each(function () {
        if ($(this).attr("checked")) {
            ids.push($(this).val());
        }
    });
    if (ids == "") {
        bootbox.alert("请选择要分配权限的角色");
        return;
    }
    if (ids.length > 1) {
        bootbox.alert("只能选择一个角色！！");
        return;
    }
    //清空所有的权限
    $("#menu_tree").jstree(true).uncheck_all();
    $("#roleSid").val(ids[0]);
    //加载当前所选角色的权限并且选中
    var url = rootPath+"/role/getRolePers.json";
    $.ajax({
        type:"POST",
        url:url,
        data:"roleSid="+ids[0],
        dataType:"json",
        success:function(data){
            $("#menu_tree").jstree(true).select_node(data);
            $('#allotPermissionModal').modal('show');
        },error:function (data) {
            bootbox.alert('加载权限数据失败，请刷新重试');
        }
    });
}

function roleList() {
    var roleTable = $('#roleList');
    roleTable.dataTable().fnClearTable(false);
    roleTable.dataTable().fnDestroy();
    roleTable.dataTable({
        "bAutoWidth": false,
        "bDestory": true,
        "bFilter": false,
        "bPaginate": true,
        "sAjaxSource": rootPath + "/role/getPageRole.json",
        "bProcessing": true,
        "searching": false, //去掉搜索框
        "bLengthChange": false,// 是否允许自定义每页显示条数.
        "bServerSide": true,
        "iDisplayLength": 10,
        "bSort": false,
        "oLanguage": {//语言设置
            "sInfo": "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
            "sInfoFiltered": "(总共 _MAX_ 条数据)",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "前一页",
                "sNext": "后一页",
                "sLast": "尾页"
            },
            "sZeroRecords": "抱歉， 没有找到",
            "sInfoEmpty": "没有数据",
            "sLoadingRecords": "加载中...",
            "sProcessing": "处理中..."
        },
        "aoColumns": [
            {
                "sTitle": '<div class="checker"><span class=""><input id="check_all" onclick="checkAll(this);" type="checkbox" class="group-checkable" data-set="#roleList .checkboxes"></span></div>',
                "mDataProp": null,
                "sWidth": "3%",
                "bSortable": false,
                "mRender": function (data, type, full) {
                    var id = full["sid"];
                    return '<div class="checker"><span><input name="sid" onclick="checkThis(this);" type="checkbox" class="checkboxes" value="' + id + '"></span></div>';
                }
            }, {
                "mDataProp": 'roleKey',
                "sTitle": "角色key",
                "sWidth": '10%'
            }, {
                "mDataProp": 'roleName',
                "sTitle": "角色名称",
                "sWidth": '10%'
            }, {
                "mDataProp": 'createTime',
                "sTitle": "时间",
                "sWidth": '10%',
                "mRender":function (data,type,full) {
                    var time=data;
                    if (time) {
                        return new Date(time).format("yyyy-MM-dd hh:mm:ss");
                    } else {
                        return "";
                    }
                }
            },{
                "mDataProp": 'updateTime',
                "sTitle": "最后更新时间",
                "sWidth": '10%',
                "mRender":function (data,type,full) {
                    var time=data;
                    if (time) {
                        return new Date(time).format("yyyy-MM-dd hh:mm:ss");
                    } else {
                        return "";
                    }
                }
            }],
        "aoColumnDefs": [{
            sDefaultContent: '',
            aTargets: ['_all']
        }],
        "fnServerData": function (sSource, aoData, fnCallback) {
            if (!aoData) {
                aoData = [];
            }
            aoData.push({
                name: "accountName",
                value: $("#accountName").val()
            });
            $.ajax({
                "dataType": 'json',
                "type": "POST",
                "url": sSource,
                "data": aoData,
                "success": function (json, flag) {
                    if (flag && json) {
                        fnCallback(json);
                    } else {
                        roleTable.dataTable().fnProcessingIndicator(false);
                        bootbox.alert("查询失败，请稍后再试！");
                    }
                },
                "timeout": 15000,
                "error": function (xhr, textStatus, error) {
                    console.log(xhr, textStatus, error);
                    if (textStatus === 'timeout') {
                        alert('The server took too long to send the data.');
                    } else if (textStatus === 'Not Found') {
                        alert('The server not found.');
                    } else {
                        alert('An error occurred on the server. Please try again in a minute.');
                    }
                    roleTable.dataTable().fnProcessingIndicator(false);
                }
            });
        },
        "fnRowCallback": function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
            //console.log(nRow, aData, iDisplayIndex, iDisplayIndexFull);
        }
    });
}


function checkAll(obj) {
    var checked = $(obj).is(":checked");
    if (checked) {
        $(obj).parents("span").addClass("checked");
    } else {
        $(obj).parents("span").removeClass("checked");
    }
    var set = $(obj).attr("data-set");
    $(set).each(function () {
        if (checked) {
            $(this).parents("span").addClass("checked");
            $(this).attr("checked", true);
            $(this).parents('tr').addClass("active");
        } else {
            $(this).parents("span").removeClass("checked");
            $(this).attr("checked", false);
            $(this).parents('tr').removeClass("active");
        }
    });
    $.uniform.update(set);
}

function checkThis(obj) {
    var checked = $(obj).is(":checked");
    if (checked) {
        $(obj).parents("span").addClass("checked");
        $(obj).attr("checked", true);
        $(obj).parents('tr').addClass("active");
    } else {
        $(obj).parents("span").removeClass("checked");
        $(obj).attr("checked", false);
        $(obj).parents('tr').removeClass("active");
    }
}

