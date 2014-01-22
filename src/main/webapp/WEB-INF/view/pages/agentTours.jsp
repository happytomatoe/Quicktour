<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<script>
    function deactivateTour(tourId) {
        var currentUrl = window.location.href;
        var url = currentUrl.substring(0, currentUrl.lastIndexOf("/"));
        jQuery.ajax({
            url: url + "/manageTours/deactivate",
            data: {id: tourId},
            type: "POST",
            timeout: 4000,
            success: function(map) {
                if (map.status) {
                    var button = document.getElementById(tourId);
                    button.setAttribute("class", "btn btn-success");
                    button.setAttribute("onclick", "activateTour(this.id)");
                    button.value = "Activate";
                }
            },
            error: function(xhr, status) {},
            complete: function(data) {
            }
        });
    }

    function activateTour(tourId) {
        var currentUrl = window.location.href;
        var url = currentUrl.substring(0, currentUrl.lastIndexOf("/"));
        jQuery.ajax({
            url: url + "/manageTours/activate",
            data: {id: tourId},
            type: "POST",
            timeout: 4000,
            success: function(map) {
                if (map.status) {
                    var button = document.getElementById(tourId);
                    button.setAttribute("class", "btn btn-warning");
                    button.setAttribute("onclick", "deactivateTour(this.id)");
                    button.value = "Deactivate";
                }
            },
            error: function(xhr, status) {},
            complete: function(data) {
            }
        });
    }
</script>

<div class="col-md-12">
<table class="table table-hover table-bordered">
    <thead>
    <tr>
        <td>Tour id</td>
        <td>Tour name</td>
        <td>Tour price</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${tours}" var="tour">
        <s:url value="/agent/manageTours/{tourId}" var="edit">
            <s:param name="tourId" value="${tour.tourId}" />
        </s:url>
        <tr>
            <td>${tour.tourId}</td>
            <td>${tour.name}</td>
            <td>${tour.price}</td>
            <c:choose>
                <c:when test="${tour.active}">
                    <td>
                        <s:url value="/agent/manageTours/deactivate/{tourId}" var="deactivate">
                            <s:param name="tourId" value="${tour.tourId}" />
                        </s:url>
                        <input type="button" class="btn btn-warning" onclick="deactivateTour(this.id)"
                                id="${tour.tourId}" value="Deactivate"/>
                    </td>
                </c:when>
                <c:otherwise>
                    <td>
                        <s:url value="/agent/manageTours/activate/{tourId}" var="activate">
                            <s:param name="tourId" value="${tour.tourId}" />
                        </s:url>
                        <input type="button" class="btn btn-success" onclick="activateTour(this.id)"
                               id="${tour.tourId}" value="Activate"/>
                    </td>
                </c:otherwise>
            </c:choose>

            <td>
                <a href="${edit}" class="btn btn-success">Edit</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</div>