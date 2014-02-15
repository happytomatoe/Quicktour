var country = $("#country");
var placeInput = $("#place");
var baseUrl;
$(document).ready(function () {
    baseUrl = $("input#baseUrl").val();
    country.select2({ placeholder: "Select a country",
        allowClear: true});
    placeInput.select2({ placeholder: "Select a place",
        allowClear: true});

});

jQuery(function () {
    jQuery("#minDate").datepicker({
        //defaultDate: "+1w",
        dateFormat: "yy-mm-dd",
        changeMonth: true,
        numberOfMonths: 1,
        onClose: function (selectedDate) {
            jQuery("#maxDate").datepicker("option", "minDate", selectedDate);
        }
    });
    jQuery("#maxDate").datepicker({
        //defaultDate: "+1w",
        dateFormat: "yy-mm-dd",
        changeMonth: true,
        numberOfMonths: 1,
        onClose: function (selectedDate) {
            $("#minDate").datepicker("option", "maxDate", selectedDate);
        }
    });
})
country.on("change", function () {
    console.log("Change");
    placeInput.select2("val", "");
    getPlacesForCountry()
});

function getPlacesForCountry() {
    if (typeof  temp != 'undefined') {
        placeInput.append(temp);
    }
    if (country.val() != "") {
        var selector = "select#place>optgroup[label!='" + country.val() + "']";
        console.log("Selector", selector);
        temp = $(selector);
        $(selector).remove();
        console.log(temp);
    }
}

