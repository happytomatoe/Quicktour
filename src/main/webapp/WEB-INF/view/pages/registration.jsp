<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<form:form commandName="user" name="user" id="registrationForm" modelAttribute="user" method="POST" cssClass="form-signin"
           enctype="multipart/form-data" style="width:50%; align:'center';">
    <div>
        <fieldset>
            <div class="form-group">
                <p><form:label name="login" path="login" cssClass="form-signin-heading">Login*</form:label>
                    <form:input name="login" id="login" path="login" size="30" maxlength="30" cssClass="form-control"
                                placeholder="Enter your login" required="true"/>
                    <form:errors path="login" cssClass="alert-danger"/></p>

                <p><form:label name="name" path="name" cssClass="form-signin-heading">Name*</form:label>
                    <form:input name="name" id="name" path="name" size="30" maxlength="30" cssClass="form-control"
                                placeholder="Enter your name" required="true"/>
                    <form:errors path="name" cssClass="alert-danger"/></p>

                <p><form:label name="surname" path="surname" cssClass="form-signin-heading">Surname*</form:label>
                    <form:input name="surname" id="surname" path="surname" size="30" maxlength="30" cssClass="form-control"
                                placeholder="Enter your surname" required="true"/>
                    <form:errors path="surname" cssClass="alert-danger"/></p>

                <p><form:label name="email" path="email" cssClass="form-signin-heading">Email*</form:label>
                    <form:input name="email" id="email" path="email" size="30" maxlength="30" cssClass="form-control"
                                placeholder="Input your email" required="true" type="email"/>
                    <form:errors path="email" cssClass="alert-danger"/></p>

                <p><form:label name="phone" path="phone" cssClass="form-signin-heading">Phone*</form:label>
                    <form:input name="phone" id="phone" path="phone" size="30" maxlength="30" cssClass="form-control"
                                placeholder="Input your phone" required="true"/>
                    <form:errors path="phone" cssClass="alert-danger"/></p>

                <p><form:label name="age" path="age" cssClass="form-signin-heading">Age</form:label>
                    <form:input name="age" id="age" path="age" size="30" maxlength="30" cssClass="form-control"
                                placeholder="Input your age"/>
                    <form:errors path="age" cssClass="alert-danger"/></p>

                <p><form:label name="companyCode" path="companyCode" cssClass="form-signin-heading">Company Code</form:label>
                    <form:input name="companyCode" id="companyCode" path="companyCode" size="30" maxlength="30" cssClass="form-control"
                                placeholder="Input your company code"/>
                    <form:errors path="companyCode" cssClass="alert-danger"/></p>

                <p><form:label name="password" path="password" cssClass="form-signin-heading">Password*</form:label>
                    <form:input name="password" id="password" type="password" path="password" size="30" maxlength="30"
                                cssClass="form-control" required="true" placeholder="Input password:"/>
                    <form:errors path="password" cssClass="alert-danger"/></p>

                <p> <form:label name="sex" path="sex" cssClass="form-signin-heading">Sex:</form:label>
                    <form:radiobutton path="sex" value="Other" checked="true"/>Other
                    <form:radiobutton path="sex" value="Male"/>Male
                    <form:radiobutton path="sex" value="Female"/>Female

                    <sec:authorize access="hasRole('admin')">
                        <p> <form:label name="role" path="roleId.role" cssClass="form-signin-heading">Role:</form:label>
                                <form:radiobutton path="roleId.role" value="user" checked="true"/>User
                                <form:radiobutton path="roleId.role" value="agent"/>Agent
                                <form:radiobutton path="roleId.role" value="admin"/>Admin
                        </sec:authorize>
                <p><label name="avatar">Avatar:</label>
                <input name="avatar" type="file"/>
                </p>
                <p> * - required fields.</p>
                <p><input name="commit" type="submit" value="Submit registration" class="btn btn-success"/></p>
            </div>
        </fieldset>
    </div>
</form:form>
