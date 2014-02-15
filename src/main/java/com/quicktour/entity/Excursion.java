package com.quicktour.entity;

import javax.persistence.*;

@Entity
@Table(name = "excursions")
public class Excursion {

    private int excursionId;
    private Place place;
    private String name;
    private Boolean optional;
    private Short price;

    @Column(name = "excursion_id")
    @Id
    public int getExcursionId() {
        return excursionId;
    }

    public void setExcursionId(int excursId) {
        this.excursionId = excursId;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "optional")
    public Boolean isOptional() {
        return optional;
    }

    public void setOptional(Boolean optional) {
        this.optional = optional;
    }

    @Column(name = "price")
    public Short getPrice() {
        return price;
    }

    public void setPrice(Short price) {
        this.price = price;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "places_id")
    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

}
