package com.quicktour.dto;

import com.quicktour.entity.DiscountPolicy;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author Roman Lukash
 */
public class DiscountPoliciesResult {
    BigDecimal discount;
    List<DiscountPolicy> discountPolicies;

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }


    public DiscountPoliciesResult(BigDecimal totalDiscount, List<DiscountPolicy> discountPolicies) {
        this.discount = totalDiscount;
        this.discountPolicies = discountPolicies;
    }

    public List<DiscountPolicy> getDiscountPolicies() {
        return discountPolicies;
    }



}
