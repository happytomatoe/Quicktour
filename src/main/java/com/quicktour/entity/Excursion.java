package com.quicktour.entity;

import javax.persistence.*;

@Entity
@Table(name = "excursions", schema = "", catalog = "quicktour")
public class Excursion {

    private int excursId;
    private Place place;
    private String name;
    private Boolean isOptional;
    private Short price;

    @Column(name = "ExcursId")
    @Id
    public int getExcursId() {
        return excursId;
    }

    public void setExcursId(int excursId) {
        this.excursId = excursId;
    }

    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "IsOptional")
    public Boolean getOptional() {
        return isOptional;
    }

    public void setOptional(Boolean optional) {
        isOptional = optional;
    }

    @Column(name = "Price")
    public Short getPrice() {
        return price;
    }

    public void setPrice(Short price) {
        this.price = price;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PlaceId")
    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

}
