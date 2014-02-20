package com.quicktour.controller;

import com.quicktour.dto.JTableResponse;
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
import java.util.List;

/**
 * Controller that performs read,update,delete operations for tour-discount policy relations
 *
 * @author Roman Lukash
 */
@Controller
@RequestMapping("/applyDiscount")
@PreAuthorize("hasRole('ROLE_AGENT')")
public class ApplyDiscountPolicyController {
    @Autowired
    private ToursService toursService;
    @Autowired
    private DiscountPolicyService discountPolicyService;
    private final Logger logger = LoggerFactory.getLogger(ApplyDiscountPolicyController.class);

    @RequestMapping(value = {"/add", "/edit"}, method = RequestMethod.POST)
    @ResponseBody
    public JTableResponse add(@RequestParam(required = false) Integer[] discount_policies,
                              @RequestParam(required = false) Integer[] tours) {
        JTableResponse<Tour> jTableResponse = new JTableResponse<Tour>(JTableResponse.Results.OK);
        if (tours == null || tours.length == 0) {
            jTableResponse.setResult(JTableResponse.Results.ERROR);
            jTableResponse.addMessage("Please select tour");
        }
        if (discount_policies == null || discount_policies.length == 0) {
            jTableResponse.setResult(JTableResponse.Results.ERROR);
            jTableResponse.addMessage("Please select policy");
        }
        if (jTableResponse.getResult() == JTableResponse.Results.ERROR) {
            return jTableResponse;
        }
        logger.debug("Apply discount policies: {} \nto Tours\n{}", discount_policies, tours);
        List<Tour> changedTours = discountPolicyService.applyDiscount(tours, discount_policies);
        jTableResponse.setResult(JTableResponse.Results.OK);
        jTableResponse.setRecord(changedTours.get(0));
        jTableResponse.setRecords(changedTours);
        return jTableResponse;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public JTableResponse delete(@RequestParam(value = "tourId", required = false) Integer id) {
        JTableResponse<Tour> jTableResponse = new JTableResponse<Tour>(JTableResponse.Results.OK);
        if (id == null) {
            jTableResponse.setResult(JTableResponse.Results.ERROR);
            jTableResponse.setMessage("Id of element is not found");
            return jTableResponse;
        }
        Tour tour = toursService.findOne(id);
        tour.setDiscountPolicies(new ArrayList<DiscountPolicy>());
        toursService.saveTour(tour);
        jTableResponse.setResult(JTableResponse.Results.OK);
        return jTableResponse;
    }

    @RequestMapping(value = "/getAllRecords", method = RequestMethod.POST)
    @ResponseBody
    public JTableResponse applyDiscount() {
        JTableResponse<Tour> jTableResponse = new JTableResponse<Tour>(JTableResponse.Results.OK);
        jTableResponse.setResult(JTableResponse.Results.OK);
        jTableResponse.setRecords(toursService.findAgencyToursWithNotEmptyDiscountPolicies());
        return jTableResponse;

    }

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "applyDiscount";
    }


}
