<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script type="text/javascript"
        src="http://maps.googleapis.com/maps/api/js?key=AIzaSyChyZ_aPtSiHgv2lsfTyvWYAccwmDXUYPE&sensor=true&libraries=panoramio">
</script>
<script src="https://ssl.panoramio.com/wapi/wapi.js?v=1"></script>
<script src="<c:url value="/resources/js/bootstrap-select.js"/>"></script>
<script src="<c:url value="/resources/js/jquery.raty.js"/>"></script>
<link href="<c:url value="/resources/css/bootstrap-select.css"/>" rel="stylesheet">
<script src="<c:url value="/resources/js/tourDetails.js"/>"></script>
<script src="<c:url value="/resources/ckeditor/ckeditor.js"/>"></script>
<script src="<c:url value="/resources/js/jquery.expander.min.js"/> "></script>

<div class="row">
    <div class="breadcrumb row">
        <div class="col-md-8">
            <h1>${tour.name}</h1>
            <!-- Star rating -->
            <c:if test="${tour.rateCount>0}">
                <div class="form-group col-md-12 text-center">
                    <span id="rateTour_${tour.tourId}"></span>
                    <span><i>(${tour.rateCount})</i></span>
                    <script type="text/javascript">
                        jQuery('#rateTour_${tour.tourId}').raty({
                            readOnly: true,
                            path: "<c:url value="/resources"/>",
                            scoreName: 'Tour.ratio',
                            score:     ${tour.rate},
                            number: 5
                        });
                    </script>
                    <!-- /Star rating -->
                </div>
            </c:if>
            <!-- /Star rating -->
        </div>

    </div>
</div>
<div class="row">
    <div class="col-md-4">

        <img src="${tour.photo.url}"
             style="height: 300px" class="img-thumbnail">

    </div>
    <div class="col-md-3">
        <sec:authorize access="!(hasRole('ROLE_AGENT'))">
            <div class="btn-group">
                <button type="button" class="btn btn-success dropdown-toggle" data-toggle="dropdown">
                    <span class="glyphicon glyphicon-hand-right"></span>
                    Order
                    <span class="glyphicon glyphicon-hand-left"></span>
                    <span class="caret"></span>
                </button>
                <ul class="dropdown-menu" role="menu">
                    <li role="presentation" class="dropdown-header">Please, select tour start date</li>
                    <c:forEach items="${tour.tourInfo}" var="tourInfo">
                        <li>
                            <a href="<c:url value="/createOrder/${tourInfo.tourInfoId}"/>"><strong>${tourInfo.startDate}
                                Price: ${tourInfo.tour.price-tourInfo.tour.price*tourInfo.discount/100}$</strong></a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </sec:authorize>
    </div>
    <div class="col-md-2 pull-right">
        <img class="img-thumbnail" src="<c:url value="${tour.company.photo.url}"/>"
             width="150px"/>

        <p></p>

        <p></p>
    </div>
    <div class="col-md-8">

        <div class="panel panel-default">
            <div class="panel-body">
                <div class="col-md-12" id="description"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${tour.description}
                </div>
            </div>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-md-7 pull-right">
        <div class="col-md-5">
            <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_USER')">
                <c:if test="${maxDiscount>0}">
                    <h3>
                        Discount: <span class="label label-danger">  ${maxDiscount}%</span>
                    </h3>
                </c:if>
            </sec:authorize>
        </div>
        <div class="col-md-7 pull-right">
            <h3>
                Price from: <span class="price-new">  $${minPrice}</span>
            </h3>
        </div>

    </div>
</div>

<div class="row">
    <ul class="list-group">
        <h3 class="text-center">Price Includes:</h3>
        <c:forEach items="${tour.priceIncludes}" var="priceIncludes">
            <li class="list-group-item list-group-item-success">
                <span class="glyphicon glyphicon-ok"></span>
                    ${priceIncludes.description}
            </li>
        </c:forEach>
    </ul>
</div>

<div class="row">
    <div class="panel panel-default">
        <div class="panel-heading">
            <p style="text-align: center">
                <button class="btn btn-lg"><span class="glyphicon glyphicon-info-sign"></span></button>
                <b>
                    If you want change photo you may click on the map on marker or simply change tag pho the photo under
                    the widget:
                </b>
                <button class="btn btn-lg"><span class="glyphicon glyphicon-info-sign"></span></button>
            </p>
        </div>
        <div class="panel-body">
            <div class="col-md-6">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="col-md-10">
                            <div id="photoWidget">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div id="map_canvas" style="width: 400px; height: 350px; margin: auto">
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-3 col-md-offset-2">
                <p></p>

                <p></p>
                <select class="selectpicker" id="filter">
                    <option>Architecture</option>
                    <option>Mountains</option>
                    <option>Art</option>
                    <option>Sea</option>
                    <option>Travel</option>
                </select>

                <p></p>

                <p></p>
            </div>
        </div>
        <div class="panel-footer">
            <p style="text-align: center"><b><span class="glyphicon glyphicon-star"></span> Row painted
                for ${tour.travelType} travel type</b></p>
        </div>

    </div>
</div>

<div class="row">


    <div class="comment-box">

    </div>

</div>
<ul class="pagination" id="pagination"></ul>


<sec:authorize access="isAuthenticated()">
    <div class="row " id="editor">
        <input type="hidden" id="login" value="${user.login}">
        <input type="hidden" id="parent" value="">
        <input type="hidden" id="commentId" value="">

        <div class="row top-buffer">
            <textarea class="col-md-12" id="new_message" name="new_message"
                      placeholder="Type in your message" rows="5"></textarea>
        </div>

        <div class="row top-buffer">
            <div class="col-md-4">
                <button class="btn btn-primary" id="post" type="submit" onclick="addNewComment()">Save</button>
            </div>
        </div>
    </div>
</sec:authorize>
<sec:authorize access="isAnonymous()">

    <div class="col-sm-12">
        <div class="row well no_margin_left">
            <footer class="align-center">
                <p>If you want to leave comment you must sign in or register.</p>
            </footer>
        </div>
    </div>
</sec:authorize>
</div>

</div>
