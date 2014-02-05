package com.quicktour.controller;

import com.quicktour.dto.DiscountPoliciesResult;
import com.quicktour.entity.*;
import com.quicktour.repository.TourRepository;
import com.quicktour.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;

@Controller
public class OrderController {

    final Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    ValidationService validationService;
    @Autowired
    private UsersService userService;
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private TourRepository tourRepository;
    @Autowired
    ToursService toursService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private DiscountPolicyService discountPolicyService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(java.sql.Date.class, new SqlDatePropertyEditor());
    }

    @PreAuthorize("hasAnyRole('user','admin','agent')")
    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public String listOrders() {
        return "redirect:/orders/id/asc/0";
    }

    @PreAuthorize("hasAnyRole('user','admin','agent')")
    @RequestMapping(value = "/orders/{sortByValue}/{sortDirection}/{pageNum}", method = RequestMethod.GET)
    public String listOrdersPaginated(@PathVariable("pageNum") int pageNum,
                                      @PathVariable("sortByValue") String value,
                                      @PathVariable("sortDirection") String direction,
                                      Model model) {

        User activeUser = usersService.getCurrentUser();

        Long allOrdersCount = ordersService.allOrdersCount(activeUser);
        Long activeOrdersCount = ordersService.activeOrdersCount(activeUser);
        Long completedOrdersCount = ordersService.ordersByStatusCount(activeUser, Order.STATUS_COMPLETED);
        Long cancelledOrdersCount = ordersService.ordersByStatusCount(activeUser, Order.STATUS_CANCELLED);

        String sortByValueLink = MessageFormat.format("{0}/{1}/", value, direction);
        Sort.Order sortOrder = ordersService.sortByValue(value, direction);
        Page page = ordersService.listOrdersPaginated(activeUser, pageNum, sortOrder);

        List<Order> orders = page.getContent();

        model.addAttribute("allOrdersCount", allOrdersCount);
        model.addAttribute("activeOrdersCount", activeOrdersCount);
        model.addAttribute("completedOrdersCount", completedOrdersCount);
        model.addAttribute("cancelledOrdersCount", cancelledOrdersCount);
        model.addAttribute("sortByValueLink", sortByValueLink);
        model.addAttribute("user", activeUser);
        model.addAttribute("orders", orders);
        model.addAttribute("page", page);

        return "list-orders";
    }

    @PreAuthorize("hasAnyRole('user','admin','agent')")
    @RequestMapping(value = "/orders/filter/{orderStatusFilter}/{sortByValue}/{sortDirection}/{pageNum}",
            method = RequestMethod.GET)
    public String filterOrdersByStatusPaginated(@PathVariable("orderStatusFilter") String orderStatusFilter,
                                                @PathVariable("sortByValue") String value,
                                                @PathVariable("sortDirection") String direction,
                                                @PathVariable("pageNum") int pageNum,
                                                Model model) {

        User activeUser = usersService.getCurrentUser();
        Long allOrdersCount = ordersService.allOrdersCount(activeUser);
        Long activeOrdersCount = ordersService.activeOrdersCount(activeUser);
        Long completedOrdersCount = ordersService.ordersByStatusCount(activeUser, Order.STATUS_COMPLETED);
        Long cancelledOrdersCount = ordersService.ordersByStatusCount(activeUser, Order.STATUS_CANCELLED);

        String filterLink = MessageFormat.format("filter/{0}/", orderStatusFilter);
        String sortByValueLink = MessageFormat.format("{0}/{1}/", value, direction);
        Sort.Order sortOrder = ordersService.sortByValue(value, direction);
        Page page = ordersService.listOrdersByStatusPaginated(activeUser, orderStatusFilter, pageNum, sortOrder);
        List<Order> orders = page.getContent();

        model.addAttribute("allOrdersCount", allOrdersCount);
        model.addAttribute("activeOrdersCount", activeOrdersCount);
        model.addAttribute("completedOrdersCount", completedOrdersCount);
        model.addAttribute("cancelledOrdersCount", cancelledOrdersCount);
        model.addAttribute("filterLink", filterLink);
        model.addAttribute("sortByValueLink", sortByValueLink);
        model.addAttribute("user", activeUser);
        model.addAttribute("orders", orders);
        model.addAttribute("page", page);

        return "list-orders";
    }

    @PreAuthorize("hasAnyRole('user','admin','agent')")
    @RequestMapping(value = "/orders/filter/{orderStatusFilter}", method = RequestMethod.GET)
    public String filterOrdersByStatus(@PathVariable("orderStatusFilter") String orderStatusFilter) {
        return MessageFormat.format("redirect:/orders/filter/{0}/id/asc/0", orderStatusFilter);
    }

    @PreAuthorize("!(hasRole('agent'))")
    @RequestMapping(value = "/createOrder/{tourId}", method = RequestMethod.GET)
    public String createOrder(@PathVariable("tourId") int tourId,
                              Model model) {


        DiscountPoliciesResult discountPoliciesResult;
        BigDecimal totalDiscount;
        User activeUser = userService.getCurrentUser();
        TourInfo tourInfo = tourRepository.findOne(tourId);
        Tour tour = tourInfo.getTour();
        BigDecimal companyDiscount = null;
        List<DiscountPolicy> activeDiscountPolicies;
        if (activeUser == null) {
            activeUser = new User();
            tour.setDiscountPolicies(null);
        } else {
            discountPoliciesResult = discountPolicyService.calculateDiscount(tour.getDiscountPolicies());
            tour.setDiscount(discountPoliciesResult.getDiscount());
            logger.debug("Discount policies {}\n", discountPoliciesResult.getDiscountPolicies());
            activeDiscountPolicies = discountPolicyService.getActivePolicies(discountPoliciesResult.getDiscountPolicies());
            logger.debug("Active discount policies {}", activeDiscountPolicies);
            tour.setDiscountPolicies(activeDiscountPolicies);
            companyDiscount = companyService.getCompanyDiscount(activeUser);
            logger.debug("Discount based on discount policies {}  .TourInfo discount {}\nCompany:{}.\nUser: {}", tour.getDiscountPolicies(),
                    tourInfo.getDiscount(), companyService.getCompanyByUserId(activeUser.getUserId()), activeUser);
        }
        totalDiscount = tour.getDiscount().add(new BigDecimal(tourInfo.getDiscount().toString()));
        if (companyDiscount != null && companyDiscount.doubleValue() > 0) {
            totalDiscount = totalDiscount.add(companyDiscount);
        }
        logger.info("Total discount {}.Discount company {}", totalDiscount, companyDiscount);
        model.addAttribute("totalDiscount", totalDiscount);
        model.addAttribute("user", activeUser);
        model.addAttribute("companyDiscount", companyDiscount);
        model.addAttribute("tourInfo", tourInfo);
        model.addAttribute("tour", tour);
        model.addAttribute("company", tour.getCompany());

        return "create-order";
    }

    @PreAuthorize("!(hasRole('agent'))")
    @RequestMapping(value = "/createOrder/{tourId}", method = RequestMethod.POST)
    public String addOrder(@Valid Order order,
                           @PathVariable("tourId") int tourId,
                           @RequestParam(value = "name") String name,
                           @RequestParam(value = "surname") String surname,
                           @RequestParam(value = "email") String email,
                           @RequestParam(value = "phone") String phone) {

        User activeUser = userService.getCurrentUser();

        if (activeUser == null) {
            User user = new User();
            user.setName(name);
            user.setSurname(surname);
            user.setEmail(email);
            user.setPhone(phone);

            if (userService.findByEmail(user.getEmail()) != null) {
                return "ordererror";
            }

//            order.setUser(userService.saveAnonymousCustomer(user));
//            ordersService.createValidationLink(user);
        }

        ordersService.add(order, tourId);

        return "ordersuccess";
    }

    @PreAuthorize("hasAnyRole('user','admin','agent')")
    @RequestMapping(value = "/manageOrder/{orderId}", method = RequestMethod.GET)
    public String showOrder(@PathVariable("orderId") int id,
                            Model model) {

        Order order = ordersService.findById(usersService.getCurrentUser(), id);
        if (order == null) {
            return "redirect:/orders";
        }

        TourInfo tour = tourRepository.findOne(order.getTourInfo().getTourInfoId());
        model.addAttribute("tour", tour);
        model.addAttribute("user", usersService.getCurrentUser());
        model.addAttribute("order", order);
        model.addAttribute("ordersService", ordersService);

        return "manage-order";
    }

    @PreAuthorize("hasAnyRole('user','admin','agent')")
    @RequestMapping(value = "/manageOrder/{orderId}", method = RequestMethod.POST)
    public String manageOrder(@PathVariable("orderId") int orderId,
                              @RequestParam(value = "sendEmail", required = false) String sendEmail,
                              @Valid Order order,
                              BindingResult bindingResult, Model model) {

        if (order == null) {
            return "redirect:/orders";
        }

        if (bindingResult.hasErrors()) {

            model.addAttribute("user", usersService.getCurrentUser());
            model.addAttribute("order", order);
            model.addAttribute("ordersService", ordersService);

            return "manage-order";
        }

        boolean notifyUserByEmail = sendEmail != null && sendEmail.equals("yes");
        order.setOrderId(orderId);
        ordersService.edit(order, notifyUserByEmail);

        return "redirect:/orders";
    }


    @PreAuthorize("hasRole('user')")
    @RequestMapping(value = "/orders/rate", method = RequestMethod.POST)
    public
    @ResponseBody
    String rateOrder(@RequestParam(value = "order", required = false) Integer order,
                     @RequestParam(value = "score", required = false) Integer score) {

        int orderScore = score == null ? 0 : score;
        logger.debug("order = [" + order + "], score = [" + score + "]");
        ordersService.addVote(order, orderScore);

        return "OK";
    }

    @PreAuthorize("hasRole('user')")
    @RequestMapping(value = "/orders/comments", method = RequestMethod.POST)
    public
    @ResponseBody
    String saveComments(@RequestParam(value = "orderId") Integer orderId,
                        @RequestParam(value = "comments") String comments) {

        ordersService.saveComments(orderId, comments);
        return "OK";
    }

    @RequestMapping(value = "/orders/calculate_discount", method = RequestMethod.POST)
    @ResponseBody
    public DiscountPoliciesResult recalculateDiscount(Order order, Integer tourId) {
        logger.debug("Recalculate discount .Order {}\nTourId {}", order, tourId);
        return discountPolicyService.calculateDiscount(toursService.findOne(tourId).getDiscountPolicies(), order);
    }


}
