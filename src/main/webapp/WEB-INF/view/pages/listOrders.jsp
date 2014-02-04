<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/orders.css"/>">
<script src="<c:url value="/resources/js/orders-list.js"/> "></script>
<script src="<c:url value="/resources/js/jquery.jec.js"/> "></script>
<script src="<c:url value="/resources/js/jquery.raty.js"/> "></script>
<div class="container">
    <div class="row">
        <div class="col col-md-6"><h1>Orders</h1></div>
    </div>
    <div class="row">
        <div class="col col-md-6">
            <ul class="nav nav-pills">
                <li id="buttonAll" class="filter">
                    <a href="<c:url value="/orders"/>">
                        All (<span id="all">${allOrdersCount}</span>)
                    </a>
                </li>
                <li id="buttonActive" class="filter">
                    <a href="<c:url value="/orders/filter/Active"/>">
                        Active (<span id="inProcess">${activeOrdersCount}</span>)
                    </a>
                </li>
                <li id="buttonCompleted" class="filter">
                    <a href="<c:url value="/orders/filter/Completed"/>">
                        Completed (<span id="completed">${completedOrdersCount}</span>)
                    </a>
                </li>
                <li id="buttonCancelled" class="filter">
                    <a href="<c:url value="/orders/filter/Cancelled"/>">
                        Cancelled (<span id="cancelled">${cancelledOrdersCount}</span>)
                    </a>
                </li>
            </ul>
        </div>
    </div>
    <div class="row">
        <div class="col col-md-6">
            <hr>
        </div>
    </div>
</div>

<div class="container">
<table class="table table-striped table-condensed table-hover">
    <thead>
    <tr>
        <c:if test="${user.role !='user'}">
            <th>
                <a href="<c:url value="/orders/${filterLink}id/asc/${page.number}"/>"
                   id="sortByIdAsc" title="Sort by order ID" class="asc">
                    ID
                </a>

                &nbsp;
                <span id="spaceId" class="glyphicon glyphicon-sort-by-attributes space"></span>
                <span id="idAscIcon" class="glyphicon glyphicon-sort-by-attributes sort-icon"></span>
                <span id="idDescIcon" class="glyphicon glyphicon-sort-by-attributes-alt sort-icon"></span>
            </th>
        </c:if>

        <th>

            <a href="<c:url value="/orders/${filterLink}tourInfo/desc/${page.number}"/>"
               id="sortByTourNameDesc" title="Sort by tour name" class="desc">
                Tour name
            </a>
            &nbsp;
            <span id="spaceTourName" class="glyphicon glyphicon-sort-by-attributes space"></span>
            <span id="tourNameAscIcon" class="glyphicon glyphicon-sort-by-attributes sort-icon"></span>
            <span id="tourNameDescIcon" class="glyphicon glyphicon-sort-by-attributes-alt sort-icon"></span>
        </th>

        <th>
            <c:out value="${user.role != 'user' ? 'Rated by customer': 'Rate this tour'}"/>
        </th>

        <th>

            <a href="<c:url value="/orders/${filterLink}price/desc/${page.number}"/>"
               id="sortByPriceDesc" title="Sort by tour price" class="desc">
                Price
            </a>
            &nbsp;
            <span id="spacePrice" class="glyphicon glyphicon-sort-by-attributes space"></span>
            <span id="priceAscIcon" class="glyphicon glyphicon-sort-by-attributes sort-icon"></span>
            <span id="priceDescIcon" class="glyphicon glyphicon-sort-by-attributes-alt sort-icon"></span>
        </th>

        <c:if test="${user.role =='user' }">
            <th>Starting date</th>

            <th>Ending date</th>
        </c:if>

        <th>

            <a href="<c:url value="/orders/${filterLink}orderDate/desc/${page.number}"/>"
               id="sortByOrderDateDesc" title="Sort by order date" class="desc">
                Order date
            </a>
            &nbsp;
            <span id="spaceOrderDate" class="glyphicon glyphicon-sort-by-attributes space"></span>
            <span id="orderDateAscIcon" class="glyphicon glyphicon-sort-by-attributes sort-icon"></span>
            <span id="orderDateDescIcon" class="glyphicon glyphicon-sort-by-attributes-alt sort-icon"></span>
        </th>

        <th>

            <a href="<c:url value="/orders/${filterLink}status/desc/${page.number}"/>"
               id="sortByOrderStatusDesc" title="Sort by order status" class="desc">
                Status
            </a>
            &nbsp;
            <span id="spaceStatus" class="glyphicon glyphicon-sort-by-attributes space"></span>
            <span id="statusAscIcon" class="glyphicon glyphicon-sort-by-attributes sort-icon"></span>
            <span id="statusDescIcon" class="glyphicon glyphicon-sort-by-attributes-alt sort-icon"></span>
        </th>

        <c:if test="${user.role !='user' }">
            <th>

                <a href="<c:url value="/orders/${filterLink}nextPaymentDate/desc/${page.number}"/>"
                   id="sortByNextPaymentDateDesc" title="Sort by next payment date" class="desc">
                    Next payment on
                </a>
                &nbsp;
                <span id="spacePayment" class="glyphicon glyphicon-sort-by-attributes space"></span>
                <span id="paymentAscIcon" class="glyphicon glyphicon-sort-by-attributes sort-icon"></span>
                <span id="paymentDescIcon" class="glyphicon glyphicon-sort-by-attributes-alt sort-icon"></span>
            </th>
        </c:if>

        <th></th>

    </tr>
    </thead>

    <tbody>

    <c:forEach items="${orders}" var="order">

        <tr>
            <c:if test="${user.role !='user' }">
                <td>${order.orderId}</td>
            </c:if>

            <td>
                <a href="<c:url value="/tour/${order.tourInfo.tour.tourId}"/>" target="_blank"
                   class="btn btn-default btn-sm">${order.tourInfo.tour.name}</a>
            </td>

            <td>
                <span id="rate_${order.orderId}"></span>
                <script type="text/javascript">
                    jQuery('#rate_${order.orderId}').raty({
                        <c:if test="${user.role !='user' }">
                        readOnly: true,
                        </c:if>
                        cancel: true,
                        path: "<c:url value="/resources"/>",
                        scoreName: 'entity.score',
                        score:     ${order.vote},
                        number: 5,
                        click: function (score, evt) {
                            jQuery.post('/orders/rate', {score: score, order:${order.orderId} })
                        }
                    });
                </script>
            </td>

            <td>$ ${order.price}</td>

            <c:if test="${user.role =='user'}">
                <td>${order.tourInfo.startDate}</td>

                <td>${order.tourInfo.endDate}</td>
            </c:if>

            <c:set var="creationDate" value="${fn:substring(order.orderDate, 0, 10)}"/>
            <td>${creationDate}</td>

            <td>${order.status}</td>

            <c:if test="${user.role !='user' }">
                <td>${order.nextPaymentDate}</td>
            </c:if>

            <td>
                <a href="<c:url value="/manageOrder/${order.orderId}"/>" class="btn btn-success btn-sm">
                    <c:out value="${user.role!= 'user' ? 'Manage': 'View order'}"/>
                </a>
            </td>
        </tr>

    </c:forEach>

    </tbody>
