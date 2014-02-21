<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="<c:url value="/resources/js/signin.js"/> "></script>

<div class="row">
    <div class="col-md-4 col-md-offset-4">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Signin via site</h3>
            </div>
            <div class="panel-body">
                ${activationMessage}
                <form accept-charset="UTF-8" role="form" action="<c:url value="/j_spring_security_check"/>"
                      method="post">
                    <fieldset>

                        <c:if test="${param.fail eq true}">
                            <div class="form-group text-danger text-center">
                                <h5> Your signin attempt was not successful, try again</h5>
                                Reason :
                                    ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
                            </div>
                            <c:if test="${not empty activationMessage}">
                                <div class="form-group text-success text-center">
                                        ${activationMessage}
                                </div>
                            </c:if>
                        </c:if>
                        <div class="form-group">
                            <input class="form-control" placeholder="Username" name="j_username" type="text"
                                   required="" autofocus="">
                        </div>
                        <div class="form-group">
                            <input class="form-control" placeholder="Password" name="j_password" type="password"
                                   required>
                        </div>
                        <div class=" checkbox">
                            <label>
                                <input name="_spring_security_remember_me" type="checkbox"> Remember Me
                            </label>
                            <a href="<c:url value="/passwordrecovery"/>" class="pull-right">Forgot your password?</a>

                        </div>
                        <input class="btn btn-lg btn-success btn-block" type="submit" value="Login">
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>