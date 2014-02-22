package com.quicktour.service;

import com.quicktour.entity.User;
import com.quicktour.entity.ValidationLink;
import com.quicktour.repository.ValidationLinksRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Contains all functional logic connected with validation links
 *
 * @author Andrew Zarichnyi
 * @version 1.0 12/27/2013
 */
@Service
@Transactional
public class ValidationService {
    private static final Logger logger = LoggerFactory.getLogger(ValidationService.class);
    @Autowired
    StandardPasswordEncoder passwordEncoder;
    @Autowired
    private UsersService userService;

    @Autowired
    private ValidationLinksRepository validationLinksRepository;
    private MessageDigest md5;


    @PostConstruct
    private void init() {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            logger.error("Cannot find md5 algorithm.{}", e);
        }

    }


    /**
     * Every 5 minutes checks database for links that are more than 2 hours old and, if there are
     * ones, cleans database from user who didn't activate his profile and his avatar.
     */
    @Scheduled(cron = "0 0 0 0/2 * *")
    public void deleteExpiredValidationLinks() {
        logger.debug("Checking links");
        validationLinksRepository.deleteExpiredLinks();

    }


    public ValidationLink findByUrl(String url) {
        return validationLinksRepository.findByUrl(url);

    }

    ValidationLink save(ValidationLink link) {
        return validationLinksRepository.saveAndFlush(link);
    }


    /**
     * Creates a validation link for newly registered user
     *
     * @param user - object of User class which represents newly registered user
     */
    public ValidationLink createValidationLink(User user) {
        ValidationLink link = new ValidationLink();
        String hash = generateMD5(user.getUserId(), user.getEmail());
        link.setUser(user);
        link.setUrl(hash);
        return save(link);
    }

    public ValidationLink createPasswordChangeLink(User user) {
        ValidationLink userLink = validationLinksRepository.findByUser(user);
        if (userLink != null) {
            validationLinksRepository.delete(userLink);
        }
        ValidationLink link = new ValidationLink();
        link.setUser(user);
        link.setUrl(generateMD5(UUID.randomUUID().toString()));
        return save(link);
    }

    public User checkPasswordChangeLink(String link) {
        User user = null;
        ValidationLink validationLink = validationLinksRepository.findByUrl(link);
        if (validationLink != null) {
            user = validationLink.getUser();
        }
        return user;
    }


    private String generateMD5(int userId, String email) {
        String stringToEncode = userId + "" + email;
        try {
            md5.update(stringToEncode.getBytes("utf-8"), 0, stringToEncode.length());
        } catch (UnsupportedEncodingException e) {
            logger.error("Unsuported encoding while generating md5 . {}", e);
        }
        String result = new BigInteger(1, md5.digest()).toString(16);
        if (result.length() < 32) {
            result = "0" + result;
        }
        return result;
    }

    private String generateMD5(String stringToEncode) {
        try {
            md5.update(stringToEncode.getBytes("utf-8"), 0, stringToEncode.length());
        } catch (UnsupportedEncodingException e) {
            logger.error("Unsuported encoding while generating md5 . {}", e);
        }
        String result = new BigInteger(1, md5.digest()).toString(16);
        if (result.length() < 32) {
            result = "0" + result;
        }
        return result;
    }


    public boolean resolveLink(String validationLink) {
        ValidationLink link = validationLinksRepository.findByUrl(validationLink);
        if (link != null) {
            User user = link.getUser();
            userService.activate(user);
            validationLinksRepository.delete(link);
            return true;
        }
        return false;
    }


    public void delete(ValidationLink validationLink) {
        validationLinksRepository.delete(validationLink);
    }

}
