//markers on google map
var markers = [];

var center;
//tourInfo dates counter
var btnAddDateClicked = 0;

//tourInfo toursPlaces counter
var toursPlacesCounter = 0;
jQuery.validator.addMethod("greaterThan",
    function (value, element, params) {
        if (!/Invalid|NaN/.test(new Date(value))) {
            return new Date(value) > new Date($(params).val());
        }
        return isNaN(value) && isNaN($(params).val())
            || (Number(value) > Number($(params).val()));
    }, 'Must be greater than start date.');
window.onload = function () {

    boxes = $('.col-sm-6');
    maxHeight = Math.max.apply(
        Math, boxes.map(function () {
            return $(this).height();
        }).get());
    boxes.height(maxHeight);

    $('.col-sm-12 .panel ').height(maxHeight / 2 - 22);//22 = 20 (bottom-margin) + 2 *1 (border)


    $(".select2").select2({placeholder: "Select price description(s)"});

    btnAddDateClicked = document.forms[0].elements["tourInfoCount"].value;
    toursPlacesCounter = document.forms[0].elements["tourPlacesCount"].value;
    var form = jQuery("#manageTourForm");
    var v = form.validate({
        errorClass: "text-danger",
        submitHandler: function (form) {
            form.submit();
        }});
    if (btnAddDateClicked == 0) {
        addTourInfo();
    }

    geocoder = new google.maps.Geocoder();

    //form elements
    var btnAddDate = document.getElementById("addDate");
    var btnGeocode = document.getElementById("geocode");
    var addrInput = document.getElementById("address");
    var mapDiv = document.getElementById("map");
    var saveAllBtn = document.getElementById("saveAll");
    var next1 = document.getElementById("next1");
    var next2 = document.getElementById("next2");
    var prev1 = document.getElementById("prev1");
    var prev2 = document.getElementById("prev2");

    prev1.onclick = function () {
        $("#tourCommon").collapse('show');
        $("#collapseTwo").collapse('hide');
        $("#collapseThree").collapse('hide');
    }

    next1.onclick = function () {
        if (v.form()) {
            $("#tourCommon").collapse('hide');
            $("#collapseTwo").collapse('show');
            $("#collapseThree").collapse('hide');
        }
    }
    //google map default settings
    var options = {
        zoom: 7,
        center: new google.maps.LatLng(37.09, -95.71),
        mapTypeId: google.maps.MapTypeId.ROADMAP
    }

    //create a map
    map = new google.maps.Map(mapDiv, options);

    next2.onclick = function () {
        if (v.form()) {
            $("#tourCommon").collapse('hide');
            $("#collapseTwo").collapse('hide');
            $("#collapseThree").collapse('show');
        }
        google.maps.event.trigger(map, 'resize');
        map.setCenter(center);
        window.scrollTo(0, 0);
    }

    prev2.onclick = function () {
        $("#tourCommon").collapse('hide');
        $("#collapseTwo").collapse('show');
        $("#collapseThree").collapse('hide');
    }


    if (toursPlacesCounter > 0) {
        setTourPlaces();
    }

    //add new tourInfo date
    btnAddDate.onclick = function () {
        addTourInfo();
    }

    //find place (button add place clicked)
    btnGeocode.onclick = function () {
        getCoordinates(addrInput.value);
    }

    //find place (click on map)
    google.maps.event.addListener(map, 'click', function (event) {
        getPlaceByCoordinates(event.latLng);
    });

    //click on save button
    saveAllBtn.onclick = function () {
        if (v.form()) {
            $("#tourCommon").collapse('show');
            $("#collapseTwo").collapse('show');
            $("#collapseThree").collapse('show');
        }
    }
    var $prices = $("input[id$='price']");
    $prices.attr("min", 0);


    setCollapses(false, true, true);
}

function setCollapses(coll1, coll2, coll3) {
    $("#tourCommon").collapse({
        toggle: coll1
    });

    $("#collapseTwo").collapse({
        toggle: coll2
    });

    $("#collapseThree").collapse({
        toggle: coll3
    });
}

