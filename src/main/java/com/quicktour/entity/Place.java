package com.quicktour.entity;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "places", schema = "", catalog = "quicktour")
public class Place {

    private int placeId;
    private String country;
    private String name;
    private Boolean isOptional;
    private String price;
    private Double geoWidth;
    private Double geoHeight;
    @JsonIgnore private String description;
    @JsonIgnore private List<Excursion> excursions;
    @JsonIgnore private List<Tour> tours;
    @JsonIgnore private List<Photo> placePhotos;

    @Column(name = "PlaceId")
    @GeneratedValue
    @Id
    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    @Column(name = "Country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "Description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "IsOptional")
    public Boolean getOptional() {
        return isOptional;
    }

    public void setOptional(Boolean optional) {
        isOptional = optional;
    }

    @Column(name = "Price")
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Column(name = "GeoHeight")
    public Double getGeoHeight() {
        return geoHeight;
    }

    public void setGeoHeight(Double geoHeight) {
        this.geoHeight = geoHeight;
    }

    @Column(name = "GeoWidth")
    public Double getGeoWidth() {
        return geoWidth;
    }

    public void setGeoWidth(Double geoWidth) {
        this.geoWidth = geoWidth;
    }


    @OneToMany(mappedBy = "place")
    @LazyCollection(LazyCollectionOption.FALSE)
    public List<Excursion> getExcursions() {
        return excursions;
    }

    public void setExcursions(List<Excursion> excursions) {
        this.excursions = excursions;
    }

    @ManyToMany(mappedBy = "toursPlaces", fetch = FetchType.EAGER)
    public List<Tour> getTours() {
        return tours;
    }

    public void setTours(List<Tour> tours) {
        this.tours = tours;
    }

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "places_has_photos",
            joinColumns = @JoinColumn(name = "Places_PlaceId"),
            inverseJoinColumns = @JoinColumn(name = "Photos_ID"))
    public List<Photo> getPlacePhotos() {
        return placePhotos;
    }

    public void setPlacePhotos(List<Photo> placePhotos) {
        this.placePhotos = placePhotos;
    }
}
