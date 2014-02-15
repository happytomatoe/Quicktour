<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!-- Headliner -->
<script type="text/javascript" src="<c:url value="/resources/js/jquery.inputmask-multi.js"/> "></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.bind-first-0.2.2.min.js"/> "></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.inputmask.js"/> "></script>
<div class="row">
    <div class="col-sm-12">
        <div class="breadcrumb row">
            <div class="col-sm-5">
                <span class="tour-name"><a href="/tour/${tour.tourId}" target="_blank">${tour.name}</a></span>
            </div>

            <div class="col-sm-3"></div>

            <div class="col-sm-4">
                <div class="pull-right"><span class="price-old">$ </span>
                    <span id="oldPrice" class="price-old">${tour.price}</span>&nbsp;&nbsp;&nbsp;
                    <span id="priceWithDiscount" class="price-new">$ </span>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- /Headliner -->

<div id="showPicture"></div>

<!-- Accordion -->
<div class="row">
<div class="col-sm-12">
<div class="panel-group" id="accordion2">

<!-- Panel TourInfo Details -->
<div class="panel">
    <div class="panel-heading">
        <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseOne">
            <span class="panel-title"><span
                    class="glyphicon glyphicon-hand-right"></span>&nbsp;&nbsp;Tour Details</span>
        </a>
    </div>
    <div id="collapseOne" class="height0 panel-collapse collapse in">
        <div class="panel-body">

            <div class="col-sm-3 no_margin_left">
                <c:choose>
                <c:when test="${fn:contains(tour.photo.url,'http')}">
                <a href="#" id="tourPhoto"><img src="${tour.photo.url}" class="img-thumbnail"></a>

                <p>
                    </c:when>
                    <c:otherwise>
                    <a href="#" id="tourPhoto"><img src="<c:url value="/images/${tour.photo.url}"/>"
                                                    class="img-thumbnail"></a>

                <p>
                    </c:otherwise>
                    </c:choose>

            </div>

            <div class="col-sm-3 pt-10">

                <div class="form-group">
                    <label>Tour starts on:</label>&nbsp;&nbsp;
                    <span id="startingDate">${tourInfo.startDate}</span>
                </div>

                <div class="form-group">
                    <label>Tour ends on:</label>&nbsp;&nbsp;
                    <span id="endingDate">${tourInfo.endDate}</span>
                </div>

                <div class="form-group">
                    <label>Days in tour:</label>&nbsp;&nbsp;
                    <span id="daysInTour"></span>
                </div>

                <div id="overallRating" rel="popover" class="form-group">
                    <label>Rating:</label>&nbsp;&nbsp;
                    <span id="rateTour"></span>
                </div>

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

            </div>

            <c:set var="description" value="${tour.description}"/>
            <c:if test="${fn:length(description) > 500}">
                <c:set var="description" value="${fn:substring(description, 0, 500)}"/>
            </c:if>
            <div class="col-sm-6 no_margin_left pt-10">
                ${description}...
                <a href="/tour/${tour.tourId}" target="_blank" title="More..."
                   class="btn btn-default btn-xs">&raquo;</a>
            </div>

        </div>
    </div>
</div>
<!-- /Panel TourInfo Details -->