function addTourInfo() {
    var id;
    var name;
    var forElement;

    var dataDiv = document.getElementById("dateBlock");
    var tourInfo = document.createElement("div");
    tourInfo.setAttribute("class", "form-group col-md-4 col-sm-6 ");
    var tourInfoPanel = document.createElement("div");
    tourInfoPanel.setAttribute("class", "panel panel-default col-sm-12");
    tourInfo.appendChild(tourInfoPanel);
    var myDiv = document.createElement("div");
    myDiv.setAttribute("class", "panel-body");
    tourInfoPanel.appendChild(myDiv);
    var closeIcon = document.createElement("a");
    closeIcon.setAttribute("class", "pull-right");
    closeIcon.setAttribute("href", "javascript:void(0)");
    var closeIconSpan = document.createElement("span");
    closeIconSpan.setAttribute("class", "glyphicon glyphicon-remove ");
    closeIcon.appendChild(closeIconSpan);
    closeIcon.onclick = function () {
        tourInfo.parentElement.removeChild(tourInfo);
    };
    myDiv.appendChild(closeIcon);
    var startDateDiv = document.createElement("div");
    startDateDiv.setAttribute("class", "form-group");

    //label for start date
    var dateNodeLabel = document.createElement("label");
    forElement = "tourInfo" + btnAddDateClicked + ".startDate";
    dateNodeLabel.setAttribute("for", forElement);
    dateNodeLabel.textContent = "Start date";
    startDateDiv.appendChild(dateNodeLabel);

    //input field for start date
    var dateNode = document.createElement("input");
    id = "tourInfo" + btnAddDateClicked + ".startDate";
    name = "tourInfo[" + btnAddDateClicked + "].startDate";
    dateNode.setAttribute("id", id);
    dateNode.setAttribute("name", name);
    dateNode.setAttribute("class", "form-control");
    dateNode.setAttribute("type", "date");
    dateNode.setAttribute("date", "true");
    dateNode.setAttribute("required", "true");
    dateNode.setAttribute("placeholder", "Tour start date");
    dateNode.setAttribute("readonly", "true");
    startDateDiv.appendChild(dateNode);
    myDiv.appendChild(startDateDiv);

    var endDateDiv = document.createElement("div");
    endDateDiv.setAttribute("class", "form-group");

    //label for end date
    var endDateNodeLabel = document.createElement("label");
    forElement = "tourInfo" + btnAddDateClicked + ".endDate";
    endDateNodeLabel.setAttribute("for", forElement);
    endDateNodeLabel.textContent = "End date";
    endDateDiv.appendChild(endDateNodeLabel);

    //input field for tourInfo endDate
    var termNode = document.createElement("input");
    id = "tourInfo" + btnAddDateClicked + ".endDate";
    name = "tourInfo[" + btnAddDateClicked + "].endDate"
    termNode.setAttribute("id", id);
    termNode.setAttribute("name", name);
    termNode.setAttribute("class", "form-control");
    termNode.setAttribute("type", "text");
    termNode.setAttribute("date", "true");
    termNode.setAttribute("required", "true");
    termNode.setAttribute("placeholder", "Tour endDate");
    termNode.setAttribute("readonly", "true");
    endDateDiv.appendChild(termNode);
    myDiv.appendChild(endDateDiv);

    var discountDiv = document.createElement("div");
    discountDiv.setAttribute("class", "form-group");

    //label for end date
    var discountNodeLabel = document.createElement("label");
    forElement = "tourInfo" + btnAddDateClicked + ".discount";
    discountNodeLabel.setAttribute("for", forElement);
    discountNodeLabel.textContent = "Discount";
    discountDiv.appendChild(discountNodeLabel);

    //input field for tourInfo discount
    var discountNode = document.createElement("input");
    id = "tourInfo" + btnAddDateClicked + ".discount";
    name = "tourInfo[" + btnAddDateClicked + "].discount"
    discountNode.setAttribute("id", id);
    discountNode.setAttribute("name", name);
    discountNode.setAttribute("class", "form-control ");
    discountNode.setAttribute("range", "0,100");
    discountNode.setAttribute("required", "true");
    discountNode.setAttribute("type", "text");
    discountNode.setAttribute("number", "true");
    discountNode.setAttribute("placeholder", "Tour discount");
    discountDiv.appendChild(discountNode);
    myDiv.appendChild(discountDiv);

    dataDiv.appendChild(tourInfo);
    id = "tourInfo" + btnAddDateClicked + ".startDate";
    jQuery(function () {
        jQuery(document.getElementById(id)).datepicker({
            dateFormat: "yy-mm-dd",
            changeMonth: true,
            numberOfMonths: 1
        });
    });
    var id2 = id;
    id = "tourInfo" + btnAddDateClicked + ".endDate";
    jQuery(document.getElementById(id)).attr("greaterThan", "#" + id2.replace(".", "\\."));
    jQuery(function () {
        jQuery(document.getElementById(id)).datepicker({
            dateFormat: "yy-mm-dd",
            changeMonth: true,
            numberOfMonths: 1
        });
    });
    btnAddDateClicked++;
}


