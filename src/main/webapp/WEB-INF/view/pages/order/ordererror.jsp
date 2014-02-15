<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container">
    <div class="jumbotron">
        <sec:authorize access="isAnonymous()">
            <p class="lead">It seems that user with your email already exists in the system.

            <p>Please sign in to proceed</p>

            <p><a class="btn btn-lg btn-success" href="/login" role="button">Sign in today</a></p>
        </sec:authorize>
    </div>
</div>
