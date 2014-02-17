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
    //pagination and number of records
    var $numberOfRecords = $("#numberOfRecords");
    $numberOfRecords.change(function () {
        var url = window.location.href;
        if (url.contains("numberOfRecords")) {
            url = url.substr(0, url.length - 1) + $numberOfRecords.val();
        } else {
            url += "?numberOfRecords=" + $numberOfRecords.val();
        }
        window.location.href = url;
    });

    var value = $numberOfRecords.attr("selected-value");
    if (typeof value != 'undefined') {
        $numberOfRecords.val(value);
    }
    $(".tourDescription").expander({slicePoint: 125});

    jQuery("#minDate").datepicker({
        defaultDate: "+1w",
        dateFormat: "yy-mm-dd",
        changeMonth: true,
        numberOfMonths: 1,
        onClose: function (selectedDate) {
            jQuery("#maxDate").datepicker("option", "minDate", selectedDate);
        }
    });
    jQuery("#maxDate").datepicker({
        defaultDate: "+1w",
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

