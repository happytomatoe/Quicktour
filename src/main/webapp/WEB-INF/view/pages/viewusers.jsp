<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container">
    <c:forEach items="${users}" var="user">
        <div class="row featurette">
            <div class="col-md-2">
                <img src="/images/<c:out value="${user.photosId.photoUrl}"/>" style='width:auto; height:100px'>
            </div>
            <div class="col-md-7">
                <h2 class="featurette-heading">
                    ${user.login}
                    <span class="text-muted">${user.roleId.role}</span>
                </h2>
                <p class="lead">${user.name}, ${user.surname}, ${user.email}, ${user.phone},
                 ${user.age}, ${user.sex}</p>
            </div>
            <div class="col-md-3">
                <a href="/edituser/<c:out value="${user.id}"/>"class="btn btn-warning">
                    Edit
                </a>
            </div>
        </div>
    </c:forEach>
</div>
