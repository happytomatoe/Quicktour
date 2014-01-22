<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <title><tiles:getAsString name="title"/></title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.css"/>">
    <%--<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">--%>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/signin.css"/>">
    <link href="<c:url value="/resources/css/orders.css"/>" type="text/css" rel="stylesheet">

    <script src="<c:url value="/resources/js/jquery.js" />" type="text/javascript"></script>
    <script src="<c:url value="/resources/js/jquery.MetaData.js" />" type="text/javascript"></script>
    <%--<script src="<c:url value="/resources/js/jquery-ui.min.js" />" type="text/javascript"></script>--%>
    <script src="<c:url value="/resources/js/bootstrap.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/js/jquery.validate.js" />" type="text/javascript"></script>
    <script src="<c:url value="/resources/js/jquery.raty.js"/>" type="text/javascript"></script>


    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
</head>
<body>

<div class="container bs-docs-container">
    <div class="page-header">
        <tiles:insertAttribute name="header"/>
    </div>
    <div class="row">
        <tiles:importAttribute name="menu" scope="page" ignore="true"/>
        <c:set var="mainDivClass" value="col-md-12"/>
        <c:if test="${ pageScope.menu!=null}">
            <c:set var="mainDivClass" value="col-md-9"/>
            <div class="col-md-3">
                <div class="bs-sidebar hidden-print" role="complementary">
                    <tiles:insertAttribute name="menu" ignore="true"/>
                </div>
            </div>
        </c:if>
        <div class="${mainDivClass}" role="main">
            <tiles:insertAttribute name="body"/>
        </div>
    </div>
</div>

</body>
</html>