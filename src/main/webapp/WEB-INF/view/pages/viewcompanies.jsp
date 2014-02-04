<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<div class="container">
    <c:forEach items="${companies}" var="company">
        <div class="row featurette">
            <div class="col-md-2">
                <img src="/images/<c:out value='${company.photo.url}'/>" style='width:auto; height:100px'>
            </div>
            <div class="col-md-7">
                <h2 class="featurette-heading">
                        ${company.name}
                    <span class="text-muted">${company.type}</span>
                </h2>

                <p class="lead">Company code: ${company.companyCode}</p>
            </div>
            <div class="col-md-3">
                <a href="<c:url value="/editcompany/${company.companyId}"/>" class="btn btn-warning">
                    Details
                </a>
            </div>
        </div>
    </c:forEach>
</div>
</body>
</html>