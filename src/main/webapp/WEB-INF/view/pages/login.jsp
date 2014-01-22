<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/signin.css"/>">
</head>
<body>
<div class="container">
    <form class="form-signin" name="f" method="POST"
          action="<c:url value="/j_spring_security_check"/>">
        <h2 class="form-signin-heading">Please sign in</h2>
        <c:if test="${param.fail eq true}">
            <p class="text-danger">Login failed!</p>
            Reason:${SPRING_SECURITY_LAST_EXCEPTION.message}
        </c:if>
        <input class="form-control" type="text"
               autofocus="" required=""
               placeholder="Username"
               name="j_username">
        <input class="form-control" type="password"
               required="" placeholder="Password"
               name="j_password">
        <label class="checkbox">
            <input type="checkbox" value="remember-me">
            Remember me
        </label>
        <button class="btn btn-lg btn-success" type="submit">Sign in</button>
        <a href="/passwordrecovery">Forgot your password?</a>
    </form>
</div>
</body>
</html>