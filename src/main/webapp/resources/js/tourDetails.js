$(window).on('load', function () {
    $('.selectpicker').selectpicker();
});
var textBoxId = 'new_message';
var currentPage = 0;
var username;
var url = window.location.href;
var cutUrl = url.substring(0, url.lastIndexOf("/"));
var baseUrl = cutUrl.substring(0, cutUrl.lastIndexOf("/") + 1);
var commentBox;
var regex = /\d/;
var tourId = regex.exec(url.substring(url.lastIndexOf("/") + 1, url.length))[0];
var editor;
$(document).ready(function () {
    getData();
    commentBox = $(".comment-box");
    getPage(0);
    var $username = $("#username");
    if ($username.length > 0) {
        username = $username.val();
        CKEDITOR.replace(textBoxId);
        editor = CKEDITOR.instances[textBoxId];

    }

    $("#description").expander({slicePoint: 600});

});

/**
 * Function create AJAX request for the server with parameters tour identificator which refer
 * for the data and on success call functions initialize widget and googleMap
 */
function getData() {
    var urlSplitted = window.location.pathname.split("/");
    var tourId = urlSplitted[urlSplitted.length - 1];
    $.ajax({
        url: tourId + "/getData",
        data: {id: tourId},
        type: "POST",
        timeout: 4000,
        success: function (map) {
            var places = map.places;
            var travelType = map.travelType;
            getPhotos(places[0].geoHeight, places[0].geoWidth, document.getElementById('filter').value);
            initializeMap(places, travelType);

        },
        error: function (xhr, status) {
            alert("Error")
        }
    });
}
function initializeMap(places, travelType) {
    var center = new google.maps.LatLng(places[0].geoHeight, places[0].geoWidth);
    var mapOptions = {
        zoom: 4,
        center: center
    };
    tourMap = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
    for (var i = 0; i < places.length; i++) {
        setMarkers(places[i].geoHeight, places[i].geoWidth, places[i].name);
    }
    paintRoute(places, travelType);
}

