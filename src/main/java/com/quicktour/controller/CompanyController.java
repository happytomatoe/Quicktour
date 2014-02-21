package com.quicktour.controller;

import com.quicktour.entity.Company;
import com.quicktour.service.CompanyService;
import com.quicktour.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Connects all functionality connected with creating new users and companies,
 * such as user registration, company registration, activating users profile,
 * and password recovering to the views.
 *
 * @author Andrew Zarichnyi
 * @version 1.0 12/27/2013
 */
@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class CompanyController {

    private final Logger logger = LoggerFactory.getLogger(CompanyController.class);
    @Autowired
    private UsersService usersService;
    @Autowired
    private CompanyService companyService;


    /**
     * Maps user's company to model which will be represented in mycompany page
     *
     * @param model - model which will be represented in mycompany page
     * @return redirects user to mycompany page
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/mycompany", method = RequestMethod.GET)
    public String myCompany(Model model) {
        Company myCompany = companyService.findByCompanyCode(usersService.getCurrentUser().getCompanyCode());
        model.addAttribute("company", myCompany);
        model.addAttribute("newCompanyCode", "");
        return "myCompany";
    }

    /**
     * Updates user's company code if user wants to update it
     *
     * @param newCompanyCode - contains company code that user wants to update
     * @param model          - model which will be represented in mycompany page
     * @return redirects user back to mycompany page which will contain information
     * about new user's company
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/mycompany", method = RequestMethod.POST)
    public String updateMyCompany(@RequestParam(value = "newCompanyCode") String newCompanyCode, Model model) {
        usersService.updateCompanyCode(newCompanyCode);
        Company myCompany = companyService.findByCompanyCode(usersService.getCurrentUser().getCompanyCode());
        model.addAttribute("company", myCompany);
        model.addAttribute("newCompanyCode", "");
        return "myCompany";
    }

}
