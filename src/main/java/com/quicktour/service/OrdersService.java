package com.quicktour.service;

import com.quicktour.entity.*;
import com.quicktour.repository.OrderRepository;
import com.quicktour.repository.TourRepository;
import com.quicktour.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

@Service
@Transactional
public class OrdersService {

    private static final int NUMBER_OF_RECORDS_PER_PAGE = 10;
    private static final String ID = "id";
    private static final List<String> SORT_VALUES = Arrays.asList("tourInfoId", "orderDate", "price", "status",
            "nextPaymentDate");
    final Logger logger = LoggerFactory.getLogger(OrdersService.class);
    @Autowired
    DiscountPolicyService discountPolicyService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private UsersService userService;
    @Autowired
    private ValidationService validationService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UsersService usersService;
    @Autowired
    private TourRepository tourRepository;
    @Autowired
    private MailSender mailSender;
    @Value("${systemEmail}")
    private String SYSTEM_EMAIL;

    public Page<Order> findByUserId(int id, int pageNumber, Sort.Order sortOrder) {
        return orderRepository.findByUsersId(id, new PageRequest(pageNumber, NUMBER_OF_RECORDS_PER_PAGE,
                new Sort(sortOrder)));
    }

    public Page<Order> findByCompanyId(int id, int pageNumber, Sort.Order sortOrder) {
        return orderRepository.findByCompanyId(id, new PageRequest(pageNumber, NUMBER_OF_RECORDS_PER_PAGE,
                new Sort(sortOrder)));
    }

    public Order findByCompanyId(int id, int orderId) {
        return orderRepository.findByCompanyId(id, orderId);
    }

    public Order findById(User activeUser, int id) {

        switch (activeUser.getRoleId().getRoleId()) {
            case Role.ADMIN_ROLE:
                return orderRepository.findById(id);
            case Role.AGENT_ROLE:
                return orderRepository.findByCompanyId(getCompanyForUser(activeUser).getId(), id);
            case Role.USER_ROLE:
                return orderRepository.findByUserId(activeUser.getId(), id);
        }

        return orderRepository.findById(id);
    }

    public Page<Order> findAll(int pageNumber, Sort.Order sortOrder) {
        return orderRepository.findAll(new PageRequest(pageNumber, NUMBER_OF_RECORDS_PER_PAGE,
                new Sort(sortOrder)));
    }

    public Page<Order> findByStatus(String status, int pageNumber, Sort.Order sortOrder) {
        return orderRepository.findByStatus(status, new PageRequest(pageNumber, NUMBER_OF_RECORDS_PER_PAGE,
                new Sort(sortOrder)));
    }

    public Page<Order> findActiveOrders(int pageNumber, Sort.Order sortOrder) {
        return orderRepository.findActiveOrders(new PageRequest(pageNumber, NUMBER_OF_RECORDS_PER_PAGE,
                new Sort(sortOrder)));
    }

    public Page<Order> findActiveOrdersByCompanyId(Company company, int pageNumber, Sort.Order sortOrder) {
        return orderRepository.findActiveOrdersByCompanyId(company.getId(),
                new PageRequest(pageNumber, NUMBER_OF_RECORDS_PER_PAGE,
                        new Sort(sortOrder)));
    }

    public Page<Order> findByStatusAndCompanyId(String status, Company company, int pageNumber, Sort.Order sortOrder) {
        return orderRepository.findByStatusAndCompanyId(status, company,
                new PageRequest(pageNumber, NUMBER_OF_RECORDS_PER_PAGE,
                        new Sort(sortOrder)));
    }

    public Page<Order> findActiveOrdersByUserId(User activeUser, int pageNumber, Sort.Order sortOrder) {
        return orderRepository.findActiveOrdersByUserId(activeUser.getId(),
                new PageRequest(pageNumber, NUMBER_OF_RECORDS_PER_PAGE,
                        new Sort(sortOrder)));
    }

    public Page<Order> findByStatusAndUserId(String status, User activeUser, int pageNumber, Sort.Order sortOrder) {
        return orderRepository.findByStatusAndUserId(status, activeUser,
                new PageRequest(pageNumber, NUMBER_OF_RECORDS_PER_PAGE,
                        new Sort(sortOrder)));
    }

    public Long countByCompanyId(Company company) {
        return orderRepository.countByCompanyId(company.getId());
    }

    public Long countByUserId(User activeUser) {
        return orderRepository.countByUserId(activeUser.getId());
    }

    public Long countByCompanyIdAndStatus(Company company, String status) {
        return orderRepository.countByCompanyIdAndStatus(company.getId(), status);
    }

    public Long countByUserIdAndStatus(User activeUser, String status) {
        return orderRepository.countByUserIdAndStatus(activeUser.getId(), status);
    }

    public Company getCompanyForUser(User activeUser) {
        String companyCode = activeUser.getCompanyCode();
        Company company = companyService.findByCompanyCode(companyCode);

        return company;
    }

