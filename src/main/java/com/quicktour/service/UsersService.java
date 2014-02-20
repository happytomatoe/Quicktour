package com.quicktour.service;

import com.quicktour.entity.*;
import com.quicktour.repository.CompanyRepository;
import com.quicktour.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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
@Service("myUserService")
@Transactional
public class UsersService {
    private static final String TOUR_AGENCY = "Tour Agency";
    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(UsersService.class);
    @Autowired
    StandardPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    ValidationService validationService;
    @Autowired
    EmailService emailService;


    /**
     * Finds and deletes users that wasn't activated within 2 days every 2 days
     */
    @Scheduled(cron = "0 0 0 0/2 * *")
    public void deleteExpiredNotActiveUsers() {
        userRepository.deleteExpiredNotActiveUsers();
    }


    public User registerUser(User user) {

        user.setEnabled(false);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null) {
            Role newRole = checkIfAgent(user.getCompanyCode()) ?
                    new Role(Role.ROLE_AGENT) : new Role(Role.ROLE_USER);
            user.setRole(newRole);
        }
        User savedUser = userRepository.saveAndFlush(user);
        logger.info("New user saved: {} with role {}", user.getUsername(), user.getRole());
        return savedUser;

    }

    /**
     * Returns current logged in user
     */
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(auth.getName());
    }

    private boolean checkIfAgent(String companyCode) {
        Company company = companyRepository.findByCompanyCode(companyCode);
        if (companyCode != null && company != null && company.getType().equals(TOUR_AGENCY)) {
            return true;
        }
        return false;
    }


    /**
     * Activates user's profile so he can log into the system
     *
     * @param user -  user to be activated
     */
    public void activate(User user) {
        user.setEnabled(true);
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
        int roleId = user.getRole().getRoleId();
        if (roleId != Role.ROLE_ADMIN && roleId != Role.ROLE_AGENT &&
                checkIfAgent(user.getCompanyCode())) {
            user.setRole(new Role(Role.ROLE_AGENT));
        }
        userRepository.saveAndFlush(user);
        logger.info("User " + user.getUsername() + " " + "has changed his company code to '"
                + user.getCompanyCode() + "'");
    }

    public void changePassword(String newPassword, User user) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.saveAndFlush(user);
        emailService.sendPasswordSuccessRecoveryEmail(user);
    }


    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findOne(Integer id) {
        return userRepository.findOne(id);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User save(User user) {
        return userRepository.saveAndFlush(user);
    }


    public void recoverPassword(User user) {
        ValidationLink passwordChangeLink = validationService.createPasswordChangeLink(user);
        emailService.sendPasswordRecoveryEmail(user, passwordChangeLink);

    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public void edit(User user) {
        User existingUser = findOne(user.getUserId());
        user.setPassword(existingUser.getPassword());
        Photo photo = existingUser.getPhoto();
        if (photo != null) {
            user.setPhoto(photo);
        }
        save(user);
    }
}
