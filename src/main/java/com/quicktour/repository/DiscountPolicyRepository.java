package com.quicktour.repository;

import com.quicktour.entity.Company;
import com.quicktour.entity.DiscountPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Roman Lukash
 */
public interface DiscountPolicyRepository extends JpaRepository<DiscountPolicy, Integer> {

    List<DiscountPolicy> findByCompany(Company company);

    List<DiscountPolicy> findByDiscountPolicyIdIn(List<Integer> ids);


}
