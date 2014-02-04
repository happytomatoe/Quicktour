package com.quicktour.service;

import com.quicktour.entity.Company;
import com.quicktour.entity.User;
import com.quicktour.repository.CompanyRepository;
import com.quicktour.repository.UserRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Andrew Zarichnyi
 * @version 1.0 12/27/2013
 */
@Service
@Transactional
public class CompanyService {
    final Logger logger = org.slf4j.LoggerFactory.getLogger(CompanyService.class);
    @Autowired
    private MailSender mailSender;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CompanyRepository companyRepository;

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company findOne(Integer id) {
        return companyRepository.findOne(id);
    }

    public void delete(Company company) {
        companyRepository.delete(company);
    }

    public Company saveAndFlush(Company company) {
        return companyRepository.saveAndFlush(company);
    }

    public Company findByCompanyCode(String code) {
        return companyRepository.findByCompanyCode(code);
    }

    public Company findByName(String name) {
        return companyRepository.findByName(name);
    }

    /**
     * Returns Company object that represents company that is connected with user by company code
     *
     * @param userId - id of the user who has company code
     * @return company if request was successful, else returns null
     */
    public Company getCompanyByUserId(int userId) {
        User user = userRepository.findOne(userId);
        if (user.getCompanyCode() != null && !user.getCompanyCode().isEmpty()) {
            return companyRepository.findByCompanyCode(user.getCompanyCode());
        } else {
            return null;
        }

    }

    /**
     * Sends registration message to the email that was input during company registration
     * and saves company to the database
     *
     * @param company - company that has to be registrated
     * @return - true if all is OK. and false if saving fails
     */
    public boolean addNewCompany(Company company) {
        try {
            companyRepository.saveAndFlush(company);
            sendRegistrationalEmail(company);
        } catch (MailException me) {
            logger.warn(me.getMessage());
            return true;
        }
        return false;
    }

    /**
     * Creates and sends registrational email to the new company
     *
     * @param company - contains all information about company which will be used in email creating
     */
    private void sendRegistrationalEmail(Company company) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("TourServe123@gmail.com");
        message.setTo(company.getContactEmail());
        message.setSubject("Registration");
        message.setText("Congratulations,"
                + company.getName() + "!\n"
                + "Your company have been registrated in TourServe!\n"
                + "Your company code " + company.getCompanyCode() + "\n"
                + "Your discount amount: " + company.getDiscount() + "\n"
                + "Spread company code among your employee and they will obtain discount by it in our system :3\n");
        mailSender.send(message);
        logger.debug(message.getTo()[0]);
        logger.debug(message.getText());
    }

    public BigDecimal getCompanyDiscount(User user) {
        BigDecimal result = BigDecimal.ZERO;
        if (user == null) {
            return result;
        }
        Company userCompany = getCompanyByUserId(user.getUserId());
        if (userCompany != null) {
            Integer companyDiscount = userCompany.getDiscount();
            if (companyDiscount != null && companyDiscount > 0) {
                result = new BigDecimal(companyDiscount.toString());
            }
        }
        return result;
    }
}
