<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link rel="stylesheet" href="<c:url value="/resources/css/datepicker.css"/>"/>
<%@ page import="com.quicktour.entity.Order.Status" %>
<script src="<c:url value="/resources/js/jquery.expander.min.js"/> "></script>

<!-- Headliner -->
<div class="row">
    <div class="col-sm-12">
        <div class="breadcrumb row">

            <div class="col-sm-4">
                Order ID: <b>${order.orderId}</b>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;Tour ID:
                <b>${order.tourInfo.tour.tourId}</b>

                <div id="customerRating" rel="popover">
                    Rated by customer: <span id="rate_${order.orderId}"></span>
                </div>
                <script type="text/javascript">
                    jQuery('#rate_${order.orderId}').raty({
                        <c:if test="${user.role != 'user'}">
                        readOnly: true,
                        </c:if>
                        cancel: true,
                        scoreName: 'entity.score',
                        score: ${order.vote},
                        number: 5,
                        click: function (score, evt) {
                            jQuery.post('/orders/rate', {score: score, order:${order.orderId} })
                        }
                    });
                </script>
            </div>

            <div class="col-sm-5 center">
                <span class="tour-name"><a href="/tour/${order.tourInfo.tour.tourId}"
                                           target="_blank">${order.tourInfo.tour.name}</a></span>
            </div>

            <div class="col-sm-3">
                <div class="pull-right">
                    <span class="price-old">$ </span><span id="oldPrice" class="price-old">${order.price}</span>&nbsp;&nbsp;&nbsp;
                    <span id="priceWithDiscount" class="price-new">$ ${order.price}</span>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- /Headliner -->

<!-- Order status timeline-->
<div class="row">
    <div class="col-sm-12">
        <div class="order_status">
            <ul class="order_status_icon">
                <div id="received" rel="popover" class="col-sm-1 timeline">
                    <li class="icon_round received"></li>
                    <li class="timeline-list received"><b>Received</b><br>
                        <c:set var="timestamp" value="${fn:substring(order.orderDate, 0, 16)}"/>
                        <span class="timeline-timestamp">${timestamp}</span>
                    </li>
                </div>
                <div class="col-sm-2">
                    <li class="icon_strip accepted gray"></li>
                </div>
                <div id="accepted" rel="popover" class="col-sm-1">
                    <li class="icon_round accepted gray"></li>
                    <li class="timeline-list accepted gray"><b>Accepted</b><br>
                        <c:set var="timestamp" value="${fn:substring(order.acceptedDate, 0, 16)}"/>
                        <span class="timeline-timestamp">${timestamp}</span>
                    </li>
                </div>
                <div class="col-sm-2">
                    <li class="icon_strip confirmed gray"></li>
                </div>
                <div id="confirmed" rel="popover" class="col-sm-1">
                    <li class="icon_round confirmed gray"></li>
                    <li class="timeline-list confirmed gray"><b>Confirmed</b><br>
                        <c:set var="timestamp" value="${fn:substring(order.confirmedDate, 0, 16)}"/>
                        <span class="timeline-timestamp">${timestamp}</span>
                    </li>
                </div>
                <div class="col-sm-2">
                    <li id="arrow_completed" class="icon_strip completed gray"></li>
                </div>
                <div id="completed" rel="popover" class="col-sm-1">
                    <li class="icon_round completed gray"></li>
                    <li class="timeline-list completed gray"><b>Completed</b><br>
                        <c:set var="timestamp" value="${fn:substring(order.completedDate, 0, 16)}"/>
                        <span class="timeline-timestamp">${timestamp}</span>
                    </li>
                </div>
                <div class="col-sm-1">
                    <li></li>
                </div>
                <div id="cancelled" rel="popover" class="col-sm-1 pull-right timeline-cancelled">
                    <li class="icon_round cancelled gray"></li>
                    <li class="timeline-list cancelled gray"><b>Cancelled</b><br>
                        <c:set var="timestamp" value="${fn:substring(order.cancelledDate, 0, 16)}"/>
                        <span class="timeline-timestamp">${timestamp}</span>
                    </li>
                </div>
            </ul>
        </div>
    </div>
