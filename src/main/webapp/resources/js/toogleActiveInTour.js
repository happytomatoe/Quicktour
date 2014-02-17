var url;
$(document).ready(function () {
    url = $(".navbar-brand").attr("href");
});

function changeButtonState(tourId) {
    var button = document.getElementById(tourId);
    if (button.getAttribute("class").contains("btn-success")) {
        button.setAttribute("class", "btn btn-warning");
        button.innerHTML = "Deactivate";
    } else {
        button.setAttribute("class", "btn btn-success");
        button.innerHTML = "Activate";

    }
}
function toggleActive(tourId) {
    console.log("Url", url + "tours/toggleActive");
    $.ajax({
        url: url + "tours/toggleActive",
        data: {id: tourId},
        type: "POST",
        timeout: 4000,
        success: function (map) {
            if (map.status) {
                changeButtonState(tourId);
            }
        },
        error: function (xhr, status) {
            alert("Cannot activate tour");
        }
    });
}
