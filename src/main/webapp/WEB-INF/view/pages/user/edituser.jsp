<%@ page import="com.quicktour.Roles" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<form:form name="user" id="myProfileForm" modelAttribute="user" method="POST" cssClass="form-signin"
           style="width:50%; align:'center';">
    <div>
        <fieldset>
            <div class="form-group">
                <p>Login:${user.login}
                    <input type="hidden" name="login" value="${user.login}"/>
                    <input type="hidden" name="userId" value="${user.userId}"/>

                <p>
                    <form:label path="name" cssClass="form-signin-heading">Name*</form:label>
                        <form:input id="name" path="name" size="30" maxlength="30" cssClass="form-control"
                                    placeholder="First name" required="true"/>

                <p>
                    <form:label path="surname" cssClass="form-signin-heading">Surname*</form:label>
                        <form:input id="surname" path="surname" size="30" maxlength="30"
                                    placeholder="Second name" cssClass="form-control"
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

                <p>
                    <% pageContext.setAttribute("sexes", new String[]{"", "Male", "Female"}); %>
                    <form:label path="sex" clas="form-signin-heading">Sex:</form:label>
                    <form:select path="sex" items="${sexes}"/>
                </p>
                <form:label path="role" clas="form-signin-heading">Role:</form:label>
                <c:if test="${user.role !='admin'}">
                <% pageContext.setAttribute("roles", Roles.values()); %>
                <form:select path="role" items="${roles}"/>
                <p>
                    </c:if>

                <p> * - required fields.</p>

                <p><input name="commit" type="submit" value="Save Changes" class="btn btn-success"/></p>
            </div>
        </fieldset>
    </div>
</form:form>