</div>
<!-- /Order status timeline-->

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
    <div id="collapseOne" class="height0 panel-collapse collapse">
        <div class="panel-body">

            <div class="col-sm-3 no_margin_left">
                <a href="#" id="tourPhoto"><img src="${order.tourInfo.tour.photo.url}"
                                                class="img-thumbnail"></a>
            </div>

            <div class="col-sm-3 pt-10">

                <div class="form-group">
                    <label>Tour starts on:</label>&nbsp;&nbsp;
                    <span id="startingDate">${order.tourInfo.startDate}</span>
                </div>

                <div class="form-group">
                    <label>Tour ends on:</label>&nbsp;&nbsp;
                    <span id="endingDate">${order.tourInfo.endDate}</span>
                </div>

                <div class="form-group">
                    <label>Days in tour:</label>&nbsp;&nbsp;
                    <span id="daysInTour"></span>
                </div>

                <div id="overallRating" rel="popover" class="form-group">
                    <label>Rating:</label>&nbsp;&nbsp;
                    <span id="rateTour"></span>
                </div>

                <script type="text/javascript">
                    jQuery('#rateTour').raty({
                        readOnly: true,
                        path: "<c:url value="/resources"/>",
                        scoreName: 'Tour.ratio',
                        score: ${order.tourInfo.tour.rate},
                        number: 5
                    });
                </script>

            </div>

            <div class="col-sm-6 no_margin_left pt-10">
                <div id="tourDescription">
                    ${order.tourInfo.tour.description}
                </div>
                <a href="/tour/${order.tourInfo.tour.tourId}" target="_blank" title="More about tour..."
                   class="btn btn-default btn-xs">&raquo;</a>
            </div>

        </div>
    </div>
</div>
<!-- /Panel TourInfo Details -->

<!-- Form Manage Order -->
<form:form role="form" method="post" modelAttribute="order" id="manageOrderForm" action="/manageOrder/${order.orderId}">
<!-- Panel Customer Information -->
<div class="panel mt">
    <div class="panel-heading">
        <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo">
            <span class="panel-title"><span class="glyphicon glyphicon-hand-right"></span>&nbsp;&nbsp;Customer Information</span>
        </a>
    </div>
    <div id="collapseTwo" class="height0 panel-collapse collapse in">
        <div class="panel-body">
            <div class="col-sm-6 no_margin_left">

                <fieldset>
                    <legend>Personal Details</legend>
                    <div class="form-group">
                        <label>Name:&nbsp;</label>
                            ${order.user.name}
                    </div>

                    <div class="form-group">
                        <label>Surname:&nbsp;</label>
                            ${order.user.surname}
                    </div>

                    <div class="form-group">
                        <label>Email:&nbsp;</label>
                        <a href="mailto:${order.user.email}">${order.user.email}</a>
                    </div>

                    <div class="form-group">
                        <label>Phone:&nbsp;</label>
                            ${order.user.phone}
                    </div>
                </fieldset>

            </div>

            <div class="col-sm-6">
                <fieldset>
                    <legend>Additional Information</legend>
                    <div class="form-group">
                        <label for="comments">Comments:</label>
                        <textarea name="userInfo" id="comments" rows="5"
                                  class="form-control">${order.userInfo}</textarea>
                        <c:if test="${user.role == 'user'}">
                            <div class="pull-right sc">
                                <a id="saveComments" class="btn btn-success btn-sm">Save Comments</a>
                            </div>
                        </c:if>
                    </div>
                </fieldset>
            </div>

        </div>
    </div>
</div>
<!-- /Panel Customer Information -->

