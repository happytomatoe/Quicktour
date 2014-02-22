package com.quicktour.controller;

import com.quicktour.entity.Company;
import com.quicktour.entity.Role;
import com.quicktour.entity.User;
import com.quicktour.service.CompanyService;
import com.quicktour.service.RolesService;
import com.quicktour.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * User and company managing functionality
 */

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {
    @Autowired
    private UsersService usersService;

    @Autowired
    private RolesService rolesService;
    @Autowired
    private CompanyService companyService;


    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String viewAllUsers(Model model) {
        List<User> users = usersService.findAll();
        model.addAttribute("users", users);
        return "viewUsers";
    }

    @RequestMapping(value = "/users/delete/{id}", method = RequestMethod.GET)
    public String deleter(@PathVariable("id") int id) {
        User user = usersService.findOne(id);
        if (user != null && user.getRole().getRoleId() != Role.ROLE_ADMIN) {
            usersService.delete(user);
        }
        return "redirect:/users/";
    }

    @RequestMapping(value = "/users/edit/{id}", method = RequestMethod.GET)
    public String myProfileForm(@PathVariable("id") int id, Model model) {
        User user = usersService.findOne(id);
        if (user.getRole().getRoleId() == Role.ROLE_ADMIN) {
            return "404";
        }
        model.addAttribute("roles", rolesService.findAll());
        model.addAttribute("user", user);
        return "editUser";
    }

    @RequestMapping(value = "/users/edit/{id}", method = RequestMethod.POST)
    public String myProfileForm(@Valid User user, BindingResult bindingResult,
                                @RequestParam("avatar") MultipartFile image) {
        if (bindingResult.hasErrors()) {
            return "editUser";
        }
        usersService.edit(user, image);
        return "redirect:/users";
    }

    @RequestMapping(value = "/company", method = RequestMethod.GET)
    public String viewAllCompanies(Model model) {
        List<Company> companies = companyService.findAll();
        model.addAttribute("companies", companies);
        return "viewCompanies";
    }

    @RequestMapping(value = "/company/edit/{id}", method = RequestMethod.GET)
    public String editCompany(@PathVariable("id") int id, Model model) {
        Company company = companyService.findOne(id);
        model.addAttribute("company", company);
        return "manageCompany";
    }

    @RequestMapping(value = "/company/add", method = RequestMethod.GET)
    public String editCompany(Model model) {
        model.addAttribute("company", new Company());
        return "manageCompany";
    }

    @RequestMapping(value = "/company/delete/{id}", method = RequestMethod.GET)
    public String editCompany(@PathVariable("id") Integer id) {
        companyService.delete(id);
        return "viewCompanies";
    }

    /**
     * Method updates company details which will come from ui to database.
     *
     * @param company       - object of Company class that will come from ui
     * @param bindingResult - contains information of correctness of company that will come from ui
     *                      due to entity restrictions
     * @param image         - contains file that admin uploads with new company details(optional).
     *                      It will be set as company avatar. If null, avatar change won't happen.
     */
    @RequestMapping(value = {"/company/edit/{id}", "company/add"}, method = RequestMethod.POST)
    public String editCompany(@Valid Company company, BindingResult bindingResult,
                              @RequestParam(value = "avatar", required = false)
                              MultipartFile image) {
        if (bindingResult.hasErrors()) {
            return "manageCompany";
        }
        companyService.saveAndFlush(company, image);
        return "redirect:/company";
    }
}
