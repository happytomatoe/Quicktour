package com.quicktour.service;

import com.quicktour.entity.Company;
import com.quicktour.entity.User;
import com.quicktour.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Andrew Zarichnyi
 * @version 1.0 12/27/2013
 */
@Service
@Transactional
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private PhotoService photoService;
    @Autowired
    private EmailService emailService;

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company findOne(Integer id) {
        return companyRepository.findOne(id);
    }

    public void delete(Integer id) {
        companyRepository.delete(id);
    }

    public Company saveAndFlush(Company company, MultipartFile image) {
        if (!image.isEmpty()) {
            photoService.saveImageAndSet(company, image);
        }
        emailService.sendRegistrationEmail(company);
        return companyRepository.saveAndFlush(company);
    }

    public Company saveAndFlush(Company company) {
        return companyRepository.saveAndFlush(company);
    }


    public Company findByCompanyCode(String code) {
        return companyRepository.findByCompanyCode(code);
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
