
$(function () {
    $("#update-password").click("click", function () {
        updatePassword();
    });

    $("#updateBtn").click("click",function(){
        $("#password-update-form").submit();
    });
});

function updatePassword() {
    //去除校验标示
    $('#password-update-form .form-group').removeClass('has-success').removeClass('has-error');
    $('#password-update-form .input-icon i').removeClass('fa-check').removeClass('fa-fail');
    $('.alert-danger').hide();
    $('.alert-success').hide();
    //重置表单
    $('#password-update-form').resetForm();
    //显示弹出框
    $('#updatePasswordModal').modal('show');
}