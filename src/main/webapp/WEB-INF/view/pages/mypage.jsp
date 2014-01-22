<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: student
  Date: 12/4/13
  Time: 1:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<table class="table table-striped table-bordered">
    <thead>
    <tr>
        <th>Id</th>
        <th>Name</th>

    </tr>
    </thead>
    <tbody>
    <c:forEach items="${controller_backs}" var="someform">
        <tr>
            <td width="100px">${someform.id}</td>
            <td width="100px">${someform.name}</td>
            <td width="100px"> <img src=" ${someform.photosId.photoUrl}"
                    width="100px"/>  </td>

<%--
            <c:forEach items="${someform.photosId}" var="request">
                <td width="100px">${request.id}</td>
                <td width="100px">${request.date}</td>
                <td width="100px">${request.status}</td>
            </c:forEach>
--%>




        </tr>
    </c:forEach>
    </tbody>

</table>
</body>
</html>