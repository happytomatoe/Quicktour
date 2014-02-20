package com.quicktour.entity;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "places")
public class Place {

    private int placeId;
    private String country;
    private String name;
    private boolean optional;
    private BigDecimal price;
    private Double geoWidth;
    private Double geoHeight;
    private String description;
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

    @NotBlank
    @Column(name = "country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @NotBlank
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
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

    @NotNull
    @Min(0)
    @Column(name = "price")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @NotNull
    @Column(name = "geoheight")
    public Double getGeoHeight() {
        return geoHeight;
    }

    public void setGeoHeight(Double geoHeight) {
        this.geoHeight = geoHeight;
    }

    @NotNull
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
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (optional ? 1 : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (geoWidth != null ? geoWidth.hashCode() : 0);
        result = 31 * result + (geoHeight != null ? geoHeight.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    public void setGeoWidth(Double geoWidth) {
        this.geoWidth = geoWidth;
    }

    @JsonBackReference
    @ManyToMany(mappedBy = "toursPlaces")
    @LazyCollection(LazyCollectionOption.TRUE)
    public List<Tour> getTours() {
        return tours;
    }

    public void setTours(List<Tour> tours) {
        this.tours = tours;
    }
}
