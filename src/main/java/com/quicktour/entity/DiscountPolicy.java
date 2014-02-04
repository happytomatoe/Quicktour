package com.quicktour.entity;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.sql.Date;

/**
 * @author Roman Lukash
 */
@Entity
@Table(name = "discount_policy")
public class DiscountPolicy {

    public static final String DAYOFWEEK = "Day of week";
    public static final String USERS_SEX = "users.sex";

    private int discountPolicyId;
    private String name;
    private String description;
    private String condition;
    private String formula;
    private Date startDate;
    private Date endDate;
    private boolean active = false;
    private Company company;


    @Id
    @GeneratedValue
    @Column(name = "discount_policy_id")
    public int getDiscountPolicyId() {
        return discountPolicyId;
    }

    public void setDiscountPolicyId(int id) {
        this.discountPolicyId = id;
    }

    @NotEmpty(message = "Name is empty")
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "cond")
    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @NotEmpty(message = "Discount is empty")
    @Column(name = "formula")
    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    @Column(name = "start_date")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Column(name = "end_date")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @ManyToOne()
    @JsonIgnore
    @LazyCollection(LazyCollectionOption.TRUE)
    @JoinColumn(name = "companies_id", insertable = false, updatable = false)
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company companyId) {
        this.company = companyId;
    }


    @Transient
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "DiscountPolicy{" +
                "id=" + discountPolicyId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", condition='" + condition + '\'' +
                ", formula='" + formula + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", companyId=" + company +
                '}';
    }
}
