<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/discount.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/select2-3.4.5/select2-bootstrap.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/select2-3.4.5/select2.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/datepicker.css"/>">
<link rel="stylesheet"
      href="<c:url value="/resources/jquery-ui-bootstrap/css/custom-theme/jquery-ui-1.10.0.custom.css"/>"/>

<script src="<c:url value="/resources/jquery-ui-bootstrap/js/jquery-ui-1.9.2.custom.min.js"/> "></script>
<script src="<c:url value="/resources/select2-3.4.5/select2.min.js"/> "></script>
<script src="<c:url value="/resources/js/index.js"/> "></script>
<script src="<c:url value="/resources/js/toogleActiveInTour.js"/> "></script>
<script src="<c:url value="/resources/js/jquery.expander.min.js"/> "></script>
<script src="<c:url value="/resources/js/jquery.raty.js"/> "></script>

<%
    String numberOfRecords = request.getParameter("numberOfRecords");
    if (numberOfRecords != null) {
        pageContext.setAttribute("numberOfRecords", numberOfRecords);
    } else {
        pageContext.setAttribute("numberOfRecords", "6");

    }
%>

<div>

<div class="row">
    <c:if test="${page.content == null}">
        <h1>Sorry can't find tours for your request</h1>
    </c:if>
    <c:forEach items="${page.content}" var="tour" varStatus="status">
        <c:if test="${status.index%2==0}">
            <div class="row">
        </c:if>
        <c:if test="${tour.active}">
            <s:url value="/tour/{id}" var="torsPageUrl">
                <s:param name="id" value="${tour.tourId}"/>
            </s:url>
            <div class="col-md-6">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <!-- Star rating -->
                        <div class="row">
                            <div class="form-group col-md-12 text-center">

                                <c:if test="${tour.rateCount>0}">
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
                                </c:if>
                            </div>
                        </div>
                        <c:if test="${tour.discount>0}">
                            <img src="<c:url value="/resources/img/discount_icon.png"/>" class="icon">
                            <span class="discount">-${tour.discount}%</span>
                        </c:if>
                        <img src="${tour.photo.url}"
                             style="height: 300px" class="img-thumbnail">

                        <div class="row">
                            <div class="col-md-6">
                                <h3>${tour.name}</h3>
                            </div>
                            <div class="col-md-6 text-right ">
                                <h3>${tour.price}$</h3>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12 tourDescription">
                                    ${tour.description}
                            </div>
                        </div>
                        <br>

                        <div class="row">
                            <div class="col-md-12">
                                <a href="${torsPageUrl}" class="btn btn-success pull-left">
                                    <span class="glyphicon glyphicon-chevron-right"></span>
                                    Read more...
                                </a>
                                <sec:authorize access="!(hasRole('ROLE_AGENT'))">
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-success dropdown-toggle"
                                                data-toggle="dropdown">
                                            Order it
                                            <span class="caret"></span>
                                        </button>
                                        <ul class="dropdown-menu" role="menu">
                                            <li role="presentation" class="dropdown-header">Please, select tour
                                                start date
                                            </li>
                                            <c:forEach items="${tour.tourInfo}" var="tourInfo">
                                                <li>
                                                    <a href="<c:url value="/createOrder/${tourInfo.tourInfoId}"/>">${tourInfo.startDate}</a>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </sec:authorize>
                                <sec:authorize access="hasRole('ROLE_AGENT')">
                                    <c:if test="${tour.company.name==companyName}">
                                        <a href="<c:url value="/tours/edit/${tour.tourId}"/>"
                                           class="btn btn-primary">Edit</a>
                                        <button type="button" id="${tour.tourId}"
                                                onclick="toggleActive(${tour.tourId})" class="btn btn-warning">
                                            Deactivate
                                        </button>
                                    </c:if>
                                </sec:authorize>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${status.index%2==1}">
            </div >
        </c:if>

    </c:forEach>
</div>
<div class="row">

    <div class="col-md-3 ">
        <label for="numberOfRecords">Row count</label>
        <select name="numberOfRecords" selected-value="${numberOfRecords}" id="numberOfRecords">
            <option value="6">6</option>
            <option value="10">10</option>
            <option value="20">20</option>
            <option value="50">50</option>
            <option value="100">100</option>
        </select>

    </div>
    <div class="col-md-9 ">

        <div class="text-center">
            <s:url value="{pageNum}" var="first">
                <s:param name="pageNum" value="0"/>
            </s:url>

            <s:url value="{pageNum}" var="last">
                <s:param name="pageNum" value="${page.totalPages - 1}"/>
            </s:url>

            <s:url value="{pageNum}" var="next">
                <s:param name="pageNum" value="${page.number + 1}"/>
            </s:url>

            <s:url value="{pageNum}" var="previous">
                <s:param name="pageNum" value="${page.number>0?page.number - 1:0}"/>
            </s:url>

            <c:if test="${page.totalPages gt 1}">
                <ul class="pagination">


                    <li id="navFirst"><a href="${first}">First</a></li>
                    <li id="navPrevious"><a href="${previous}">&laquo;</a></li>

                    <c:forEach var="num" begin="1" end="${page.totalPages}">
                        <s:url value="{pageNum}" var="pageBtnUrl">
                            <s:param name="pageNum" value="${num - 1}"/>
                            <s:param name="numberOfRecords" value="${numberOfRecords}"/>

                        </s:url>

                        <c:choose>
                            <c:when test="${page.number eq num - 1}">
                                <c:set var="buttonClass" value="class='active'"/>
                            </c:when>
                            <c:otherwise>
                                <c:set var="buttonClass" value=""/>
                            </c:otherwise>
                        </c:choose>

                        <c:if test="${((page.number - num) ge 0) && ((page.number - num) lt 2 )}">
                            <li  ${buttonClass}
                                    >
                                <a href="${pageBtnUrl}">
                                    <c:out value="${num}"/>
                                </a>
                            </li>
                        </c:if>

                        <c:if test="${((num - page.number) gt 0) && ((num - page.number) lt 4)}">
                            <li ${buttonClass}>
                                <a href="${pageBtnUrl}">
                                    <c:out value="${num}"/>
                                </a>
                            </li>
                        </c:if>

                    </c:forEach>

                    <li id="navNext"><a href="${next}">&raquo;</a></li>
                    <li id="navLast"><a href="${last}">Last</a></li>
                </ul>

                <c:if test="${page.number gt 0}">
                    <span id="first"></span>
                </c:if>

                <c:if test="${page.number lt page.totalPages - 1}">
                    <span id="last"></span>
                </c:if>

            </c:if>
        </div>
        &nbsp;
        &nbsp;
        &nbsp;
        &nbsp;
        &nbsp;
    </div>

</div>
</div>