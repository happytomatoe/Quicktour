<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container">
    <form:form commandName="user" name="user" id="registrationForm" modelAttribute="user" method="POST" cssClass="form-signin"
               enctype="multipart/form-data" style="width:50%; align:'center';">
    <h2 class="form-signin-heading">Please input your email:</h2>
        <p><form:label name="email" path="email" cssClass="form-signin-heading">Email*</form:label>
            <form:input name="email" id="email" path="email" size="30" maxlength="30" cssClass="form-control"
                        placeholder="Input your email" required="true" type="email"/>
            <form:errors path="email" cssClass="alert-danger"/></p>
    <button class="btn btn-lg btn-success" type="submit">Send new password to email!</button>
    </form:form>
</div>
