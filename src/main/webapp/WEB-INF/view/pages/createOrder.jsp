<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- Headliner -->
<div class="row">
    <div class="col-sm-12">
        <div class="breadcrumb row">
            <div class="col-sm-5">
                <span class="tour-name"><a href="/tour/${tour.tour.tourId}" target="_blank">${tour.tour.name}</a></span>
            </div>

            <div class="col-sm-3"></div>

            <div class="col-sm-4">
                <div class="pull-right"><span class="price-old">$ </span>
                    <span id="oldPrice" class="price-old">${tour.tour.price}</span>&nbsp;&nbsp;&nbsp;
                    <span id="priceWithDiscount" class="price-new">$ ${tour.tour.price}</span>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- /Headliner -->

<div id="showPicture"></div>

<!-- Accordion -->
<div class="row">
    <div class="col-sm-12">
        <div class="panel-group" id="accordion2">

            <!-- Panel TourInfo Details -->
            <div class="panel">
                <div class="panel-heading">
                    <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseOne">
                        <span class="panel-title"><span class="glyphicon glyphicon-hand-right"></span>&nbsp;&nbsp;Tour Details</span>
                    </a>
                </div>
                <div id="collapseOne" class="height0 panel-collapse collapse in">
                    <div class="panel-body">

                        <div class="col-sm-3 no_margin_left">
                            <a href="#" id="tourPhoto"><img src="/img/${tour.tour.mainPhotoUrl}" class="img-thumbnail"></a><p>
                        </div>

                        <div class="col-sm-3 pt-10">

                            <div class="form-group">
                                <label>Tour starts on:</label>&nbsp;&nbsp;
                                <span id="startingDate">${tour.startDate}</span>
                            </div>

                            <div class="form-group">
                                <label>Tour ends on:</label>&nbsp;&nbsp;
                                <span id="endingDate">${tour.endDate}</span>
                            </div>

                            <div class="form-group">
                                <label>Days in tour:</label>&nbsp;&nbsp;
                                <span id="daysInTour"></span>
                            </div>

                            <div id="overallRating" rel="popover" class="form-group">
                                <label>Rating:</label>&nbsp;&nbsp;
                                <span id="rateTour"></span>
                            </div>

                            <script type="text/javascript">
                                jQuery('#rateTour').raty({
                                    readOnly: true,
                                    scoreName: 'Tour.ratio',
                                    score:     ${ordersService.getRatio(tour.tour.tourId)},
                                    number:    5
                                });
                            </script>

                        </div>

                        <c:set var="description" value="${tour.tour.description}"/>
                        <c:if test="${fn:length(description) > 500}">
                            <c:set var="description" value="${fn:substring(description, 0, 500)}"/>
                        </c:if>
                        <div class="col-sm-6 no_margin_left pt-10">
                            ${description}...
                            <a href="/tour/${tour.tour.tourId}" target="_blank" title="More..."
                               class="btn btn-default btn-xs">&raquo;</a>
                        </div>

                    </div>
                </div>
            </div>
            <!-- /Panel TourInfo Details -->

            <!-- Form Enter Registration Information -->
            <c:url var="saveUrl" value="/createOrder/${tour.tourId}"/>
            <form:form role="form" method="post" modelAttribute="order" id="createOrderForm" action="${saveUrl}">
                <!-- Panel Enter Registration Information -->
                <div class="panel mt">
                    <div class="panel-heading">
                        <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo">
                            <span class="panel-title"><span class="glyphicon glyphicon-hand-right"></span>&nbsp;&nbsp;Enter Registration Information</span>
                        </a>
                    </div>
                    <div id="collapseTwo" class="height0 panel-collapse collapse in">
                        <div class="panel-body">
                            <div class="col-sm-6 no_margin_left">

                                <fieldset>
                                    <legend>Required Personal Details</legend>
                                    <div class="form-group">
                                        <label for="firstName">Name</label>
                                        <input id="firstName" name="name" value="${user.name}"
                                           placeholder="Enter your name" class="form-control" type="text" required>
                                    </div>

                                    <div class="form-group">
                                        <label for="surname">Surname</label>
                                        <input id="surname" name="surname" value="${user.surname}"
                                           placeholder="Enter your surname" class="form-control" type="text" required>
                                    </div>

                                    <div class="form-group">

                                        <label for="email">Email Address</label>
                                        <input id="email" name="email" value="${user.email}"
                                           placeholder="Enter email address" class="form-control" type="email" required>

                                    </div>

                                    <div class="form-group">
                                        <label for="phone">Phone</label>
                                        <input id="phone" name="phone" value="${user.phone}"
                                           placeholder="Enter your phone number" class="form-control" type="text" required>
                                    </div>

                                </fieldset>
                                <p>&nbsp;</p>
                            </div>

                            <div class="col-sm-6">
                                <legend>Additional Information</legend>
                                <div class="row">
                                    <div class="col-sm-4">
                                        <fieldset>
                                            <div class="form-group">
                                                <label for="numberOfAdults">Number of adults:</label>

                                                <select id="numberOfAdults" name="numberOfAdults" class="form-control num" required>
                                                    <option value="1">1</option>
                                                    <option value="2">2</option>
                                                    <option value="3">3</option>
                                                    <option value="4">4</option>
                                                    <option value="5">5</option>
                                                </select>
                                                <form:errors path="numberOfAdults" cssClass="alert-danger"/>
                                            </div>
                                            <div class="form-group">
                                                <label for="numberOfChildren">Number of children:</label>

                                                <select id="numberOfChildren" name="numberOfChildren"
                                                        class="form-control num" required>
                                                    <option value="0">0</option>
                                                    <option value="1">1</option>
                                                    <option value="2">2</option>
                                                    <option value="3">3</option>
                                                    <option value="4">4</option>
                                                    <option value="5">5</option>
                                                </select>
                                                <form:errors path="numberOfChildren" cssClass="alert-danger"/>
                                            </div>
                                        </fieldset>
                                    </div>
                                    <div class="col-sm-8">
                                        <fieldset>
                                            <label>Discount information:</label>
                                            <div class="form-group">

                                                <!-- Discount information -->
                                                <div id="discountPolicies">
                                                    <c:forEach items="${tour.tour.discountPolicies}" var="discountPolicy">
                                                        <div class="row">
                                                            <div class="col-sm-9 discountPolicyName"
                                                                 data-content="${discountPolicy.description}">${discountPolicy.name}</div>
                                                            <div class="col-sm-3">${discountPolicy.formula}%</div>
                                                        </div>
                                                    </c:forEach>
                                                </div>
                                                <div class="row">
                                                    <div class="col-sm-9">Tour discount:</div>
                                                    <div class="col-sm-3" id="tourDiscount">${tour.discount}%</div>
                                                </div>
                                                <hr>
                                                <div class="row">
                                                    <div class="col-sm-9 green"><b>Total discount:</b></div>
                                                    <div class="col-sm-3 green" id="totalDiscount"><b>${discount}%</b></div>
                                                </div><!-- /Discount information -->

                                            </div>
                                        </fieldset>
                                    </div>
                                </div>

                                <input name="discount" id="discount" value="${discount}" hidden="true" disabled>

                                <fieldset>
                                    <div class="form-group">
                                        <label for="comments">Comments:</label>
                                        <textarea id="comments" name="userInfo" rows="4"
                                                  class="form-control">User_info</textarea>
                                    </div>
                                    <button type="button" id="calculateDiscount" class="btn btn-primary btn-sm">
                                        Calculate discount
                                    </button>
                                    <span class="pull-right">
                                        <input type="submit" value="Confirm Order" class="btn btn-success btn-sm">
                                        <a class="btn btn-default btn-sm" href="/">Cancel</a>
                                    </span>
                                </fieldset>
                                <p>&nbsp;</p>
                            </div>

                        </div>
                    </div>
                </div>
                <!-- /Panel Enter Registration Information -->

            </form:form><!-- /Form Enter Registration Information -->
        </div>
    </div>
</div>
<!-- /Accordion -->

<hr>

<div class="col-sm-12">
    <div class="row well no_margin_left">
        <footer class="align-center">
            <p><b>Submitting the order does not obligates you to book the tour.</b></p><br>

            <p>Our agent will contact you to explane some details, ways of payment and obtaining required papers.</p>

            <p>This order further can be edited if during communication process will be found more convenient
                options.</p>
        </footer>
    </div>
</div>
