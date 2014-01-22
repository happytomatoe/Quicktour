$(window).on('load', function () {
    $('.selectpicker').selectpicker();
});

$(document).ready(function(){
    getData();
    getPage(0);
});

/**
 * Function create AJAX request for the server with parameters tour identificator which refer
 * for the data and on success call functions initialize widget and googleMap
 */
function getData(){
    var urlSplitted=window.location.pathname.split("/");
    var tourId=urlSplitted[urlSplitted.length-1];
    $.ajax({
        url: tourId+"/getData",
        data: {id:tourId},
        type: "POST",
        timeout: 10000,
        success:function (map){
            var places=map.places;
            var travelType=map.travelType;
            var comments=map.comments;
            getPhotos(places[0].geoHeight,places[0].geoWidth,document.getElementById('filter').value);
            initializeMap(places,travelType);

        },
        error: function (xhr, status) {},
        complete: function (data) {}
    });
}
function initializeMap(places,travelType) {
    var center = new google.maps.LatLng(places[0].geoHeight, places[0].geoWidth);
    var mapOptions = {
        zoom: 4,
        center: center
    } ;
    tourMap = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
    for(var i=0; i<places.length; i++){
        setMarkers(places[i].geoHeight, places[i].geoWidth, places[i].name);
    }
    paintRoute(places,travelType);
}

function paintRoute(places,travelType) {
    var rendererOptions = {
        map: tourMap,
        suppressMarkers : true
    };
    var directionsDisplay=new google.maps.DirectionsRenderer(rendererOptions);
    var directionsService = new google.maps.DirectionsService();

    var start = new google.maps.LatLng(places[0].geoHeight, places[0].geoWidth);
    var end = new google.maps.LatLng(places[places.length-1].geoHeight, places[places.length-1].geoWidth)
    var wayPoints=[];
    for (var i=1; i<places.length-1; i++){
        var location= new google.maps.LatLng(places[i].geoHeight, places[i].geoWidth);
        wayPoints.push({location:location});
    }

    var travelMode;
    switch(travelType){
        case 'bycicling':
            travelMode=google.maps.TravelMode.BICYCLING;
            break;
        case 'walking':
            travelMode=google.maps.TravelMode.WALKING;
            break;
        default:
            travelMode=google.maps.TravelMode.DRIVING;
    }

    request = {
        origin: start,
        destination: end,
        waypoints : wayPoints,
        travelMode: travelMode
    };
    directionsService.route(request, function(response, status) {
        if (status == google.maps.DirectionsStatus.OK) {
            directionsDisplay.setDirections(response);
        }
    });
}

function setMarkers(latitude,longitude,name) {
    var markerPosition = new google.maps.LatLng(latitude, longitude);
    var marker = new google.maps.Marker({
        position: markerPosition,
        title: name,
        map: tourMap
    });
    google.maps.event.addListener(marker, 'click', function(event) {
        var tag = document.getElementById("filter").value;
        getPhotos(latitude,longitude,tag);
    });

}

function getPhotos(latitude,longitude,tag){

    var component= document.getElementById("filter");
    component.onchange=function(){
        tag=component.value;
        getPhotos(latitude,longitude,tag);
    };
    var displacement=0.3; /*12.365 sq.km, 112 km*/
    var filter = {
        'tag': tag,
        'rect': {'sw': {'lat': latitude-displacement, 'lng': longitude-displacement}, 'ne': {'lat': latitude+displacement, 'lng': longitude+displacement}}
    };
    var options = {'width': 400, 'height': 350, croppedPhotos: panoramio.Cropping.TO_FILL };
    var widget = new panoramio.PhotoWidget('photoWidget', filter, options);
    widget.setPosition(0);

}

function addNewComment(){
    var fieldValue = document.getElementById('textOfComment').value;
    var urlSplitted=window.location.pathname.split("/");
    var tourId=urlSplitted[urlSplitted.length-1];
    $.ajax({
        url: tourId+"/saveComment",
        data:  {tourId: tourId, comment: fieldValue},
        type: "POST",
        timeout: 10000,
        success:function (data){
            document.getElementById('comments').innerHTML=data;
            clearField();
        },
        error: function (xhr, status) {},
        complete: function (data){
            var arr = document.getElementById("comments").getElementsByTagName('script')
            for (var n = 0; n < arr.length; n++)
                eval(arr[n].innerHTML)
        }
    })
}

function clearField(){
    document.getElementById('textOfComment').value=null;
}

function getPage(page){
    var urlSplitted=window.location.pathname.split("/");
    var tourId=urlSplitted[urlSplitted.length-1];

    $.ajax({
        url: tourId+"/getComments",
        data: {id:tourId, page: page},
        type: "POST",
        timeout: 10000,
        success:function (map){
            document.getElementById('comments').innerHTML=map;
        },
        error: function (xhr, status) {},
        complete: function (data) {
            var arr = document.getElementById("comments").getElementsByTagName('script')
            for (var n = 0; n < arr.length; n++)
                eval(arr[n].innerHTML)
        }
    });
}

function prepareText(commentId){

    var buttonSave = document.getElementById('buttonSave');
    var buttonEdit = document.getElementById('buttonEdit');
    buttonSave.style.display = 'none';
    buttonEdit.style.display = 'inline';
    var textArea =  document.getElementById('textOfComment');
    var comment = document.getElementById(commentId).innerHTML.replace(/<br>/g,"\n");

    textArea.focus();
    window.scrollBy(0,50);
    textArea.value = comment.trim();
    buttonEdit.onclick=function(){editComment(commentId)}
}
function editComment(commentId){
    var urlSplitted = window.location.pathname.split("/");
    var tourId = urlSplitted[urlSplitted.length-1];
    var comment = document.getElementById('textOfComment').value;
    $.ajax({
        url: tourId+"/editComment",
        data: {tourId:tourId, comment: comment, commentId: commentId},
        type: "POST",
        timeout: 10000,
        success:function (map){
            document.getElementById('comments').innerHTML=map;
            var editedComment = document.getElementById(commentId);
            editedComment.scrollIntoView();
            window.scrollBy(0,-100);
            clearField();

        },
        error: function (xhr, status) {},
        complete: function (data) {
            var arr = document.getElementById("comments").getElementsByTagName('script')
            for (var n = 0; n < arr.length; n++)
                eval(arr[n].innerHTML)
        }
    });
}
function answerUser(userName){
    var textArea = document.getElementById('textOfComment');
    textArea.focus();
    window.scrollBy(0,50);
    textArea.value = userName+', ';

}


