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

@Entity
@Table(name = "orders", schema = "", catalog = "quicktour")
public class Order {

    final public static String STATUS_ACCEPTED = "Accepted";
    final public static String STATUS_CONFIRMED = "Confirmed";
    final public static String STATUS_COMPLETED = "Completed";
    final public static String STATUS_CANCELLED = "Cancelled";

    public static final String NUMBER_OF_CHILDREN = "orders.number_of_children";
    public static final String NUMBER_OF_ADULTS = "orders.number_of_adults";

    private int id;
    private TourInfo tourInfoId;
    private Timestamp orderDate;
    private Integer numberOfAdults;
    private Integer numberOfChildren;
    private String userInfo;
    private BigDecimal price;
    private BigDecimal discount;
    private Date nextPaymentDate;
    private Timestamp acceptedDate;
    private Timestamp confirmedDate;
    private Timestamp completedDate;
    private Timestamp cancelledDate;
    private String status;
    private User userId;
    private Company companyId;
    private Integer vote;

    @Column(name = "id")
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "order_date")
    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    @NotNull
    @Min(1)
    @Column(name = "number_of_adults")
    public Integer getNumberOfAdults() {
        return numberOfAdults;
    }

    public void setNumberOfAdults(Integer numberOfAdults) {
        this.numberOfAdults = numberOfAdults;
    }

    @NotNull
    @Min(0)
    @Column(name = "number_of_children")
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

    @NotNull
    @Min(0)
    @Max(100)
    @Column(name = "discount")
    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "next_payment_date")
    public Date getNextPaymentDate() {
        return nextPaymentDate;
    }

    public void setNextPaymentDate(Date nextPaymentDate) {
        this.nextPaymentDate = nextPaymentDate;
    }

    @Column(name = "Accepted_date")
    public Timestamp getAcceptedDate() {
        return acceptedDate;
    }

    public void setAcceptedDate(Timestamp acceptedDate) {
        this.acceptedDate = acceptedDate;
    }

    @Column(name = "Confirmed_date")
    public Timestamp getConfirmedDate() {
        return confirmedDate;
    }

    public void setConfirmedDate(Timestamp confirmedDate) {
        this.confirmedDate = confirmedDate;
    }

    @Column(name = "Completed_date")
    public Timestamp getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(Timestamp completedDate) {
        this.completedDate = completedDate;
    }

    @Column(name = "Cancelled_date")
    public Timestamp getCancelledDate() {
        return cancelledDate;
    }

    public void setCancelledDate(Timestamp cancelledDate) {
        this.cancelledDate = cancelledDate;
    }

    @NotNull
    @Pattern(regexp = "Received|Accepted|Confirmed|Completed|Cancelled",
            message = "Available statuses are: Received, Accepted, Confirmed, Completed, Cancelled")
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Users_ID")
    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Companies_id")
    public Company getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Company companyId) {
        this.companyId = companyId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TourId")
    public TourInfo getTourInfoId() {
        return tourInfoId;
    }

    public void setTourInfoId(TourInfo tourInfoId) {
        this.tourInfoId = tourInfoId;
    }

    @Column(name = "vote")
    public Integer getVote() {
        return (vote == null) ? 0 : vote;
    }

    public void setVote(Integer tourVote) {
        this.vote = tourVote;
    }


}