    /*
     *  Defines sorting parameters for a list of orders
     */
    public Sort.Order sortByValue(String value, String direction) {

        String sortValue = SORT_VALUES.contains(value) ? value : ID;
        Sort.Order sortOrder = direction.equalsIgnoreCase("desc") ? new Sort.Order(Sort.Direction.DESC, sortValue) :
                new Sort.Order(Sort.Direction.ASC, sortValue);

        return sortOrder;
    }

    /*
     *  Displays all orders for specified user with pagination and sorting
     */
    public Page<Order> listOrdersPaginated(User activeUser, int pageNumber, Sort.Order sortOrder) {
        Page<Order> orders = null;

        switch (activeUser.getRoleId().getRoleId()) {
            case Role.ADMIN_ROLE:
                orders = findAll(pageNumber, sortOrder);
                break;
            case Role.AGENT_ROLE:
                Company company = getCompanyForUser(activeUser);
                orders = findByCompanyId(company.getId(), pageNumber, sortOrder);
                break;
            case Role.USER_ROLE:
                orders = findByUserId(activeUser.getId(), pageNumber, sortOrder);
                break;
        }

        return orders;
    }

    /*
     *  Displays orders for specified user by order status with pagination and sorting
     */
    public Page<Order> listOrdersByStatusPaginated(User activeUser, String status, int pageNumber, Sort.Order sortOrder) {
        Page<Order> orders = null;

        switch (activeUser.getRoleId().getRoleId()) {
            case Role.ADMIN_ROLE:
                orders = status.equals("Active") ? findActiveOrders(pageNumber, sortOrder) :
                        findByStatus(status, pageNumber, sortOrder);
                break;
            case Role.AGENT_ROLE:
                Company company = getCompanyForUser(activeUser);
                orders = status.equals("Active") ? findActiveOrdersByCompanyId(company, pageNumber, sortOrder) :
                        findByStatusAndCompanyId(status, company, pageNumber, sortOrder);
                break;
            case Role.USER_ROLE:
                orders = status.equals("Active") ? findActiveOrdersByUserId(activeUser, pageNumber, sortOrder) :
                        findByStatusAndUserId(status, activeUser, pageNumber, sortOrder);
                break;
        }

        return orders;
    }

    /*
     *  Counts the number of orders for specified user
     */
    public Long allOrdersCount(User activeUser) {
        Long allCount = null;

        switch (activeUser.getRoleId().getRoleId()) {
            case Role.ADMIN_ROLE:
                allCount = orderRepository.count();
                break;
            case Role.AGENT_ROLE:
                allCount = countByCompanyId(getCompanyForUser(activeUser));
                break;
            case Role.USER_ROLE:
                allCount = countByUserId(activeUser);
                break;
        }

        return allCount;
    }

