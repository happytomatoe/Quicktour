<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container">
    <c:forEach items="${users}" var="user">
        <div class="row featurette">
            <div class="col-md-2">
                <img src="${user.photo.url}" style='width:auto; height:100px'>
            </div>
            <div class="col-md-7">
                <h2 class="featurette-heading">
                        ${user.login}
                    <span class="text-muted">${user.role}</span>
                </h2>

                <p class="lead">${user.name}, ${user.surname}, ${user.email}, ${user.phone},
                        ${user.age}, ${user.sex}</p>
            </div>
            <c:if test="${user.role!='admin'}">

            <div class="col-md-3">
                <a href="<c:url value="/users/edit/${user.userId}"/>" class="btn btn-warning">
                    Edit
                </a>
                <a href="<c:url value="/users/delete/${user.userId}"/>" class="btn btn-warning">
                    Delete
                </a>
                </c:if>
            </div>
        </div>
    </c:forEach>
</div>
