<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row">
    <div class="col-sm-4 col-md-4">
        <img src="${user.photo.url}"
             class="img-rounded img-responsive" style="width: 500px;height: 500px"/>
    </div>
    <div class="col-sm-8 col-md-8">
        <p>Login:${user.login} </p>

        <p>${user.name} ${user.surname}</p>

        <p><i class="glyphicon glyphicon-envelope"></i> ${user.email}
            <br/>

        <p>Age:${user.age}</p>
        <c:if test="${user.sex!=null}">
            <p>Gender:${user.sex}</p>
        </c:if>
    </div>

</div>