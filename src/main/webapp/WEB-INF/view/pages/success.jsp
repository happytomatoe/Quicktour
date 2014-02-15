<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">
    <div class="jumbotron">
        <c:if test="${messageHeader!=null}">
            <h1>${messageHeader}</h1>
        </c:if>
        <p class="lead">${message}</p>

        <p><a class="btn btn-lg btn-success" href="<c:url value="/"/>" role="button">Main page</a></p>
    </div>
</div>
