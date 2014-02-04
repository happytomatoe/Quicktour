package com.quicktour.controller;

import com.quicktour.entity.Company;
import com.quicktour.entity.Photo;
import com.quicktour.entity.User;
import com.quicktour.service.CompanyService;
import com.quicktour.service.PhotoService;
import com.quicktour.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * Connects all functionality connected with editing users profiles to the views.
 * Contains actually editing profile form and updating company code form.
 *
 * @author Andrew Zarichnyi
 * @version 1.0 12/27/2013
 */
@Controller
public class ProfileController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private PhotoService photoService;
    @Autowired
    private CompanyService companyService;

    /**
     * Maps empty User object to the profile editing form
     *
     * @param model - model that will be represented in the profile editing form
     * @return redirects user to the profile editing form
     */
    @RequestMapping(value = "/myprofile", method = RequestMethod.GET)
    public String myProfileForm(Model model) {
        User user = usersService.getCurrentUser();
        model.addAttribute("user", user);
        return "myprofile";
    }

    /**
     * Makes UserService to apply profile changes and PhotoService to save new user's avatar
     *
     * @param user          - object of User class which contains all profile changes
     * @param bindingResult - contains information about correctness of User object
     * @param model         - represents the data model of the eidt profile form
     * @param image         - contains file uploaded by user if he wants to change his avatar
     * @return redirects user to main page
     */
    @RequestMapping(value = "/myprofile", method = RequestMethod.POST)
    public String myProfileForm(@Valid User user, BindingResult bindingResult, Model model,
                                @RequestParam(value = "avatar", required = false)
                                MultipartFile image) {
        String newAvatarName;
        if (usersService.findByLogin(user.getLogin()).getPhoto() != null) {
            newAvatarName = "@" + usersService.findByLogin(user.getLogin()).getPhoto().getUrl();

        } else {
            newAvatarName = user.getLogin() + ".jpg";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "/myprofile";
        }
        if (image.isEmpty()) {
            usersService.save(user);
            return "redirect:/";
        } else {
            Photo photo = photoService.saveImage(newAvatarName, image);
            if (photo != null) {
                user.setPhoto(photo);
                usersService.save(user);
                //TODO :test
                return "redirect:/";
            } else return "/myprofile";
        }
    }

    /**
     * Maps user's company to model which will be represented in mycompany page
     *
     * @param model - model which will be represented in mycompany page
     * @return redirects user to mycompany page
     */
    @RequestMapping(value = "/mycompany", method = RequestMethod.GET)
    public String myCompany(Model model) {
        Company myCompany = companyService.findByCompanyCode(usersService.getCurrentUser().getCompanyCode());
        model.addAttribute("company", myCompany);
        model.addAttribute("newCompanyCode", "");
        return "mycompany";
    }

    /**
     * Updates user's company code if user wants to update it
     *
     * @param newCompanyCode - contains company code that user wants to update
     * @param model          - model which will be represented in mycompany page
     * @return redirects user back to mycompany page which will contain information
     * about new user's company
     */
    @RequestMapping(value = "/mycompany", method = RequestMethod.POST)
    public String updateMyCompany(@RequestParam(value = "newCompanyCode") String newCompanyCode, Model model) {
        usersService.updateCompanyCode(newCompanyCode);
        Company myCompany = companyService.findByCompanyCode(usersService.getCurrentUser().getCompanyCode());
        model.addAttribute("company", myCompany);
        model.addAttribute("newCompanyCode", "");
        return "mycompany";
    }

}