function paintRoute(places, travelType) {
    var rendererOptions = {
        map: tourMap,
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

function setMarkers(latitude, longitude, name) {
    var markerPosition = new google.maps.LatLng(latitude, longitude);
    var marker = new google.maps.Marker({
        position: markerPosition,
        title: name,
        map: tourMap
    });
    google.maps.event.addListener(marker, 'click', function (event) {
        var tag = document.getElementById("filter").value;
        getPhotos(latitude, longitude, tag);
    });

}

function getPhotos(latitude, longitude, tag) {

    var component = document.getElementById("filter");
    component.onchange = function () {
        tag = component.value;
        getPhotos(latitude, longitude, tag);
    };
    var displacement = 0.3;
    /*12.365 sq.km, 112 km*/
    var filter = {
        'tag': tag,
        'rect': {'sw': {'lat': latitude - displacement, 'lng': longitude - displacement}, 'ne': {'lat': latitude + displacement, 'lng': longitude + displacement}}
    };
    var options = {'width': 400, 'height': 350, croppedPhotos: panoramio.Cropping.TO_FILL };
    var widget = new panoramio.PhotoWidget('photoWidget', filter, options);
    widget.setPosition(0);

}

function addNewComment() {
    var fieldValue = editor.getData();
    console.log(fieldValue);
    var urlSplitted = window.location.pathname.split("/");
    var tourId = urlSplitted[urlSplitted.length - 1];
    var data = { content: fieldValue};
    var commentIdVal = $("input#commentId").val();
    var parentIdVal = $("input#parent").val();
    if (commentIdVal !== "") {
        data['id'] = commentIdVal;
    }
    if (parentIdVal !== "") {
        data['parent.id'] = parentIdVal;
    }

    $.ajax({
        url: tourId + "/saveComment",
        data: data,
        type: "POST",
        timeout: 10000,
        success: function (data) {
            if (parentIdVal !== "") {
                cancelReply();
                console.log("Create comment", data);
                createComment(data, parentIdVal);

            }
            else if (commentIdVal !== "") {
                editComment();
            } else {
                getLastPage();

            }
        },
        error: function (xhr, status) {
            alert("Error");
        }
    });
    return false;
}

function clearField() {
    document.getElementById('textOfComment').value = null;
}

function createComment(comment, parentId) {
    var commentBox = $("#comment" + parentId).parent().parent();
    var childRow = commentBox.next();
    if (childRow.attr("class").indexOf("col-md-offset-2") == -1) {
        childRow = $("<div class='col-md-offset-2 row'></div>");
        commentBox.append(childRow);
    }
    commentBox = childRow;
    var date = new Date(comment.commentDate);
    console.log("Create comment .Comment box", commentBox);
    var selector = '<div class="comment row">\
                <div class="col-md-2">\
                    <a href="' + baseUrl + 'user/' + comment.user.username + '" class="comment' + comment.id + '">\
                        <img class="img-rounded" width="100px"\
                        src="' + baseUrl + 'images/' + comment.user.photo.url + '"/>\
                        <h4 class="username">' + comment.user.username + '</h4>\
                    </a>\
                </div>\
                <div class="col-md-10">\
                    <div class="comment_content" id="comment' + comment.id + '">\
                    ' + comment.comment + '\
                    </div><a href="javascript:void(0)" onclick="editComment(' + comment.id + ')"><span class="glyphicon glyphicon-edit"></span> </a>' +
        '<a href="javascript:void(0)"  onclick="removeComment(' + comment.id + ')" ><span class="glyphicon glyphicon-remove"></span> </a>' +
        '<span class="date">' + date.toLocaleFormat('%d-%b-%Y') + '</span></div>\
            </div>';

    var $comment = $(selector);
    commentBox.append($comment);
    window.scroll(0, $comment.offset().top - 200);

}
function displayComment(content, commentBox) {

    $.each(content, function (i, comment) {
        var date = new Date(comment.commentDate);
        var selector = '<div class="comment row">\
                <div class="col-md-2">\
                    <a href="' + baseUrl + 'user/' + comment.user.username + '" class="comment' + comment.id + '">\
                        <img class="img-rounded" width="100px"\
                        src="' + comment.user.photo.url + '"/>\
                        <h4 class="username">' + comment.user.username + '</h4>\
                    </a>\
                </div>\
                <div class="col-md-10">\
                    <div class="comment_content" id="comment' + comment.id + '">\
                    ' + comment.content + '\
                    </div>';
        if (comment.user.username === username) {
            selector += '<a href="javascript:void(0)" onclick="editComment(' + comment.id + ')"><span class="glyphicon glyphicon-edit"></span> </a>' +
                '<a href="javascript:void(0)"  onclick="removeComment(' + comment.id + ')" ><span class="glyphicon glyphicon-remove"></span> </a>';
        }
        else {
            selector += '<a href="javascript:void(0)" onclick="reply(' + comment.id + ')"><span class="glyphicon glyphicon-comment"></span> Reply</a>';
        }
        selector += '<span class="date">' + date.toLocaleFormat('%d-%b-%Y') + '</span></div>\
            <hr></div>';
        commentBox.append($(selector));
        if (comment.children.length > 0) {
            var $div = $("<div class='col-md-offset-2 row'></div>");
            commentBox.append($div);
            displayComment(comment.children, $div);
        }
    });
}
function displayPagination(page) {
    currentPage = page.number;
    var firstPage = page.firstPage;
    var lastPage = page.lastPage;
    var totalPages = page.totalPages;
    var $first = $('<li><a href="javascript:void(0)" onclick="getPage(0)" >&laquo;</a></li>');
    var $last = $('<li><a href="javascript:void(0)" onclick="getLastPage()" >&raquo;</a></li>');
    var $pagination = $("#pagination");
    $pagination.html("");

    if (page.totalPages > 1) {
        $pagination.append($first);
        for (var i = -3; i < 3; i++) {
            var newPage = currentPage + i;
            if (newPage < 0 || newPage > totalPages - 1) {
                continue;
            }
            if (i == 0) {
                $pagination.append('<li class="active"><a href="javascript:void(0)"  >' + (newPage + 1) + '<span class="sr-only">(current)</span></a></li>')
            }
            else {
                $pagination.append('<li><a href="javascript:void(0)" onclick="getPage(' + newPage + ')" >' + (newPage + 1) + '</a></li>')
            }
        }
        $pagination.append($last);
    }
};
var clearCommentBox = function () {
    commentBox.html("");
};
function clearTextBox() {
    editor.setData("");
}
function getPage(page) {
    var cutUrl = url.substring(0, url.lastIndexOf("/") + 1) + tourId;
    var requestUrl = typeof page !== 'undefined' ? cutUrl + "/getComments" : cutUrl + "/getLastComments";
    var numberOfRecords = 5;
    $.ajax({
        url: requestUrl,
        data: { page: page, numberOfRecords: numberOfRecords},
        type: "POST",
        timeout: 10000,
        success: function (map) {
            clearCommentBox();
            if (map.content.length > 0) {
                commentBox.html("<h3>Comments:</h3>");
            }
            displayComment(map.content, commentBox);
            displayPagination(map);
            clearTextBox();
        },
        error: function (xhr, status) {
            alert("Error")
        }
    });

}
function getLastPage() {
    getPage();
}


function createTwoButtons(OkButtonLabel) {
    var $postButton = $("#post");
    $postButton.text(OkButtonLabel);
    if ($("#cancel").length > 0) {
        return;
    }
    var cancelButtonString = '<button id="cancel" type="button" class="btn btn-primary" onclick="';
    cancelButtonString += OkButtonLabel.toLowerCase() == "reply" ? 'cancelReply()' : 'cancelEdit()';
    cancelButtonString += '">Cancel</button>';
    console.log("Cancel button", cancelButtonString);
    $postButton.parent().prepend($(cancelButtonString));
}
function editComment(commentId) {
    console.log("Edit comment ", commentId);
    if (typeof commentId === 'undefined') {
        //Edit post
        var val = $("#commentId").val();
        var selector = "#comment" + val;
        var $comment = $(selector);
        $comment.html(editor.getData());
        var selector2 = ".comment" + val;
        console.log("Editing comment", selector2);
        window.scroll(0, $(selector2).offset().top - 200);
        clearTextBox();
        $("#commentId").val("");
        return;
    }
    editor.setData($("#comment" + commentId).html());
    $("#commentId").val(commentId);
    window.scroll(0, $('#editor').offset().top - 200);
    createTwoButtons("Edit");
}
function cancelEdit() {
    $('#cancel').remove();
    $("#commentId").val("");
    editor.setData("");
    var $postButton = $("#post");
    $postButton.text("Save");

}
function cancelReply() {
    cancelEdit();
    $("#parent").val("");
    editor.destroy();
    $("#editor").insertAfter($("#pagination"));
    editor = CKEDITOR.replace(textBoxId);


}
function removeComment(commentId) {
    $.ajax({
        url: cutUrl + "/" + tourId + "/removeComment",
        data: { id: commentId},
        type: "POST",
        timeout: 10000,
        success: function (map) {
            $("#comment" + commentId).parent().parent().remove();
            if ($(".comment").length == 0) {
                console.log("Reload page", currentPage - 1);
                getPage(currentPage - 1);
            }
        },
        error: function (xhr, status) {
            alert("Error")
        }
    });

}
function reply(commentId) {
    editor.destroy();
    $("#editor").insertAfter($("#comment" + commentId).parent().parent());
    editor = CKEDITOR.replace(textBoxId);
    if ($("#commentId").val() != "") {
        cancelEdit();
    }
    createTwoButtons("Reply");
    $("#parent").val(commentId);
}
