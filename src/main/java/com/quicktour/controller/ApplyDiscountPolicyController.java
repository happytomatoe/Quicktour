package com.quicktour.controller;

import com.quicktour.entity.DiscountPolicy;
import com.quicktour.entity.Tour;
import com.quicktour.service.DiscountPolicyService;
import com.quicktour.service.ToursService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller that performs read,update,delete operations for tour-discount policy relations
 *
 * @author Roman Lukash
 */
@Controller
@RequestMapping("/applyDiscount")
@PreAuthorize("hasRole('agent')")
public class ApplyDiscountPolicyController {
    @Autowired
    private ToursService toursService;
    @Autowired
    private DiscountPolicyService discountPolicyService;
    private final Logger logger = LoggerFactory.getLogger(ApplyDiscountPolicyController.class);

    @RequestMapping(value = {"/add", "/edit"}, method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> add(@RequestParam(required = false) Integer[] discount_policies,
                                   @RequestParam(required = false) Integer[] tours) {
        Map<String, Object> map = new HashMap<String, Object>(3);
        boolean error = false;
        if (tours == null || tours.length == 0) {
            map.put("Result", "ERROR");
            map.put("Message", "Please select tour");
            error = true;
        }
        if (discount_policies == null || discount_policies.length == 0) {
            map.put("Result", "ERROR");
            map.put("Message", "Please select policy");
            error = true;
        }
        if (error) {
            return map;
        }
        logger.debug("Apply discount policies: {} \nto Tours\n{}", discount_policies, tours);
        List<Tour> changedTours = discountPolicyService.applyDiscount(tours, discount_policies);
        map.put("Result", "OK");
        map.put("Record", changedTours.get(0));
        map.put("Records", changedTours);
        return map;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Map delete(@RequestParam(value = "tourId", required = false) Integer id) {
        Map<String, Object> map = new HashMap<String, Object>(3);
        if (id == null) {
            map.put("Result", "ERROR");
            map.put("Message", "Id of element is not found");
            return map;
        }
        Tour tour = toursService.findOne(id);
        tour.setDiscountPolicies(new ArrayList<DiscountPolicy>());
        toursService.saveTour(tour);
        map.put("Result", "OK");
        return map;
    }

    @RequestMapping(value = "/getAllRecords", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> applyDiscount() {
        Map<String, Object> map = new HashMap<String, Object>(3);
        boolean empty = false;
        map.put("Result", "OK");
        map.put("Records", toursService.findAgencyToursWithDiscountPoliciesAreEmpty(empty));
        return map;

    }

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "applyDiscount";
    }


}