function addPlace(markerNumber) {
    var id;
    var name;
    var forElement;
    //get div for toursPlaces
    var placeDiv = document.getElementById("placeBlock");

    //set place div view
    var newPlaceContainer = document.createElement("div");
    id = "place" + toursPlacesCounter;
    newPlaceContainer.setAttribute("id", id);
    newPlaceContainer.setAttribute("class", "col-md-6");
    newPlaceContainer.height = maxHeight / 2 - 22;
//Add panel and panel body and close icon
    var placePanel = document.createElement("div");
    placePanel.setAttribute("class", "panel panel-default col-sm-12");
    newPlaceContainer.appendChild(placePanel);
    var newPlaceDiv = document.createElement("div");
    newPlaceDiv.setAttribute("class", "panel-body");
    placePanel.appendChild(newPlaceDiv);
    var closeIcon = document.createElement("a");
    closeIcon.setAttribute("class", "pull-right");
    closeIcon.setAttribute("href", "javascript:void(0)");
    var closeIconSpan = document.createElement("span");
    closeIconSpan.setAttribute("class", "glyphicon glyphicon-remove ");
    closeIcon.appendChild(closeIconSpan);
    closeIcon.onclick = function () {
        setMarkerAction(markerNumber);
    };
    newPlaceDiv.appendChild(closeIcon);


    //hiden element for place country
    var countryNode = document.createElement("input");
    id = "toursPlaces" + toursPlacesCounter + ".country";
    name = "toursPlaces[" + toursPlacesCounter + "].country";
    countryNode.setAttribute("id", id);
    countryNode.setAttribute("name", name);
    countryNode.setAttribute("type", "hidden");
    newPlaceDiv.appendChild(countryNode);

    //hidden element for place Lat coordinate
    var latNode = document.createElement("input");
    id = "toursPlaces" + toursPlacesCounter + ".geoHeight";
    name = "toursPlaces[" + toursPlacesCounter + "].geoHeight";
    latNode.setAttribute("id", id);
    latNode.setAttribute("name", name);
    latNode.setAttribute("type", "hidden");
    newPlaceDiv.appendChild(latNode);

    //hidden element for place Lng coordinate
    var lngNode = document.createElement("input");
    id = "toursPlaces" + toursPlacesCounter + ".geoWidth";
    name = "toursPlaces[" + toursPlacesCounter + "].geoWidth";
    lngNode.setAttribute("id", id);
    lngNode.setAttribute("name", name);
    lngNode.setAttribute("type", "hidden");
    newPlaceDiv.appendChild(lngNode);

    var nameLabelNode = document.createElement("label");
    forElement = "toursPlaces" + toursPlacesCounter + ".name";
    nameLabelNode.setAttribute("for", forElement);
    nameLabelNode.textContent = "Place name";

    var nameNode = document.createElement("input");
    id = "toursPlaces" + toursPlacesCounter + ".name";
    name = "toursPlaces[" + toursPlacesCounter + "].name";
    nameNode.setAttribute("id", id);
    nameNode.setAttribute("name", name);
    nameNode.setAttribute("type", "text");
    nameNode.setAttribute("required", "true");
    nameNode.setAttribute("class", "form-control");
    newPlaceDiv.appendChild(nameLabelNode);
    newPlaceDiv.appendChild(nameNode);

    var descLabelNode = document.createElement("label");
    forElement = "toursPlaces" + toursPlacesCounter + ".description";
    descLabelNode.setAttribute("for", forElement);
    descLabelNode.textContent = "Place description";

    var descriptNode = document.createElement("textarea");
    id = "toursPlaces" + toursPlacesCounter + ".description";
    name = "toursPlaces[" + toursPlacesCounter + "].description";
    descriptNode.setAttribute("id", id);
    descriptNode.setAttribute("name", name);
    descriptNode.setAttribute("class", "form-control");
    newPlaceDiv.appendChild(descLabelNode);
    newPlaceDiv.appendChild(descriptNode);

    var priceLabelNode = document.createElement("label");
    forElement = "toursPlaces" + toursPlacesCounter + ".price";
    priceLabelNode.setAttribute("for", forElement);
    priceLabelNode.textContent = "Price";

    var placePriceNode = document.createElement("input");
    id = "toursPlaces" + toursPlacesCounter + ".price";
    name = "toursPlaces[" + toursPlacesCounter + "].price";
    placePriceNode.setAttribute("id", id);
    placePriceNode.setAttribute("name", name);
    placePriceNode.setAttribute("type", "text");
    placePriceNode.setAttribute("class", "form-control");
    placePriceNode.setAttribute("number", "true");
    placePriceNode.setAttribute("required", "true");
    newPlaceDiv.appendChild(priceLabelNode);
    newPlaceDiv.appendChild(placePriceNode);

    placeDiv.appendChild(newPlaceContainer);
    toursPlacesCounter++;
}

