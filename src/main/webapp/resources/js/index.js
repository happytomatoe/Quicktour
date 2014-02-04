var country = $("#country");
var placeInput = $("#place");
var baseUrl;
$(document).ready(function () {
    baseUrl = $("input#baseUrl").val();
    country.select2();
    placeInput.select2();

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
    getPlacesForCountry()
});

function getPlacesForCountry() {
    jQuery.ajax({
        url: baseUrl + "placesByCountry",
        data: {country: country.select2("val")},
        type: "POST",
        timeout: 4000,
        success: function (map) {

            console.log(map);
            var places = map.places;
            var placeUl = document.getElementById("placesDropDown");
            while (placeUl.firstChild) {
                placeUl.removeChild(placeUl.firstChild);
            }
            places.forEach(function (place) {
                setPlacesList(place);
            })
        },
        error: function (xhr, status) {
        },
        complete: function (data) {
        }
    })
}

function setPlacesList(place) {
    var placeUl = document.getElementById("placesDropDown");
    var li = document.createElement("li");
    var a = document.createElement("a");
    a.textContent = place;
    a.onclick = function () {
        placeInput.value = this.textContent;
    }
    li.appendChild(a);
    placeUl.appendChild(li);
}