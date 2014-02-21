<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container">
    <br>
    <br>

    <form method="POST" class="form-inline"
          enctype="multipart/form-data">
        <input class="input-lg form-control" id="userInfo" name="userInfo" size="30" maxlength="30"
               placeholder="Input your email or username" required="true" type="email"/>
        <br>
        <br>

        <div class="text-center">
            <button class="btn btn-lg btn-success " type="submit">Send email</button>
        </div>
    </form>
</div>
