package com.quicktour.service;

import com.quicktour.entity.Company;
import com.quicktour.entity.Order;
import com.quicktour.entity.User;
import com.quicktour.entity.ValidationLink;
import org.apache.commons.lang.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Calendar;
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
    @Autowired
    UsersService usersService;
    @Value("${systemEmail}")
    private String SYSTEM_EMAIL;

    @Value("${userRegistrationMailSubject}")
    private String USER_REGISTRATION_SUBJECT;
    @Value("${userRegistrationMailText}")
    private String USER_REGISTRATION_TEXT;


    @Value("${passwordRecoveryMailSubject}")
    private String PASSWORD_RECOVERY_SUBJECT;
    @Value("${passwordRecoveryMailText}")
    private String PASSWORD_RECOVERY_TEXT;

    @Value("${passwordSuccessfulyRecoveredEmailSubject}")
    private String PASSWORD_SUCCESSFULY_RECOVERED_SUBJECT;
    @Value("${passwordSuccessfulyRecoveredEmailText}")
    private String PASSWORD_SUCCESSFULY_RECOVERED_TEXT;

    @Value("${orderStatusChangedMailSubject}")
    private String ORDER_STATUS_CHANGED_SUBJECT;
    @Value("${orderStatusChangedMailText}")
    private String ORDER_STATUS_CHANGED_TEXT;

    @Value("${companyRegistrationMailSubject}")
    private String COMPANY_REGISTRATION_SUBJECT;
    @Value("${companyRegistrationMailText}")
    private String COMPANY_REGISTRATION_TEXT;

    @Value("${commentChangedInOrderMailSubject}")
    private String COMMENT_CHANGED_IN_ORDER_SUBJECT;
    @Value("${commentChangedInOrderMailText}")
    private String COMMENT_CHANGED_IN_ORDER_TEXT;

    @Async
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(SYSTEM_EMAIL);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);

    }


    public void sendRegistrationEmail(User user, ValidationLink validationLink) {
        String toReplace[] = {"Name", "Surname", "Username", "ValidationLink"};
        String[] replacement = {user.getName(), user.getSurname(), user.getUsername(),
                retrieveBaseUrl() + "/activate/" + validationLink.getUrl()};
        String emailText = formatEmailText(USER_REGISTRATION_TEXT, toReplace, replacement);
        sendEmail(user.getEmail(), USER_REGISTRATION_SUBJECT, emailText);
        logger.info("Registrational email sent to: " + user.getUsername());
    }

    public void sendRegistrationEmail(Company company) {
        String[] toReplace = {"CompanyName", "CompanyCode", "CompanyDiscount"};
        String[] replacement = {company.getName(), company.getCompanyCode(), String.valueOf(company.getDiscount())};
        String mailText = formatEmailText(COMPANY_REGISTRATION_TEXT, toReplace, replacement);
        sendEmail(company.getContactEmail(), COMPANY_REGISTRATION_SUBJECT, mailText);
    }

    private String formatEmailText(String template, String[] toReplace, String[] replacement) {
        if (toReplace.length != replacement.length) {
            throw new IllegalArgumentException("Number of elements to " +
                    "replace must match number of elements in replacement");
        }
        Map<String, String> values = new HashMap<String, String>();
        for (int i = 0; i < toReplace.length; i++) {
            values.put(toReplace[i], replacement[i]);
        }
        StrSubstitutor sub = new StrSubstitutor(values, "%(", ")");
        return sub.replace(template);
    }

    private String retrieveBaseUrl() {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                        .getRequest();
        String baseUrl = request.getRequestURL().toString().replace(request.getServletPath(), "");
        String pathInfo = request.getPathInfo();
        if (pathInfo != null && !pathInfo.isEmpty()) {
            baseUrl = baseUrl.replace(pathInfo, "");
        }
        return baseUrl;
    }

    public void sendPasswordRecoveryEmail(User user, ValidationLink passwordChangeLink) {
        String[] toReplace = {"Username", "URL"};
        String[] replacement = {user.getUsername(), retrieveBaseUrl() + "/changePassword/" + passwordChangeLink.getUrl()};
        String mailText = formatEmailText(PASSWORD_RECOVERY_TEXT, toReplace, replacement);
        sendEmail(user.getEmail(), PASSWORD_RECOVERY_SUBJECT, mailText);
    }

    public void sendPasswordSuccessRecoveryEmail(User user) {
        String[] toReplace = {"URL", "Email"};
        String[] replacement = {retrieveBaseUrl() + "/login", SYSTEM_EMAIL};
        String emailText = formatEmailText(PASSWORD_SUCCESSFULY_RECOVERED_TEXT, toReplace, replacement);
        sendEmail(user.getEmail(), PASSWORD_SUCCESSFULY_RECOVERED_SUBJECT, emailText);

    }

    public void sendOrderStatusChanged(Order order, String userName, StringBuffer requestURL) {
        String[] toReplace = {"OrderId", "TourName", "StartDate"};
        String[] replacement = {String.valueOf(order.getOrderId()), order.getTourInfo().getTour().getName()};
        String mailSubject = formatEmailText(ORDER_STATUS_CHANGED_SUBJECT, toReplace, replacement);

        Timestamp currentTimestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
        toReplace = new String[]{"OrderId", "OrderStatus", "Timestamp", "Username", "URL"};
        replacement = new String[]{String.valueOf(order.getOrderId()), order.getStatus().toString(),
                currentTimestamp.toString(), userName, requestURL.toString()};
        String mailText = formatEmailText(ORDER_STATUS_CHANGED_TEXT, toReplace, replacement);

        sendEmail(order.getUser().getEmail(), mailSubject, mailText);

    }

    public void sendOrderCommentChangedEmail(Order order) {
        String email = order.getTourInfo().getTour().getCompany().getContactEmail();
        String[] toReplace = {"OrderId", "TourName", "TourInfoStartDate"};
        String orderId = String.valueOf(order.getOrderId());
        String[] replacement = {orderId, order.getTourInfo().getTour().getName(),
                order.getTourInfo().getStartDate().toString()};
        String mailSubject = formatEmailText(COMMENT_CHANGED_IN_ORDER_SUBJECT, toReplace, replacement);

        Timestamp currentTimestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
        String username = usersService.getCurrentUser().getUsername();
        toReplace = new String[]{"OrderId", "Timestamp", "Username", "TourName", "TourInfoStartDate"};
        replacement = new String[]{orderId, currentTimestamp.toString(), username,
                order.getTourInfo().getTour().getName(), order.getTourInfo().getStartDate().toString()};
        String mailText = formatEmailText(ORDER_STATUS_CHANGED_TEXT, toReplace, replacement);

        sendEmail(email, mailSubject, mailText);

        logger.info("Change comment in order:ID-{} ,order's company: {}, user {}.", orderId, order.getCompany().getName(), username);

    }
}