    /**
     * Counts the number of orders with given status for specified user
     */
    public Long ordersByStatusCount(User activeUser, String status) {
        Long byStatusCount = null;

        switch (activeUser.getRoleId().getRoleId()) {
            case Role.ADMIN_ROLE:
                byStatusCount = orderRepository.countByStatus(status);
                break;
            case Role.AGENT_ROLE:
                byStatusCount = countByCompanyIdAndStatus(getCompanyForUser(activeUser), status);
                break;
            case Role.USER_ROLE:
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

        return allOrdersCount(activeUser) - ordersByStatusCount(activeUser, Order.STATUS_COMPLETED) -
                ordersByStatusCount(activeUser, Order.STATUS_CANCELLED);
    }

    /*
     *  Editing and saving order
     */
    public void edit(Order order, Boolean sendEmail) {

        Timestamp currentTimestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Order existingOrder = this.findById(usersService.getCurrentUser(), order.getId());

        existingOrder.setNumberOfAdults(order.getNumberOfAdults());
        existingOrder.setNumberOfChildren(order.getNumberOfChildren());
        existingOrder.setUserInfo(order.getUserInfo());
        existingOrder.setDiscount(order.getDiscount());
        existingOrder.setNextPaymentDate(order.getNextPaymentDate());
        existingOrder.setStatus(order.getStatus());

        switch (order.getStatus()) {
            case Order.STATUS_ACCEPTED:
                existingOrder.setAcceptedDate(currentTimestamp);

                logger.debug("For order[" + order.getId() + "] set status [" + Order.STATUS_ACCEPTED + "] "
                        + currentTimestamp + " by User[" + userName + "]");
                break;
            case Order.STATUS_CONFIRMED:
                existingOrder.setConfirmedDate(currentTimestamp);
                logger.debug("For order[" + order.getId() + "] set status [" + Order.STATUS_CONFIRMED + "] "
                        + currentTimestamp + " by User[" + userName + "]");
                break;
            case Order.STATUS_COMPLETED:
                existingOrder.setCompletedDate(currentTimestamp);
                logger.debug("For order[" + order.getId() + "] set status [" + Order.STATUS_COMPLETED + "] "
                        + currentTimestamp + " by User[" + userName + "]");
                break;
            case Order.STATUS_CANCELLED:
                existingOrder.setCancelledDate(currentTimestamp);
                logger.debug("For order[" + order.getId() + "] set status [" + Order.STATUS_CANCELLED + "] "
                        + currentTimestamp + " by User[" + userName + "]");
                break;
            default:
                logger.error("Invalid status [" + order.getStatus() + "] for order [" + order.getId() + "]");
        }

        orderRepository.saveAndFlush(existingOrder);
        logger.info("[" + currentTimestamp + "] Order: ID [" + "] was edited by [" + userName + "].");

        if (sendEmail) {
            logger.debug("send e-mail to user [" + existingOrder.getUserId().getName()
                    + "] to address[" + existingOrder.getUserId().getEmail() + "]");

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom(SYSTEM_EMAIL);
            message.setTo(existingOrder.getUserId().getEmail());
            message.setSubject("Changed status of your order[" + order.getId() + "] " + existingOrder.getTourInfoId().getTour().getName()
                    + " on date " + existingOrder.getTourInfoId().getStartDate());
            message.setText("For order[" + order.getId() + "] set status[" + order.getStatus() + "] "
                    + currentTimestamp + " by User[" + userName + "]" + "\nFor more info please visit " + request.getRequestURL());
            mailSender.send(message);

        }
    }

    /*
     *  Saves "Comments" field edited by user and notifies company by email
     */
    public void saveComments(int orderId, String comments) {

        Order existingOrder = this.findById(usersService.getCurrentUser(), orderId);
        Timestamp currentTimestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        if (existingOrder != null) {

            existingOrder.setUserInfo(comments);
            orderRepository.saveAndFlush(existingOrder);

            SimpleMailMessage message = new SimpleMailMessage();
            String email = existingOrder.getTourInfoId().getTour().getCompany().getContactEmail();
            message.setFrom(SYSTEM_EMAIL);
            message.setTo(email);
            message.setSubject("Changed comments in order: '" +
                    existingOrder.getTourInfoId().getTour().getName() + "' ID: '" + orderId +
                    "' on " + existingOrder.getTourInfoId().getStartDate());
            message.setText("On " + currentTimestamp + " comments was changed by user '" + userName +
                    "'.\nOrder ID: " + orderId + "\nTour name: '" + existingOrder.getTourInfoId().getTour().getName() +
                    "'\nTour starting date: " + existingOrder.getTourInfoId().getStartDate());
            mailSender.send(message);

            logger.info("[" + currentTimestamp + "] Changed comments in order: ID [" + orderId + "]" +
                    "by user [" + userName + "]." +
                    "\n[" + currentTimestamp + "] Send email by user [" + userName + "] to address [" + email + "].");

        }
    }

    /*
     *  Creating and saving order
     */
    public void add(Order order, int tourId) {

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User activeUser = userRepository.findByLogin(userName);

        TourInfo tourInfo = tourRepository.findOne(tourId);
        Tour tour = tourInfo.getTour();
        Company company = tour.getCompany();

        Timestamp currentTimestamp = new Timestamp(Calendar.getInstance().getTime().getTime());

        order.setCompanyId(company);
        order.setTourInfoId(tourInfo);
        if (order.getUserId() == null) { //if user was not set in controller
            order.setUserId(activeUser);
        }
        order.setOrderDate(currentTimestamp);
        order.setPrice(tour.getPrice().multiply(new BigDecimal(order.getNumberOfAdults().toString())));

        BigDecimal tourInfoDiscount = new BigDecimal(tourInfo.getDiscount());
        BigDecimal discountPoliciesDiscount = discountPolicyService.
                calculateDiscount(tour.getDiscountPolicies()).getDiscount();
        BigDecimal totalDiscount = tourInfoDiscount.add(discountPoliciesDiscount);
        BigDecimal companyDiscount = companyService.getCompanyDiscount(activeUser);
        if(companyDiscount.doubleValue()>0){
            totalDiscount = totalDiscount.add(companyDiscount);
        }
        order.setDiscount(totalDiscount);
        order.setStatus("Received");

        orderRepository.saveAndFlush(order);
    }

    public void addVote(int order, int score) {

        Order existingOrder = this.findById(usersService.getCurrentUser(), order);
        existingOrder.setTourVote(score);

        orderRepository.saveAndFlush(existingOrder);
    }

    public BigDecimal getRatio(int tourId) {

        return orderRepository.getRatio(tourId) == null ? new BigDecimal(0) : orderRepository.getRatio(tourId);
    }

    public BigInteger getRatioCount(int tourId) {

        return orderRepository.getRatioCount(tourId) == null ? new BigInteger("0") :
                orderRepository.getRatioCount(tourId);
    }

    public BigDecimal getRatio(int tourId, int userId) {

        return orderRepository.getRatio(tourId, userId) == null ? new BigDecimal(0) :
                orderRepository.getRatio(tourId, userId);
    }

    public BigInteger getRatioCount(int tourId, int userId) {

        return orderRepository.getRatioCount(tourId, userId) == null ? new BigInteger("0") :
                orderRepository.getRatioCount(tourId, userId);
    }

    public void createValidationLink(User user) {

        ValidationLink link = new ValidationLink();
        link.setUserId(userService.findByLogin(user.getLogin()).getId());
        link.setValidationLink("localhost:/login/" + user.getLogin());
        validationService.addValidationLink(link);

    }

}
