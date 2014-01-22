package com.quicktour.entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
@Table(name = "companies", schema = "", catalog = "quicktour")
public class Company {

    private int id;
    private String name;
    private String information;
    private String address;
    private String contactPhone;
    private String contactEmail;
    private String type;
    private Integer discountAmount;
    private String license;
    private String companyCode;
    private Photo photosId;
    private Collection<DiscountPolicy> discountPolicyByCompanyId;
    private Collection<CompanyCreationRequests> requestByCompanyId;
    private Collection<Order> ordersByCompanyId;
    private Collection<Tour> toursByCompanyId;

    @Column(name = "id")
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Pattern(regexp="^[а-яА-ЯІіЇїЄєa-zA-Z\\s]+$",
            message="Company name must consist only of English or Ukrainian letters(or both)")
    @Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters long.")
    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "Information")
    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    @Size(min = 3, message = "Address cannot be so short")
    @Column(name = "Address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Size(min = 5, max = 25, message = "Phone number must be between 5 and 25 characters long")
    @Pattern(regexp = "^\\+?\\d+(-\\d+)*$",
            message = "There should be only numbers, '+' and '-' symbols in your phone number")
    @Column(name = "Contact_Phone", unique = true)
    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    @Pattern(regexp = "[-0-9a-zA-Z.+_]+@[-0-9a-zA-Z.+_]+\\.[a-zA-Z]{2,4}",
            message = "Input correct email")
    @Column(name = "Contact_Email", unique = true)
    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    @Column(name = "Type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @DecimalMin(value = "0", message = "Input correct discount amount, it cannot be less than 0%")
    @DecimalMax(value = "100", message = "Input correct discount amount, it cannot be more than 100%")
    @Column(name = "Discount_amount")
    public Integer getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Integer discountAmount) {
        this.discountAmount = discountAmount;
    }
    @Size(min = 5, max = 25, message = "License must be between 5 and 25 characters long")
    @Column(name = "License", unique = true)
    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }
    @Size(min = 5, max = 25, message = "Company code must be between 5 and 25 characters long")
    @Column(name = "Company_Code", unique = true)
    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }


    @OneToMany(mappedBy = "company")
    @LazyCollection(LazyCollectionOption.FALSE)
    public Collection<Tour> getToursByCompanyId() {
        return toursByCompanyId;
    }

    public void setToursByCompanyId(Collection<Tour> toursByCompanyId) {
        this.toursByCompanyId = toursByCompanyId;
    }

    @OneToMany(mappedBy = "companyId")
    @LazyCollection(LazyCollectionOption.FALSE)
    public Collection<Order> getOrdersByCompanyId() {
        return ordersByCompanyId;
    }

    public void setOrdersByCompanyId(Collection<Order> ordersByCompanyId) {
        this.ordersByCompanyId = ordersByCompanyId;
    }

    @OneToMany(mappedBy = "companyId")
    @LazyCollection(LazyCollectionOption.FALSE)
    public Collection<CompanyCreationRequests> getRequestByCompanyId() {
        return requestByCompanyId;
    }

    public void setRequestByCompanyId(Collection<CompanyCreationRequests> requestByCompanyId) {
        this.requestByCompanyId = requestByCompanyId;
    }

    @OneToMany(mappedBy = "company")
    @LazyCollection(LazyCollectionOption.FALSE)
    public Collection<DiscountPolicy> getDiscountPolicyByCompanyId() {
        return discountPolicyByCompanyId;
    }

    public void setDiscountPolicyByCompanyId(Collection<DiscountPolicy> discountPolicyByCompanyId) {
        this.discountPolicyByCompanyId = discountPolicyByCompanyId;
    }


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Photos_ID")
    public Photo getPhotosId() {
        return photosId;
    }

    public void setPhotosId(Photo photosId) {
        this.photosId = photosId;
    }

}
