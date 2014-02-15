package com.quicktour.controller;

import com.quicktour.entity.User;
import com.quicktour.entity.ValidationLink;
import com.quicktour.service.EmailService;
import com.quicktour.service.PhotoService;
import com.quicktour.service.UsersService;
import com.quicktour.service.ValidationService;
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
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * @author Roman Lukash
 */
@Controller
@PreAuthorize("isAnonymous()")
public class UserController {

    @Autowired
    private UsersService usersService;

    private final Logger logger = LoggerFactory.getLogger(CompanyController.class);
    @Autowired
    private PhotoService photoService;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private EmailService emailService;
    @Value("${maxImageSize}")
    int maxImageSize;

    /**
     * Maps empty User object to the registration form
     *
     * @param model - model that will be represented in registration page
     * @return redirects user to the registration page
     */
    @PreAuthorize("isAnonymous()||hasRole('admin')")
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
    @PreAuthorize("isAnonymous()||hasRole('admin')")
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
        User newUser = usersService.registerUser(user);
        ValidationLink validationLink = validationService.createValidationLink(newUser);
        emailService.sendRegistrationEmail(user, validationLink);
        return "registrationsuccess-tile";

    }


    /**
     * Activate new user profile after he visits link that he has received in the
     * registration email.
     *
     * @param validationLink - link of the user to activate
     * @return redirects user to the login page
     */
    @RequestMapping(value = "/activate/{validationLink}")
    public String validationResolve(@PathVariable("validationLink") String validationLink) {
        boolean success = validationService.resolveLink(validationLink);
        if (!success) {
            return "404";
        }
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
    public String passwordRecovery(@Valid User user, BindingResult bindingResult, Model model) {
        User existingUser = usersService.findByEmail(user.getEmail());
        if (existingUser == null || !existingUser.isActive()) {
            bindingResult.rejectValue("email", "user.notexists", "User with such email doesn't exist or is not active");
        }
        if (bindingResult.hasErrors()) {
            return "passwordrecovery";
        }
        usersService.recoverPassword(existingUser);
        model.addAttribute("message", "Check your email for further instructions!");
        return "success";
    }

    @RequestMapping(value = "/changePassword/{link}", method = RequestMethod.GET)
    public String changePassword(@PathVariable("link") String link) {
        User user = validationService.checkPasswordChangeLink(link);
        if (user == null) {
            return "404";
        }
        return "change-pass";
    }

    @RequestMapping(value = "/changePassword/{link}", method = RequestMethod.POST)
    public String changePasswordPost(@RequestParam("password") String password,
                                     @RequestParam("password2") String password2,
                                     @PathVariable("link") String link,
                                     Model model) {
        boolean fail = false;
        if (password.isEmpty()) {
            model.addAttribute("passwordError", "Password is empty");
            fail = true;
        }
        if (password.length() < 8) {
            model.addAttribute("passwordError", "Password length must be at least 8 character ");
            fail = true;
        }
        if (!password2.equals(password)) {
            model.addAttribute("password2Error", "Passwords dont match");
            fail = true;
        }
        if (fail) {
            return "change-pass";
        }
        model.addAttribute("message", "Your password successfuly chaged!");
        ValidationLink validationLink = validationService.findByUrl(link);
        usersService.changePassword(password, validationLink.getUserId());
        validationService.delete(validationLink);
        return "success";
    }


}
