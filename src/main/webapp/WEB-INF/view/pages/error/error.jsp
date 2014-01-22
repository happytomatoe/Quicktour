<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2 class="text-danger text-center">${message}</h2>
<!--
Failed URL: ${url}
Exception:  ${exception}
Exception:  ${exception.message}
<c:forEach items="${exception.stackTrace}" var="stack">    ${stack}
</c:forEach>
--!>
