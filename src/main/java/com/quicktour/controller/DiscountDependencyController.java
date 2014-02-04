package com.quicktour.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @PreAuthorize("hasAnyRole('admin','agent')")
    @RequestMapping(value = "/getAllDependencies")
    @ResponseBody
    public Map<String, Object> getAllDependencies() {
        Map<String, Object> map = new HashMap<String, Object>(3);
        map.put("Result", "OK");
        map.put("Records", dependenciesService.findAllDependencies());
        return map;
    }

    @PreAuthorize("hasRole('admin')")
    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("dependencies", dependenciesService.findAllDependencies());
        model.addAttribute("dependency", new DiscountDependency());
        return "discountDependency";
    }

    @PreAuthorize("hasRole('admin')")
    @RequestMapping(value = {"/add", "/edit"}, method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> add(@Valid DiscountDependency discountDependency, BindingResult bindingResult) {
        logger.debug("Save discount dependency {}", discountDependency);
        Map<String, Object> map = new HashMap<String, Object>(3);
        if (bindingResult.hasErrors()) {
            map.put("Result", "ERROR");
            StringBuilder message = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                message.append(error.getDefaultMessage()).append("<br>");
            }
            map.put("Message", message);
            return map;
        }
        discountDependency = dependenciesService.saveAndFlush(discountDependency);
        map.put("Result", "OK");
        map.put("Record", discountDependency);
        return map;
    }

    @PreAuthorize("hasRole('admin')")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Map delete(@RequestParam(value = "id", required = false) Integer id) {
        Map<String, String> map = new HashMap<String, String>(2);
        dependenciesService.delete(id);
        map.put("Result", "OK");
        return map;
    }

    @PreAuthorize("hasRole('admin')")
    @RequestMapping(value = "/getAvailableFields", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getColumnNames() {

        return dependenciesService.findAvailableColumns();
    }
}
