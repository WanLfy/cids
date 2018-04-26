$(function() {
    $("#commentForm").validate({
        rules: {
            username: "required",
            password: "required"
        },
        messages: {
            username: "请输入用户名",
            password: "请输入密码"
        }
    });
    $('#reset').on('click', function() {
        $('.form-input').val('');
    });
})