package com.quicktour.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Entity that describes table orders in DB.
 * Some functionality of system(calculating discount policies ) requires than column names
 * in table orders match properties in class
 */
@Entity
@Table(name = "orders")
public class Order {

    final public static String STATUS_ACCEPTED = "Accepted";
    final public static String STATUS_CONFIRMED = "Confirmed";
    final public static String STATUS_COMPLETED = "Completed";
    final public static String STATUS_CANCELLED = "Cancelled";
    private int orderId;
    private TourInfo tourInfo;
    private Timestamp orderDate;
    private Integer numberOfAdults;
    private Integer numberOfChildren;
    private String userInfo;
    private BigDecimal price;
    private BigDecimal discount;
    private Date nextPaymentDate;
    private Timestamp acceptedDate;
    private Timestamp confirmedDate;
    private Timestamp cancelledDate;
    private Timestamp completedDate;
    private String status;
    private User user;
    private Company company;
    private Integer vote;

    @Id
    @Column(name = "order_id")
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int id) {
        this.orderId = id;
    }

    @Column(name = "order_date")
    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    @Column(name = "number_of_adults")
    @NotNull
    @Min(1)
    public Integer getNumberOfAdults() {
        return numberOfAdults;
    }

    public void setNumberOfAdults(Integer numberOfAdults) {
        this.numberOfAdults = numberOfAdults;
    }

    @Column(name = "number_of_children")
    @NotNull
    @Min(0)
    public Integer getNumberOfChildren() {
        return numberOfChildren;
    }

    public void setNumberOfChildren(Integer numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    @Column(name = "user_info")
    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    @Column(name = "price")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(name = "discount")
    @NotNull
    @Min(0)
    @Max(100)
    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    @Column(name = "next_payment_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getNextPaymentDate() {
        return nextPaymentDate;
    }

    public void setNextPaymentDate(Date nextPaymentDate) {
        this.nextPaymentDate = nextPaymentDate;
    }

    @Column(name = "accepted_date")
    public Timestamp getAcceptedDate() {
        return acceptedDate;
    }

    public void setAcceptedDate(Timestamp acceptedDate) {
        this.acceptedDate = acceptedDate;
    }

    @Column(name = "confirmed_date")
    public Timestamp getConfirmedDate() {
        return confirmedDate;
    }

    public void setConfirmedDate(Timestamp confirmedDate) {
        this.confirmedDate = confirmedDate;
    }

    @Column(name = "completed_date")
    public Timestamp getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(Timestamp completedDate) {
        this.completedDate = completedDate;
    }

    @Column(name = "cancelled_date")
    public Timestamp getCancelledDate() {
        return cancelledDate;
    }

    public void setCancelledDate(Timestamp cancelledDate) {
        this.cancelledDate = cancelledDate;
    }

    @Column(name = "status")
    @NotNull
    @Pattern(regexp = "Received|Accepted|Confirmed|Completed|Cancelled",
            message = "Available statuses are: Received, Accepted, Confirmed, Completed, Cancelled")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id", referencedColumnName = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User userId) {
        this.user = userId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "companies_id")
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company companyId) {
        this.company = companyId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tour_info_id")
    public TourInfo getTourInfo() {
        return tourInfo;
    }

    public void setTourInfo(TourInfo tourInfoId) {
        this.tourInfo = tourInfoId;
    }

    @Column(name = "vote")
    public Integer getVote() {
        return (vote == null) ? new Integer("0") : vote;
    }

    public void setVote(Integer tourVote) {
        this.vote = tourVote;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("id=").append(orderId);
        sb.append(", numberOfAdults=").append(numberOfAdults);
        sb.append(", numberOfChildren=").append(numberOfChildren);
        sb.append('}');
        return sb.toString();
    }


}
