package com.quicktour.service;

import com.quicktour.entity.User;
import com.quicktour.entity.ValidationLink;
import com.quicktour.repository.UserRepository;
import com.quicktour.repository.ValidationLinksRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Contains all functional logic connected with validation links
 *
 * @author Andrew Zarichnyi
 * @version 1.0 12/27/2013
 */
@Service
@Transactional
public class ValidationService {
    @Autowired
    StandardPasswordEncoder passwordEncoder;
    Logger logger = LoggerFactory.getLogger(ValidationService.class);
    @Autowired
    private UsersService userService;

    @Autowired
    private ValidationLinksRepository validationLinksRepository;
    MessageDigest md5;
    @Autowired
    private UserRepository userRepository;
    private
    @Autowired
    HttpServletRequest request;
    private static String baseUrl;

    @PostConstruct
    private void init() {
        try {
            md5 = md5.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            logger.error("Cannot find md5 algorithm.{}", e);
        }

    }

    /**
     * Every 5 minutes checks database for links that are more than 2 hours old and, if there are
     * ones, cleans database from user who didn't activate his profile and his avatar.
     */
//    @Scheduled(cron="0 0 0 0/1 * ?")
//    public void checkExpiredValidationLinks() {
//        Timestamp time = new Timestamp(System.currentTimeMillis());
//        List<ValidationLink> links = validationLinksRepository.findAll();
//        for (ValidationLink link : links) {
//            if (time.getTime() - link.getTimeRegistered().getTime() > 7200000) {
//                photoRepository.delete(photoRepository.findOne
//                        (userRepository.findOne(link.getUserId()).getPhotosId().getId()));
//                userRepository.delete(link.getUserId());
//                validationLinksRepository.delete(link.getId());
//            }
//        }
//    }
    public ValidationLink save(ValidationLink link) {
        return validationLinksRepository.saveAndFlush(link);
    }

    public ValidationLink findByUser(User user) {
        return validationLinksRepository.findByUserId(user.getUserId());
    }

    /**
     * Creates a validation link for newly registered user
     *
     * @param user - object of User class which represents newly registered user
     */
    public ValidationLink createValidationLink(User user) {
        ValidationLink link = new ValidationLink();
        String hash = generateMD5(user.getUserId(), user.getEmail());
        link.setUserId(user.getUserId());
        link.setUrl(hash);
        return save(link);
    }

    private String generateMD5(int userId, String email) {
        String stringToEncode = userId + "" + email;
        md5.update(stringToEncode.getBytes(), 0, stringToEncode.length());
        String result = new BigInteger(1, md5.digest()).toString(16);
        if (result.length() < 32) {
            result = "0" + result;
        }
        return result;
    }


    public boolean resolveLink(String validationLink) {
        ValidationLink link = validationLinksRepository.findByUrl(validationLink);
        if (link != null) {
            User user = userService.findOne(link.getUserId());
            userService.activate(user);
            validationLinksRepository.delete(link);
            return true;
        }
        return false;
    }

}
