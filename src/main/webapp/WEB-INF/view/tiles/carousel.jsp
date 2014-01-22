<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row">
    <div class="col-md-3">
        <div class="panel panel-success">
            <div class="panel-heading">
                Filter by...
            </div>
            <div class="panel-body">
                <form action="/filter/results/0" method="post">
                    <div class="form-group">
                        <label for="country">Country</label>
                        <div class="input-group">
                            <input type="text" class="form-control" id="country" name="country" list="countries">
                            <datalist id="countries">
                                <c:forEach items="${countries}" var="country">
                                <option value="${country}">
                                    </c:forEach>
                            </datalist>
                            <div class="input-group-btn">
                                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                    <span class="caret"></span>
                                </button>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="place">Place</label>
                        <div class="input-group">
                            <input type="text" class="form-control" id="place" name="place" list="places">
                            <datalist id="places">
                                <c:forEach items="${places}" var="place">
                                    <option value="${place}">
                                </c:forEach>
                            </datalist>
                            <div class="input-group-btn">
                                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                    <span class="caret"></span>
                                </button>
                                <ul class="dropdown-menu" id="placesDropDown">
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-md-6">
                            <label for="minDate">From date</label>
                            <input type="text" class="form-control" id="minDate" name="minDate">
                        </div>
                        <div class="form-group col-md-6">
                            <label for="maxDate">To date</label>
                            <input type="text" class="form-control" id="maxDate" name="maxDate">
                        </div>
                        <div class="form-group col-md-6">
                            <label for="minPrice">Min price</label>
                            <input type="text" class="form-control" id="minPrice" name="minPrice">
                        </div>
                        <div class="form-group col-md-6">
                            <label for="maxPrice">Max price</label>
                            <input type="text" class="form-control" id="maxPrice" name="maxPrice">
                        </div>
                    </div>
                    <input type="submit" class="btn btn-success pull-left" value="Filter">
                </form>
            </div>
        </div>
    </div>

    <div class=" col-md-9">
        <div id="myCarousel" class="carousel slide" data-ride="carousel" style="height: 500px">
            <ol class="carousel-indicators">
                <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                <li data-target="#myCarousel" data-slide-to="1"></li>
                <li data-target="#myCarousel" data-slide-to="2"></li>
            </ol>
            <div class="carousel-inner">
                <c:forEach items="${famousTours}" var="tour" varStatus="i">
                    <s:url value="/tour/{id}" var="torsPageUrl">
                        <s:param name="id" value="${tour.tourId}"/>
                    </s:url>
                    <c:set var="itemClass" value="item" />
                    <c:if test="${i.index==0}">
                        <c:set var="itemClass" value="item active" />
                    </c:if>
                    <div class="${itemClass}">
                        <img src="/images/<c:url value="${tour.mainPhotoUrl}"/>" style="height: 500px; width: 100%">
                        <div class="container">
                            <div class="carousel-caption">
                                <h1>${tour.name}</h1>
                                <p><a class="btn btn-success" href="${torsPageUrl}" role="button">Learn more</a></p>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <a class="left carousel-control" href="#myCarousel" data-slide="prev"><span class="glyphicon glyphicon-chevron-left"></span></a>
            <a class="right carousel-control" href="#myCarousel" data-slide="next"><span class="glyphicon glyphicon-chevron-right"></span></a>
        </div>
    </div>
</div>

<script>
    var country = document.getElementById("country");
    var placeInput = document.getElementById("place");

    jQuery(function() {
        jQuery("#minDate").datepicker({
            //defaultDate: "+1w",
            dateFormat: "yy-mm-dd",
            changeMonth: true,
            numberOfMonths: 1,
            onClose: function( selectedDate ) {
                jQuery( "#maxDate" ).datepicker( "option", "minDate", selectedDate );
            }
        });
        jQuery("#maxDate").datepicker({
            //defaultDate: "+1w",
            dateFormat: "yy-mm-dd",
            changeMonth: true,
            numberOfMonths: 1,
            onClose: function( selectedDate ) {
                $( "#minDate" ).datepicker( "option", "maxDate", selectedDate );
            }
        });
    })

    country.onchange = function() {
        getPlacesForCountry()
    }

    function getPlacesForCountry() {
        jQuery.ajax ({
            url: "/placesByCountry",
            data: {country: country.value},
            type: "POST",
            timeout: 4000,
            success: function(map) {
                var places = map.places;
                var placeUl = document.getElementById("placesDropDown");
                while (placeUl.firstChild) {
                    placeUl.removeChild(placeUl.firstChild);
                }
                places.forEach(function(place) {
                    setPlacesList(place);
                })
            },
            error: function(xhr, status) {},
            complete: function(data) {
            }
        })
    }

    function setPlacesList(place) {
        var placeUl = document.getElementById("placesDropDown");
        var li = document.createElement("li");
        var a = document.createElement("a");
        a.textContent = place;
        a.onclick = function() {
            placeInput.value = this.textContent;
        }
        li.appendChild(a);
        placeUl.appendChild(li);
    }
</script>