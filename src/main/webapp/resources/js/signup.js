var baseUrl;
var validator;
$(document).ready(function () {

    baseUrl = $(".navbar-brand").attr("href");
    $("select:has(option[value=]:first-child)").on('change',function () {
        $(this).toggleClass("empty", $.inArray($(this).val(), ['', null]) >= 0);
    }).trigger('change');
    var $registrationForm = $("#registrationForm");
    validator = $registrationForm.validate({
        errorElement: "div",
        rules: {
            name: {
                minlength: 3
            }, surname: {
                minlength: 3
            }, username: {
                remote: baseUrl + "username/validate"
            }, email: {
                email: true,
                remote: baseUrl + "email/validate"
            }, password: {
                minlength: 8
            }, password_confirmation: {
                equalTo: "#password"
            }, avatar: {
                accept: "image/*"
            }
        }, messages: {
            username: {
                remote: "Username is taken"
            }, email: {
                remote: "Email is taken"
            }, avatar: {
                accept: "Uploaded file is not an image"
            }
        }, errorPlacement: function (error, element) {
            if (element.attr("name") == "avatar") {
                element.parent().parent().after(error);
            } else {
                element.after(error);
            }

        },
        errorClass: "text-danger",
        submitHandler: function (form) {
            form.submit();
        }
    });
    $registrationForm.submit(function () {
        return validator.form();
    });
    console.log("Validate login url", baseUrl + "email/validate");


    var maskList = $.masksSort($.masksLoad("resources/js/phone-codes.json"), ['#'], /[0-9]|#/, "mask");
    var maskOpts = {
        inputmask: {
            definitions: {
                '#': {
                    validator: "[0-9]",
                    cardinality: 1
                }
            },
            //clearIncomplete: true,
            showMaskOnHover: false,
            autoUnmask: true
        },
        match: /[0-9]/,
        replace: '#',
        list: maskList,
        listKey: "mask"
    };
    var $phone = $('#phone');
//      $phone.inputmasks(maskOpts);
//    if ($phone.val() == "") {
//        $phone.val(7);
//    }
    $("#password").passField({
        pattern: "aB3",
        minlength: 8,
        allowEmpty: false
    });
    $('.button-checkbox').each(function () {

        // Settings
        var $widget = $(this),
            $button = $widget.find('button'),
            $checkbox = $widget.find('input:checkbox'),
            color = $button.data('color'),
            settings = {
                on: {
                    icon: 'glyphicon glyphicon-check'
                },
                off: {
                    icon: 'glyphicon glyphicon-unchecked'
                }
            };

        // Event Handlers
        $button.on('click', function () {
            $checkbox.prop('checked', !$checkbox.is(':checked'));
            $checkbox.triggerHandler('change');
            updateDisplay();
        });
        $checkbox.on('change', function () {
            updateDisplay();
        });

        // Actions
        function updateDisplay() {
            var isChecked = $checkbox.is(':checked');

            // Set the button's state
            $button.data('state', (isChecked) ? "on" : "off");

            // Set the button's icon
            $button.find('.state-icon')
                .removeClass()
                .addClass('state-icon ' + settings[$button.data('state')].icon);

            // Update the button's color
            if (isChecked) {
                $button
                    .removeClass('btn-default')
                    .addClass('btn-' + color + ' active');
            }
            else {
                $button
                    .removeClass('btn-' + color + ' active')
                    .addClass('btn-default');
            }
        }

        // Initialization
        function init() {

            updateDisplay();

            // Inject the icon if applicable
            if ($button.find('.state-icon').length == 0) {
                $button.prepend('<i class="state-icon ' + settings[$button.data('state')].icon + '"></i> ');
            }
        }

        init();
    });
});


