var discount;
var oldPrice;
var price;
var $oldPrice;
$(document).ready(
    function () {

        // Date picker for "Next payment date" field
        $(function () {
            $("#paymentDate").datepicker({dateFormat: "yy-mm-dd", constraintInput: true});
        });

        // Editable SELECT with validation for "Number Of Adults" and "Number Of Children" fields
        $('#numberOfAdults').jec({maxLength: 3, acceptedKeys: [
            {min: 48, max: 57}
        ], blinkingCursor: true,
            blinkingCursorInterval: 500});
        $('#numberOfChildren').jec({maxLength: 3, acceptedKeys: [
            {min: 48, max: 57}
        ], blinkingCursor: true,
            blinkingCursorInterval: 500});

        // Calculation and display discount price
        discount = $('#discount');
        $oldPrice = $('#oldPrice');
        oldPrice = Number($oldPrice.text());
        price = oldPrice;
        console.log("Price", price);

        calculatePrice();
        discount.change(
            function () {
                console.log("Total discount", discount.val());
                $oldPrice.show();
                console.log("Discount ", discount.val());
                calculatePrice();
            }
        );

        function calculatePrice() {
            if (typeof numOfAdults != "undefined") {
                oldPrice = price * Number(numOfAdults.val());
                console.log()
            }
            console.log("Old price", oldPrice, price, numOfAdults);
            if ((discount.val() >= 0) && (discount.val() <= 100)) {
                var newPrice = oldPrice - oldPrice * Number(discount.val()) / 100;
                newPrice = Math.round(newPrice * 100) / 100;
                console.log("New price", newPrice);
                $('#priceWithDiscount').text('$ ' + newPrice);
                $("#totalDiscount").text(discount.val() + "%");
                $oldPrice.text(oldPrice);
            } else {
                $oldPrice.hide();
                $('#priceWithDiscount').text('$ ' + oldPrice);
            }
        }

        // Order status icons color change
        var statusField = $('#status');
        var received = $('.received');
        var accepted = $('.accepted');
        var confirmed = $('.confirmed');
        var completed = $('.completed');
        var cancelled = $('.cancelled');

        switch (statusField.val()) {
            case 'Accepted':
                accepted.removeClass("gray");
                break;
            case 'Confirmed':
                accepted.removeClass("gray");
                confirmed.removeClass("gray");
                break;
            case 'Completed':
                accepted.removeClass("gray");
                confirmed.removeClass("gray");
                completed.removeClass("gray");
                break;
            case 'Cancelled':
                received.addClass("gray");
                cancelled.removeClass("gray");
                break;
        }

        // Days in tour calculation
        var startingDate = new Date($('#startingDate').html());
        var endingDate = new Date($('#endingDate').html())
        var daysInTour = (endingDate - startingDate) / (1000 * 60 * 60 * 24);
        (daysInTour >= 0) ? $('#daysInTour').text(daysInTour) : $('#daysInTour').text('sorry... wrong dates');

        // Order status popovers
        $('#received').popover({placement: 'bottom', trigger: 'hover',
            content: 'The order was received and now is in the waiting list.'});

        accepted.hasClass('gray') ? $('#accepted').popover({placement: 'bottom', trigger: 'hover',
            content: 'Our agent will call you to explain some details.'}) :
            $('#accepted').popover({placement: 'bottom', trigger: 'hover',
                content: 'The order is processed by the agent. Awaiting for prepayment.'});

        confirmed.hasClass('gray') ? $('#confirmed').popover({placement: 'bottom', trigger: 'hover',
            content: 'After the prepayment will be done the preparation of documents will start.'}) :
            $('#confirmed').popover({placement: 'bottom', trigger: 'hover',
                content: 'The prepayment was made. Preparing documents for the tour. Awaiting for full payment.'});

        completed.hasClass('gray') ? $('#completed').popover({placement: 'bottom', trigger: 'hover',
            content: 'After the full payment will be done you will receive all needed documents.'}) :
            $('#completed').popover({placement: 'bottom', trigger: 'hover',
                content: 'Full payment was made. All documents was received by the client.'});

        cancelled.hasClass('gray') ? $('#cancelled').popover({placement: 'bottom', trigger: 'hover',
            content: 'This status is set if the order is cancelled for some reason.'}) :
            $('#cancelled').popover({placement: 'bottom', trigger: 'hover',
                content: 'The order was cancelled for some reason.'});

        // Star rating popovers
        $('#overallRating').popover({placement: 'top', trigger: 'hover',
            content: 'Overall rating for this tour'});

        $('#customerRating').popover({placement: 'bottom', trigger: 'hover',
            content: 'Tour rating from customer for this particular order'});

        // Tour photo display in original size on click
        $('#tourPhoto').click(function () {
            var thumbnail = $(this).parent();
            var url = thumbnail.find('img').attr('src');
            var html = '<a href="#" title="Click to close" class="originalSize"><img src="' + url + '" /></a>';
            $('#showPicture').append(html);
            $('.originalSize').click(function () {
                $(this).remove();
            });
        });

        // Manage order form validation
        $("#manageOrderForm").validate({
            rules: {
                numberOfAdults: {
                    required: true,
                    min: 1
                },
                numberOfChildren: {
                    required: true
                },
                discount: {
                    required: true,
                    discountRange: true
                }
            },
            messages: {
                numberOfAdults: {
                    required: "This field can not be empty!"
                },
                numberOfChildren: {
                    required: "This field can not be empty!"
                },
                discount: {
                    required: "This field can not be empty!",
                    discountRange: "Discount value must be between 0 and 100!"
                }
            },
            errorPlacement: function (error, element) {
                if (element.attr("name") == "numberOfAdults") error.insertAfter($("select[name=numberOfAdults]"));
                if (element.attr("name") == "numberOfChildren") error.insertAfter($("select[name=numberOfChildren]"));
                if (element.attr("name") == "discount") error.insertAfter($("input[name=discount]"));
            }
        });

        $.validator.addMethod("discountRange", function () {
                var isCorrect = (discount.val() >= 0) && (discount.val() <= 100) ? true : false;
                return isCorrect;
            }, ""
        );

        $.validator.addMethod("nameIsCorrect", function (value, element) {
                return this.optional(element) || /^[а-яА-ЯІіЇїЄєa-zA-Z]+$/i.test(value);
            }, ""
        );

        $.validator.addMethod("phoneIsCorrect", function (value, element) {
                return this.optional(element) || /^\+?\d+(-\d+)*$/i.test(value);
            }, "Only numbers, '+' at the beginning and '-' as separate symbol are allowed."
        );

        // Create order form validation
        $("#createOrderForm").validate({
            rules: {
                numberOfAdults: {
                    required: true,
                    min: 1
                },
                numberOfChildren: {
                    required: true
                },
                name: {
                    required: true,
                    nameIsCorrect: true,
                    minlength: 3,
                    maxlength: 30
                },
                surname: {
                    required: true,
                    nameIsCorrect: true,
                    minlength: 3,
                    maxlength: 30
                },
                email: {
                    required: true,
                    email: true
                },
                phone: {
                    required: true,
                    phoneIsCorrect: true,
                    minlength: 5,
                    maxlength: 25
                }
            },
            messages: {
                numberOfAdults: {
                    required: "This field can not be empty!",
                    min: "Please enter a value greater than 0."
                },
                numberOfChildren: {
                    required: "This field can not be empty!"
                },
                name: {
                    required: "This field can not be empty!",
                    nameIsCorrect: "Name must contain only letters with no spaces."
                },
                surname: {
                    required: "This field can not be empty!",
                    nameIsCorrect: "Surname must contain only letters with no spaces."
                },
                email: {
                    required: "This field can not be empty!"
                },
                phone: {
                    required: "This field can not be empty!"
                }
            },
            errorPlacement: function (error, element) {
                if (element.attr("name") == "numberOfAdults") error.insertAfter($("select[name=numberOfAdults]"));
                if (element.attr("name") == "numberOfChildren") error.insertAfter($("select[name=numberOfChildren]"));
                if (element.attr("name") == "name") error.insertAfter($("input[name=name]"));
                if (element.attr("name") == "surname") error.insertAfter($("input[name=surname]"));
                if (element.attr("name") == "email") error.insertAfter($("input[name=email]"));
                if (element.attr("name") == "phone") error.insertAfter($("input[name=phone]"));
            }
        });

        // Comments saving from "Comments" textarea
        $('#comments').focus(function () {
            $('#saveComments').css('visibility', 'visible');
        });

        $('#saveComments').click(function () {
            $.post('/orders/comments', {orderId: $('#orderId').val(), comments: $('#comments').val()});
            $('#saveComments').css('visibility', 'hidden');
        });

        //Recalculate discount policies

        var numOfChildren = $('#numberOfChildren');
        var numOfAdults = $('#numberOfAdults');
        var discountPolicies = $('#discountPolicies');
        $("#calculateDiscount").click(function () {
            var currentUrl = window.location.href;
            var tourInfoId = currentUrl.substr(currentUrl.lastIndexOf("/") + 1, currentUrl.length);
            var cutUrl = currentUrl.substr(0, currentUrl.lastIndexOf("/"));
            var url = cutUrl.substr(0, cutUrl.lastIndexOf("/")) + "/orders/calculate_discount";
            var data = {numberOfChildren: numOfChildren.val(), numberOfAdults: numOfAdults.val(), tourId: tourInfoId};
            console.log("Data ", data);

            var tourDiscount = $("#tourDiscount").text();
            var totalDiscount = Number(tourDiscount.substr(0, tourDiscount.length - 1));
            var companyDiscount = $("#companyDiscount");
            if (companyDiscount.length > 0) {
                var companyDiscountString = companyDiscount.text();
                totalDiscount += Number(companyDiscountString.substr(0, companyDiscountString.length - 1));
            }
            $.ajax({
                url: url,
                dataType: "json",
                type: "POST",
                data: data,
                success: function (data, textStatus) {
                    console.log(data.length);
                    if (data.discountPolicies.length > 0) {
                        discountPolicies.html("");

                        $.each(data.discountPolicies, function (i, item) {
                            var discountPolicy = $('<div class="row">\
                        <div class="col-sm-9 discountPolicyName"\
                        data-content="' + item.description + '">' + item.name + '</div>\
                            <div class="col-sm-3">' + item.formula + '%</div>\
                        </div>\
                        ');
                            console.log("Discount policy ", discountPolicy.html());
                            discountPolicies.append(discountPolicy);

                        });
                        $(".discountPolicyName").popover({placement: 'left', trigger: 'hover', html: true});
                    }
                    totalDiscount += Number(data.discount);
                    $("#discount").val(totalDiscount).trigger("change");
                }
            });
        });
    }
);