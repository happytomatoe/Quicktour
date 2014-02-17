<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
    <div class="col-md-4 col-md-offset-4">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Change password</h3>
            </div>
            <div class="panel-body">
                <form accept-charset="UTF-8" role="form" method="post">
                    <fieldset>
                        <div class="form-group">
                            <input class="form-control" placeholder="New password" name="password" type="password">
                        </div>
                        <c:if test="${passwordError!=null}">
                            <span class="text-danger">${passwordError}</span>
                        </c:if>
                        <div class="form-group">
                            <input class="form-control" placeholder="New password again" name="password2"
                                   type="password" value="">
                        </div>
                        <c:if test="${password2Error!=null}">
                            <span class="text-danger">${password2Error}</span>
                        </c:if>

                        <input class="btn btn-lg btn-success btn-block" type="submit" value="OK">
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>