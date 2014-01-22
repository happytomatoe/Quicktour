package com.quicktour.entity;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "users", schema = "", catalog = "quicktour")
public class User {

    private int id;
    @JsonIgnore private Role roleId;
    private String login;
    private String password;
    private String email;
    private String phone;
    private Timestamp createTime;
    private String name;
    private String surname;
    private Integer age;
    private String sex;
    private String companyCode;
    private Photo photosId;
    private boolean active;


    @JsonIgnore private Collection<CompanyCreationRequests> requestByUserId;
    @JsonIgnore private Collection<Order> ordersByUserId;
    @JsonIgnore private Collection<Comment> commentsByUserId;

    @Column(name = "ID")
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Pattern(regexp="^[a-zA-Z0-9]+$",
            message="Login must be alpha numeric with no spaces")
    @Size(min = 3, max = 30, message = "Login must be between 3 and 30 characters long.")
    @Column(name = "login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Size(min = 4, message = "Password must be at least 4 characters long.")
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Pattern(regexp = "[-0-9a-zA-Z.+_]+@[-0-9a-zA-Z.+_]+\\.[a-zA-Z]{2,4}",
            message = "Input correct email")
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Size(min = 5, max = 25, message = "Phone number must be between 5 and 25 characters long")
    @Pattern(regexp = "^\\+?\\d+(-\\d+)*$",
            message = "There should be only numbers, '+' and '-' symbols in your phone number")
    @Column(name = "Phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Pattern(regexp="^[а-яА-ЯІіЇїЄєa-zA-Z]+$",
            message="Name must contain only letters with no spaces")
    @Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters long.")
    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Pattern(regexp="^[а-яА-ЯІіЇїЄєa-zA-Z]+$",
            message="Surname must contain only letters with no spaces")
    @Size(min = 3, max = 30, message = "Surname must be between 3 and 30 characters long.")
    @Column(name = "Surname")
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


    @Column(name = "Age")
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Column(name = "Sex")
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Column(name = "Company_Code")
    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    @OneToMany( mappedBy = "userId")
    @LazyCollection(LazyCollectionOption.FALSE)
    public Collection<Order> getOrdersByUserId() {
        return ordersByUserId;
    }

    public void setOrdersByUserId(Collection<Order> ordersByUserId) {
        this.ordersByUserId = ordersByUserId;
    }

    @OneToMany(mappedBy = "userId")
    public Collection<CompanyCreationRequests> getRequestByUserId() {
        return requestByUserId;
    }

    public void setRequestByUserId(Collection<CompanyCreationRequests> requestByUserId) {
        this.requestByUserId = requestByUserId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Photos_ID")
    public Photo getPhotosId() {
        return photosId;
    }

    public void setPhotosId(Photo photosId) {
        this.photosId = photosId;
    }

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "Roles_RoleId")
    public Role getRoleId() {
        return roleId;
    }

    public void setRoleId(Role roleId) {
        this.roleId = roleId;
    }

    @OneToMany(mappedBy = "user")
    @LazyCollection(LazyCollectionOption.FALSE)
    public Collection<Comment> getCommentsByUserId() {
        return commentsByUserId;
    }

    public void setCommentsByUserId(Collection<Comment> commentsByUserId) {
        this.commentsByUserId = commentsByUserId;
    }

    @Column(name = "active")
    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public User(){

    }

    public User(String name, String surname, String email, String phone){
        setName(name);
        setSurname(surname);
        setEmail(email);
        setPhone(phone);
    }
}
