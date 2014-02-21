<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="<c:url value="/resources/css/jasny-bootstrap.min.css"/>"/>
<script src="<c:url value="/resources/js/jasny-bootstrap.min.js"/> "></script>

<form:form name="user" id="myProfileForm" enctype="multipart/form-data" modelAttribute="user" method="POST"
           cssClass="form-signin"
           style="width:50%; align:'center';">
    <div>
        <fieldset>
            <div class="form-group">
                <p>Username:${user.username}
                        <form:hidden path="username"/>
                        <form:hidden path="userId"/>
                        <form:hidden path="enabled"/>

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
                    <% pageContext.setAttribute("genders", new String[]{"", "Male", "Female"}); %>
                    <form:label path="gender" clas="form-signin-heading">Gender:</form:label>
                    <form:select path="gender" items="${genders}" cssClass="form-control input-lg"/>
                </p>

                <div class="form-group ">
                    <h4>Avatar</h4>
                    <form:hidden path="photo.photoId"/>
                    <div class="fileinput fileinput-new" data-provides="fileinput">

                        <c:if test="${user.photo.url!=null}">
                            <div class="fileinput-new thumbnail" style="width: 200px; height: 150px;">
                                <img src="${user.photo.url}">
                            </div>
                        </c:if>
                        <div>
                    <span class="btn btn-default btn-file"><span class="fileinput-new">Select image</span><span
                            class="fileinput-exists">Change</span><input type="file" accept="image/*" name="avatar"
                                                                         tabindex="11"></span>
                            <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
                        </div>
                        <div class="fileinput-preview fileinput-exists thumbnail"
                             style="max-width: 320px; max-height: 320px;">


                        </div>

                    </div>
                    <form:errors path="photo" cssClass="alert-danger"/>
                </div>

                <form:label path="role" clas="form-signin-heading">Role:</form:label>
                <div class="form-group">
                    <form:select path="role.roleId" class="form-control input-lg">
                        <c:forEach items="${roles}" var="role">
                            <option value="${role.roleId}"
                                    <c:if test="${role.roleId==user.role.roleId}">
                                        selected="selected"
                                    </c:if>
                                    >${role.name}
                            </option>
                        </c:forEach>

                    </form:select>
                </div>

                <p> * - required fields.</p>

                <p><input name="commit" type="submit" value="Save Changes" class="btn btn-success"/></p>
            </div>
        </fieldset>
    </div>
</form:form>
