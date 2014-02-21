package com.quicktour.service;

import com.quicktour.dto.DiscountPoliciesResult;
import com.quicktour.entity.*;
import com.quicktour.repository.OrderRepository;
import com.quicktour.repository.TourRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

@Service
@Transactional
public class OrdersService {
    @Value("${numberOfOrdersOnPage}")
    private int NUMBER_OF_ORDERS_PER_PAGE;
    private static final List<String> SORT_VALUES = Arrays.asList("tourInfoId", "orderDate", "price", "status",
            "nextPaymentDate");
    private static final String ACTIVE = "Active";
    private static final String DESC = "desc";
    private final Logger logger = LoggerFactory.getLogger(OrdersService.class);
    @Autowired
    private DiscountPolicyService discountPolicyService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private UsersService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private TourRepository tourRepository;

    public Page<Order> findByUser(User user, int pageNumber, Sort.Order sortOrder) {
        return orderRepository.findByUser(user, new PageRequest(pageNumber, NUMBER_OF_ORDERS_PER_PAGE,
                new Sort(sortOrder)));
    }

    public Page<Order> findByCompanyId(int id, int pageNumber, Sort.Order sortOrder) {
        return orderRepository.findByCompanyId(id, new PageRequest(pageNumber, NUMBER_OF_ORDERS_PER_PAGE,
                new Sort(sortOrder)));
    }


    public Order findById(User activeUser, int id) {
        switch (activeUser.getRole().getRoleId()) {
            case Role.ROLE_ADMIN:
                return orderRepository.findOne(id);
            case Role.ROLE_AGENT:
                return orderRepository.findByCompanyId(companyService.findByCompanyCode(activeUser.getCompanyCode())
                        .getCompanyId(), id);
            case Role.ROLE_USER:
                return orderRepository.findByUserId(activeUser.getUserId(), id);
        }

        return orderRepository.findOne(id);
    }

    public Page<Order> findAll(int pageNumber, Sort.Order sortOrder) {
        return orderRepository.findAll(new PageRequest(pageNumber, NUMBER_OF_ORDERS_PER_PAGE,
                new Sort(sortOrder)));
    }

    public Page<Order> findByStatus(Order.Status status, int pageNumber, Sort.Order sortOrder) {
        return orderRepository.findByStatus(status, new PageRequest(pageNumber, NUMBER_OF_ORDERS_PER_PAGE,
                new Sort(sortOrder)));
    }

    public Page<Order> findActiveOrders(int pageNumber, Sort.Order sortOrder) {
        return orderRepository.findActiveOrders(new PageRequest(pageNumber, NUMBER_OF_ORDERS_PER_PAGE,
                new Sort(sortOrder)));
    }

    public Page<Order> findActiveOrdersByCompanyId(Company company, int pageNumber, Sort.Order sortOrder) {
        return orderRepository.findActiveOrdersByCompanyId(company.getCompanyId(),
                new PageRequest(pageNumber, NUMBER_OF_ORDERS_PER_PAGE,
                        new Sort(sortOrder)));
    }

    public Page<Order> findByStatusAndCompanyId(Order.Status status, Company company, int pageNumber, Sort.Order sortOrder) {
        logger.debug("Find by {}.{}.{}.{}", status, company, pageNumber, sortOrder);
        return orderRepository.findByStatusAndCompany(status, company,
                new PageRequest(pageNumber, NUMBER_OF_ORDERS_PER_PAGE,
                        new Sort(sortOrder)));
    }

    public Page<Order> findActiveOrdersByUserId(User activeUser, int pageNumber, Sort.Order sortOrder) {
        return orderRepository.findActiveOrdersByUserId(activeUser.getUserId(),
                new PageRequest(pageNumber, NUMBER_OF_ORDERS_PER_PAGE,
                        new Sort(sortOrder)));
    }

    public Page<Order> findByStatusAndUserId(Order.Status status, User activeUser, int pageNumber, Sort.Order sortOrder) {
        return orderRepository.findByStatusAndUser(status, activeUser,
                new PageRequest(pageNumber, NUMBER_OF_ORDERS_PER_PAGE,
                        new Sort(sortOrder)));
    }

    public Long countByCompanyId(Company company) {
        return orderRepository.countByCompanyId(company.getCompanyId());
    }

    public Long countByUserId(User activeUser) {
        return orderRepository.countByUserId(activeUser.getUserId());
    }

    public Long countByCompanyIdAndStatus(Company company, Order.Status status) {
        return orderRepository.countByCompanyIdAndStatus(company.getCompanyId(), status);
    }

    public Long countByUserIdAndStatus(User activeUser, Order.Status status) {
        return orderRepository.countByUserAndStatus(activeUser, status);
    }

    /*
     *  Defines sorting parameters for a list of orders
     */
    public Sort.Order sortByValue(String value, String direction) {
        String sortValue = SORT_VALUES.contains(value) ? value : Order.ID;
        Sort.Order sortOrder;
        if (DESC.equalsIgnoreCase(direction)) {
            sortOrder = new Sort.Order(Sort.Direction.DESC, sortValue);
        } else {
            sortOrder = new Sort.Order(Sort.Direction.ASC, sortValue);
        }

        return sortOrder;
    }

