<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
                    <p><label name="name" path="name" cssClass="form-signin-heading">Name*</label>
                        <form:input name="name" id="name" path="name" size="30" maxlength="30" cssClass="form-control"
                                    placeholder="Company name" required="true"/>
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
                        <button class="btn btn-default" id="generateCode" type="button" onclick="generate()">Generate!
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

                    <p><label name="avatar">Logo:</label>
                        <img src="/images/<c:out value='${company.photo.url}'/>" style='width:auto; height:100px'>
                        <input name="avatar" type="file" value="Upload new"/>
                    </p>

                    <p> * - required fields.</p>

                    <p><input name="commit" type="submit" value="Save changes" class="btn btn-success"/></p>
                </div>
            </fieldset>
        </div>
    </form:form>
</div>