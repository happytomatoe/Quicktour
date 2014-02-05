package com.quicktour.service;

import com.quicktour.Roles;
import com.quicktour.entity.User;
import com.quicktour.repository.CompanyRepository;
import com.quicktour.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Contains all functional logic connected with users, such as:
 * registrating new user, getting current logged in user, updating
 * user's profile, creating registration email which will be sent
 * to every new user, functionality connected with user roles, updating
 * user's profile with new generated password and setting user active
 * or inactive
 *
 * @author Andrew Zarichnyi
 * @version 1.0 12/27/2013
 */
@Service
@Transactional
public class UsersService {
    public static final String TOUR_AGENCY = "Tour Agency";
    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(UsersService.class);
    @Autowired
    StandardPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CompanyRepository companyRepository;


    /**
     * Registers new user
     *
     * @param user - user that has to be registrated
     */
    public User registrateNewUser(User user) {
        user.setActive(false);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            if (user.getRole() == null) {
                user.setRole(updateRoleByCode(user));
            }
        User savedUser = userRepository.saveAndFlush(user);
        logger.info("New user saved: {} with role {}", user.getLogin(), user.getRole());
        return savedUser;

    }

    /**
     * Returns current logged in user
     */
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByLogin(auth.getName());
    }

    /**
     * Updates user role by his company code. Note: Admin cannot change his role in this way
     *
     * @param user - object of User class which company code was changed
     * @return object of Role class which represents role that this user object has to have
     */
    private Roles updateRoleByCode(User user) {
        if (user.getRole() != Roles.admin)
            if (user.getCompanyCode() == null ||
                    companyRepository.findByCompanyCode(user.getCompanyCode()) == null) {
                return Roles.user;
            } else if (companyRepository.findByCompanyCode(user.getCompanyCode()).getType().equals(TOUR_AGENCY)) {
                return Roles.agent;
            } else {
                return Roles.user;
            }
        else {
            return Roles.admin;
        }
    }



    /**
     * Activates user's profile so he can log into the system
     *
     * @param user -  user to be activated
     * @return true if user is activated and false in case when something goes wrong
     */
    public void activate(User user) {
        user.setActive(true);
            userRepository.saveAndFlush(user);

    }


    /**
     * Updates user's company code and role in database due to new company code.
     *
     * @param newCompanyCode - String which contains new company code of the user.
     */
    public void updateCompanyCode(String newCompanyCode) {
        User user = getCurrentUser();
        user.setCompanyCode(newCompanyCode);
        user.setRole(updateRoleByCode(user));
        userRepository.saveAndFlush(user);
        logger.info("User " + user.getLogin() + " " + "has changed his company code to '"
                + user.getCompanyCode() + "'");
    }

//    /**
//     * Generates login and password for anonymous who has ordered the tour in the system.
//     * Login and password are based on part of the user's mail before '@'.
//     * After this, sets user's role to user and avatar to default , encodes his password to md5
//     * and saves him to database.
//     *
//     * @param user - object of User class which comes from order form
//     * @return - object of User class which was recently saved to database
//     */
//    public User saveAnonymousCustomer(User user) {
//        String login = user.getEmail().split("@")[0];
//        String password = login;
//        user.setRole(Roles.user);
//        user.setLogin(login);
//        user.setPassword(password);
//        user.setPhoto(photoService.getDefaultAvatar());
//        sendRegistrationEmail(user);
//        user.setPassword(passwordEncoder.encode(password));
//        userRepository.saveAndFlush(user);
//        logger.info("Anonymous user with email {} has ordered a tour", user.getEmail());
//        return userRepository.findByEmail(user.getEmail());
//    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findOne(Integer id) {
        return userRepository.findOne(id);
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User save(User user) {
        return userRepository.saveAndFlush(user);
    }


}
