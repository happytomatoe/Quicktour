package com.quicktour.controller;

import com.quicktour.entity.Company;
import com.quicktour.entity.User;
import com.quicktour.entity.ValidationLink;
import com.quicktour.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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
public class RegistrationController {

    @Autowired
    private UsersService usersService;

    private final Logger logger = LoggerFactory.getLogger(RegistrationController.class);
    @Autowired
    private PhotoService photoService;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private EmailService emailService;
    @Autowired
    private CompanyService companyService;
    @Value("${maxImageSize}")
    int maxImageSize;

    /**
     * Maps empty User object to the registration form
     *
     * @param model - model that will be represented in registration page
     * @return redirects user to the registration page
     */
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    /**
     * Checks User object that will come from ui for correctness due to entity restrictions
     * by bindingResult, then if user didn't upload any avatar, he will be just registrated
     * to the system, otherwise the image will be saved to the database, User object will be
     * connected with uploaded image and then saved to the database
     *
     * @param user          - contains all information that user inputs in registration form
     * @param bindingResult - contains information about correctness of User object that will
     *                      come from ui due to enity restrictions
     * @param image         - contains file that user will upload with his information as avatar
     *                      (may be empty)
     * @return - redirects user back to registration page if something goes wrong and to the
     * "registration success" notification if all is OK
     */
    @Transactional
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registrationForm(@Valid User user, BindingResult bindingResult,
                                   @RequestParam(value = "avatar", required = false)
                                   MultipartFile image) {
        String type = image.getContentType().split("/")[0];
        logger.debug("Content type {}.Type {}", image.getContentType(), type);
        if (usersService.findByEmail(user.getEmail()) != null) {
            bindingResult.rejectValue("email", "email.invalid", "User with such email already exists");
        }
        if (usersService.findByLogin(user.getLogin()) != null) {
            bindingResult.rejectValue("login", "login.invalid", "User with such login already exists");
        }
        if (user.getLogin().trim().equals(user.getPassword().trim())) {
            bindingResult.rejectValue("password", "password.invalid", "Password can't match login ");
        }

        if (!image.isEmpty() && !type.equalsIgnoreCase("image")) {
            bindingResult.rejectValue("photo", "photo.type", "Uploaded file is not image");
        }
        if (image.getSize() > maxImageSize) {
            bindingResult.rejectValue("photo", "photo.invalid", "Maximum upload size of " + maxImageSize + " bytes exceeded ");
        }
        logger.debug("BindingResult :{}", bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if (!image.isEmpty()) {
            String avatarName = user.getLogin() + ".jpg";
            user.setPhoto(photoService.saveImage(avatarName, image));
        }
        User newUser = usersService.registrateNewUser(user);
        ValidationLink validationLink = validationService.createValidationLink(newUser);
        ServletRequestAttributes sra = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes());
        HttpServletRequest request = sra.getRequest();
        emailService.sendRegistrationEmail(user, request, validationLink);
        return "registrationsuccess-tile";

    }

    /**
     * Maps empty Company object to company registration form
     *
     * @param model - model that will be represented on the "add company" page
     * @return - redirects user to the "add company" page
     */
    @PreAuthorize("hasRole('admin')")
    @RequestMapping(value = "/addcompany", method = RequestMethod.GET)
    public String addNewCompany(Model model) {
        Company company = new Company();
        model.addAttribute("company", company);
        return "companyregistration";
    }

    /**
     * Checks Company object that will come from ui for correctness due to entity restrictions
     * by bindingResult, then if admin didn't upload any avatar, company will be just registrated
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
    @PreAuthorize("hasRole('admin')")
    @RequestMapping(value = "/addcompany", method = RequestMethod.POST)
    public String addNewCompany(@Valid Company company, BindingResult bindingResult,
                                @RequestParam(value = "avatar", required = false)
                                MultipartFile image) {
        String avatarName = company.getName() + "comp.jpg";
        company.setPhoto(photoService.saveImage(avatarName, image));

        if (bindingResult.hasErrors() || companyService.addNewCompany(company)) {
            return "companyregistration";
        }
        return "redirect:/";
    }

    /**
     * Activate new user profile after he visits link that he has received in the
     * registration email.
     *
     * @param validationLink - link of the user to activate
     * @return redirects user to the login page
     */
    @RequestMapping(value = "/login/{validationLink}")
    public String validationResolve(@PathVariable("validationLink") String validationLink, Model model) {
        boolean success = validationService.resolveLink(validationLink);
        model.addAttribute("validationSuccess", success);
        logger.debug("Validation link {}.{}", validationLink, success);
        return "login";
    }

    /**
     * Adds empty User object to the model that will be represented in password recovery page
     *
     * @param model - model that will be represented in password recovery page
     * @return redirects user to the password recovery page
     */
    @RequestMapping(value = "/passwordrecovery", method = RequestMethod.GET)
    public String passwordRecovery(Model model) {
        model.addAttribute("user", new User());
        return "passwordrecovery";
    }

    /**
     * Sets randomly generated password to user, whose email was input in the password recovery
     * form and then sends to this email message with new user credentials
     *
     * @param user          - contains email of the user who wants to recover his password
     * @param bindingResult - contains information about correctness of the email due to entity restrictions
     * @return - redirects user to login page
     */
    @RequestMapping(value = "/passwordrecovery", method = RequestMethod.POST)
    public String passwordRecovery(@Valid User user, BindingResult bindingResult) {
        //  usersService.setNewPassword(user);
        //TODO:change
        return "login";
    }
}