    /*
     *  Displays all orders for specified user with pagination and sorting
     */
    public Page<Order> listOrdersPaginated(User activeUser, int pageNumber, Sort.Order sortOrder) {
        Page<Order> orders = null;

        switch (activeUser.getRole().getRoleId()) {
            case Role.ROLE_ADMIN:
                orders = findAll(pageNumber, sortOrder);
                break;
            case Role.ROLE_AGENT:
                Company company = companyService.findByCompanyCode(activeUser.getCompanyCode());
                orders = findByCompanyId(company.getCompanyId(), pageNumber, sortOrder);
                break;
            case Role.ROLE_USER:
                orders = findByUser(activeUser, pageNumber, sortOrder);
                break;
        }

        return orders;
    }

    /*
     *  Displays orders for specified user by order status with pagination and sorting
     */
    public Page<Order> listOrdersByStatusPaginated(User activeUser, String status, int pageNumber, Sort.Order sortOrder) {
        Page<Order> orders = null;
        Order.Status orderStatus = null;
        if (!status.contains(ACTIVE)) {
            orderStatus = Order.Status.valueOf(status.toUpperCase());
        }
        switch (activeUser.getRole().getRoleId()) {
            case Role.ROLE_ADMIN:
                orders = status.contains(ACTIVE) ? findActiveOrders(pageNumber, sortOrder) :
                        findByStatus(orderStatus, pageNumber, sortOrder);
                break;
            case Role.ROLE_AGENT:
                Company company = companyService.findByCompanyCode(activeUser.getCompanyCode());
                orders = status.contains(ACTIVE) ? findActiveOrdersByCompanyId(company, pageNumber, sortOrder) :
                        findByStatusAndCompanyId(orderStatus, company, pageNumber, sortOrder);
                break;
            case Role.ROLE_USER:
                orders = status.contains(ACTIVE) ? findActiveOrdersByUserId(activeUser, pageNumber, sortOrder) :
                        findByStatusAndUserId(orderStatus, activeUser, pageNumber, sortOrder);
                break;
        }

        return orders;
    }

    /*
     *  Counts the number of orders for specified user
     */
    public Long allOrdersCount(User activeUser) {
        Long allCount = null;

        switch (activeUser.getRole().getRoleId()) {
            case Role.ROLE_ADMIN:
                allCount = orderRepository.count();
                break;
            case Role.ROLE_AGENT:
                allCount = countByCompanyId(companyService.findByCompanyCode(activeUser.getCompanyCode()));
                break;
            case Role.ROLE_USER:
                allCount = countByUserId(activeUser);
                break;
        }

        return allCount;
    }

    /**
     * Counts the number of orders with given status for specified user
     */
    public Long ordersByStatusCount(User activeUser, Order.Status status) {
        Long byStatusCount = null;

        switch (activeUser.getRole().getRoleId()) {
            case Role.ROLE_ADMIN:
                byStatusCount = orderRepository.countByStatus(status);
                break;
            case Role.ROLE_AGENT:
                byStatusCount = countByCompanyIdAndStatus(
                        companyService.findByCompanyCode(activeUser.getCompanyCode()), status);
                break;
            case Role.ROLE_USER:
                byStatusCount = countByUserIdAndStatus(activeUser, status);
                break;
        }

        return byStatusCount;
    }

    /*
     *  Counts the number of orders with active statuses (eg. 'Received', 'Accepted', 'Confirmed')
     *  for specified user
     */
    public Long activeOrdersCount(User activeUser) {
        return orderRepository.countByActiveStatusAndUser(activeUser);
    }

