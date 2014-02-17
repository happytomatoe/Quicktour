<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<script src="<c:url value="/resources/js/toogleActiveInTour.js"/> "></script>

<div class="col-md-12">
    <table class="table table-hover table-bordered">
        <thead>
        <th>Tour id</th>
        <th>Tour name</th>
        <th>Tour price</th>
        </thead>
        <tbody>
        <c:forEach items="${tours}" var="tour">
            <tr>
                <td>${tour.tourId}</td>
                <td>${tour.name}</td>
                <td>${tour.price}</td>
                <td>
                    <button type="button" onclick="toggleActive(this.id)" id="${tour.tourId}"
                            <c:choose>
                                <c:when test="${tour.active}">
                                    class="btn btn-warning">Deactivate
                                </c:when>
                                <c:otherwise>
                                    class="btn btn-success" > Activate
                                </c:otherwise>
                            </c:choose>
                    </button>
                </td>
                <td>
                    <a href="<c:url value="/tours/edit/${tour.tourId}"/> " class="btn btn-success">Edit</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>