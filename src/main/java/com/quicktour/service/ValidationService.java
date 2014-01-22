package com.quicktour.service;

import com.quicktour.entity.User;
import com.quicktour.entity.ValidationLink;
import com.quicktour.repository.PhotoRepository;
import com.quicktour.repository.UserRepository;
import com.quicktour.repository.ValidationLinksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains all functional logic connected with validation links
 * @author Andrew Zarichnyi
 * @version 1.0 12/27/2013
 */
@Service
public class ValidationService {

    @Autowired
    private UsersService userService;

    @Autowired
    private ValidationLinksRepository validationLinksRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PhotoRepository photoRepository;

    /**
     * Every 5 minutes checks database for links that are more than 2 hours old and, if there are
     * ones, cleans database from user who didn't activate his profile and his avatar.
     */
    @Scheduled(fixedRate = 300000)
    public void  checkExpiredValidationLinks(){
        Timestamp time = new Timestamp(System.currentTimeMillis());
        List<ValidationLink> links = validationLinksRepository.findAll();
        for(ValidationLink link :links){
            if(time.getTime() - link.getTimeRegistered().getTime() > 7200000){
                photoRepository.delete(photoRepository.findOne
                        (userRepository.findOne(link.getUserId()).getPhotosId().getId()));
                userRepository.delete(link.getUserId());
                validationLinksRepository.delete(link.getId());
            }
        }
    }

    public boolean addValidationLink(ValidationLink link){
        validationLinksRepository.saveAndFlush(link);
        return true;
    }

    /**
     * Deletes validation link from database after user activates his profile
     * @param userLogin - login of the user who activates his profile
     * @return true if operation was successful
     */
    public boolean clearLink(String userLogin){
        validationLinksRepository.delete(validationLinksRepository.findByUserId(
                userRepository.findByLogin(userLogin).getId()).getId());
        return true;
    }

    /**
     * Creates a validation link for newly registered user
     * @param user - object of User class which represents newly registered user
     */
    public void createValidationLink(User user) {
        ValidationLink link = new ValidationLink();
        link.setUserId(userService.findByLogin(user.getLogin()).getId());
        link.setValidationLink("localhost:/login/" + user.getLogin());
        addValidationLink(link);
    }

    public void resolveLink(String validationLink) {
        ValidationLink link = validationLinksRepository.findByValidationLink("localhost:/login/" + validationLink);
        if(link != null){
            User user = userService.findById(link.getUserId());
            userService.setUserActive(user);
            clearLink(validationLink);
        }
    }
}
