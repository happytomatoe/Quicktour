var baseUrl;
$(document).ready(function () {
    baseUrl = $(".navbar-brand").attr("href");
    var $registrationForm = $("#createOrderForm");
    validator = $registrationForm.validate({
        errorElement: "div",
        rules: {
            "user\\.name": {
                minlength: 3
            }, "user.surname": {
                minlength: 3
            }, "user.email": {
                email: true,
                remote: baseUrl + "email/validate"
            }
        }, messages: {
            email: {
                remote: "User with such email already exists"
            }
        },
        errorClass: "text-danger",
        submitHandler: function (form) {
            form.submit();
        }
    });

});