function getCoordinates(address) {
    geocoder.geocode({ 'address': address}, function (results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            isSuccess = true;
            map.setCenter(results[0].geometry.location);
            marker = new google.maps.Marker({
                map: map,
                position: results[0].geometry.location
            });
            markers.push(marker);
            var length = markers.length;
            google.maps.event.addListener(markers[length - 1], 'click', function () {
                setMarkerAction(length - 1);
            });

            var data = results[0].address_components;
            var lat = results[0].geometry.location.lat();
            var lng = results[0].geometry.location.lng();
            var locality = getGooglePlaceElement(data, "locality");
            var country = getGooglePlaceElement(data, "country");
            addPlace();
            setPlaceData(locality, country, lat, lng);
        } else {
            alert('Geocode was not successful for the following reason: ' + status);
        }
    });
}

function getPlaceByCoordinates(location) {
    marker = new google.maps.Marker({
        position: location,
        map: map
    });
    markers.push(marker);
    var length = markers.length;
    google.maps.event.addListener(markers[length - 1], 'click', function () {
        setMarkerAction(length - 1);
    });
    geocoder.geocode({'latLng': location}, function (results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            if (results[1]) {
                var data = results[1].address_components;
                var lat = location.lat();
                var lng = location.lng();
                var locality = getGooglePlaceElement(data, "locality");
                var country = getGooglePlaceElement(data, "country");
                addPlace(length - 1);
                setPlaceData(locality, country, lat, lng);
            }
        }
    });
}

function setMarkerAction(number) {

    console.log("Trying to delete", number);
    markers[number].setMap(null);
    var id = "place" + number;
    var markerPlace = document.getElementById(id);
    console.log("Others", "#" + id, $("#" + id), $("#" + id).nextAll().find("input"));
    $("#" + id).nextAll().find("input").each(function (i, item) {
        var p = /(\d+)/;
        var match = parseInt(p.exec(item.id));
        console.log("Replacing value", match);
        item.id = item.id.replace(match, match - 1);
        item.name = item.name.replace(match, match - 1);
    })
    markerPlace.parentElement.removeChild(markerPlace);

}

function getGooglePlaceElement(data, element) {
    for (i = 0; i < data.length; i++) {
        if (data[i].hasOwnProperty("types")) {
            var dataTypes = data[i].types;
            for (j = 0; j < dataTypes.length; j++) {
                if (dataTypes[j] == element) {
                    return data[i].long_name;
                }
            }
        }
    }
}


function setPlaceData(locality, country, lat, lng) {
    document.getElementById("toursPlaces" + (toursPlacesCounter - 1) + ".name").setAttribute("value", locality);
    document.getElementById("toursPlaces" + (toursPlacesCounter - 1) + ".country").setAttribute("value", country);
    document.getElementById("toursPlaces" + (toursPlacesCounter - 1) + ".geoHeight").setAttribute("value", lat);
    document.getElementById("toursPlaces" + (toursPlacesCounter - 1) + ".geoWidth").setAttribute("value", lng);
}

