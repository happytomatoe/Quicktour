<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<form:form name="user" id="myProfileForm" modelAttribute="user" method="POST" cssClass="form-signin"
           style="width:50%; align:'center';">
    <div>
        <fieldset>
            <div class="form-group">
                <p>${user.login}

                <p>
                    <form:label path="name" cssClass="form-signin-heading">Name*</form:label>
                        <form:input id="name" path="name" size="30" maxlength="30" cssClass="form-control"
                                    placeholder="Enter your name" required="true"/>

                <p>
                    <form:label path="surname" cssClass="form-signin-heading">Surname*</form:label>
                        <form:input id="surname" path="surname" size="30" maxlength="30" cssClass="form-control"
                                    required="true"/>

                <p>
                    <form:label path="email" cssClass="form-signin-heading">Email*</form:label>
                        <form:input id="email" path="email" size="30" maxlength="30" cssClass="form-control"
                                    required="true" type="email"/>

                <p>
                    <form:label path="phone" cssClass="form-signin-heading">Phone*</form:label>
                        <form:input id="phone" path="phone" size="30" maxlength="30" cssClass="form-control"
                                    required="true"/>

                <p>
                    <form:label path="age" cssClass="form-signin-heading">Age</form:label>
                        <form:input id="age" path="age" size="30" maxlength="30" cssClass="form-control"/>

                <p>
                    <form:label path="companyCode" cssClass="form-signin-heading">Company Code</form:label>
                        <form:input id="companyCode" path="companyCode" size="30" maxlength="30"
                                    cssClass="form-control"/>

                <p><form:label path="sex" cssClass="form-signin-heading">Sex:</form:label>
                    <form:radiobutton path="sex" value=""/>
                    <form:radiobutton path="sex" value="Male"/>Male
                    <form:radiobutton path="sex" value="Female"/>Female
                </p>

                <p>

                <p> * - required fields.</p>

                <p><input name="commit" type="submit" value="Save Changes" class="btn btn-success"/></p>
            </div>
        </fieldset>
    </div>
</form:form>