</table>
<hr>

<!-- Pagination: page numbers and buttons -->
<div>
    Page ${page.number + 1} of ${page.totalPages}
</div>

<div class="col-md-4 col-md-offset-4">

    <s:url value="/orders/${filterLink}${sortByValueLink}{pageNumber}" var="first">
        <s:param name="pageNumber" value="0"/>
    </s:url>

    <s:url value="/orders/${filterLink}${sortByValueLink}{pageNumber}" var="last">
        <s:param name="pageNumber" value="${page.totalPages - 1}"/>
    </s:url>

    <s:url value="/orders/${filterLink}${sortByValueLink}{pageNumber}" var="next">
        <s:param name="pageNumber" value="${page.number + 1}"/>
    </s:url>

    <s:url value="/orders/${filterLink}${sortByValueLink}{pageNumber}" var="previous">
        <s:param name="pageNumber" value="${page.number - 1}"/>
    </s:url>

    <c:if test="${page.totalPages gt 1}">
        <ul class="pagination">

            <li id="navFirst"><a href="<c:url value="${first}"/>">First</a></li>
            <li id="navPrevious"><a href="<c:url value="${previous}"/>">&laquo;</a></li>

            <c:forEach var="num" begin="1" end="${page.totalPages}">
                <s:url value="/orders/${filterLink}${sortByValueLink}{pageNumber}" var="pageBtnUrl">
                    <s:param name="pageNumber" value="${num - 1}"/>
                </s:url>

                <c:choose>
                    <c:when test="${page.number eq num - 1}">
                        <c:set var="buttonClass" value="active"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="buttonClass" value=""/>
                    </c:otherwise>
                </c:choose>

                <c:if test="${((page.number - num) ge 0) && ((page.number - num) lt 2 )}">
                    <li class="${buttonClass}">
                        <a href="<c:url value="${pageBtnUrl}"/>">
                            <c:out value="${num}"/>
                        </a>
                    </li>
                </c:if>

                <c:if test="${((num - page.number) gt 0) && ((num - page.number) lt 4)}">
                    <li class="${buttonClass}">
                        <a href="<c:url value="${pageBtnUrl}"/>">
                            <c:out value="${num}"/>
                        </a>
                    </li>
                </c:if>

            </c:forEach>

            <li id="navNext"><a href="<c:url value="${next}"/>">&raquo;</a></li>
            <li id="navLast"><a href="<c:url value="${last}"/>">Last</a></li>

        </ul>

        <c:if test="${page.number gt 0}">
            <span id="first"></span>
        </c:if>

        <c:if test="${page.number lt page.totalPages - 1}">
            <span id="last"></span>
        </c:if>

    </c:if>
</div>
<!-- /Pagination page numbers and buttons -->
</div>
