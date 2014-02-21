<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="<c:url value="/resources/js/jasny-bootstrap.min.js"/> "></script>
<link rel="stylesheet" href="<c:url value="/resources/css/jasny-bootstrap.min.css"/>"/>

<form:form commandName="user" name="user" id="myProfileForm" modelAttribute="user" method="POST" cssClass="form-signin"
           style="width:50%; align:'center';"
           enctype="multipart/form-data">
    <div>
        <fieldset>
            <div class="form-group">
                <div class="row text-center">
                    <label cssClass="form-signin-heading">Username:</label>
                        ${user.username}
                </div>
                <form:hidden path="enabled"/>
                <div class="row">
                    <div class="col-md-6">
                        <form:label path="name" cssClass="form-signin-heading text-center">Name*</form:label>
                        <form:input id="name" path="name" size="30" maxlength="30" tabindex="1"
                                    cssClass="form-control input-lg"
                                    required="true"/>
                    </div>
                    <div class="col-md-6">
                        <form:label path="surname" cssClass="form-signin-heading text-center">Surname*</form:label>
                        <form:input id="surname" path="surname" size="30" maxlength="30" tabindex="2"
                                    cssClass="form-control input-lg" required="true"/>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <form:label path="email" cssClass="form-signin-heading text-center">Email*</form:label>
                        <form:input id="email" path="email" size="30" maxlength="30" tabindex="3"
                                    cssClass="form-control input-lg" required="true" type="email"/>
                    </div>
                    <div class="col-md-6">
                        <form:label path="phone" cssClass="form-signin-heading text-center">Phone*</form:label>
                        <form:input id="phone" path="phone" size="30" maxlength="30" tabindex="4"
                                    cssClass="form-control input-lg" required="true"/>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <form:label path="age" cssClass="form-signin-heading text-center">Age</form:label>
                        <form:input id="age" path="age" size="30" maxlength="30" tabindex="5"
                                    cssClass="form-control input-lg"/>
                    </div>
                    <div class="col-md-6">
                        <form:label path="companyCode" cssClass="form-signin-heading">Company Code</form:label>
                        <form:input id="companyCode" path="companyCode" size="30" maxlength="30" tabindex="6"
                                    cssClass="form-control input-lg"/>
                    </div>
                </div>
                <div class="row">
                    <form:label path="gender" cssClass="form-signin-heading">Gender:</form:label>
                    <br>
                    <select name="gender" class=" form-control input-lg" tabindex="7">
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
                </div>
            </div>
            <div>
                <label name="avatar">Avatar:</label>
                <form:hidden path="photo.photoId"/>
            </div>
            <div class="fileinput fileinput-new" data-provides="fileinput">
                <c:if test="${user.photo.url!=null}">
                    <div class="fileinput-new thumbnail" style="width: 200px; height: 150px;">
                        <img src="${user.photo.url}">
                    </div>
                </c:if>
                <div class="fileinput-preview fileinput-exists thumbnail"
                     style="max-width: 200px; max-height: 150px;"></div>
                <div>
                    <span class="btn btn-default btn-file"><span class="fileinput-new">Select image</span><span
                            class="fileinput-exists">Change</span><input type="file" accept="image/*" name="avatar"
                                                                         tabindex="11"></span>
                    <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
                </div>
            </div>
                <form:errors path="photo" cssClass="alert-danger"/>

    </div>

    <div> * - required fields.</div>

    <div><input name="commit" type="submit" value="Save Changes" class="btn btn-success" tabindex="8"/></div>
    </div>
    </fieldset>
    </div>
</form:form>
