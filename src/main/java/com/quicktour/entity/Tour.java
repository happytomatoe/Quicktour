package com.quicktour.entity;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonManagedReference;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "tours")
public class Tour {
    private int tourId;
    private Company company;
    private BigDecimal price;
    @Valid
    private List<TourInfo> tourInfo;
    @Valid
    private List<Place> toursPlaces;
    private List<PriceDescription> priceIncludes;
    private List<DiscountPolicy> discountPolicies;
    private String name;
    private String description;
    private String transportDesc;
    private Photo photo;
    private Boolean active;
    private BigDecimal discount = BigDecimal.ZERO;
    private String travelType;
    private Double rate;

    private Long rateCount;

    @Formula("(SELECT AVG( orders.vote ) FROM orders " +
            "INNER JOIN tour_info ON orders.tour_info_id = tour_info.tour_info_id " +
            "INNER JOIN tours ON tour_info.tours_id = tours.tour_id " +
            "WHERE orders.vote >0 " +
            "AND tours.tour_id =tour_id " +
            "GROUP BY tours.tour_id)")
    @JsonIgnore
    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    @Formula("(SELECT count(orders.vote) FROM orders " +
            "INNER JOIN tour_info ON orders.tour_info_id = tour_info.tour_info_id " +
            "INNER JOIN tours ON tour_info.tours_id = tours.tour_id " +
            "WHERE orders.vote >0 AND tours.tour_id =tour_id GROUP BY tours.tour_id)")
    @JsonIgnore
    public Long getRateCount() {
        return rateCount;
    }

    public void setRateCount(Long rateCount) {
        this.rateCount = rateCount;
    }

    @ManyToOne()
    @JoinColumn(name = "companies_id")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Column(name = "tour_id")
    @GeneratedValue
    @Id
    public int getTourId() {
        return tourId;
    }

    public void setTourId(int toursId) {
        this.tourId = toursId;
    }

    @Column(name = "price")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(name = "travel_type")
    public String getTravelType() {
        return travelType;
    }

    public void setTravelType(String travelType) {
        this.travelType = travelType;
    }

    @OneToMany(mappedBy = "tour", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    public List<TourInfo> getTourInfo() {
        return tourInfo;
    }

    public void setTourInfo(List<TourInfo> tourInfo) {
        this.tourInfo = tourInfo;
    }

    @Column(name = "active")
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void toogleActive() {
        this.active = !this.active;
    }


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "tours_places",
            joinColumns = @JoinColumn(name = "tours_id"),
            inverseJoinColumns = @JoinColumn(name = "places_id"))
    @JsonManagedReference
    public List<Place> getToursPlaces() {
        return toursPlaces;
    }

    public void setToursPlaces(List<Place> toursPlaces) {
        this.toursPlaces = toursPlaces;
    }

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tours_price_includes",
            joinColumns = @JoinColumn(name = "tours_id"),
            inverseJoinColumns = @JoinColumn(name = "price_includes_id"))
    public List<PriceDescription> getPriceIncludes() {
        return priceIncludes;
    }

    public void setPriceIncludes(List<PriceDescription> priceIncludes) {
        this.priceIncludes = priceIncludes;
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JsonManagedReference
    @JoinTable(name = "tours_discount_policy",
            joinColumns = @JoinColumn(name = "tours_id"),
            inverseJoinColumns = @JoinColumn(name = "discount_policy_id"))
    @LazyCollection(LazyCollectionOption.FALSE)
    public List<DiscountPolicy> getDiscountPolicies() {
        return discountPolicies;
    }

    public void setDiscountPolicies(List<DiscountPolicy> discountPolicy) {
        this.discountPolicies = discountPolicy;
    }

    @NotBlank
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotBlank
    @Column(name = "description", columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotBlank
    @Column(name = "transport_description")
    public String getTransportDesc() {
        return transportDesc;
    }

    public void setTransportDesc(String transportDesc) {
        this.transportDesc = transportDesc;
    }


    @Transient
    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "photo_id")
    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Tour{");
        sb.append("tourId=").append(tourId);
        sb.append("places=").append(toursPlaces.size());
        sb.append("travelType=").append(travelType);
        sb.append(", company=").append(company);
        sb.append(", price=").append(price);
        sb.append(", discountPolicies=").append(discountPolicies);
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }

}




