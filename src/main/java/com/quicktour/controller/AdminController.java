package com.quicktour.controller;

import com.quicktour.Roles;
import com.quicktour.entity.Company;
import com.quicktour.entity.User;
import com.quicktour.service.CompanyService;
import com.quicktour.service.PhotoService;
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
 * Controller that connects all admin's functionality with viewing
 * and editing all users and companies
 *
 * @author Andrew Zarichnyi
 * @version 1.0 12/27/2013
 */

@Controller
@PreAuthorize("hasRole('admin')")
public class AdminController {
    @Autowired
    private UsersService usersService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private PhotoService photoService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String viewAllUsers(Model model) {
        List<User> users = usersService.findAll();
        model.addAttribute("users", users);
        return "viewusers";
    }

    @RequestMapping(value = "/users/delete/{id}", method = RequestMethod.GET)
    public String deleter(@PathVariable("id") int id) {
        User user = usersService.findOne(id);
        if (user != null && user.getRole() != Roles.admin) {
            usersService.delete(user);
        }
        return "redirect:/users/";
    }

    /**
     * Method gets user by id that was set in url and maps him to the edituser-tile model
     *
     * @param id    - identificator of the user whose profile admin wants to edit
     * @param model - model that will be mapped to th edituser tile
     * @return name of the tile where controller will redirect user after this method will end its work
     */
    @RequestMapping(value = "/users/edit/{id}", method = RequestMethod.GET)
    public String myProfileForm(@PathVariable("id") int id, Model model) {
        User user = usersService.findOne(id);
        if (user.getRole() == Roles.admin) {
            return "404";
        }
        model.addAttribute("user", user);
        return "edituser-tile";
    }

    /**
     * Method updates user profile that will come from ui to the database
     *
     * @param user          - object of User class which comes from ui
     * @param bindingResult - contains information of correctness of the user which will come from ui
     *                      due to entity restrictions
     * @return - redirects user to the main page if the update was successful and back
     * to the myrofile page if bindingResult will have errors.
     */
    @RequestMapping(value = "/users/edit/{id}", method = RequestMethod.POST)
    public String myProfileForm(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edituser-tile";
        }
        usersService.save(user);
        return "redirect:/users";
    }

    /**
     * Method gets the List<Company> with  all companies that exist in the system and
     * adds it to viewcompanies tile model.
     *
     * @param model - Model that will be mapped to the viewcompanies tile
     */
    @RequestMapping(value = "/company", method = RequestMethod.GET)
    public String viewAllCompanies(Model model) {
        List<Company> companies = companyService.findAll();
        model.addAttribute("companies", companies);
        return "viewcompanies";
    }

    /**
     * Method gets company by id that was set in url and maps him to the editcompany tile model
     *
     * @param id    - identificator of the company whose details admin wants to edit
     * @param model - model that will be mapped to th edituser tile
     * @return name of the tile where controller will redirect admin after this method will end its work
     */
    @RequestMapping(value = "/company/edit/{id}", method = RequestMethod.GET)
    public String editCompany(@PathVariable("id") int id, Model model) {
        Company company = companyService.findOne(id);
        model.addAttribute("company", company);
        return "editcompany";
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
    @RequestMapping(value = "/company/edit/{id}", method = RequestMethod.POST)
    public String editCompany(@Valid Company company, BindingResult bindingResult,
                              @RequestParam(value = "avatar", required = false)
                              MultipartFile image) {
        if (bindingResult.hasErrors()) {
            return "editcompany";
        }
        String newAvatarName = company.getName() + "comp.jpg";
        if (!image.isEmpty()) {
            company.setPhoto(photoService.saveImage(newAvatarName, image));
        }
        if (companyService.saveAndFlush(company) == null) {
            return "editcompany";
        }
        return "redirect:/company";
    }
}
