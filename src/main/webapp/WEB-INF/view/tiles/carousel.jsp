<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="row">
    <div class="col-md-3 pull-down" style="margin-top:30px">

        <div class="panel panel-success ">

            <div class="panel-heading ">
                Filter by...
            </div>
            <div class="panel-body">
                <form action="<c:url value="/filter/results/0"/>" method="post">
                    <div class="form-group">
                        <label for="country">Country</label>
                        <select type="text" class="form-control" id="country" name="country">
                            <option selected></option>
                            <c:forEach items="${countriesWithPlaces}" var="country">
                                <option value="${country.name}"> ${country.name}</option>
                            </c:forEach>
                        </select>

                    </div>
                    <div class="form-group">
                        <label for="place">Place</label>

                        <select class="form-control" id="place" name="place">
                            <option selected></option>
                            <c:forEach items="${countriesWithPlaces}" var="country">
                            <optgroup label="${country.name}">
                                <c:forEach items="${country.places}" var="place">
                                <option value="${place}">${place}</option>
                                </c:forEach>
                                </c:forEach>
                        </select>
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
                    <input type="submit" class="btn btn-success pull-left" value="Search">
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
                    <c:set var="itemClass" value="item"/>
                    <c:if test="${i.index==0}">
                        <c:set var="itemClass" value="item active"/>
                    </c:if>
                    <div class="${itemClass}">
                        <img src="${tour.photo.url}"
                             style="height: 500px; width: 100%">

                        <div class="container">
                            <div class="carousel-caption">
                                <h1>${tour.name}</h1>

                                <p><a class="btn btn-success" href="${torsPageUrl}" role="button">Learn more</a></p>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <a class="left carousel-control" href="#myCarousel" data-slide="prev"><span
                    class="glyphicon glyphicon-chevron-left"></span></a>
            <a class="right carousel-control" href="#myCarousel" data-slide="next"><span
                    class="glyphicon glyphicon-chevron-right"></span></a>
        </div>
    </div>
</div>

