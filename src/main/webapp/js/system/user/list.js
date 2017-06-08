$(function () {
    userList();

    $("#search").click("click", function () {// 绑定查询按扭
        /*var searchParams = $("#searchForm").serializeJson();// 初始化传参数*/
        userList();
    });
    $("#addUser").click("click", function () {
        addUser();
    });

    $("#updateUser").click("click", function () {
        updateUser();
    });
    $("#deleteUser").click("click", function () {
        deleteUser();
    });
    $("#lockUser").click("click", function () {
        lockUser();
    });

    $("#saveBtn").click("click",function(){
        $("#user_form").submit();
    });

    $("#updateBtn").click("click",function(){
        $("#user-update-form").submit();
    });
});

function addUser(){
    //去除校验标示
    $('#user_form .form-group').removeClass('has-success').removeClass('has-error');
    $('#user_form .input-icon i').removeClass('fa-check').removeClass('fa-fail');
    $('.alert-danger').hide();
    $('.alert-success').hide();
    //重置表单
    $('#user_form').resetForm();
    //将角色多选框还原
    $('#roleMulti').multiSelect('deselect_all');
    //显示弹出框
    $('#addUserModal').modal('show');
}

function updateUser() {
    var ids = [];
    $("input.checkboxes[name='sid']:checkbox").each(function () {
        if ($(this).attr("checked")) {
            ids.push($(this).val());
        }
    });
    if(ids==""){
        bootbox.alert("请选择要修改的用户！！");
        return;
    }
    if (ids.length > 1) {
        bootbox.alert("只能选择一个用户修改！！");
        return;
    }
    //去除校验标示
    $('#user-update-form .form-group').removeClass('has-success').removeClass('has-error');
    $('#user-update-form .input-icon i').removeClass('fa-check').removeClass('fa-fail');
    $('.alert-danger').hide();
    $('.alert-success').hide();
    //重置表单
    $('#user-update-form').resetForm();
    //将角色多选框还原
    $('#updateRoleMulti').multiSelect('deselect_all');
    var url = rootPath + '/user/get.json';
    $.ajax({
        type:"POST",
        url:url,
        data:"sid="+ids[0],
        dataType:"json",
        success:function(data){
            if(data){
                $('#userSid').val(data.sid);
                $("#user-update-form input[name='username']").val(data.username);
                $("#user-update-form input[name='email']").val(data.email);
                $("#user-update-form input[name='phone']").val(data.phone);
                $("#updateRoleMulti").multiSelect('select',data.roles.toString().split(","));
                //显示弹出框
                $('#updateUserModal').modal('show');
            }else{
                bootbox.alert('获取用户数据失败');
            }
        },error:function (data) {
            bootbox.alert('获取用户数据失败');
        }
    });

}

function deleteUser() {
    var ids = [];
    $("input.checkboxes[name='sid']:checkbox").each(function () {
        if ($(this).attr("checked")) {
            ids.push($(this).val());
        }
    });
    /*var cbox = grid.getSelectedCheckbox();*/
    if (ids == "") {
        bootbox.alert("请选择要删除的用户！！");
        return;
    }
    bootbox.confirm('是否删除选定的用户？', function (result) {
        var url = rootPath + '/user/del.json';
        if(result){
            $.ajax({
                type:"POST",
                url:url,
                data:"ids="+ids.join(","),
                dataType:"json",
                success:function(data){
                    if(data.code=="success"){
                        bootbox.alert('删除成功');
                        userList();
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

function lockUser() {
    var ids = [];
    $("input.checkboxes[name='sid']:checkbox").each(function () {
        if ($(this).attr("checked")) {
            ids.push($(this).val());
        }
    });
    if (ids == "") {
        bootbox.alert("请选择要锁定的用户");
        return;
    }
    bootbox.confirm('确定要锁定选择的用户吗？锁定之后将不能再登录', function (result) {
        var url = rootPath + '/user/lock.json';
        if(result){
            $.ajax({
                type:"POST",
                url:url,
                data:"ids="+ids.join(","),
                dataType:"json",
                success:function(data){
                    if(data.code=="success"){
                        bootbox.alert('锁定成功');
                        userList();
                    }else{
                        bootbox.alert('锁定失败');
                    }
                },error:function (data) {
                    bootbox.alert('锁定失败');
                }
            });
        }
    });
}

function userList() {
    var userTable = $('#userList');
    userTable.dataTable().fnClearTable(false);
    userTable.dataTable().fnDestroy();
    userTable.dataTable({
        "bAutoWidth": false,
        "bDestory": true,
        "bFilter": false,
        "bPaginate": true,
        "sAjaxSource": rootPath + "/user/getPageUser.json",
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
                "sTitle": '<div class="checker"><span class=""><input id="check_all" onclick="checkAll(this);" type="checkbox" class="group-checkable" data-set="#userList .checkboxes"></span></div>',
                "mDataProp": null,
                "sWidth": "3%",
                "bSortable": false,
                "mRender": function (data, type, full) {
                    var id = full["sid"];
                    return '<div class="checker"><span><input name="sid" onclick="checkThis(this);" type="checkbox" class="checkboxes" value="' + id + '"></span></div>';
                }
            }, {
                "mDataProp": 'username',
                "sTitle": "用户名",
                "sWidth": '10%'
            }, {
                "mDataProp": 'email',
                "sTitle": "邮箱",
                "sWidth": '10%'
            }, {
                "mDataProp": 'phone',
                "sTitle": "电话",
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
                "mDataProp": 'lastLoginTime',
                "sTitle": "最后登录时间",
                "sWidth": '10%',
                "mRender":function (data,type,full) {
                    var time=data;
                    if (time) {
                        return new Date(time).format("yyyy-MM-dd hh:mm:ss");
                    } else {
                        return "";
                    }
                }
            }, {
                "mDataProp": 'status',
                "sTitle": "状态",
                "sWidth": '10%',
                "mRender":function (data,type,full) {
                    var status = data;
                    if(status==1){
                        return "<span class=\"label label-lg label-success\">正常</span>";
                    }else if(status ==2){
                        return "<span class=\"label label-lg label-warning\">锁定</span>";
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
                        userTable.dataTable().fnProcessingIndicator(false);
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
                    userTable.dataTable().fnProcessingIndicator(false);
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

