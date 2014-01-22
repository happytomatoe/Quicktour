package com.quicktour.controller;

import com.quicktour.entity.DiscountPolicy;
import com.quicktour.service.DiscountDependencyService;
import com.quicktour.service.DiscountPolicyService;
import com.quicktour.service.SqlDatePropertyEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Controller that performs CRUD operations for discount policy
 *
 * @author babkamen
 */
@Controller
@PreAuthorize("hasRole('agent')")
@RequestMapping("/discount_policy")
public class DiscountPolicyController {

    static final Logger logger = LoggerFactory.getLogger(DiscountDependencyController.class);
    private static final String ONE = "1";
    private static final String RESULT = "Result";
    private static final String ERROR = "ERROR";
    private static final String MESSAGE = "Message";
    private static final String OK = "OK";
    private static final String RECORD = "Record";
    private static final String RECORDS = "Records";
    private static final String POLICIES = "policies";
    private static final String POLICY = "policy";
    private static final String DAYOFWEEK = "dayofweek";
    private static final String USERS_SEX = "users.sex";
    private static final String USERS_NAME = "users.name";
    @Autowired
    DiscountPolicyService discountPolicyService;
    @Autowired
    DiscountDependencyService discountDependencyService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(java.sql.Date.class, new SqlDatePropertyEditor());
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute(POLICIES, discountPolicyService.findByCompany());
        model.addAttribute(POLICY, new DiscountPolicy());
        return "discountPolicy";
    }

    @RequestMapping(value = "/getAllPolicies", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getAllPolicies() {
        Map<String, Object> map = new LinkedHashMap<String, Object>(3);
        map.put(RESULT, OK);
        map.put(RECORDS, discountPolicyService.findByCompany());
        return map;
    }

    @RequestMapping("/getAllDiscounts")
    @ResponseBody
    public Map<String, Object> getAllDiscounts() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<DiscountPolicy> discountPolicies = discountPolicyService.findByCompany();
        for (DiscountPolicy discountPolicy : discountPolicies) {
            map.put(String.valueOf(discountPolicy.getId()), discountPolicy.getName());
        }
        return map;
    }

    @RequestMapping(value = {"/add", "/edit"}, method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(
            @Valid DiscountPolicy discountPolicy,
            BindingResult bindingResult,
            @RequestParam(value = "relation", required = false) String[] relation,
            @RequestParam(value = "condition", required = false) String[] conditions,
            @RequestParam(value = "sign", required = false) String[] signs,
            @RequestParam(value = "param", required = false) String[] params) {
        Map<String, Object> map = new HashMap<String, Object>(3);
        StringBuilder message = new StringBuilder();
        double amount = 0;
        boolean isFormula=false;
        Date startDate = discountPolicy.getStartDate();
        Date endDate = discountPolicy.getEndDate();
        boolean startDateIsNULL = startDate == null;
        boolean endDateIsNULL = endDate == null;
        String formula = discountPolicy.getFormula();
        if (formula != null && formula.length() > 0) {
            String[] tags = discountDependencyService.findAllTags();
            for (String tag : tags) {
                formula = formula.replace(tag, ONE);
            }
            Pattern pattern = Pattern.compile("[()\\d\\+\\*/.-]+");
            if (!pattern.matcher(formula).find()) {
                bindingResult.rejectValue("formula", "formula.syntaxerror", "Discount syntax is incorrect");
            } else {
                if (!discountPolicyService.testFormula(formula)) {
                    bindingResult.rejectValue("formula", "formula.syntaxerror", "Discount syntax is incorrect");
                }
            }
        }
        if (!startDateIsNULL) {
            if (endDateIsNULL) {
                bindingResult.rejectValue("endDate", "endDate.invalid", "End date is required if Start date is set ");
            } else if (startDate.after(endDate)) {
                bindingResult.rejectValue("endDate", "endDate.invalid", "End date cannot be before Start date ");
            }
        }
        if (endDate != null) {
            if (startDateIsNULL) {
                bindingResult.rejectValue("startDate", "startDate.invalid", "Start date is required if end date is set ");
            }
        }
        try {
            amount = Double.valueOf(discountPolicy.getFormula());
        } catch (Exception e) {
            isFormula=true;
        }
        if (amount < 0 || amount > 100) {
            bindingResult.rejectValue("formula", "formula.invalid", "Discount is higher than 100 or lower than 0");
        }

        if (conditions != null) {
            if (params.length == 0) {
                bindingResult.rejectValue("condition", "condition.invalid", "Condition is empty");
            }
            for (int i = 0; i < params.length; i++) {
                if (params[i].trim().length() == 0) {
                    bindingResult.rejectValue("condition", "condition.invalid", "Some condition is empty");
                } else if (!DAYOFWEEK.equals(conditions[i]) &&
                        !USERS_SEX.equals(conditions[i]) && !USERS_SEX.equals(conditions[i]) &&
                        !USERS_NAME.equals(conditions[i]) && !params[i].matches("(\\d+(\\.\\d+)?)")) {
                    bindingResult.rejectValue("condition", "condition.empty", conditions[i].replace("users.", "User's ")
                            .replace("orders.", "Order's ") + " must be number  ");
                }

            }
        }
        if (bindingResult.hasErrors()) {
            map.put(RESULT, ERROR);
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                message.append(error.getDefaultMessage()).append("<br>");
            }
            map.put(MESSAGE, message);
            return map;
        }
        if (conditions != null) {
            discountPolicy = discountPolicyService.addDiscountPolicy(discountPolicy, relation, conditions, signs, params);
        } else {
            discountPolicy = discountPolicyService.addDiscountPolicy(discountPolicy);
        }
        if(isFormula){
            discountPolicy.setFormula(discountDependencyService.convertFormula(discountPolicy.getFormula()));
        }
        map.put(RESULT, OK);
        map.put(RECORD, discountPolicy);
        logger.info("Save discount policy {}", discountPolicy);
        return map;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Map delete(@RequestParam(value = "id", required = false) Integer id) {
        Map<String, Object> map = new HashMap<String, Object>(2);
        discountPolicyService.delete(id);
        map.put(RESULT, OK);
        return map;
    }


}
