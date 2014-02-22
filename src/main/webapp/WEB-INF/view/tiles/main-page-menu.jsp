<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="panel-group" id="accordion">
    <div class="panel panel-success">
        <div class="panel-heading">
            Countries
        </div>
        <div class="panel-body">
            <ul class="nav nav-pills nav-stacked">
                <c:forEach var="i" begin="0" end="2">
                    <c:if test="${countriesWithPlaces[i].name != null}">
                        <s:url value="/country/{country}/0" var="countryUrl">
                            <s:param name="country" value="${countriesWithPlaces[i].name}"/>
                        </s:url>
                        <li>
                            <a href="${countryUrl}">${countriesWithPlaces[i].name}</a>
                        </li>
                    </c:if>
                </c:forEach>

                <li data-toggle="collapse" data-target="#demo">
                    <a>
                        More
                        <span class="caret"/>
                    </a>
                </li>

                <div id="demo" class="col-md-12">
                    <c:forEach var="i" begin="3" end="${fn:length(countriesWithPlaces)}">
                    <c:if test="${countriesWithPlaces[i].name != null}">
                            <s:url value="/country/{country}/0" var="countryUrl">
                                <s:param name="country" value="${countriesWithPlaces[i].name}"/>
                            </s:url>
                            <li>
                                <a href="${countryUrl}">${countriesWithPlaces[i].name}</a>
                            </li>
                        </c:if>
                    </c:forEach>
                </div>
                <script>
                    jQuery("#demo").collapse('hide');
                </script>
            </ul>
        </div>
    </div>

    <div class="panel panel-success">
        <div class="panel-heading">
            Places
        </div>
        <div class="panel-body">
            <ul class="nav nav-pills nav-stacked">
                <c:forEach var="i" begin="0" end="2">
                    <c:if test="${places[i] != null}">
                        <s:url value="/place/{place}/0" var="placeUrl">
                            <s:param name="place" value="${places[i]}"/>
                        </s:url>
                        <li>
                            <a href="${placeUrl}">${places[i]}</a>
                        </li>
                    </c:if>
                </c:forEach>

                <li data-toggle="collapse" data-target="#demo1">
                    <a>
                        More
                        <span class="caret"/>
                    </a>
                </li>

                <div id="demo1" class="col-md-12">
                    <c:forEach var="i" begin="3" end="${fn:length(places)}">
                    <c:if test="${places[i] != null}">
                            <s:url value="/place/{place}/0" var="placeUrl">
                                <s:param name="place" value="${places[i]}"/>
                            </s:url>
                            <li>
                                <a href="${placeUrl}">${places[i]}</a>
                            </li>
                        </c:if>
                    </c:forEach>
                </div>
                <script>
                    jQuery("#demo1").collapse('hide');
                </script>
            </ul>
        </div>
    </div>

    <div class="panel panel-success">
        <div class="panel-heading">
            Price
        </div>
        <div class="panel-body">
            <ul class="nav nav-pills nav-stacked">
                <c:forEach varStatus="price" begin="0" end="5001" step="1000">
                    <s:url value="/price/{min}/{max}/0" var="priceUrl">
                        <s:param name="min" value="${price.current}"/>
                        <s:param name="max" value="${price.current + price.step}"/>
                    </s:url>
                    <li>
                        <a href="${priceUrl}">
                            <c:choose>
                                <c:when test="${price.first}">
                                    less then ${price.current + price.step}
                                </c:when>
                                <c:when test="${price.last}">
                                    more then ${price.current}
                                </c:when>
                                <c:otherwise>
                                    ${price.current} - ${price.current + price.step}
                                </c:otherwise>
                            </c:choose>
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>

<%--<div class="panel panel-success">
    <div class="panel-heading">
        Filter by...
    </div>
    <div class="panel-body">
        <form action="/filter/results/0" method="post">
            <div class="form-group">
                <label for="country">Country</label>
                <div class="input-group">
                    <input type="text" class="form-control" id="country" name="country">
                    <div class="input-group-btn">
                        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                            <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu">
                            <li>
                                <a>${countrie}</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label for="place">Place</label>
                <div class="input-group">
                    <input type="text" class="form-control" id="place" name="place">
                    <div class="input-group-btn">
                        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                            <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu" id="placesDropDown">
                        </ul>
                    </div>
                </div>
            </div>
                <label for="dates">Date</label>
                <div class="form-group" id="dates">
                    <div class="panel row">
                        <div class="panel-body">
                            <div class="row">
                                <div class="form-group col-md-6 center-block">
                                    <label for="minDate">From</label>
                                    <input type="text" class="form-control" id="minDate" name="minDate">
                                </div>
                                <div class="form-group col-md-6">
                                    <label for="maxDate">To</label>
                                    <input type="text" class="form-control" id="maxDate" name="maxDate">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            <input type="submit" class="btn btn-success pull-left" value="Filter">
        </form>
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
</script>--%>
