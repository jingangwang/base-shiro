var MenuFormValidation = function () {
    // validation using icons
    var handleAddValidation = function() {
        // for more info visit the official plugin documentation:
        // http://docs.jquery.com/Plugins/Validation

        var form = $('#menu_form');
        var error = $('.alert-danger', form);
        var success = $('.alert-success', form);

        form.validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block help-block-error', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",  // validate all fields including form hidden input
            rules: {
                permissionName: {
                    minlength: 2,
                    maxlength:20,
                    required: true
                },
                permissionKey: {
                    required: true,
                    minlength: 2,
                    maxlength:20
                },
                parentSid: {
                    required: true
                },
                sort: {
                    required: true,
                    minlength:1,
                    maxlength:2,
                    number: true
                },
                type: {
                    required: true
                }
            },

            invalidHandler: function (event, validator) { //display error alert on form submit
                success.hide();
                error.show();
                Metronic.scrollTo(error, -200);
            },

            errorPlacement: function (error, element) { // render error placement for each input type
                var icon = $(element).parent('.input-icon').children('i');
                icon.removeClass('fa-check').addClass("fa-warning");
                icon.attr("data-original-title", error.text()).tooltip({'container': 'body'});
            },

            highlight: function (element) { // hightlight error inputs
                $(element)
                    .closest('.form-group').removeClass("has-success").addClass('has-error'); // set error class to the control group
            },

            unhighlight: function (element) { // revert the change done by hightlight

            },

            success: function (label, element) {
                var icon = $(element).parent('.input-icon').children('i');
                $(element).closest('.form-group').removeClass('has-error').addClass('has-success'); // set success class to the control group
                icon.removeClass("fa-warning").addClass("fa-check");
            },

            submitHandler: function (form) {
                success.show();
                error.hide();
                $(form).ajaxSubmit({
                    type:"post",
                    dataType:"json",
                    data:$(form).serialize(),
                    success:function (data) {
                        if(data.code=="success"){
                            $('#addMenuModal').modal('hide');
                            $(form).resetForm();
                            success.hide();
                            bootbox.alert('增加菜单成功');
                            //刷新菜单树
                            $("#menu_tree").jstree(true).refresh();
                        }else{
                            bootbox.alert(data.msg);
                        }
                    },
                    error:function(data){
                        bootbox.alert('增加菜单失败');
                    }
                });
            }
        });


    }


    var handleUpdateValidation = function() {
        // for more info visit the official plugin documentation:
        // http://docs.jquery.com/Plugins/Validation

        var form = $('#menu-update-form');
        var error = $('.alert-danger', form);
        var success = $('.alert-success', form);

        form.validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block help-block-error', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",  // validate all fields including form hidden input
            rules: {
                permissionName: {
                    minlength: 2,
                    maxlength:20,
                    required: true
                },
                permissionKey: {
                    required: true,
                    minlength: 2,
                    maxlength:20
                },
                sort: {
                    required: true,
                    minlength:1,
                    maxlength:2,
                    number: true
                },
                type: {
                    required: true
                }
            },

            invalidHandler: function (event, validator) { //display error alert on form submit
                success.hide();
                error.show();
                Metronic.scrollTo(error, -200);
            },

            errorPlacement: function (error, element) { // render error placement for each input type
                var icon = $(element).parent('.input-icon').children('i');
                icon.removeClass('fa-check').addClass("fa-warning");
                icon.attr("data-original-title", error.text()).tooltip({'container': 'body'});
            },

            highlight: function (element) { // hightlight error inputs
                $(element)
                    .closest('.form-group').removeClass("has-success").addClass('has-error'); // set error class to the control group
            },

            unhighlight: function (element) { // revert the change done by hightlight

            },

            success: function (label, element) {
                var icon = $(element).parent('.input-icon').children('i');
                $(element).closest('.form-group').removeClass('has-error').addClass('has-success'); // set success class to the control group
                icon.removeClass("fa-warning").addClass("fa-check");
            },

            submitHandler: function (form) {
                success.show();
                error.hide();
                $(form).ajaxSubmit({
                    type:"post",
                    dataType:"json",
                    data:$(form).serialize(),
                    success:function (data) {
                        if(data.code=="success"){
                            $('#updateMenuModal').modal('hide');
                            $(form).resetForm();
                            success.hide();
                            bootbox.alert('修改菜单成功');
                            //刷新菜单树
                            $("#menu_tree").jstree(true).refresh();
                        }else{
                            bootbox.alert(data.msg);
                        }
                    },
                    error:function(data){
                        bootbox.alert('修改菜单失败');
                    }
                });
            }
        });


    }

    return {
        init:function(){
            handleAddValidation();
            handleUpdateValidation();
        }
    }
}();