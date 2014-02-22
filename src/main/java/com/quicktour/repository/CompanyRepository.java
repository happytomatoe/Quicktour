package com.quicktour.repository;

import com.quicktour.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    Company findByCompanyCode(String code);
}
