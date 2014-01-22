jQuery(document).ready(
    function(){

        // Filter buttons usability
        var allBtn = jQuery('#buttonAll');
        var activeBtn = jQuery('#buttonActive');
        var completedBtn = jQuery('#buttonCompleted');
        var cancelledBtn = jQuery('#buttonCancelled');

        var all = parseInt(jQuery('#all').html());
        var active = parseInt(jQuery('#inProcess').html());
        var completed = parseInt(jQuery('#completed').html());
        var cancelled = parseInt(jQuery('#cancelled').html());

        if (all == 0) {
            allBtn.addClass("disabled");
            jQuery('#buttonAll a').removeAttr('href');
        }

        if (active == 0) {
            activeBtn.addClass("disabled");
            jQuery('#buttonActive a').removeAttr('href');
        }

        if (completed == 0){
            completedBtn.addClass("disabled");
            jQuery('#buttonCompleted a').removeAttr('href');
        }

        if (cancelled == 0) {
            cancelledBtn.addClass("disabled");
            jQuery('#buttonCancelled a').removeAttr('href');
        }

        // Disables buttons "First" and "Last" for first and last pages respectively
        if (!(jQuery('#first').length > 0)){
            jQuery('#navFirst').addClass('disabled');
            jQuery('#navFirst a').removeAttr('href');
            jQuery('#navPrevious').addClass('disabled');
            jQuery('#navPrevious a').removeAttr('href');
        }

        if (!(jQuery('#last').length > 0)){
            jQuery('#navNext').addClass('disabled');
            jQuery('#navNext a').removeAttr('href');
            jQuery('#navLast').addClass('disabled');
            jQuery('#navLast a').removeAttr('href');
        }

        var url = document.location.pathname;

        if (url.contains('/orders/filter/Active')) {
            activeBtn.addClass('active');
        } else if (url.contains('/orders/filter/Completed')) {
            completedBtn.addClass('active');
        } else if (url.contains('/orders/filter/Cancelled')) {
            cancelledBtn.addClass('active');
        } else {
            allBtn.addClass('active');
        }

        // Sort order displaying in table head
        if (url.contains('/id/asc/')) {
            jQuery('#spaceId').removeAttr('class');
            jQuery('#sortByIdAsc').hide();
            jQuery('#sortByIdDesc').show();
            jQuery('#idAscIcon').show();
        }
        if (url.contains('/id/desc/')) {
            jQuery('#spaceId').removeAttr('class');
            jQuery('#sortByIdAsc').show();
            jQuery('#idDescIcon').show();
        }
        if (url.contains('/tourInfoId/asc/')) {
            jQuery('#spaceTourName').removeAttr('class');
            jQuery('#sortByTourNameAsc').hide();
            jQuery('#sortByTourNameDesc').show();
            jQuery('#tourNameAscIcon').show();
        }
        if (url.contains('/tourInfoId/desc/')) {
            jQuery('#spaceTourName').removeAttr('class');
            jQuery('#sortByTourNameAsc').show();
            jQuery('#tourNameDescIcon').show();
        }
        if (url.contains('/price/asc/')) {
            jQuery('#spacePrice').removeAttr('class');
            jQuery('#sortByPriceAsc').hide();
            jQuery('#sortByPriceDesc').show();
            jQuery('#priceAscIcon').show();
        }
        if (url.contains('/price/desc/')) {
            jQuery('#spacePrice').removeAttr('class');
            jQuery('#sortByPriceAsc').show();
            jQuery('#priceDescIcon').show();
        }
        if (url.contains('/orderDate/asc/')) {
            jQuery('#spaceOrderDate').removeAttr('class');
            jQuery('#sortByOrderDateAsc').hide();
            jQuery('#sortByOrderDateDesc').show();
            jQuery('#orderDateAscIcon').show();
        }
        if (url.contains('/orderDate/desc/')) {
            jQuery('#spaceOrderDate').removeAttr('class');
            jQuery('#sortByOrderDateAsc').show();
            jQuery('#orderDateDescIcon').show();
        }
        if (url.contains('/status/asc/')) {
            jQuery('#spaceStatus').removeAttr('class');
            jQuery('#sortByOrderStatusAsc').hide();
            jQuery('#sortByOrderStatusDesc').show();
            jQuery('#statusAscIcon').show();
        }
        if (url.contains('/status/desc/')) {
            jQuery('#spaceStatus').removeAttr('class');
            jQuery('#sortByOrderStatusAsc').show();
            jQuery('#statusDescIcon').show();
        }
        if (url.contains('/nextPaymentDate/asc/')) {
            jQuery('#spacePayment').removeAttr('class');
            jQuery('#sortByNextPaymentDateAsc').hide();
            jQuery('#sortByNextPaymentDateDesc').show();
            jQuery('#paymentAscIcon').show();
        }
        if (url.contains('/nextPaymentDate/desc/')) {
            jQuery('#spacePayment').removeAttr('class');
            jQuery('#sortByNextPaymentDateAsc').show();
            jQuery('#paymentDescIcon').show();
        }

    }
);

