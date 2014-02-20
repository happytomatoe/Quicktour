package com.quicktour.controller;

import com.quicktour.entity.User;
import com.quicktour.entity.ValidationLink;
import com.quicktour.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author Roman Lukash
 */
@Controller
public class UserController {

    @Autowired
    private UsersService usersService;

    private final Logger logger = LoggerFactory.getLogger(CompanyController.class);
    @Autowired
    private PhotoService photoService;

    @Autowired
    private ValidationService validationService;
    @Autowired
    private RolesService rolesService;

    @Autowired
    private EmailService emailService;
    @Value("${maxImageSize}")
    int maxImageSize;

    @PreAuthorize("isAnonymous()")
    @RequestMapping(value = "/signin")
    public String signin() {
        return "signin";
    }

    /**
     * Maps empty User object to the signup form
     *
     * @param model - model that will be represented in signup page
     * @return redirects user to the signup page
     */
    @PreAuthorize("isAnonymous()||hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signupForm(Model model, HttpServletRequest request) {
        model.addAttribute("user", new User());
        if (request.isUserInRole("ROLE_ADMIN")) {
            model.addAttribute("roles", rolesService.findAll());
        }
        return "signup";
    }

    /**
     * Checks User object that will come from ui for correctness due to entity restrictions
     * by bindingResult, then if user didn't upload any avatar, he will be just registrated
     * to the system, otherwise the image will be saved to the database, User object will be
     * connected with uploaded image and then saved to the database
     *
     * @param user          - contains all information that user inputs in signup form
     * @param bindingResult - contains information about correctness of User object that will
     *                      come from ui due to enity restrictions
     * @param image         - contains file that user will upload with his information as avatar
     *                      (may be empty)
     * @return - redirects user back to signup page if something goes wrong and to the
     * "signup success" notification if all is OK
     */
    @PreAuthorize("isAnonymous()||hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signupForm(@Valid User user, BindingResult bindingResult,
                             @RequestParam(value = "avatar", required = false)
                             MultipartFile image) {
        String type = image.getContentType().split("/")[0];
        logger.debug("Content type {}.Type {}", image.getContentType(), type);
        if (usersService.findByEmail(user.getEmail()) != null) {
            bindingResult.rejectValue("email", "email.invalid", "User with such email already exists");
        }
        if (usersService.findByUsername(user.getUsername()) != null) {
            bindingResult.rejectValue("username", "username.invalid", "User with such username already exists");
        }
        if (user.getUsername().trim().equals(user.getPassword().trim())) {
            bindingResult.rejectValue("password", "password.invalid", "Password can't match username ");
        }

        if (!image.isEmpty() && !type.equalsIgnoreCase("image")) {
            bindingResult.rejectValue("photo", "photo.type", "Uploaded file is not an image");
        }
        if (image.getSize() > maxImageSize) {
            bindingResult.rejectValue("photo", "photo.invalid", "Maximum upload size of " + maxImageSize + " bytes exceeded ");
        }
        logger.debug("BindingResult :{}", bindingResult);
        if (bindingResult.hasErrors()) {
            return "signup";
        }
        if (!image.isEmpty()) {
            photoService.saveImageAndSet(user, image);
        }
        User newUser = usersService.registerUser(user);
        ValidationLink validationLink = validationService.createValidationLink(newUser);
        emailService.sendRegistrationEmail(user, validationLink);
        return "signupSuccess";

    }


    @RequestMapping("/username/validate")
    @ResponseBody
    public boolean validateusername(@RequestParam(required = false) String username) {
        return usersService.findByUsername(username) == null;
    }

    @RequestMapping("/email/validate")
    @ResponseBody
    public boolean validateEmail(@RequestParam(required = false) String email) {
        return usersService.findByEmail(email) == null;
    }

    /**
     * Activate new user profile after he visits link that he has received in the
     * signup email.
     *
     * @param validationLink - link of the user to activate
     * @return redirects user to the username page
     */
    @PreAuthorize("isAnonymous()")
    @RequestMapping(value = "/activate/{validationLink}")
    public String validationResolve(@PathVariable("validationLink") String validationLink, Model model) {
        boolean success = validationService.resolveLink(validationLink);
        if (!success) {
            return "404";
        }
        logger.debug("Adding to model activation message");
        model.addAttribute("activationMessage", "You successfuly activated your account!");
        return "signin";
    }

    /**
     * Adds empty User object to the model that will be represented in password recovery page
     *
     * @param model - model that will be represented in password recovery page
     * @return redirects user to the password recovery page
     */
    @PreAuthorize("isAnonymous()")
    @RequestMapping(value = "/passwordrecovery", method = RequestMethod.GET)
    public String passwordRecovery(Model model) {
        model.addAttribute("user", new User());
        return "passwordRecovery";
    }

    /**
     * Sets randomly generated password to user, whose email was input in the password recovery
     * form and then sends to this email message with new user credentials
     *
     * @param user          - contains email of the user who wants to recover his password
     * @param bindingResult - contains information about correctness of the email due to entity restrictions
     * @return - redirects user to username page
     */
    @PreAuthorize("isAnonymous()")
    @RequestMapping(value = "/passwordrecovery", method = RequestMethod.POST)
    public String passwordRecovery(@Valid User user, BindingResult bindingResult, Model model) {
        User existingUser = usersService.findByEmail(user.getEmail());
        if (existingUser == null || !existingUser.isEnabled()) {
            bindingResult.rejectValue("email", "user.notexists", "User with such email doesn't exist or is not active");
        }
        if (bindingResult.hasErrors()) {
            return "passwordRecovery";
        }
        usersService.recoverPassword(existingUser);
        model.addAttribute("message", "Check your email for further instructions!");
        return "success";
    }

    @PreAuthorize("isAnonymous()")
    @RequestMapping(value = "/changePassword/{link}", method = RequestMethod.GET)
    public String changePassword(@PathVariable("link") String link) {
        User user = validationService.checkPasswordChangeLink(link);
        if (user == null) {
            return "404";
        }
        return "changePassword";
    }

    @PreAuthorize("isAnonymous()")
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
            model.addAttribute("password2Error", "Passwords don't match");
            fail = true;
        }
        if (fail) {
            return "changePassword";
        }
        model.addAttribute("message", "Your password successfully changed!");
        ValidationLink validationLink = validationService.findByUrl(link);
        usersService.changePassword(password, validationLink.getUser());
        validationService.delete(validationLink);
        return "success";
    }


    /**
     * Maps empty User object to the profile editing form
     *
     * @param model - model that will be represented in the profile editing form
     * @return redirects user to the profile editing form
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String myProfileForm(Model model) {
        User user = usersService.getCurrentUser();
        model.addAttribute("user", user);
        return "myProfile";
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
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public String myProfileForm(@Valid User user, BindingResult bindingResult, Model model,
                                @RequestParam(value = "avatar", required = false)
                                MultipartFile image) {
        User currentUser = usersService.getCurrentUser();
        user.setUsername(currentUser.getUsername());
        user.setUserId(currentUser.getUserId());
        user.setPassword(currentUser.getPassword());
        user.setRole(currentUser.getRole());
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "/profile";
        }
        if (!image.isEmpty()) {
            photoService.saveImageAndSet(user, image);
        }
        usersService.save(user);
        return "redirect:/";


    }

    @RequestMapping(value = "/user/{id}")
    public String userInfo(@PathVariable("id") int id, Model model) {
        User user = usersService.findOne(id);
        if (user == null) {
            return "404";
        }
        model.addAttribute("user", user);
        return "userInfo";
    }
}
