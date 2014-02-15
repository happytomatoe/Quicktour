<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form:form commandName="user" name="user" id="myProfileForm" modelAttribute="user" method="POST" cssClass="form-signin"
           style="width:50%; align:'center';"
           enctype="multipart/form-data">
    <div>
        <fieldset>
            <div class="form-group">
                <p>
                    <label cssClass="form-signin-heading">Login*:</label>
                        ${user.login}

                <p>
                    <form:label name="name" path="name" cssClass="form-signin-heading">Name*</form:label>
                        <form:input name="name" id="name" path="name" size="30" maxlength="30" cssClass="form-control"
                                    placeholder="Enter your name" required="true"/>

                <p>
                    <form:label name="surname" path="surname" cssClass="form-signin-heading">Surname*</form:label>
                        <form:input name="surname" id="surname" path="surname" size="30" maxlength="30"
                                    cssClass="form-control"
                                    required="true"/>

                <p>
                    <form:label name="email" path="email" cssClass="form-signin-heading">Email*</form:label>
                        <form:input name="email" id="email" path="email" size="30" maxlength="30"
                                    cssClass="form-control"
                                    required="true" type="email"/>

                <p>
                    <form:label name="phone" path="phone" cssClass="form-signin-heading">Phone*</form:label>
                        <form:input name="phone" id="phone" path="phone" size="30" maxlength="30"
                                    cssClass="form-control"
                                    required="true"/>

                <p>
                    <form:label name="age" path="age" cssClass="form-signin-heading">Age</form:label>
                        <form:input name="age" id="age" path="age" size="30" maxlength="30" cssClass="form-control"/>

                <p>
                    <form:label name="companyCode" path="companyCode" cssClass="form-signin-heading">Company
                    Code</form:label>
                        <form:input name="companyCode" id="companyCode" path="companyCode" size="30" maxlength="30"
                                    cssClass="form-control"/>

                <p><form:label name="sex" path="sex" cssClass="form-signin-heading">Sex:</form:label>
                    <select name="sex" class="form-control input-lg">
                        <option value="" disabled selected>
                            Gender
                        </option>
                        <option value="Male">
                            Male
                        </option>
                        <option value="Female">
                            Female
                        </option>
                    </select>
                </p>

                <p><label name="avatar">Avatar:</label>
                    <img src="/images/<c:out value='${user.photo.url}'/>" alt="No avatar"
                         style='width:auto; height:100px'>
                    <input name="avatar" type="file"/>
                </p>

                <p> * - required fields.</p>

                <p><input name="commit" type="submit" value="Save Changes" class="btn btn-success"/></p>
            </div>
        </fieldset>
    </div>
</form:form>