<!-- Panel Manage Order-->
<div class="panel">
    <div class="panel-heading">
        <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseTree">
                  <span class="panel-title"><span class="glyphicon glyphicon-hand-right"></span>&nbsp;&nbsp;
                    <c:out value="${user.role != 'user' ? 'Manage Order': 'Order Information'}"/>
                  </span>
        </a>
    </div>
    <div id="collapseTree" class="height0 panel-collapse collapse in">
        <div class="panel-body">
            <div class="col-sm-2 no_margin_left">
                <fieldset>
                    <div class="form-group">
                        <label for="numberOfAdults">Adults:</label>
                        <c:choose>
                            <c:when test="${user.role == 'user'}">
                                &nbsp;&nbsp;${order.numberOfAdults}
                            </c:when>
                            <c:otherwise>
                                <select id="numberOfAdults" name="numberOfAdults" class="form-control" required>
                                    <option value="${order.numberOfAdults}">${order.numberOfAdults}</option>
                                    <option value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                    <option value="4">4</option>
                                    <option value="5">5</option>
                                </select>
                            </c:otherwise>
                        </c:choose>
                        <form:errors path="numberOfAdults" cssClass="alert-danger"/>
                    </div>
                </fieldset>
            </div>

            <div class="col-sm-2">
                <fieldset>
                    <div class="form-group">
                        <label for="numberOfChildren">Children:</label>
                        <c:choose>
                            <c:when test="${user.role == 'user'}">
                                &nbsp;&nbsp;${order.numberOfChildren}
                            </c:when>
                            <c:otherwise>
                                <select id="numberOfChildren" name="numberOfChildren" class="form-control" required>
                                    <option value="${order.numberOfChildren}">${order.numberOfChildren}</option>
                                    <option value="0">0</option>
                                    <option value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                    <option value="4">4</option>
                                    <option value="5">5</option>
                                </select>
                            </c:otherwise>
                        </c:choose>
                        <form:errors path="numberOfChildren" cssClass="alert-danger"/>
                    </div>
                </fieldset>
            </div>

            <div class="col-sm-2">
                <fieldset>
                    <div class="form-group">
                        <label for="discount">Discount, %:</label>
                        <c:choose>
                            <c:when test="${user.role == 'user'}">
                                &nbsp;&nbsp;${order.discount}
                                <input id="discount" value="${order.discount}" type="hidden">
                            </c:when>
                            <c:otherwise>
                                <input name="discount" id="discount" value="${order.discount}" class="form-control"
                                       type="text">
                            </c:otherwise>
                        </c:choose>
                        <form:errors path="discount" cssClass="alert-danger"/>
                    </div>
                </fieldset>
            </div>

            <div class="col-sm-3">
                <fieldset>

                    <div class="form-group">
                        <label for="paymentDate">Next payment until:</label>
                        <c:choose>
                            <c:when test="${user.role == 'user'}">
                                &nbsp;&nbsp;${order.nextPaymentDate}
                            </c:when>
                            <c:otherwise>
                                <input id="paymentDate" name="nextPaymentDate" placeholder="YYYY-MM-DD"
                                       value="${order.nextPaymentDate}" class="form-control" type="text" readonly>
                            </c:otherwise>
                        </c:choose>
                        <form:errors path="nextPaymentDate" cssClass="alert-danger"/>
                    </div>
                </fieldset>
            </div>

            <div class="col-sm-3">
                <fieldset>

                    <div class="form-group">
                        <label for="status">Order status:</label>
                        <c:choose>
                            <c:when test="${user.role == 'user'}">
                                &nbsp;&nbsp;${order.status}
                            </c:when>
                            <c:otherwise>
                                <select name="status" id="status" class="form-control">
                                    <option value="${order.status}" selected="selected">${order.status}</option>
                                    <% pageContext.setAttribute("statuses", Status.values()); %>
                                    <c:forEach var="status" items="${statuses}">
                                        <c:if test="${status!=order.status}">
                                            <option value="${status}">${status}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </c:otherwise>
                        </c:choose>
                        <form:errors path="status" cssClass="alert-danger"/>
                    </div>
                    <input id="orderId" name="id" value="${order.orderId}" type="hidden">
                    <c:choose>
                        <c:when test="${user.role == 'user'}">
                          <span class="pull-right">
                            <a class="btn btn-default btn-sm" href="/orders">Back</a>
                          </span>
                        </c:when>
                        <c:otherwise>
                            <div class="checkbox">
                                <label class="pull-right">
                                    <input name="sendEmail" value="yes" type="checkbox" checked> Notify user by email
                                </label>
                            </div>
                          <span class="pull-right">
                            <form:button type="submit" id="saveBtn" class="btn btn-success btn-sm">Save</form:button>
                            <a class="btn btn-default btn-sm" href="/orders">Cancel</a>
                          </span>
                        </c:otherwise>
                    </c:choose>

                </fieldset>
                <p>&nbsp;</p>
            </div>
        </div>
    </div>
</div>
<!-- /Panel Manage Order-->

</form:form><!-- /Form Manage Order -->

</div>
</div>
</div>
<!-- /Accordion -->
