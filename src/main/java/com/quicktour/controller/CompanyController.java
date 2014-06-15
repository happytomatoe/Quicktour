package com.quicktour.controller;

import com.quicktour.entity.Company;
import com.quicktour.service.CompanyService;
import com.quicktour.service.UsersService;
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
 */
@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class CompanyController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private CompanyService companyService;


    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/mycompany", method = RequestMethod.GET)
    public String myCompany(Model model) {
        Company myCompany = companyService.findByCompanyCode(usersService.getCurrentUser().getCompanyCode());
        model.addAttribute("company", myCompany);
        model.addAttribute("newCompanyCode", "");
        return "myCompany";
    }

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
