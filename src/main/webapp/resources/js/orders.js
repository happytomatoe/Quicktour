jQuery(document).ready(
    function(){

        // Date picker for "Next payment date" field
        jQuery(function() {
            jQuery( "#paymentDate" ).datepicker({dateFormat: "yy-mm-dd", constraintInput: true});
        });

        // Editable SELECT with validation for "Number Of Adults" and "Number Of Children" fields
        jQuery('#numberOfAdults').jec({maxLength: 3, acceptedKeys: [{min:48, max:57}], blinkingCursor: true,
            blinkingCursorInterval: 500});
        jQuery('#numberOfChildren').jec({maxLength: 3, acceptedKeys: [{min:48, max:57}], blinkingCursor: true,
            blinkingCursorInterval: 500});

        // Calculation and display discount price
        var discount = jQuery('#discount');
        var oldPrice = jQuery('#oldPrice').html();

        displayDiscount();

        discount.change(
            function(){
                jQuery('.price-old').show();
                displayDiscount();
            }
        )

        function displayDiscount(){
            if ((discount.val() >= 0) && (discount.val() <= 100)) {
                var newPrice = oldPrice - oldPrice * discount.val() / 100;
                jQuery('#priceWithDiscount').text('$ ' + Math.round(newPrice*100)/100);
            }  else {
                jQuery('.price-old').hide();
                jQuery('#priceWithDiscount').text('$ ' + (oldPrice - 0));
            }
        }

        // Order status icons color change
        var statusField = jQuery('#status');
        var received = jQuery('.received');
        var accepted = jQuery('.accepted');
        var confirmed = jQuery('.confirmed');
        var completed = jQuery('.completed');
        var cancelled = jQuery('.cancelled');

        switch (statusField.val()){
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
        var startingDate = new Date(jQuery('#startingDate').html());
        var endingDate = new Date(jQuery('#endingDate').html())
        var daysInTour = (endingDate - startingDate)/(1000*60*60*24);
        (daysInTour >= 0) ? jQuery('#daysInTour').text(daysInTour) : jQuery('#daysInTour').text('sorry... wrong dates');

        // Order status popovers
        jQuery('#received').popover({placement: 'bottom', trigger: 'hover',
            content: 'The order was received and now is in the waiting list.'});

        accepted.hasClass('gray') ? jQuery('#accepted').popover({placement: 'bottom', trigger: 'hover',
            content: 'Our agent will call you to explain some details.'}) :
            jQuery('#accepted').popover({placement: 'bottom', trigger: 'hover',
            content: 'The order is processed by the agent. Awaiting for prepayment.'});

        confirmed.hasClass('gray') ? jQuery('#confirmed').popover({placement: 'bottom', trigger: 'hover',
            content: 'After the prepayment will be done the preparation of documents will start.'}) :
            jQuery('#confirmed').popover({placement: 'bottom', trigger: 'hover',
            content: 'The prepayment was made. Preparing documents for the tour. Awaiting for full payment.'});

        completed.hasClass('gray') ? jQuery('#completed').popover({placement: 'bottom', trigger: 'hover',
            content: 'After the full payment will be done you will receive all needed documents.'}) :
            jQuery('#completed').popover({placement: 'bottom', trigger: 'hover',
            content: 'Full payment was made. All documents was received by the client.'});

        cancelled.hasClass('gray') ? jQuery('#cancelled').popover({placement: 'bottom', trigger: 'hover',
            content: 'This status is set if the order is cancelled for some reason.'}) :
            jQuery('#cancelled').popover({placement: 'bottom', trigger: 'hover',
            content: 'The order was cancelled for some reason.'});

        // Star rating popovers
        jQuery('#overallRating').popover({placement: 'top', trigger: 'hover',
            content: 'Overall rating for this tour'});

        jQuery('#customerRating').popover({placement: 'bottom', trigger: 'hover',
            content: 'Tour rating from customer for this particular order'});

        // Tour photo display in original size on click
        jQuery('#tourPhoto').click(function() {
            var thumbnail=$(this).parent();
            var url=thumbnail.find('img').attr('src');
            var html='<a href="#" title="Click to close" class="originalSize"><img src="'+url+'" /></a>';
            jQuery('#showPicture').append(html);
            jQuery('.originalSize').click(function () {
                $(this).remove();
            });
        });

        // Manage order form validation
        jQuery("#manageOrderForm").validate({
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
            messages:{
                numberOfAdults:{
                    required: "This field can not be empty!"
                },
                numberOfChildren:{
                    required: "This field can not be empty!"
                },
                discount: {
                    required: "This field can not be empty!",
                    discountRange: "Discount value must be between 0 and 100!"
                }
            },
            errorPlacement: function(error, element) {
                if (element.attr("name") == "numberOfAdults") error.insertAfter(jQuery("select[name=numberOfAdults]"));
                if (element.attr("name") == "numberOfChildren") error.insertAfter(jQuery("select[name=numberOfChildren]"));
                if (element.attr("name") == "discount") error.insertAfter(jQuery("input[name=discount]"));
            }
        });

        jQuery.validator.addMethod("discountRange", function(){
                var isCorrect = (discount.val() >= 0) && (discount.val() <= 100) ? true : false;
                return isCorrect;
            }, ""
        );

        jQuery.validator.addMethod("nameIsCorrect",function(value,element){
                return this.optional(element) || /^[а-яА-ЯІіЇїЄєa-zA-Z]+$/i.test(value);
            },""
        );

        jQuery.validator.addMethod("phoneIsCorrect",function(value,element){
                return this.optional(element) || /^\+?\d+(-\d+)*$/i.test(value);
            },"Only numbers, '+' at the beginning and '-' as separate symbol are allowed."
        );

        // Create order form validation
        jQuery("#createOrderForm").validate({
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
            messages:{
                numberOfAdults:{
                    required: "This field can not be empty!",
                    min: "Please enter a value greater than 0."
                },
                numberOfChildren:{
                    required: "This field can not be empty!"
                },
                name:{
                    required: "This field can not be empty!",
                    nameIsCorrect: "Name must contain only letters with no spaces."
                },
                surname:{
                    required: "This field can not be empty!",
                    nameIsCorrect: "Surname must contain only letters with no spaces."
                },
                email:{
                    required: "This field can not be empty!"
                },
                phone:{
                    required: "This field can not be empty!"
                }
            },
            errorPlacement: function(error, element) {
                if (element.attr("name") == "numberOfAdults") error.insertAfter(jQuery("select[name=numberOfAdults]"));
                if (element.attr("name") == "numberOfChildren") error.insertAfter(jQuery("select[name=numberOfChildren]"));
                if (element.attr("name") == "name") error.insertAfter(jQuery("input[name=name]"));
                if (element.attr("name") == "surname") error.insertAfter(jQuery("input[name=surname]"));
                if (element.attr("name") == "email") error.insertAfter(jQuery("input[name=email]"));
                if (element.attr("name") == "phone") error.insertAfter(jQuery("input[name=phone]"));
            }
        });

        // Comments saving from "Comments" textarea
        jQuery('#comments').focus(function(){
            jQuery('#saveComments').css('visibility', 'visible');
        });

        jQuery('#saveComments').click(function(){
            jQuery.post('/orders/comments',{orderId: jQuery('#orderId').val(), comments: jQuery('#comments').val()});
            jQuery('#saveComments').css('visibility', 'hidden');
        });
        //Recalculate discount policies
        $("#calculateDiscount").click(function () {
            var currentUrl = window.location.href;
            var tourInfoId = currentUrl.substr(currentUrl.lastIndexOf("/") + 1, currentUrl.length);
            var cutUrl = currentUrl.substr(0, currentUrl.lastIndexOf("/"));
            var url = cutUrl.substr(0, cutUrl.lastIndexOf("/")) + "/orders/calculate_discount";
            var data = {numberOfChildren: numOfChildren.val(), numberOfAdults: numOfAdults.val(), tourId: tourInfoId};

            $.ajax({
                url: url,
                dataType: "json",
                type: "POST",
                data: data,
                success: function (data, textStatus) {
                    console.log(data.length);
                    if (data.length > 0) {

                    }
                    discountPolicies.html("");

                    $.each(data, function (i, item) {
                        var discountPolicy = $('<div class="row">\
                        <div class="col-sm-9 discountPolicyName"\
                        data-content="' + item.description + '">' + item.name + '</div>\
                            <div class="col-sm-3">' + item.formula + '%</div>\
                        </div>\
                        <div class="row text-center">+</div>\
                        ');
                        console.log("Discount policy ", discountPolicy.html());
                        discountPolicies.append(discountPolicy);

                    });
                    $(".discountPolicyName").popover({placement: 'left', trigger: 'hover', html: true});

                }
            });
        });
    }
);