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
@Table(name = "users")
public class User extends PhotoHolder {

    private int userId;
    private String username;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private String email;
    @JsonIgnore
    private String phone;
    @JsonIgnore
    private Timestamp createTime;
    @JsonIgnore
    private String name;
    @JsonIgnore
    private String surname;
    @JsonIgnore
    private Integer age;
    @JsonIgnore
    private String gender;
    @JsonIgnore
    private String companyCode;
    private Photo photo;
    @JsonIgnore
    private boolean enabled;
    @JsonIgnore
    private Role role;
    @JsonIgnore
    private Collection<Order> orders;
    @JsonIgnore
    private Collection<Comment> comments;

    @Column(name = "user_id")
    @Id
    @GeneratedValue
    public int getUserId() {
        return userId;
    }

    public void setUserId(int id) {
        this.userId = id;
    }

    @Pattern(regexp = "^[a-zA-Z0-9]+$",
            message = "Login must be alpha numeric with no spaces")
    @Size(min = 3, max = 30, message = "Login must be between 3 and 30 characters long.")
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Size(min = 8, message = "Password must be at least 8 characters long.")
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
    @Column(name = "phone")
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

    @Pattern(regexp = "^[а-яА-ЯІіЇїЄєa-zA-Z]+$",
            message = "Name must contain only letters with no spaces")
    @Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters long.")
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Pattern(regexp = "^[а-яА-ЯІіЇїЄєa-zA-Z]+$",
            message = "Surname must contain only letters with no spaces")
    @Size(min = 3, max = 30, message = "Surname must be between 3 and 30 characters long.")
    @Column(name = "surname")
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


    @Column(name = "age")
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Column(name = "gender")
    public String getGender() {
        return gender;
    }

    public void setGender(String sex) {
        this.gender = sex;
    }

    @Column(name = "company_code")
    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    @OneToMany(mappedBy = "user")
    @LazyCollection(LazyCollectionOption.TRUE)
    public Collection<Order> getOrders() {
        return orders;
    }

    public void setOrders(Collection<Order> ordersByUserId) {
        this.orders = ordersByUserId;
    }


    @ManyToOne
    @LazyCollection(LazyCollectionOption.TRUE)
    @JoinColumn(name = "photos_id")
    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photosId) {
        this.photo = photosId;
    }


    @OneToMany(mappedBy = "user")
    @LazyCollection(LazyCollectionOption.TRUE)
    public Collection<Comment> getComments() {
        return comments;
    }

    public void setComments(Collection<Comment> comments) {
        this.comments = comments;
    }

    @Column(name = "enabled")
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean active) {
        this.enabled = active;
    }

    @ManyToOne
    @JoinColumn(name = "roles_id", referencedColumnName = "role_id")
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" + "role=" + role +
                ", id=" + userId +
                ", username='" + username +
                '\'' + ", password='" + password +
                '\'' + ", email='" + email +
                '\'' + ", phone='" + phone +
                '\'' + ", createTime=" + createTime
                + ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age + ", sex='" + gender
                + '\'' + ", companyCode='" + companyCode +
                '\'' + ", photosId=" + photo +
                ", active=" + enabled + '}';
    }
}
