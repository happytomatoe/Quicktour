var username;
$(document).ready(function () {
    username = $('#username').html();
    $.validator.addMethod("validatePassfield", function (value, element, param) {
        console.log(element);
        return $(element).validatePass();
    }, "Password must contain letters in upper and lower cases and be at least 8 symbols long");
    jQuery.validator.addMethod("notEqual", function (value, element, param) {
        return this.optional(element) || value != param;
    }, "Password can't match username");

    $('#password').passField({
        pattern: "aB3",
        minlength: 8,
        allowEmpty: false

    });
    $('#form').validate({
        errorClass: "text-danger",
        rules: {
            password: {required: true, validatePassfield: true, notEqual: username},
            password2: {
                required: true,
                equalTo: "#password"
            }
        }, submitHandler: function (form) {
            form.submit();
        }
    })
});