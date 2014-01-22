//markers on google map
var markers = [];

//tourInfo dates counter
var btnAddDateClicked = 0;

//tourInfo places counter
var placesCounter = 0;

window.onload = function () {
    btnAddDateClicked = document.forms[0].elements["tourInfoCount"].value;
    placesCounter = document.forms[0].elements["tourPlacesCount"].value;
    var v = jQuery("#manageTourForm").validate();
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

    prev1.onclick = function() {
        $("#tourCommon").collapse('show');
        $("#collapseTwo").collapse('hide');
        $("#collapseThree").collapse('hide');
    }

    next1.onclick = function() {
        if(v.form()) {
            $("#tourCommon").collapse('hide');
            $("#collapseTwo").collapse('show');
            $("#collapseThree").collapse('hide');
        }
    }

    next2.onclick = function() {
        if(v.form()) {
            $("#tourCommon").collapse('hide');
            $("#collapseTwo").collapse('hide');
            $("#collapseThree").collapse('show');
        }
    }

    prev2.onclick = function() {
        $("#tourCommon").collapse('hide');
        $("#collapseTwo").collapse('show');
        $("#collapseThree").collapse('hide');
    }

    //google map default settings
    var options = {
        zoom: 4,
        center: new google.maps.LatLng(37.09, -95.71),
        mapTypeId: google.maps.MapTypeId.ROADMAP
    }

    //create a map
    map = new google.maps.Map(mapDiv, options);

    if (placesCounter > 0) {
        setTourPlaces();
    }

    //add new tourInfo date
    btnAddDate.onclick = function() {
        addTourInfo();
    }

    //find place (button add place clicked)
    btnGeocode.onclick = function() {
        getCoordinates(addrInput.value);
    }

    //find place (click on map)
    google.maps.event.addListener(map, 'click', function(event) {
        getPlaceByCoordinates(event.latLng);
    });

    //click on save button
    saveAllBtn.onclick = function() {
        if (v.form()) {
            $("#tourCommon").collapse('show');
            $("#collapseTwo").collapse('show');
            $("#collapseThree").collapse('show');
        }
    }

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

    var myDiv = document.createElement("div");
    myDiv.setAttribute("class", "form-group col-md-4");

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
    discountNode.setAttribute("class", "form-control");
    discountNode.setAttribute("type", "text");
    discountNode.setAttribute("number", "true");
    discountNode.setAttribute("placeholder", "Tour discount");
    discountDiv.appendChild(discountNode);
    myDiv.appendChild(discountDiv);

    dataDiv.appendChild(myDiv);
    id = "tourInfo" + btnAddDateClicked + ".startDate";
    jQuery(function () {
        jQuery(document.getElementById(id)).datepicker({
            dateFormat: "yy-mm-dd",
            changeMonth: true,
            numberOfMonths: 1,
            onClose: function( selectedDate ) {
                $(document.getElementById("tourInfo" + btnAddDateClicked + ".endDate")).datepicker( "option",
                    id, selectedDate );
            }
        });
    });
    id = "tourInfo" + btnAddDateClicked + ".endDate";
    jQuery(function () {
        jQuery(document.getElementById(id)).datepicker({
            dateFormat: "yy-mm-dd",
            changeMonth: true,
            numberOfMonths: 1,
            onClose: function( selectedDate ) {
                $(document.getElementById("tourInfo" + btnAddDateClicked + ".startDate")).datepicker( "option",
                    id, selectedDate );
            }
        });
    });
    btnAddDateClicked++;
}

