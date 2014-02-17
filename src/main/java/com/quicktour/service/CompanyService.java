package com.quicktour.service;

import com.quicktour.entity.Company;
import com.quicktour.entity.User;
import com.quicktour.repository.CompanyRepository;
import com.quicktour.repository.UserRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
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
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(CompanyService.class);
    @Autowired
    UserRepository userRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    EmailService emailService;

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


    /**
     * Sends registration message to the email that was input during company registration
     * and saves company to the database
     *
     * @param company - company that has to be registered
     * @return - true if all is OK. and false if saving fails
     * TODO:test and check
     */
    public boolean addNewCompany(Company company) {
        try {
            companyRepository.saveAndFlush(company);
            emailService.sendRegistrationEmail(company);
        } catch (MailException me) {
            logger.warn(me.getMessage());
            return true;
        }
        return false;
    }


    public BigDecimal getCompanyDiscount(User user) {
        BigDecimal result = BigDecimal.ZERO;
        if (user == null) {
            return result;
        }
        Company userCompany = findByCompanyCode(user.getCompanyCode());
        if (userCompany != null) {
            Integer companyDiscount = userCompany.getDiscount();
            if (companyDiscount != null && companyDiscount > 0) {
                result = new BigDecimal(companyDiscount.toString());
            }
        }
        return result;
    }
}
