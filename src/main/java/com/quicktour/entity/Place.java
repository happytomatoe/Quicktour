package com.quicktour.entity;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "places")
public class Place {

    private int placeId;
    @NotNull
    private String country;
    @NotNull
    private String name;
    private boolean optional;
    @NotNull
    @Min(0)
    private BigDecimal price;
    @NotNull
    private Double geoWidth;
    @NotNull
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
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Place place = (Place) o;

        if (optional != place.optional) return false;
        if (placeId != place.placeId) return false;
        if (country != null ? !country.equals(place.country) : place.country != null) return false;
        if (description != null ? !description.equals(place.description) : place.description != null) return false;
        if (!geoHeight.equals(place.geoHeight)) return false;
        if (!geoWidth.equals(place.geoWidth)) return false;
        if (!name.equals(place.name)) return false;
        if (price != null ? !price.equals(place.price) : place.price != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = placeId;
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + name.hashCode();
        result = 31 * result + (optional ? 1 : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + geoWidth.hashCode();
        result = 31 * result + geoHeight.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
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
