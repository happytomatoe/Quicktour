
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${company.name != null}">
<div class="row col-md-8" style="align:left">
        <div>
            <fieldset>
                <div class="form-group">
                    <p><h3><label name="name" cssClass="form-signin-heading">Company Name:</label>
                        <span>${company.name}</span></h3>
                       </p>
                </div>
                <div class="form-group">
                    <p><h3><label name="type" cssClass="form-signin-heading">Company Type:</label>
                        <span>${company.type}</span></h3>
                    </p>
                </div>
                <div class="form-group">
                    <p><h3><label name="info" cssClass="form-signin-heading">Company Info:</label>
                        <span>${company.information}</span></h3>
                    </p>
                </div>
                <div class="form-group">
                    <p><h3><label name="address" cssClass="form-signin-heading">Company Address:</label>
                        <span>${company.address}</span></h3>
                    </p>
                </div>
                <div class="form-group">
                    <p><h3><label name="phone" cssClass="form-signin-heading">Company Phone:</label>
                        <span>${company.contactPhone}</span></h3>
                    </p>
                </div>
                <div class="form-group">
                    <p><h3><label name="email" cssClass="form-signin-heading">Company Email:</label>
                        <span>${company.contactEmail}</span></h3>
                    </p>
                </div>
                <div class="form-group">
                    <p><h3><label name="name" cssClass="form-signin-heading">Company Name:</label>
                        <span>${company.name}</span></h3>
                    </p>
                </div>
                <div class="form-group">
                    <p><h3><label name="name" cssClass="form-signin-heading">Company Discount:</label>
                    <span class="btn btn-success">${company.discountAmount} %</span></h3>
                    </p>
                </div>
            </fieldset>

        </div>
</div>
<img src="/images/<c:out value='${company.photosId.photoUrl}'/>" class="col-md-4">
    <label><h2>If you want to change your company, please enter your new company code:</h2></label>
    <form method="post" action="/mycompany">
        <div class="input-group">
            <input type="text" class="form-control" name="newCompanyCode">
      <span class="input-group-btn">
        <input type="submit" class="btn btn-default" value="Go!">
      </span>
        </div>
    </form>

</c:if>

<c:if test="${company.name == null}">
    <div class="jumbotron">
        <h2>Sorry, but no company was found due to your company code</h2>
            <p class="lead">Maybe you have not input company code or you have input incorrect company code</p>
    <label>If you want to change your company, please enter your new company code:</label>
        <form method="post" action="/mycompany">
    <div class="input-group">
            <input type="text" class="form-control" name="newCompanyCode">
      <span class="input-group-btn">
        <input type="submit" class="btn btn-default" value="Go!">
      </span>
    </div>
        </form>
    </div>
</c:if>

