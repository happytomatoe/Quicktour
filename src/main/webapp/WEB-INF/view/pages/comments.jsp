<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="row">
    <h3>Comments:</h3>
    <table class="table table-striped" id="tableComments"  style="width: auto">

        <thead></thead>
        <tbody>

        <c:forEach items="${page.content}" var="comments">
            <tr class="success">
                <td>
                        ${comments.user.login}
                </td>
                <td>
                    <c:if test="${comments.user.roleId.role == 'user' }">
                    <!-- Star rating -->
                    <div class="col-md-3">
                        <span id="rateComments_${comments.commentId}"></span>
                        <span><i>(${ordersService.getRatioCount(tour.tourId, comments.user.id)})</i></span>
                    </div>
                    <script type="text/javascript" id="rateScript" style:visibility="true">
                        jQuery('#rateComments_${comments.commentId}').raty({
                            readOnly: true,
                            scoreName: 'Tour.ratio',
                            score:     ${ordersService.getRatio(tour.tourId, comments.user.id)},
                            number: 5

                        });
                    </script>
                    <!-- /Star rating -->
                    </c:if>
                    <span class="pull-right">
                <i><spring:eval expression="comments.commentDate"/></i>
                    </span>
                </td>
            </tr>

            <tr>
                <td>
                    <img class="img-responsive" src="<c:url value="/images/"/><c:url value=" ${comments.user.photosId.photoUrl}"/>" width="100px"/>
                </td>
                <td style="width: 100%">
                        <div id="<spring:eval expression="comments.commentId"/>">${comments.comment}</div>
                </td>
            </tr>
            <sec:authorize access="isAuthenticated()">
                <tr>
                    <td></td>
                    <td>
                        <span class="pull-right">
                               <sec:authorize access="!(principal.username=='${comments.user.login}') ">
                                   <button class="btn btn-sm btn-success" onclick="answerUser('${comments.user.login}')">Answer</button>
                               </sec:authorize>
                               <sec:authorize access="principal.username=='${comments.user.login}' ">
                                   <button class="btn btn-sm btn-success" onclick="prepareText(<spring:eval expression="comments.commentId"/>)">Edit..<span class="glyphicon glyphicon-pencil"></span></button>
                               </sec:authorize>
                        </span>
                    </td>
                </tr>
                <tr></tr>
            </sec:authorize>
        </c:forEach>
        </tbody>
    </table>
    <ul class="pagination" id="pagination">
        <c:if test="${page.totalPages > 1}">
            <c:if test="${page.number > 0}">
                <li onclick="getPage(0)" >
                    <a>&laquo</a>
                </li>
            </c:if>
            <c:forEach var="i" begin="0" end="${page.totalPages-1}">
                <spring:url  value="/tour/{id}/getComments" var="pageNumber">
                    <spring:param name="id" value="${tour.tourId}" />
                </spring:url>
                <c:choose>
                    <c:when test="${page.number == i}">
                        <c:set var="buttClass" value="active" />
                    </c:when>
                    <c:otherwise>
                        <c:set var="buttClass" value="" />
                    </c:otherwise>
                </c:choose>
                <c:if test="${((page.number - i) ge 0) && ((page.number - i) lt 2 )}">
                    <li onclick="getPage(<spring:eval expression="${i}"/>)" class="${buttClass}">
                        <a>
                            <c:out value="${i+1}" />
                        </a>
                    </li>
                </c:if>
                <c:if test="${((i - page.number) gt 0) && ((i - page.number) lt 2)}">
                    <li onclick="getPage(<spring:eval expression="${i}"/>)" class="${buttClass}">
                        <a>
                            <c:out value="${i+1}"/>
                        </a>
                    </li>
                </c:if>
            </c:forEach>
            <c:if test="${page.number < page.totalPages - 1}">
                <li onclick="getPage(<spring:eval expression="${page.totalPages-1}"/>)">
                    <a>&raquo</a>
                </li>
            </c:if>
        </c:if>
    </ul>

    <sec:authorize access="isAuthenticated()">
        <div class="form-group">
            <label>Comments</label>
            <textarea id="textOfComment" required="true" class="form-control" style="resize: none" rows="6"></textarea>
        </div>
        <span class="pull-right">
            <button class="btn btn-success btn-sm" id="buttonEdit" style="display: none">Edit</button>
            <button class="btn btn-success btn-sm" onclick="addNewComment()" id="buttonSave">Save Comment</button>
            <button class="btn btn-success btn-sm" onclick="clearField()">Clear</button>
         </span>
    </sec:authorize>
    <sec:authorize access="isAnonymous()">
        <p></p>
        <div class="col-sm-12">
            <div class="row well no_margin_left">
                <footer class="align-center">
                    <p>If you want to leave comment you must sign in or register.</p>
                </footer>
            </div>
        </div>
    </sec:authorize>
</div>