function addPlace() {
    var id;
    var name;
    var forElement;

    //get div for places
    var placeDiv = document.getElementById("placeBlock");

    //set place div view
    var newPlaceDiv = document.createElement("div");
    id = "place" + placesCounter;
    newPlaceDiv.setAttribute("id", id);
    newPlaceDiv.setAttribute("class", "col-md-6");

    //hiden element for place country
    var countryNode = document.createElement("input");
    id = "places" + placesCounter + ".country";
    name = "places[" + placesCounter + "].country";
    countryNode.setAttribute("id", id);
    countryNode.setAttribute("name", name);
    countryNode.setAttribute("type", "hidden");
    newPlaceDiv.appendChild(countryNode);

    //hidden element for place Lat coordinate
    var latNode = document.createElement("input");
    id = "places" + placesCounter + ".geoHeight";
    name = "places[" + placesCounter + "].geoHeight";
    latNode.setAttribute("id", id);
    latNode.setAttribute("name", name);
    latNode.setAttribute("type", "hidden");
    newPlaceDiv.appendChild(latNode);

    //hidden element for place Lng coordinate
    var lngNode = document.createElement("input");
    id = "places" + placesCounter + ".geoWidth";
    name = "places[" + placesCounter + "].geoWidth";
    lngNode.setAttribute("id", id);
    lngNode.setAttribute("name", name);
    lngNode.setAttribute("type", "hidden");
    newPlaceDiv.appendChild(lngNode);

    var nameLabelNode = document.createElement("label");
    forElement = "places" + placesCounter + ".name";
    nameLabelNode.setAttribute("for", forElement);
    nameLabelNode.textContent = "Place name";

    var nameNode = document.createElement("input");
    id = "places" + placesCounter + ".name";
    name = "places[" + placesCounter + "].name";
    nameNode.setAttribute("id", id);
    nameNode.setAttribute("name", name);
    nameNode.setAttribute("type", "text");
    nameNode.setAttribute("required", "true");
    nameNode.setAttribute("class", "form-control");
    newPlaceDiv.appendChild(nameLabelNode);
    newPlaceDiv.appendChild(nameNode);

    var descLabelNode = document.createElement("label");
    forElement = "places" + placesCounter + ".description";
    descLabelNode.setAttribute("for", forElement);
    descLabelNode.textContent = "Place description";

    var descriptNode = document.createElement("textarea");
    id = "places" + placesCounter + ".description";
    name = "places[" + placesCounter + "].description";
    descriptNode.setAttribute("id", id);
    descriptNode.setAttribute("name", name);
    descriptNode.setAttribute("class", "form-control");
    newPlaceDiv.appendChild(descLabelNode);
    newPlaceDiv.appendChild(descriptNode);

    var priceLabelNode = document.createElement("label");
    forElement = "places" + placesCounter + ".price";
    priceLabelNode.setAttribute("for", forElement);
    priceLabelNode.textContent = "Price";

    var placePriceNode = document.createElement("input");
    id = "places" + placesCounter + ".price";
    name = "places[" + placesCounter + "].price";
    placePriceNode.setAttribute("id", id);
    placePriceNode.setAttribute("name", name);
    placePriceNode.setAttribute("type", "text");
    placePriceNode.setAttribute("class", "form-control");
    placePriceNode.setAttribute("number", "true");
    placePriceNode.setAttribute("required", "true");
    newPlaceDiv.appendChild(priceLabelNode);
    newPlaceDiv.appendChild(placePriceNode);

    placeDiv.appendChild(newPlaceDiv);
    placesCounter++;
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
            google.maps.event.addListener(markers[length - 1], 'click', function() {
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
    google.maps.event.addListener(markers[length - 1], 'click', function() {
        setMarkerAction(length - 1);
    });
    geocoder.geocode({'latLng': location}, function(results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            if (results[1]) {
                var data = results[1].address_components;
                var lat = location.lat();
                var lng = location.lng();
                var locality = getGooglePlaceElement(data, "locality");
                var country = getGooglePlaceElement(data, "country");
                addPlace();
                setPlaceData(locality, country, lat, lng);
            }
        }
    });
}

function setMarkerAction(number) {
    markers[number].setMap(null);
    var id = "place" + number;
    var placeDiv = document.getElementById("placeBlock");
    var markerPlace = document.getElementById(id);
    placeDiv.removeChild(markerPlace);
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
    document.getElementById("places" + (placesCounter - 1) + ".name").setAttribute("value", locality);
    document.getElementById("places" + (placesCounter - 1) + ".country").setAttribute("value", country);
    document.getElementById("places" + (placesCounter - 1) + ".geoHeight").setAttribute("value", lat);
    document.getElementById("places" + (placesCounter - 1) + ".geoWidth").setAttribute("value", lng);
}

function setTourPlaces() {
    for (var i = 0; i < placesCounter; i++) {
        var id = "places" + i + ".geoHeight";
        var height = document.forms[0].elements[id].value;
        id = "places" + i + ".geoWidth";
        var width = document.forms[0].elements[id].value;
        var latLng = new google.maps.LatLng(height, width, false);
        if (i == 0) {
            map.setCenter(latLng);
        }
        marker = new google.maps.Marker({
            position: latLng,
            map: map
        });
        markers.push(marker);
        var length = markers.length;
        google.maps.event.addListener(markers[length - 1], 'click', function() {
            setMarkerAction(length - 1);
        });
    }
    console.log(markers);
}
