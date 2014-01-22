package com.quicktour.service;

import com.quicktour.entity.Role;
import com.quicktour.entity.User;
import com.quicktour.repository.CompanyRepository;
import com.quicktour.repository.RoleRepository;
import com.quicktour.repository.UserRepository;
import org.apache.commons.lang.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.sql.Timestamp;
import java.util.*;
import java.util.logging.Logger;

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
    public static final String USER_ROLE = "user";
    public static final String AGENT_ROLE = "agent";
    private static final String ADMIN_ROLE = "admin";
    public static final String TOUR_AGENCY = "Tour Agency";
    public static final Integer DEFAULT_AVATAR_ID = 4;
    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(UsersService.class);

    @Autowired
    private PhotoService photoService;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CompanyRepository companyRepository;
    public static final String VALID_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    @Value("${systemEmail}")
    private String SYSTEM_EMAIL;

    @Value("${registrationMailSubject}")
    private String REGISTRATION_MAIL_SUBJECT;

    @Value("${registrationMailText}")
    private String REGISTRATION_MAIL_TEXT;

    @Value("${passwordRecoveryMailSubject}")
    private String RECOVERY_MAIL_SUBJECT;

    @Value("${passwordRecoveryMailText}")
    private String RECOVERY_MAIL_TEXT;

    /**
     * Sends registration message to the email that was input during user registration,
     * sets user inactive for the start, encodes user password to md5, sets user role
     * due to company code that was input during registration and saves user to the database
     *
     * @param user - user that has to be registrated
     * @return - true if all is OK. and false if saving fails
     */
    public boolean registrateNewUser(User user) {
        if (userRepository.findByLogin(user.getLogin()) == null
                && userRepository.findByEmail(user.getEmail()) == null) {
            try{
            sendRegistrationEmail(user);
            }
            catch (MailException me){
                logger.error(me.getMessage());
            };
            user.setActive(false);
            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
            user.setRoleId(chooseRole(user));
            userRepository.saveAndFlush(user);
            logger.info("New user saved: " + user.getLogin() + " " + user.getRoleId().getRole());
            return true;
        }
        return false;
    }

    /**
     * @return object of User class which represents currently authenticated user
     *         and null if user is not authenticated
     */
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByLogin(auth.getName());
    }

    /**
     * Updates user's profile in database with the changes made in edit profile form
     * Note: login cannot be changed
     *
     * @param user - object of User class which contains all changes made in edit
     *             profile form;
     * @return true if update was successful, otherwise returns false.
     */
    public boolean updateUser(User user) {
        User oldUser = userRepository.findByLogin(user.getLogin());
        mapUserFromUIToDB(user, oldUser);
        userRepository.saveAndFlush(oldUser);
        logger.info("User updated: " + user.getLogin());
        return true;
    }

    /**
     * Updates user role by his company code. Note: Admin cannot change his role in this way
     *
     * @param user - object of User class which company code was changed
     * @return object of Role class which represents role that this user object has to have
     */
    private Role updateRoleByCode(User user) {
        if (user.getRoleId().getRole() != ADMIN_ROLE)
            if (user.getCompanyCode() == null ||
                    companyRepository.findByCompanyCode(user.getCompanyCode()) == null) {
                return roleService.findByRole(USER_ROLE);
            } else if (companyRepository.findByCompanyCode(user.getCompanyCode()).getType().equals(TOUR_AGENCY)) {
                return roleService.findByRole(AGENT_ROLE);
            } else {
                return roleService.findByRole(USER_ROLE);
            } else {
            return roleService.findByRole(ADMIN_ROLE);
        }
    }

    /**
     * Formats and sends registrational email with user credentials to email box
     * which was input during registration.
     *
     * @param user - object of User class which represents newly registered user
     *             and contains all necessary information
     */
    public void sendRegistrationEmail(User user) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(SYSTEM_EMAIL);
        message.setTo(user.getEmail());
        message.setSubject(REGISTRATION_MAIL_SUBJECT);
        message.setText(formatEmailText(user, REGISTRATION_MAIL_TEXT));
        mailSender.send(message);
        logger.info("Registrational email sent to: " + user.getLogin());
    }

    /**
     * Formats email text with user's information that is needed in the email
     *
     * @param user      - object of User class which contains all required information
     * @param emailText - template of the email
     * @return String - formatted email text
     */
    private String formatEmailText(User user, String emailText) {
        Map<String, String> values = new HashMap<String, String>();
        values.put("Name", user.getName());
        values.put("Surname", user.getSurname());
        values.put("Login", user.getLogin());
        values.put("Password", user.getPassword());
        StrSubstitutor sub = new StrSubstitutor(values, "%(", ")");
        return sub.replace(emailText);
    }

    /**
     * Maps the changes that user has done in edit profile form to his entity in database.
     * Admin cannot change his role by company code. When admin changes user's role, this user
     * has role without role_id, so we need to set it by role_name.
     *
     * @param user
     * @param oldUser
     */
    private void mapUserFromUIToDB(User user, User oldUser) {
        if (user.getPhotosId() != null)
            oldUser.setPhotosId(user.getPhotosId());
        oldUser.setEmail(user.getEmail());
        oldUser.setName(user.getName());
        oldUser.setSurname(user.getSurname());
        oldUser.setAge(user.getAge());
        oldUser.setPhone(user.getPhone());
        oldUser.setCompanyCode(user.getCompanyCode());
        oldUser.setRoleId(updateRoleByCode(oldUser));
        oldUser.setSex(user.getSex());
        if (user.getRoleId() != null)
            oldUser.getRoleId().setRole(user.getRoleId().getRole());

    }

    /**
     * Activates user's profile so he can log into the system
     *
     * @param user -  user to be activated
     * @return true if user is activated and false in case when something goes wrong
     */
    public boolean setUserActive(User user) {
        if (user != null) {
            user.setActive(true);
            userRepository.saveAndFlush(user);
            return true;
        } else return false;
    }

    /**
     * Deactivates user's profile so he cannot log into the system
     *
     * @param login - login of the user to be deactivated
     * @return true if user is deactivated and false in case when something goes wrong
     */
    public boolean setUserInactive(String login) {
        User user = userRepository.findByLogin(login);
        if (user != null) {
            user.setActive(false);
            userRepository.saveAndFlush(user);
            return true;
        } else return false;
    }

    /**
     * This function is used when new user is registrated in the system
     * When admin registrates new user with custom set role, object of User class comes from ui with cropped role.
     * Usually, user's role is represented by roles id and roles name, but in this case user object
     * has only roles name, so we need to set full role to this user. If role wasn't set by admin,
     * we just update user's role by his company code.
     *
     * @param user - object of User class which comes from registration form
     * @return - object of Role class which represents the role that this user has to have
     */
    private Role chooseRole(User user) {
        if (user.getRoleId() == null) {
            user.setRoleId(roleService.findByRole(USER_ROLE));
            return updateRoleByCode(user);
        } else return roleService.findByRole(user.getRoleId().getRole());
    }

    /**
     * This is password recovery function which seeks for user in database by email
     * which was input in password recovery form. If any was found, function updates
     * password and saves it to database. Also it copies all information that is needed
     * for password recovery email from found user to object of User class which has come
     * from password recovery form and sends it on email that was input.
     *
     * @param user - object of User class which comes from password recovery form and
     *             contains only email of user who wants to recover his password.
     */
    public void setNewPassword(User user) {
        String newPassword = generatePassword();
        User oldUser = userRepository.findByEmail(user.getEmail());
        if (oldUser != null) {
            user.setPassword(newPassword);
            user.setLogin(oldUser.getLogin());
            user.setName(oldUser.getName());
            user.setSurname(oldUser.getSurname());
            oldUser.setPassword(DigestUtils.md5DigestAsHex(newPassword.getBytes()));
            userRepository.saveAndFlush(oldUser);
            sendNewPasswordEmail(user);
            logger.info("Password for user " + user.getLogin() + " was changed to '" + newPassword + "'");
        }
    }

    /**
     * Sends email with new password to user who wants to recover his lost password
     *
     * @param user - object of User class which contains information about
     *             user who wants to recover his lost password
     */
    private void sendNewPasswordEmail(User user) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(RECOVERY_MAIL_SUBJECT);
        message.setTo(user.getEmail());
        message.setSubject("New account details");
        message.setText(formatEmailText(user, RECOVERY_MAIL_TEXT));
        mailSender.send(message);

    }

    /**
     * Generates random password for password recovery functionality
     *
     * @return String - generated password
     */
    private String generatePassword() {
        char[] password = new char[10];
        Random rand = new Random(System.nanoTime());
        for (int i = 0; i < 10; i++) {
            password[i] = VALID_CHARS.charAt(rand.nextInt(VALID_CHARS.length()));
        }
        return new String(password);
    }

    /**
     * Updates user's company code and role in database due to new company code.
     *
     * @param newCompanyCode - String which contains new company code of the user.
     */
    public void updateCompanyCode(String newCompanyCode) {
        User user = getCurrentUser();
        user.setCompanyCode(newCompanyCode);
        user.setRoleId(updateRoleByCode(user));
        userRepository.saveAndFlush(user);
        logger.info("User " + user.getLogin() + " " + "has changed his company code to '"
                + user.getCompanyCode() + "'");
    }

    /**
     * Generates login and password for anonymous who has ordered the tour in the system.
     * Login and password are based on part of the user's mail before '@'.
     * After this, sets user's role to user and avatar to default , encodes his password to md5
     * and saves him to database.
     *
     * @param user - object of User class which comes from order form
     * @return - object of User class which was recently saved to database
     */
    public User saveAnonymousCustomer(User user) {
        String login = user.getEmail().split("@")[0];
        String password = login;
        user.setRoleId(roleService.findByRole(USER_ROLE));
        user.setLogin(login);
        user.setPassword(password);
        user.setPhotosId(photoService.findOne(DEFAULT_AVATAR_ID));
        sendRegistrationEmail(user);
        user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        userRepository.saveAndFlush(user);
        logger.info("Anonymous user with email " + user.getEmail() + " has ordered a tour");
        return userRepository.findByEmail(user.getEmail());
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findOne(Integer id) {
        return userRepository.findOne(id);
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public User findById(int id) {
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

}
