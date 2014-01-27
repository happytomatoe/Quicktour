package com.quicktour.entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: student
 * Date: 26.11.13
 * Time: 19:04
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "tourinfo", schema = "", catalog = "quicktour")
public class TourInfo {
    private int tourId;

    private Tour tour;

    @Column(name = "TourId")
    @GeneratedValue
    @Id
    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    @ManyToOne()
    @LazyCollection(LazyCollectionOption.TRUE)
    @JoinColumn(name = "ToursId")
    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    private Date startDate;

    @Column(name = "StartDate")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    private Date endDate;

    @Column(name = "EndDate")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    private Integer discount;

    @Column(name = "Discount")
    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TourInfo tourInfo = (TourInfo) o;

        if (tourId != tourInfo.tourId) return false;
        //if (tour != tourInfo.tour) return false;
        if (discount != null ? !discount.equals(tourInfo.discount) : tourInfo.discount != null) return false;
        if (startDate != null ? !startDate.equals(tourInfo.startDate) : tourInfo.startDate != null) return false;
        if (endDate != null ? !endDate.equals(tourInfo.endDate) : tourInfo.endDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tourId;
        //result = 31 * result + tour;
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (discount != null ? discount.hashCode() : 0);
        return result;
    }

    private Collection<Order> ordersByTourInfo;

    @OneToMany(mappedBy = "tourInfoId")
    @LazyCollection(LazyCollectionOption.TRUE)
    public Collection<Order> getOrdersByTourInfo() {
        return ordersByTourInfo;
    }

    public void setOrdersByTourInfo(Collection<Order> ordersByTourInfo) {
        this.ordersByTourInfo = ordersByTourInfo;
    }

}
