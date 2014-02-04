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
@Table(name = "tour_info")
public class TourInfo {
    private int tourInfoId;

    private Tour tour;

    private Date startDate;

    private Date endDate;

    private Integer discount;

    private Collection<Order> orders;

    @GeneratedValue
    @Id
    @Column(name = "tour_info_id")
    public int getTourInfoId() {
        return tourInfoId;
    }

    public void setTourInfoId(int tourId) {
        this.tourInfoId = tourId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tours_id")
    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
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

    @Column(name = "discount")
    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TourInfo tourInfo = (TourInfo) o;

        return tourInfoId == tourInfo.tourInfoId;

    }

    @Override
    public int hashCode() {
        int result = tourInfoId;
        //result = 31 * result + tour;
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (discount != null ? discount.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "tourInfo")
    @LazyCollection(LazyCollectionOption.FALSE)
    public Collection<Order> getOrders() {
        return orders;
    }

    public void setOrders(Collection<Order> ordersByTourInfo) {
        this.orders = ordersByTourInfo;
    }

}