    /*
     *  Editing and saving order
     */
    public void edit(Order order, Boolean sendEmail) {

        Timestamp currentTimestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Order existingOrder = this.findById(userService.getCurrentUser(), order.getOrderId());

        existingOrder.setNumberOfAdults(order.getNumberOfAdults());
        existingOrder.setNumberOfChildren(order.getNumberOfChildren());
        existingOrder.setUserInfo(order.getUserInfo());
        existingOrder.setDiscount(order.getDiscount());
        existingOrder.setNextPaymentDate(order.getNextPaymentDate());
        existingOrder.setStatus(order.getStatus());

        switch (order.getStatus()) {
            case ACCEPTED:
                existingOrder.setAcceptedDate(currentTimestamp);

                logger.debug("For order[" + order.getOrderId() + "] set status [" + Order.Status.ACCEPTED + "] "
                        + currentTimestamp + " by User[" + userName + "]");
                break;
            case CONFIRMED:
                existingOrder.setConfirmedDate(currentTimestamp);
                logger.debug("For order[" + order.getOrderId() + "] set status [" + Order.Status.CONFIRMED + "] "
                        + currentTimestamp + " by User[" + userName + "]");
                break;
            case COMPLETED:
                existingOrder.setCompletedDate(currentTimestamp);
                logger.debug("For order[" + order.getOrderId() + "] set status [" + Order.Status.COMPLETED + "] "
                        + currentTimestamp + " by User[" + userName + "]");
                break;
            case CANCELLED:
                existingOrder.setCancelledDate(currentTimestamp);
                logger.debug("For order[" + order.getOrderId() + "] set status [" + Order.Status.CANCELLED + "] "
                        + currentTimestamp + " by User[" + userName + "]");
                break;
            default:
                logger.error("Invalid status [" + order.getStatus() + "] for order [" + order.getOrderId() + "]");
        }

        existingOrder = orderRepository.saveAndFlush(existingOrder);
        logger.info("[" + currentTimestamp + "] Order: ID [" + "] was edited by [" + userName + "].");

        if (sendEmail) {
            logger.debug("send e-mail to user [" + existingOrder.getUser().getName()
                    + "] to address[" + existingOrder.getUser().getEmail() + "]");
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.
                    currentRequestAttributes()).getRequest();
            emailService.sendOrderStatusChanged(existingOrder, existingOrder.getTourInfo().getTour().getName(),
                    existingOrder.getTourInfo().getStartDate().toString(), userName, request.getRequestURL().toString());

        }
    }

    /*
     *  Saves "Comments" field edited by user and notifies company by email
     */
    public void saveComments(int orderId, String comments) {

        Order existingOrder = this.findById(userService.getCurrentUser(), orderId);
        if (existingOrder != null) {

            existingOrder.setUserInfo(comments);
            orderRepository.saveAndFlush(existingOrder);

            emailService.sendOrderCommentChangedEmail(existingOrder);


        }
    }

    public void add(Order order, int tourId) {

        //Extracting values
        TourInfo tourInfo = tourRepository.findOne(tourId);
        User activeUser = userService.getCurrentUser();
        Tour tour = tourInfo.getTour();
        Company company = tour.getCompany();
        Timestamp currentTimestamp = new Timestamp(Calendar.getInstance().getTime().getTime());

        //calculate total discount
        BigDecimal tourInfoDiscount = new BigDecimal(tourInfo.getDiscount());
        DiscountPoliciesResult discountPoliciesResult = discountPolicyService.
                calculateDiscount(tour.getDiscountPolicies());
        BigDecimal companyDiscount = companyService.getCompanyDiscount(activeUser);
        BigDecimal totalDiscount = tourInfoDiscount.add(discountPoliciesResult.getDiscount());
        if (companyDiscount.doubleValue() > 0) {
            totalDiscount = totalDiscount.add(companyDiscount);
        }
        String discountInformation = prepareDiscountInformation(totalDiscount, tourInfoDiscount,
                discountPoliciesResult, companyDiscount);
        order.setDiscount(totalDiscount);
        order.setOrderDate(currentTimestamp);
        order.setPrice(tour.getPrice().multiply(new BigDecimal(order.getNumberOfAdults().toString())));
        order.setCompany(company);
        order.setTourInfo(tourInfo);
        order.setDiscountInformation(discountInformation.toString());
        order.setStatus(Order.Status.RECEIVED);
        emailService.sendOrderStatusChanged(order, tour.getName(), tourInfo.getStartDate().toString(),
                order.getUser().getName(),
                emailService.retrieveBaseUrl() + "orders/");
        if (activeUser != null) {
            order.setUser(activeUser);
        }
        if (activeUser == null) {
            order.setUserInfo(constructAnonymousUserInfo(order));
            order.setUser(null);
        }

        orderRepository.saveAndFlush(order);
    }

    private String prepareDiscountInformation(BigDecimal totalDiscount, BigDecimal tourInfoDiscount, DiscountPoliciesResult discountPoliciesResult, BigDecimal companyDiscount) {
        StringBuilder discountInformation = new StringBuilder();
        if (tourInfoDiscount.doubleValue() > 0) {
            discountInformation.append("tour discount=").append(totalDiscount.toString()).append("<br>");
        }

        if (companyDiscount.doubleValue() > 0) {
            discountInformation.append("Company discount=").append(companyDiscount.toString()).append("<br>");
        }
        if (discountPoliciesResult.getDiscount().doubleValue() > 0) {
            discountInformation.append("Discount policies <br><table><thead><th>Name</th><th>Discount</th>" +
                    "<th>Condition</th></thead><tbdody>");
            for (DiscountPolicy discountPolicy : discountPoliciesResult.getDiscountPolicies()) {
                discountInformation.append("<tr><td>" + discountPolicy.getName()).append("</td>")
                        .append("<td>").append(discountPolicy.getFormula()).append("</td>")
                        .append("<td>").append(discountPolicy.getCondition()).append("</td>")
                        .append("</tr>");

            }
            discountInformation.append("</tbody></table>");

        }
        return discountInformation.toString();
    }

    public void addVote(int order, int score) {

        Order existingOrder = this.findById(userService.getCurrentUser(), order);
        existingOrder.setVote(score);

        orderRepository.saveAndFlush(existingOrder);
    }


    public String constructAnonymousUserInfo(Order order) {
        User user = order.getUser();
        return "Email:" + user.getEmail() + "<br>" + user.getName() + " " +
                user.getSurname() + "<br>" + user.getPhone() + "<br>" +
                order.getUserInfo();
    }
}
