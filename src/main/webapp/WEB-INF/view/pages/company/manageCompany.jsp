<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="<c:url value="/resources/js/jasny-bootstrap.min.js"/> "></script>
<link rel="stylesheet" href="<c:url value="/resources/css/jasny-bootstrap.min.css"/>"/>
<script type="text/javascript">
    function generate() {
        var text = "";
        var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        for (var i = 0; i < 15; i++)
            text += possible.charAt(Math.floor(Math.random() * possible.length));
        document.getElementById("code").value = text;
        return text;
    }
</script>
<div class="row col-md-6" style="align:center">
    <form:form commandName="company" name="company" id="registrationForm" modelAttribute="company" method="POST"
               cssClass="form-signin"
               enctype="multipart/form-data">
        <div>
            <fieldset>
                <div class="form-group">
                    <form:hidden path="companyId"/>
                    <p><form:label name="name" path="name" cssClass="form-signin-heading">Name*</form:label>
                        <form:input name="name" id="name" path="name" size="30" maxlength="30" cssClass="form-control"
                                    placeholder="Enter your name" required="true"/>
                        <form:errors path="name" cssClass="alert-danger"/></p>

                    <p><form:label name="address" path="address" cssClass="form-signin-heading">Address*</form:label>
                        <form:input name="address" id="address" path="address" size="30" maxlength="30"
                                    cssClass="form-control"
                                    placeholder="Enter company address" required="true"/>
                        <form:errors path="address" cssClass="alert-danger"/></p>

                    <p><form:label name="license" path="license" cssClass="form-signin-heading">License*</form:label>
                        <form:input name="license" id="license" path="license" size="30" maxlength="30"
                                    cssClass="form-control"
                                    placeholder="Enter company license" required="true"/>
                        <form:errors path="license" cssClass="alert-danger"/></p>

                    <p><form:label name="contactEmail" path="contactEmail"
                                   cssClass="form-signin-heading">Email*</form:label>
                        <form:input name="contactEmail" id="contactEmail" path="contactEmail" size="30" maxlength="30"
                                    cssClass="form-control"
                                    placeholder="Input company contact email" required="true" type="email"/>
                        <form:errors path="contactEmail" cssClass="alert-danger"/></p>

                    <p><form:label name="contactPhone" path="contactPhone"
                                   cssClass="form-signin-heading">Phone:*</form:label>
                        <form:input name="contactPhone" id="contactPhone" path="contactPhone" size="30" maxlength="30"
                                    cssClass="form-control"
                                    placeholder="Input company contact phone" required="true"/>
                        <form:errors path="contactPhone" cssClass="alert-danger"/></p>

                    <p><form:label name="discount" path="discount"
                                   cssClass="form-signin-heading">Discount:*</form:label>
                        <form:input name="discount" id="discount" path="discount" size="30"
                                    maxlength="30" cssClass="form-control"
                                    placeholder="Input company discount amount" type="number"/>
                        <form:errors path="discount" cssClass="alert-danger"/></p>

                    <p>
                        <form:label name="companyCode" path="companyCode" cssClass="form-signin-heading">Company
                        Code:*</form:label>

                    <div class="input-group">
                            <span class="input-group-btn">
                            <button class="btn btn-default" id="generateCode" type="button" onclick="generate()">
                                Generate!
                            </button>
                            </span>
                        <form:input name="companyCode" id="code" path="companyCode" size="30" maxlength="30"
                                    cssClass="form-control"
                                    placeholder="Input your company code"/>
                        <form:errors path="companyCode" cssClass="alert-danger"/>
                    </div>

                    <form:label path="information" for="editor1">
                        Company description
                    </form:label>
                    <form:textarea path="information" rows="10" cols="128"/>
                    <form:errors path="information"/>

                    <p>
                        <form:select path="type" name="type" class="form-control">
                        <option value="Tour Agency">Tour Agency</option>
                        <option value="IT Company">IT Company</option>
                        <option value="Manufacture">Manufacture</option>
                        <option value="Education">Education</option>
                        <option value="Medicine">Medicine</option>
                        <option value="Police">Police</option>
                        </form:select>

                    <p><label for="avatar">Logo:</label>

                    <div class="fileinput fileinput-new" data-provides="fileinput">
                        <div class="fileinput-preview fileinput-exists thumbnail"
                             style="max-width: 200px; max-height: 150px;"></div>
                        <c:if test="${company.photo.url!=null}">
                            <div class="fileinput-new thumbnail" style="width: 200px; height: 150px;">
                                <img src="${company.photo.url}">
                            </div>
                        </c:if>

                        <div>
                    <span class="btn btn-default btn-file"><span class="fileinput-new">Select image</span><span
                            class="fileinput-exists">Change</span><input type="file" accept="image/*"
                                                                         name="avatar" id="avatar"></span>
                            <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
                        </div>
                    </div>
                    <form:errors path="photo" cssClass="alert-danger"/>
                    </p>
                    <p> * - required fields.</p>

                    <p>
                        <button name="commit" type="submit" class="btn btn-success">Save</button>
                    </p>
                </div>
            </fieldset>
        </div>
    </form:form>
</div>
