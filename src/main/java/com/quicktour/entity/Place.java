package com.quicktour.entity;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "places")
public class Place {

    private int placeId;
    private String country;
    private String name;
    private boolean optional;
    private String price;
    private Double geoWidth;
    private Double geoHeight;
    @JsonIgnore
    private String description;
    @JsonIgnore
    private List<Excursion> excursions;
    @JsonBackReference
    private List<Tour> tours;

    @Column(name = "place_id")
    @GeneratedValue
    @Id
    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    @Column(name = "country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "optional")
    public boolean isOptional() {
        return optional;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    @Column(name = "price")
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Column(name = "geoheight")
    public Double getGeoHeight() {
        return geoHeight;
    }

    public void setGeoHeight(Double geoHeight) {
        this.geoHeight = geoHeight;
    }

    @Column(name = "geowidth")
    public Double getGeoWidth() {
        return geoWidth;
    }

    public void setGeoWidth(Double geoWidth) {
        this.geoWidth = geoWidth;
    }

    @OneToMany(mappedBy = "place")
    @LazyCollection(LazyCollectionOption.TRUE)
    public List<Excursion> getExcursions() {
        return excursions;
    }

    public void setExcursions(List<Excursion> excursions) {
        this.excursions = excursions;
    }

    @ManyToMany(mappedBy = "toursPlaces")
    @LazyCollection(LazyCollectionOption.TRUE)
    public List<Tour> getTours() {
        return tours;
    }

    public void setTours(List<Tour> tours) {
        this.tours = tours;
    }
}
