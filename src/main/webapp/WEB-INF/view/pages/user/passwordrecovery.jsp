<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container">
    <br>
    <br>
    <form:form name="user" modelAttribute="user" method="POST" cssClass="form-inline"
               enctype="multipart/form-data">
        <form:label name="email" path="email">Email</form:label>
        <form:input id="email" cssClass="input-medium" path="email" size="30" maxlength="30"
                    placeholder="Input your email" required="true" type="email"/>
        <form:errors path="email" cssClass="alert-danger"/>
        <button class="btn btn-success" type="submit">Ok</button>
    </form:form>
</div>
