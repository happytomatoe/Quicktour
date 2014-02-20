<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <a href="<c:url value="/"/>" class="navbar-brand">TourServe</a>
        <button class="navbar-toggle" data-toggle="collapse" data-target=".navHeaderCollapse">
            Expand
        </button>
        <div class="collapse navbar-collapse navHeaderCollapse">
            <ul class="nav navbar-nav navbar-right">
                <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_AGENT', 'ROLE_USER')">
                    <li><a href="<c:url value="/orders"/>">Orders</a></li>
                </sec:authorize>
                <sec:authorize access="hasRole('ROLE_AGENT')">
                    <li>
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                            Discount
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="<c:url value="/discount_policy"/>">Discount policies</a>
                            </li>
                            <li>
                                <a href="<c:url value="/applyDiscount"/>">Apply discount policy</a>
                            </li>

                        </ul>
                    </li>

                </sec:authorize>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <li>
                        <a href="<c:url value="/discount_dependency"/>">Discount dependencies</a>
                    </li>
                    <li>
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                            View all ...
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="<c:url value="/users"/>">Users</a>
                            </li>
                            <li>
                                <a href="<c:url value="/company"/>">Companies</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                            Add new...
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="<c:url value="/company/add"/>">Company</a>
                            </li>
                            <li>
                                <a href="<c:url value= "/signup"/>">User</a>
                            </li>
                        </ul>
                    </li>
                </sec:authorize>
                <sec:authorize access="hasAnyRole( 'ROLE_AGENT')">
                    <li>
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                            Manage tours
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="<c:url value="/tours/add" />">Create new tour</a>
                            </li>
                            <li>
                                <a href="<c:url value="/tours" />">Tours</a>
                            </li>
                        </ul>
                    </li>
                </sec:authorize>
                <sec:authorize access="isAnonymous()">
                    <li>
                        <a href="<c:url value="/signup"/>">Signup</a>
                    </li>
                    <li>
                        <a href="<c:url value="/signin"/>">Signin</a>
                    </li>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <li>
                        <a class="dropdown-toggle glyphicon glyphicon-user" data-toggle="dropdown" href="#">
                            <sec:authentication property="principal.username"/>
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="<c:url value="/profile"/>">Profile</a>
                            </li>
                            <sec:authorize access="!hasRole('ROLE_ADMIN')">
                                <li>
                                    <a href="<c:url value="/mycompany"/>">Company</a>
                                </li>
                            </sec:authorize>
                            <li>
                                <a href="<c:url value="/j_spring_security_logout"/>">Signout</a>
                            </li>
                        </ul>
                    </li>
                </sec:authorize>
            </ul>
        </div>
    </div>
</div>