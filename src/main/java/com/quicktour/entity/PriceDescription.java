package com.quicktour.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "price_includes")
@Entity
public class PriceDescription {
    private int priceDescriptionId;
    private String description;

    @Column(name = "price_include_id")
    @Id
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

        if (priceDescriptionId != that.priceDescriptionId) {
            return false;
        }
        return description.equals(that.description);

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
