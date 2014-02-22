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
@Table(name = "companies")
public class Company extends PhotoHolder {

    private int companyId;
    private String name;
    private String information;
    private String address;
    private String contactPhone;
    private String contactEmail;
    private String type;
    private Integer discount;
    private String license;
    private String companyCode;
    private Photo photo;
    private Collection<Order> orders;
    private Collection<Tour> tours;

    @Column(name = "company_id")
    @Id
    @GeneratedValue
    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int id) {
        this.companyId = id;
    }

    @Pattern(regexp = "^[а-яА-ЯІіЇїЄєa-zA-Z\\s]+$",
            message = "Company name must consist only of English or Ukrainian letters(or both)")
    @Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters long.")
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "information")
    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    @Size(min = 3, message = "Address cannot be so short")
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Size(min = 5, max = 25, message = "Phone number must be between 5 and 25 characters long")
    @Pattern(regexp = "^\\+?\\d+(-\\d+)*$",
            message = "There should be only numbers, '+' and '-' symbols in your phone number")
    @Column(name = "contact_phone", unique = true)
    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    @Pattern(regexp = "[-0-9a-zA-Z.+_]+@[-0-9a-zA-Z.+_]+\\.[a-zA-Z]{2,4}",
            message = "Input correct email")
    @Column(name = "contact_email", unique = true)
    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @DecimalMin(value = "0", message = "Input correct discount amount, it cannot be less than 0%")
    @DecimalMax(value = "100", message = "Input correct discount amount, it cannot be more than 100%")
    @Column(name = "discount")
    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discountAmount) {
        this.discount = discountAmount;
    }

    @Size(min = 5, max = 25, message = "License must be between 5 and 25 characters long")
    @Column(name = "license", unique = true)
    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    @Size(min = 5, max = 25, message = "Company code must be between 5 and 25 characters long")
    @Column(name = "company_code", unique = true)
    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }


    @OneToMany(mappedBy = "company")
    @LazyCollection(LazyCollectionOption.FALSE)
    public Collection<Tour> getTours() {
        return tours;
    }

    public void setTours(Collection<Tour> toursByCompanyId) {
        this.tours = toursByCompanyId;
    }

    @OneToMany(mappedBy = "company")
    @LazyCollection(LazyCollectionOption.FALSE)
    public Collection<Order> getOrders() {
        return orders;
    }

    public void setOrders(Collection<Order> ordersByCompanyId) {
        this.orders = ordersByCompanyId;
    }


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "photos_id")
    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photosId) {
        this.photo = photosId;
    }

}
