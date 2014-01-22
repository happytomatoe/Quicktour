package com.quicktour.repository;

import com.quicktour.entity.Company;
import com.quicktour.entity.DiscountPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Roman Lukash
 */
public interface DiscountPolicyRepository extends JpaRepository<DiscountPolicy, Integer> {

    List<DiscountPolicy> findByCompany(Company company);
    @Query("SELECT p FROM DiscountPolicy p WHERE p.id IN(?1)")
    List<DiscountPolicy> findByIds(Integer... ids);


}
