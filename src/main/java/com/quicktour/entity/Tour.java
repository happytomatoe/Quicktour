package com.quicktour.entity;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonManagedReference;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tours", schema = "", catalog = "quicktour")
public class Tour {
    private int tourId;
    private Company company;
    private BigDecimal price;
    private Collection<TourInfo> tourInfo;
    private List<Place> toursPlaces;
    private Set<PriceDescription> priceIncludes;
    private List<DiscountPolicy> discountPolicies;
    private String name;
    private String description;
    private String transportDesc;
    private Photo photo;
    private Boolean isActive;
    private BigDecimal discount = BigDecimal.ZERO;
    private String travelType;
    private Double rate;
    private Long rateCount;

    @Formula("(select avg(orders.vote)  from orders inner join tours ON orders.TourId = tours.ToursId " +
            "where orders.vote > 0 and tours.ToursId = ToursId group by tours.ToursId)")
    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    @Formula("(select count(orders.vote) from orders inner join tours ON orders.TourId = tours.ToursId " +
            "where orders.vote > 0 and tours.ToursId = ToursId group by tours.ToursId)")
    public Long getRateCount() {
        return rateCount;
    }

    public void setRateCount(Long rateCount) {
        this.rateCount = rateCount;
    }

    @ManyToOne()
    @JoinColumn(name = "company_id")
    @LazyCollection(LazyCollectionOption.TRUE)
    @JsonIgnore
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Column(name = "ToursId", insertable = false, updatable = false)
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

    @OneToMany(mappedBy = "tour", fetch = FetchType.EAGER)
    @JsonIgnore
    public Collection<TourInfo> getTourInfo() {
        return tourInfo;
    }

    public void setTourInfo(Collection<TourInfo> tourInfo) {
        this.tourInfo = tourInfo;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tours_places",
            joinColumns = @JoinColumn(name = "ToursId"),
            inverseJoinColumns = @JoinColumn(name = "PlaceId"))
    @JsonManagedReference
    @LazyCollection(LazyCollectionOption.TRUE)
    public List<Place> getToursPlaces() {
        return toursPlaces;
    }

    public void setToursPlaces(List<Place> toursPlaces) {
        this.toursPlaces = toursPlaces;
    }

    @OneToMany()
    @JoinTable(name = "tours_price_includes",
            joinColumns = @JoinColumn(name = "ToursId"),
            inverseJoinColumns = @JoinColumn(name = "PriceIncludesId"))
    @JsonIgnore
    @LazyCollection(LazyCollectionOption.TRUE)
    public Set<PriceDescription> getPriceIncludes() {
        return priceIncludes;
    }

    public void setPriceIncludes(Set<PriceDescription> priceIncludes) {
        this.priceIncludes = priceIncludes;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JsonManagedReference
    @JoinTable(name = "tours_discount_policy",
            joinColumns = @JoinColumn(name = "Tours_ToursId"),
            inverseJoinColumns = @JoinColumn(name = "discount_policy_id"))
    @LazyCollection(LazyCollectionOption.TRUE)
    public List<DiscountPolicy> getDiscountPolicies() {
        return discountPolicies;
    }

    public void setDiscountPolicies(List<DiscountPolicy> discountPolicy) {
        this.discountPolicies = discountPolicy;
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

    @Column(name = "TransportDesc")
    public String getTransportDesc() {
        return transportDesc;
    }

    public void setTransportDesc(String transportDesc) {
        this.transportDesc = transportDesc;
    }

    @Column(name = "IsActive")
    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
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
        sb.append(", company=").append(company);
        sb.append(", price=").append(price);
        sb.append(", discountPolicies=").append(discountPolicies);
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }

}