var setRightZoomLevel = function (map, minlat, maxlat, minlng, maxlng, ctrlat, ctrlng) {
    var mapdisplay = 600;
    var interval = 0;

    if ((maxlat - minlat) > (maxlng - minlng)) {
        interval = (maxlat - minlat) / 2;
        minlng = ctrlng - interval;
        maxlng = ctrlng + interval;
    } else {
        interval = (maxlng - minlng) / 2;
        minlat = ctrlat - interval;
        maxlat = ctrlat + interval;
    }

    var dist = (6371 * Math.acos(Math.sin(minlat / 57.2958) * Math.sin(maxlat / 57.2958)
        + (Math.cos(minlat / 57.2958) * Math.cos(maxlat / 57.2958) * Math.cos((maxlng / 57.2958) - (minlng / 57.2958)))));

    var zoom = Math.floor(8 - Math.log(1.6446 * dist / Math.sqrt(2 * (mapdisplay * mapdisplay))) / Math.log(2));
    console.log("Zoom", zoom);
    if (zoom > 7) {
        zoom = 7;
    }
    map.setZoom(zoom);


};
function setTourPlaces() {
    var minlat;
    var maxlat;
    var minlng;
    var maxlng;
    for (var i = 0; i < toursPlacesCounter; i++) {
        var id = "toursPlaces" + i + ".geoHeight";
        var height = document.forms[0].elements[id].value;
        id = "toursPlaces" + i + ".geoWidth";
        var width = document.forms[0].elements[id].value;
        var latLng = new google.maps.LatLng(height, width, false);
        marker = new google.maps.Marker({
            position: latLng,
            map: map
        });
        if (i == 0) {
            minlat = height;
            maxlat = height;
            minlng = width;
            maxlng = width;

        }
        minlat = Math.min(minlat, height);
        maxlat = Math.max(maxlat, height);
        minlng = Math.min(minlng, width);
        maxlng = Math.max(maxlng, width);

        markers.push(marker);
        var length = markers.length;
        id = id.replace(".", "\\.");
        var panel = $("#" + id).parent().parent().parent();

        console.log("Element", "#" + id);
        console.log("panel", panel);
        console.log("MarkeAction", length - 1);
        console.log("ashka", panel.find("a"));
        a = function (length) {
            var markerInd = length - 1;
            panel.find("a").click(function () {
                //TODO:repair
                console.log("Trying to set delete marker .Length", markerInd);

                setMarkerAction(markerInd);

            });
        }
        a(length);
        panel.find("a").attr("marker", length - 1);
        google.maps.event.addListener(markers[length - 1], 'click', function () {
            setMarkerAction(length - 1);
        });
    }

    var ctrlng = (maxlng + minlng) / 2;
    var ctrlat = (minlat + maxlat) / 2;
    console.log("Center", ctrlat, ctrlng);
    setRightZoomLevel(map, minlat, maxlat, minlng, maxlng, ctrlat, ctrlng);
    center = new google.maps.LatLng(ctrlat, ctrlng, false);
    paintRoute(markers, $("#travelType").val());

    console.log(markers.length);
}
function paintRoute(places, travelType) {
    var rendererOptions = {
        map: map,
        suppressMarkers: true
    };
    var directionsDisplay = new google.maps.DirectionsRenderer(rendererOptions);
    var directionsService = new google.maps.DirectionsService();

    var start = new google.maps.LatLng(places[0].geoHeight, places[0].geoWidth);
    var end = new google.maps.LatLng(places[places.length - 1].geoHeight, places[places.length - 1].geoWidth)
    var wayPoints = [];
    for (var i = 1; i < places.length - 1; i++) {
        var location = new google.maps.LatLng(places[i].geoHeight, places[i].geoWidth);
        wayPoints.push({location: location});
    }

    var travelMode;
    switch (travelType) {
        case 'bycicling':
            travelMode = google.maps.TravelMode.BICYCLING;
            break;
        case 'walking':
            travelMode = google.maps.TravelMode.WALKING;
            break;
        default:
            travelMode = google.maps.TravelMode.DRIVING;
    }

    request = {
        origin: start,
        destination: end,
        waypoints: wayPoints,
        travelMode: travelMode
    };
    directionsService.route(request, function (response, status) {
        if (status == google.maps.DirectionsStatus.OK) {
            directionsDisplay.setDirections(response);
        }
    });
}

