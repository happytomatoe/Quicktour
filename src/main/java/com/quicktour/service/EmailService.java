package com.quicktour.service;

import com.quicktour.entity.User;
import com.quicktour.entity.ValidationLink;
import org.apache.commons.lang.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Roman Lukash
 */
@Service
public class EmailService {
    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(EmailService.class);
    @Autowired
    private MailSender mailSender;

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
     * Sends user registration email
     */
    @Async
    public void sendRegistrationEmail(User user, HttpServletRequest request, ValidationLink validationLink) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(SYSTEM_EMAIL);
        message.setTo(user.getEmail());
        message.setSubject(REGISTRATION_MAIL_SUBJECT);
        message.setText(formatEmailText(user, request, validationLink, REGISTRATION_MAIL_TEXT));
        mailSender.send(message);
        logger.info("Registrational email sent to: " + user.getLogin());
    }

    /**
     * Formats email text with user's information that is needed in the email
     *
     * @param user           - object of User class which contains all required information
     * @param validationLink
     * @param emailText      - template of the email  @return String - formatted email text
     */
    private String formatEmailText(User user, HttpServletRequest request, ValidationLink validationLink, String emailText) {
        String baseUrl = retrieveBaseUrl(request);
        Map<String, String> values = new HashMap<String, String>();
        values.put("Name", user.getName());
        values.put("Surname", user.getSurname());
        values.put("Login", user.getLogin());
        values.put("ValidationLink", baseUrl + "/login/" + validationLink.getUrl());
        StrSubstitutor sub = new StrSubstitutor(values, "%(", ")");
        return sub.replace(emailText);
    }

    private String retrieveBaseUrl(HttpServletRequest request) {
        String baseUrl = request.getRequestURL().toString().replace(request.getServletPath(), "");
        String pathInfo = request.getPathInfo();
        if (pathInfo != null && !pathInfo.isEmpty()) {
            baseUrl = baseUrl.replace(pathInfo, "");
        }
        return baseUrl;
    }
}
