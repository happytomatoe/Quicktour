package com.quicktour.entity;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonManagedReference;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "tours", schema = "", catalog = "quicktour")
public class Tour {
    private int tourId;
    @JsonIgnore private Company company;
    private BigDecimal price;
    @JsonIgnore private Collection<TourInfo> tourInfo;     // TODO should be tours
    @JsonIgnore private Collection<Comment> commentsByTourId;
    @JsonIgnore private List<Place> toursPlaces;      // TODO palaces
    @JsonIgnore private List<PriceDescription> priceIncludes;
    private List<DiscountPolicy> discountPolicies;    // TODO policies
    private String name;
    private String description;
    private String transportDesc;
    private String mainPhotoUrl;
    private Boolean isActive;
    private BigDecimal discount = BigDecimal.ZERO;
    private String travelType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
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

    @OneToMany(mappedBy = "tour")
    @LazyCollection(LazyCollectionOption.FALSE)
    public Collection<Comment> getCommentsByTourId() {
        return commentsByTourId;
    }

    public void setCommentsByTourId(Collection<Comment> commentsByTourId) {
        this.commentsByTourId = commentsByTourId;
    }

    @OneToMany(mappedBy = "tour")
    @LazyCollection(LazyCollectionOption.FALSE)
    public Collection<TourInfo> getTourInfo() {
        return tourInfo;
    }

    public void setTourInfo(Collection<TourInfo> tourInfo) {
        this.tourInfo = tourInfo;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(name = "Tours_has_Places",
            joinColumns = @JoinColumn(name = "ToursId"),
            inverseJoinColumns = @JoinColumn(name = "PlaceId"))
    public List<Place> getToursPlaces() {
        return toursPlaces;
    }

    public void setToursPlaces(List<Place> toursPlaces) {
        this.toursPlaces = toursPlaces;
    }

    @ManyToMany()
    @JoinTable(name = "Tours_has_PriceIncludes",
            joinColumns = @JoinColumn(name = "ToursId"),
            inverseJoinColumns = @JoinColumn(name = "PriceIncludesId"))
    @LazyCollection(LazyCollectionOption.FALSE)
    public List<PriceDescription> getPriceIncludes() {
        return priceIncludes;
    }

    public void setPriceIncludes(List<PriceDescription> priceIncludes) {
        this.priceIncludes = priceIncludes;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JsonManagedReference
    @JoinTable(name = "tours_discount_policy",
            joinColumns = @JoinColumn(name = "Tours_ToursId"),
            inverseJoinColumns = @JoinColumn(name = "discount_policy_id"))
    @LazyCollection(LazyCollectionOption.FALSE)
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

    @Column(name = "MainPhotoUrl")
    public String getMainPhotoUrl() {
        return mainPhotoUrl;
    }

    public void setMainPhotoUrl(String mainPhotoUrl) {
        this.mainPhotoUrl = mainPhotoUrl;
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




