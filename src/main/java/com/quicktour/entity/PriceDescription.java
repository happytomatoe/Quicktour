package com.quicktour.entity;

import javax.persistence.*;

@Table(name = "price_includes")
@Entity
public class PriceDescription {

    private int priceDescriptionId;
    private String description;

    public PriceDescription(int id) {
        priceDescriptionId = id;
    }

    public PriceDescription() {
    }

    @Column(name = "price_include_id")
    @Id
    @GeneratedValue
    public int getPriceDescriptionId() {
        return priceDescriptionId;
    }

    public void setPriceDescriptionId(int priceIncludesId) {
        this.priceDescriptionId = priceIncludesId;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String includeDescription) {
        this.description = includeDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PriceDescription that = (PriceDescription) o;

        return priceDescriptionId == that.priceDescriptionId && description.equals(that.description);

    }

    @Override
    public String toString() {
        return description;
    }

    @Override
    public int hashCode() {
        int result = priceDescriptionId;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }


}
