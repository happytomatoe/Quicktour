<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title><tiles:getAsString name="title"/></title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.css"/>">
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">

    <script src="<c:url value="/resources/js/jquery.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/js/jquery.MetaData.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/js/jquery-ui.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/js/bootstrap.min.js"/>" type="text/javascript"></script>

    <tiles:insertAttribute name="meta-info"/>

    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->

</head>
<body>

<div class="container">
    <div class="page-header">

        <tiles:insertAttribute name="header"/>

    </div>

    <tiles:insertAttribute name="body"/>

</div>

</body>
</html>