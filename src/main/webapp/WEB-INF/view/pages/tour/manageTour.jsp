<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<link rel="stylesheet" href="<c:url value="/resources/css/jasny-bootstrap.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/select2-3.4.5/select2-bootstrap.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/select2-3.4.5/select2.css"/>">
<link rel="stylesheet"
      href="<c:url value="/resources/jquery-ui-bootstrap/css/custom-theme/jquery-ui-1.10.0.custom.css"/>"/>
<script src="<c:url value="/resources/js/jasny-bootstrap.min.js"/> "></script>
<script src="<c:url value="/resources/ckeditor/ckeditor.js" />"></script>
<script src="<c:url value="/resources/js/jquery.validate.js" />"></script>
<script src="<c:url value="/resources/js/manageTours.js"/> "></script>
<script src="<c:url value="/resources/jquery-ui-bootstrap/js/jquery-ui-1.9.2.custom.min.js"/> "></script>
<script src="<c:url value="/resources/select2-3.4.5/select2.min.js"/> "></script>
<script type="text/javascript"
        src="http://maps.google.com/maps/api/js?key=AIzaSyDIB3fP0FaICwh-ysdOd-Ut2xCFpH2DcVc&sensor=false&language=en">
</script>


<form:form role="form" method="post" modelAttribute="tour" id="manageTourForm" enctype="multipart/form-data">
<div class="panel-group" id="accordion">
    <div class="panel panel-default">
        <div id="tourCommon" class="panel-collapse collapse in">
            <div class="panel-body">
                <c:choose>
                    <c:when test="${edit}">
                        <form:hidden path="tourId"/>
                        <input type="hidden" id="tourInfoCount" value="${tour.tourInfo.size()}"/>
                        <input type="hidden" id="tourPlacesCount" value="${tour.toursPlaces.size()}"/>
                    </c:when>
                    <c:otherwise>
                        <input type="hidden" id="tourInfoCount" value="0"/>
                        <input type="hidden" id="tourPlacesCount" value="0"/>
                    </c:otherwise>
                </c:choose>
                <div class="form-group">
                    <form:label path="name" for="inputName">Tour name</form:label>
                    <form:input type="text" class="form-control" id="inputName"
                                path="name" placeholder="Tour name"
                                required="true"/>
                    <form:errors path="name"/>
                </div>
                <div class="form-group">
                    <div class="checkbox ">
                        <label>
                            <form:checkbox path="active" value="active"/>Active
                        </label>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <form:label path="description" for="editor1">
                    Tour description
                </form:label>
                <form:textarea path="description" id="editor1" rows="10" cols="10"
                               required="true" cssClass="ckeditor"/>
                <form:errors path="description"/>
            </div>
            <div class="form-group col-md-6">
                <form:label path="transportDesc" for="inputTransport">Transport description</form:label>
                <form:input path="transportDesc"
                            type="text" class="form-control"
                            id="inputTransport"
                            placeholder="Transport description"
                            required="true"/>
                <form:errors path="transportDesc"/>
            </div>
            <div class="form-group col-md-6">
                <form:label path="travelType">Travel type</form:label>
                <form:select path="travelType" class="form-control">
                    <form:option value="driving"/>
                    <form:option value="walking"/>
                    <form:option value="bycicling"/>
                </form:select>
            </div>
            <div class="form-group ">
                <div class="col-md-12">
                    <h4>Main photo</h4>
                    <form:hidden path="photo.photoId"/>
                    <div class="fileinput fileinput-new" data-provides="fileinput">

                        <c:if test="${tour.photo.url!=null}">
                            <div class="fileinput-new thumbnail" style="width: 200px; height: 150px;">
                                <img src="${tour.photo.url}">
                            </div>
                        </c:if>
                        <div>
                    <span class="btn btn-default btn-file"><span class="fileinput-new">Select image</span><span
                            class="fileinput-exists">Change</span><input type="file" accept="image/*" name="mainPhoto"
                                                                         tabindex="11"></span>
                            <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
                        </div>
                        <div class="fileinput-preview fileinput-exists thumbnail"
                             style="max-width: 320px; max-height: 320px;">


                        </div>

                    </div>
                    <form:errors path="photo" cssClass="alert-danger"/>
                </div>
            </div>
            <div class="form-group col-sm-12">
                <form:label path="priceIncludes" for="prInclude" cssClass="form-group">
                    Price Includes:
                </form:label>
                <br/>
                <form:select path="priceIncludes" items="${priceIncludes}"
                             itemValue="priceDescriptionId" itemLabel="description" multiple="true"
                             cssClass="select2 form-control">
                </form:select>
            </div>
            <div class="row">
                <div class="col-md-2 col-md-offset-5">
                    <button type="button" class="btn btn-success center-block" id="next1">
                        Next
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="panel panel-default">
    <div id="collapseTwo" class="panel-collapse collapse in">
        <div class="panel-body">
            <div class="row">
                <div id="dateBlock" class="col-md-12">
                    <c:if test="${edit}">
                        <c:forEach items="${tour.tourInfo}" varStatus="i">
                            <div class="col-sm-6  col-md-4 " id="tourInfo${i.index}">

                                <div class="col-sm-12   panel panel-default">
                                    <a href="javascript:void(0)" onclick="$('#tourInfo${i.index}').remove()"
                                       class="pull-right"><span class="glyphicon glyphicon-remove "></span></a>


                                    <div class="panel-body">
                                        <form:hidden path="tourInfo[${i.index}].tourInfoId"/>
                                        <div class="form-group">
                                            <form:label path="tourInfo[${i.index}].startDate">Start date</form:label>
                                            <form:input path="tourInfo[${i.index}].startDate" type="date"
                                                        date="true" required="true" class="form-control"/>
                                            <form:errors path="tourInfo[${i.index}].startDate" cssClass="text-danger"/>
                                        </div>
                                        <div class="form-group">
                                            <form:label path="tourInfo[${i.index}].endDate">End date</form:label>
                                            <form:input path="tourInfo[${i.index}].endDate" type="date"
                                                        date="true" required="true" class="form-control"/>
                                            <form:errors path="tourInfo[${i.index}].endDate" cssClass="text-danger"/>
                                        </div>
                                        <div class="form-group">
                                            <form:label path="tourInfo[${i.index}].discount">Discount</form:label>
                                            <form:input path="tourInfo[${i.index}].discount" type="text"
                                                        number="true" required="true" cssClass="form-control "/>
                                            <form:errors path="tourInfo[${i.index}].discount" cssClass="text-danger"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:if>
                </div>
            </div>
            <div class="row">
                <div class="col-md-2 col-md-offset-10">
                    <button type="button" class="btn btn-primary pull-right" id="addDate"><span
                            class="glyphicon glyphicon-plus"></span></button>
                </div>
            </div>
            <div class="row">
                <div class="col-md-2 col-md-offset-5">
                    <button type="button" class="btn btn-danger pull-left" id="prev1">
                        Previous
                    </button>
                    <button type="button" class="btn btn-success pull-right" id="next2">
                        Next
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="panel panel-default">
    <div id="collapseThree" class="panel-collapse collapse in">
        <div class="panel-body">
            <div class="col-md-3 col-md-offset-4">
                <input id="address" name="address" type="text"
                       class="form-control" placeholder="Place name">
            </div>
            <button type="button" class="btn btn-success" id="geocode">Add Place</button>
            <br>
            <br>

            <div id="map" style="width: 100%; height: 400px"></div>
            <div class="row">
                <div id="placeBlock" class="form-group">
                    <br>
                    <c:if test="${edit}">
                        <c:forEach items="${tour.toursPlaces}" varStatus="i">

                            <div class="col-md-6  col-sm-6" id="place${i.index}">
                                <div class="col-sm-12 panel panel-default">
                                    <a href="javascript:void(0)"><span
                                            class="glyphicon glyphicon-remove pull-right"></span></a>

                                    <div class="panel-body">
                                        <div>

                                            <form:hidden path="toursPlaces[${i.index}].placeId"/>
                                            <form:hidden path="toursPlaces[${i.index}].country"/>
                                            <form:hidden path="toursPlaces[${i.index}].geoWidth"/>
                                            <form:hidden path="toursPlaces[${i.index}].geoHeight"/>

                                            <div class="form-group">
                                                <label for="toursPlaces[${i.index}].name">Place name</label>
                                                <br>
                                                <form:input path="toursPlaces[${i.index}].name"
                                                            id="toursPlaces[${i.index}].name" required=""
                                                            сssClass="form-control"/>
                                                <form:errors path="toursPlaces[${i.index}].name"
                                                             cssClass="text-danger"/>
                                            </div>

                                            <p>
                                                <form:label
                                                        path="toursPlaces[${i.index}].description">Place description</form:label>
                                                <form:textarea path="toursPlaces[${i.index}].description"
                                                               cssClass="form-control"/>
                                                <form:errors path="toursPlaces[${i.index}].description"
                                                             cssClass="text-danger"/>
                                            </p>

                                            <p>
                                                <form:label path="toursPlaces[${i.index}].price">Price</form:label>
                                                <form:input path="toursPlaces[${i.index}].price"
                                                            cssClass="form-control" required="" number="true"/>
                                                <form:errors path="toursPlaces[${i.index}].price"
                                                             cssClass="text-danger"/>
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <c:if test="${i.index%2==1}">

                                <p></p>
                            </c:if>
                        </c:forEach>

                    </c:if>
                </div>
                <p></p>

            </div>
            <div class="row">
                <div class="col-md-2 col-md-offset-5">
                    <button type="button" class="btn btn-danger pull-left" id="prev2">
                        Previous
                    </button>
                    <form:button class="btn btn-success pull-right" id="saveAll">Finish</form:button>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
</form:form>

