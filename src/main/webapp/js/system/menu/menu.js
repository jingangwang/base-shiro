var UITree = function () {
    var ajaxTree = function () {
        $("#menu_tree").jstree({
            "core": {
                "themes": {
                    "responsive": false
                },
                // so that create works
                "check_callback": true,
                'data': {
                    'url': function (node) {
                        return rootPath + '/menu/getTreeNode.json';
                    },
                    'data': function (node) {
                        return {'parent': node.id};
                    }
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
            "state": {"key": "demo3"},
            "plugins": ["dnd", "state", "types"]
        }).on('select_node.jstree', function (e, data) {
            var sid = data.node.id;
            var url = rootPath + "/menu/getNodeData.json";
            // 如果选择的节点为空或者是顶点节点，不进行ajax获取数据
            if(sid=="" || sid==0){
                return;
            }
            $.ajax({
                type: "POST",
                url: url,
                data: "sid=" + sid,
                dataType: "json",
                success: function (data) {
                    if (data) {
                        $("#form_view input[name='permissionName']").val(data.permissionName);
                        $("#form_view input[name='permissionKey']").val(data.permissionKey);
                        $("#form_view input[name='iconName']").val(data.iconName);
                        $("#form_view input[name='url']").val(data.url);
                        $("#form_view input[name='type']").val(data.type == 1 ? "菜单" : "按钮");
                        $("#form_view input[name='parentSid']").val(data.parentSid);
                        $("#form_view input[name='sort']").val(data.sort);
                        $("#menu_form input[name='parentSid']").val(data.sid);
                    } else {
                        bootbox.alert('获取菜单数据失败');
                    }
                }, error: function (data) {
                    bootbox.alert('获取菜单数据失败');
                }
            });
        });
    }

    return {
        init: function () {
            ajaxTree();
        }
    }
}();

$(function(){
    $('#addMenu').click(function () {
        addMenu();
    });

    $('#updateMenu').click(function () {
        updateMenu();
    });

    $('#deleteMenu').click(function(){
        deleteMenu();
    });

    $("#saveBtn").click("click",function(){
        $("#menu_form").submit();
    });

    $("#updateBtn").click("click",function(){
        $("#menu-update-form").submit();
    });
});

function addMenu() {
    //去除校验标示
    $('#menu_form .form-group').removeClass('has-success').removeClass('has-error');
    $('#menu_form .input-icon i').removeClass('fa-check').removeClass('fa-fail');
    $('.alert-danger').hide();
    $('.alert-success').hide();
    //重置表单
    $('#menu_form').resetForm();
    //显示弹出框
    $('#addMenuModal').modal('show');
}

function updateMenu(){
    /**
     *  获取tree的当前选择的节点id
     *  get_selected(true)返回整个节点的信息
     *  get_selected(false)只返回id
     */
    var sid = $('#menu_tree').jstree(true).get_selected(false);
    if(sid.length!=1){
        bootbox.alert('只能选择一个节点进行修改');
        return;
    }
    if(sid=="0"){
        bootbox.alert('顶级菜单不能修改');
        return;
    }
    //去除校验标示
    $('#menu-update-form .form-group').removeClass('has-success').removeClass('has-error');
    $('#menu-update-form .input-icon i').removeClass('fa-check').removeClass('fa-fail');
    $('.alert-danger').hide();
    $('.alert-success').hide();
    //重置表单
    $('#menu-update-form').resetForm();
    var url = rootPath + '/menu/getNodeData.json';
    $.ajax({
        type:"POST",
        url:url,
        data:"sid="+sid,
        dataType:"json",
        success:function(data){
            if(data){
                $("#menu-update-form input[name='sid']").val(data.sid);
                $("#menu-update-form input[name='permissionName']").val(data.permissionName);
                $("#menu-update-form input[name='permissionKey']").val(data.permissionKey);
                $("#menu-update-form input[name='url']").val(data.url);
                $("#menu-update-form input[name='iconName']").val(data.iconName);
                $("#menu-update-form input[name='sort']").val(data.sort);
                $("#menu-update-form select[name='type']").val(data.type);
                //显示弹出框
                $('#updateMenuModal').modal('show');
            }else{
                bootbox.alert('获取菜单数据失败');
            }
        },error:function (data) {
            bootbox.alert('获取菜单数据失败');
        }
    });

}

function deleteMenu(){
    /**
     *  获取tree的当前选择的节点id
     *  get_selected(true)返回整个节点的信息
     *  get_selected(false)只返回id
     */
    var sid = $('#menu_tree').jstree(true).get_selected(false);
    if(sid.length!=1){
        bootbox.alert('只能选择一个节点进行删除');
        return;
    }
    if(sid=="0"){
        bootbox.alert('顶级菜单不能删除');
        return;
    }
    var url = rootPath + '/menu/deleteMenu.json';
    bootbox.confirm('是否删除选定的菜单么？', function (result) {
        if(result){
            $.ajax({
                type:"POST",
                url:url,
                data:"sid="+sid,
                dataType:"json",
                success:function(data){
                    if(data.code=="success"){
                        bootbox.alert('删除菜单成功');
                        //刷新菜单树
                        $("#menu_tree").jstree(true).refresh();
                    }else{
                        bootbox.alert(data.msg);
                    }
                },error:function (data) {
                    bootbox.alert('删除菜单失败');
                }
            });
        }
    });
}