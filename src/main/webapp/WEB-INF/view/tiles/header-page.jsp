<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <a href="<c:url value="/"/> " class="navbar-brand">TourServe</a>
        <button class="navbar-toggle" data-toggle="collapse" data-target=".navHeaderCollapse">
            Expand
        </button>
        <div class="collapse navbar-collapse navHeaderCollapse">
            <ul class="nav navbar-nav navbar-right">
                <sec:authorize access="hasAnyRole('admin', 'agent', 'user')">
                    <li><a href="/orders">Orders</a></li>
                    <li><a href="#">History</a></li>
                </sec:authorize>
                <sec:authorize access="hasRole('agent')">
                    <li>
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                            Discount
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="<c:url value="/discount_policy"/>">Discount Policy</a>
                            </li>
                            <li>
                                <a href="<c:url value="/applyDiscount"/>">Apply Discount policy</a>
                            </li>

                        </ul>
                    </li>

                    <li>
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                            Agency history
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="#">Tour History</a>
                            </li>
                            <li>
                                <a href="#">Order History</a>
                            </li>


                        </ul>
                    </li>
                </sec:authorize>
                <sec:authorize access="hasRole('admin')">
                    <li>
                        <a href="<c:url value="/discount_dependency"/>">Discount dependency</a>
                    </li>
                    <li>
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                            View all ...
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="<c:url value="/viewusers"/>">Users</a>
                            </li>
                            <li>
                                <a href="<c:url value="/viewcompanies"/>">Companies</a>
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
                                <a href="/addcompany">Company</a>
                            </li>
                            <li>
                                <a href="/registration">User</a>
                            </li>
                        </ul>
                    </li>
                </sec:authorize>
                <sec:authorize access="hasAnyRole('admin', 'agent')">
                    <li>
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                            Manage tours
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="<c:url value="/agent/manageTours" />" >Create New Tour...</a>
                            </li>
                            <li>
                                <a href="<c:url value="/agent/showOwnTours" />">Show my tours</a>
                            </li>
                        </ul>
                    </li>
                </sec:authorize>
                <sec:authorize access="isAnonymous()">
                    <li>
                        <a href="<c:url value="/registration"/>">Registration</a>
                    </li>
                    <li>
                        <a href="<c:url value="/login"/>">Login</a>
                    </li>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <li>
                    <a class="dropdown-toggle glyphicon glyphicon-user" data-toggle="dropdown" href="#">
                        <sec:authentication property="principal.username" />
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="/myprofile">My Profile</a>
                        </li>
                        <li>
                            <a href="<c:url value="/mycompany"/>">My company</a>
                        </li>
                        <li>
                            <a href="<c:url value="/j_spring_security_logout"/>">Logout</a>
                        </li>
                    </ul>
                    </li>
                </sec:authorize>
            </ul>
        </div>
    </div>
</div>