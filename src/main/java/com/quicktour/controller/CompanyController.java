package com.quicktour.controller;

import com.quicktour.entity.Company;
import com.quicktour.service.CompanyService;
import com.quicktour.service.PhotoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * Connects all functionality connected with creating new users and companies,
 * such as user registration, company registration, activating users profile,
 * and password recovering to the views.
 *
 * @author Andrew Zarichnyi
 * @version 1.0 12/27/2013
 */
@Controller
@PreAuthorize("hasRole('admin')")
public class CompanyController {

    private final Logger logger = LoggerFactory.getLogger(CompanyController.class);
    @Autowired
    private PhotoService photoService;
    @Autowired
    private CompanyService companyService;

    /**
     * Maps empty Company object to company registration form
     *
     * @param model - model that will be represented on the "add company" page
     * @return - redirects user to the "add company" page
     */
    @RequestMapping(value = "/company/add", method = RequestMethod.GET)
    public String addNewCompany(Model model) {
        Company company = new Company();
        model.addAttribute("company", company);
        return "companyregistration";
    }

    /**
     * Checks Company object that will come from ui for correctness due to entity restrictions
     * by bindingResult, then if admin didn't upload any avatar, company will be just registered
     * to the system, otherwise the image will be saved to the database, Company object will be
     * connected with uploaded image and then saved to the database
     *
     * @param company       - contains all information that admin inputs in company registration form
     * @param bindingResult - contains all information about correctness of Company object that
     *                      will come from ui due to entity restrictions
     * @param image         - contains file that admin will upload with company information as its avatar
     *                      (may be empty)
     * @return - redirects admin back to the registration form if something is wrong or to the main
     * page if all is OK
     */
    @RequestMapping(value = "/company/add", method = RequestMethod.POST)
    public String addNewCompany(@Valid Company company, BindingResult bindingResult,
                                @RequestParam(value = "avatar", required = false)
                                MultipartFile image) {
        String avatarName = company.getName() + "comp.jpg";
        company.setPhoto(photoService.saveImage(avatarName, image));

        if (bindingResult.hasErrors() || companyService.addNewCompany(company)) {
            return "companyregistration";
        }
        return "redirect:/company";
    }

}