<!-- Form Enter Registration Information -->
<c:url var="saveUrl" value="/createOrder/${tourInfo.tourInfoId}"/>
<form:form role="form" method="post" modelAttribute="order" id="createOrderForm" action="${saveUrl}">
    <!-- Panel Enter Registration Information -->
    <div class="panel mt">
        <div class="panel-heading">
            <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo">
                <span class="panel-title"><span class="glyphicon glyphicon-hand-right"></span>&nbsp;&nbsp;Enter Registration Information</span>
            </a>
        </div>
        <div id="collapseTwo" class="height0 panel-collapse collapse in">
            <div class="panel-body">
                <div class="col-sm-6 no_margin_left">

                    <fieldset>
                        <legend>Required Personal Details</legend>
                        <div class="form-group">
                            <form:label path="user.name">Name</form:label>
                            <form:input id="firstName" path="user.name" value="${user.name}"
                                        placeholder="Enter your name" class="form-control" type="text" required=""/>
                            <form:errors path="user.name" cssClass="text-error"/>
                        </div>

                        <div class="form-group">
                            <form:label path="user.surname">Surname</form:label>
                            <form:input id="surname" path="user.surname"
                                        placeholder="Enter your surname" class="form-control" type="text" required=""/>
                            <form:errors path="user.surname" cssClass="text-error"/>
                        </div>

                        <div class="form-group">

                            <form:label path="user.email">Email Address</form:label>
                            <form:input id="email" path="user.email"
                                        placeholder="Enter email address" class="form-control" type="email"
                                        required=""/>
                            <form:errors path="user.email" cssClass="text-error"/>


                        </div>
                        <div class="form-group">
                            <form:label path="user.phone">Phone</form:label>
                            <form:input id="phone" path="user.phone" size="25"
                                        placeholder="+___(__)___-__-__" class="form-control " type="text" required=""/>
                            <form:errors path="user.phone" cssClass="text-error"/>
                        </div>

                    </fieldset>
                    <p>&nbsp;</p>
                </div>

                <div class="col-sm-6">
                    <legend>Additional Information</legend>
                    <div class="row">
                        <div class="col-sm-4">
                            <fieldset>
                                <div class="form-group">
                                    <form:label path="numberOfAdults">Number of adults:</form:label>

                                    <select id="numberOfAdults" name="numberOfAdults" class="form-control num"
                                            required=""/>
                                    <option value="1">1</option>
                                        <option value="2">2</option>
                                        <option value="3">3</option>
                                        <option value="4">4</option>
                                        <option value="5">5</option>
                                    </select>
                                    <form:errors path="numberOfAdults" cssClass="alert-danger"/>
                                </div>
                                <div class="form-group">
                                    <label for="numberOfChildren">Number of children:</label>

                                    <select id="numberOfChildren" name="numberOfChildren"
                                            class="form-control num" required>
                                        <option value="0">0</option>
                                        <option value="1">1</option>
                                        <option value="2">2</option>
                                        <option value="3">3</option>
                                        <option value="4">4</option>
                                        <option value="5">5</option>
                                    </select>
                                    <form:errors path="numberOfChildren" cssClass="alert-danger"/>
                                </div>
                                <sec:authorize access="hasAnyRole('admin', 'agent', 'user')">
                                    <button type="button" id="calculateDiscount" class="btn btn-primary btn-sm">
                                        Calculate
                                    </button>
                                </sec:authorize>
                            </fieldset>
                        </div>
                        <div class="col-sm-8">
                            <fieldset>
                                <label>Discount information:</label>

                                <div class="form-group">

                                    <!-- Discount information -->
                                    <div id="discountPolicies">
                                        <c:forEach items="${tour.discountPolicies}" var="discountPolicy">
                                            <div class="row">
                                                <div class="col-sm-9 discountPolicyName"
                                                     data-content="${discountPolicy.description}">${discountPolicy.name}</div>
                                                <div class="col-sm-3">${discountPolicy.formula}%</div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-9">Tour discount:</div>
                                        <div class="col-sm-3" id="tourDiscount">${tourInfo.discount}%</div>
                                    </div>
                                    <c:if test="${companyDiscount>0}">
                                        <div class="row">
                                            <div class="col-sm-9">Company discount:</div>
                                            <div class="col-sm-3" id="companyDiscount">${companyDiscount}%</div>
                                        </div>
                                    </c:if>
                                    <hr>
                                    <div class="row">

                                        <div class="col-sm-9 green"><b>Total discount:</b></div>
                                        <div class="col-sm-3 green" id="totalDiscount"><b><c:out
                                                value="${totalDiscount}"/></b></div>

                                    </div>
                                    <!-- /Discount information -->

                                </div>
                            </fieldset>
                        </div>
                    </div>

                    <input name="discount" id="discount" value="${totalDiscount}" hidden="true">

                    <fieldset>
                        <div class="form-group">
                            <label for="comments">Comments:</label>
                            <textarea id="comments" name="userInfo" rows="4"
                                      class="form-control">User_info</textarea>
                        </div>
                                    <span class="pull-right">
                                        <input type="submit" value="Confirm Order" class="btn btn-success btn-sm">
                                        <a class="btn btn-default btn-sm" href="/">Cancel</a>
                                    </span>
                    </fieldset>
                    <p>&nbsp;</p>
                </div>

            </div>
        </div>
    </div>
    <!-- /Panel Enter Registration Information -->

</form:form><!-- /Form Enter Registration Information -->
</div>
</div>
</div>
<!-- /Accordion -->

<hr>

<div class="col-sm-12">
    <div class="row well no_margin_left">
        <footer class="align-center">
            <p><b>Submitting the order does not obligates you to book the tour.</b></p><br>

            <p>Our agent will contact you to explane some details, ways of payment and obtaining required papers.</p>

            <p>This order further can be edited if during communication process will be found more convenient
                options.</p>
        </footer>
    </div>
</div>
<script>
    var maskList = $.masksSort($.masksLoad("../resources/js/phone-codes.json"), ['#'], /[0-9]|#/, "mask");
    var maskOpts = {
        inputmask: {
            definitions: {
                '#': {
                    validator: "[0-9]",
                    cardinality: 1
                }
            },
            //clearIncomplete: true,
            showMaskOnHover: false,
            autoUnmask: true
        },
        match: /[0-9]/,
        replace: '#',
        list: maskList,
        listKey: "mask"

    };
    var $phone = $('#phone');
    $phone.inputmasks(maskOpts);
    if ($phone.val() == undefined) {
        $phone.val("7");
    }
</script>
