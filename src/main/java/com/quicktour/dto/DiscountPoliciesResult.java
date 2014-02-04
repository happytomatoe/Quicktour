package com.quicktour.dto;

import com.quicktour.entity.DiscountPolicy;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Roman Lukash
 */
public class DiscountPoliciesResult {
    BigDecimal discount;

    List<DiscountPolicy> discountPolicies;

    public void setDiscountPolicies(List<DiscountPolicy> discountPolicies) {
        this.discountPolicies = discountPolicies;
    }

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
