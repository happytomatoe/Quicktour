package com.quicktour.controller;

import com.quicktour.dto.JTableResponse;
import com.quicktour.entity.DiscountDependency;
import com.quicktour.service.DiscountDependencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller that performs CRUD operations on discount dependency
 *
 * @author Roman Lukash
 */
@Controller
@RequestMapping("/discount_dependency")
public class DiscountDependencyController {
    @Autowired
    DiscountDependencyService dependenciesService;

    private final Logger logger = LoggerFactory.getLogger(DiscountDependencyController.class);

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_AGENT')")
    @RequestMapping(value = "/getAllDependencies")
    @ResponseBody
    public JTableResponse getAllDependencies() {
        JTableResponse<DiscountDependency> jTableResponse = new JTableResponse<DiscountDependency>(JTableResponse.Results.OK);
        jTableResponse.setResult(JTableResponse.Results.OK);
        jTableResponse.setRecords(dependenciesService.findAllDependencies());
        return jTableResponse;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("dependencies", dependenciesService.findAllDependencies());
        model.addAttribute("dependency", new DiscountDependency());
        return "discountDependency";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/add", "/edit"}, method = RequestMethod.POST)
    @ResponseBody
    public JTableResponse add(@Valid DiscountDependency discountDependency, BindingResult bindingResult) {
        logger.debug("Save discount dependency {}", discountDependency);
        JTableResponse<DiscountDependency> jTableResponse = new JTableResponse<DiscountDependency>(JTableResponse.Results.OK);
        if (bindingResult.hasErrors()) {
            jTableResponse.setResult(JTableResponse.Results.ERROR);
            for (FieldError error : bindingResult.getFieldErrors()) {
                jTableResponse.addMessage(error.getDefaultMessage());
            }
            return jTableResponse;
        }
        discountDependency = dependenciesService.saveAndFlush(discountDependency);
        jTableResponse.setResult(JTableResponse.Results.OK);
        jTableResponse.setRecord(discountDependency);
        return jTableResponse;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public JTableResponse delete(DiscountDependency discountDependency) {
        JTableResponse<DiscountDependency> jTableResponse = new JTableResponse<DiscountDependency>(JTableResponse.Results.OK);
        dependenciesService.delete(discountDependency);
        jTableResponse.setResult(JTableResponse.Results.OK);
        return jTableResponse;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/getAvailableFields", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getColumnNames() {
        return dependenciesService.findAvailableColumns();
    }
}
