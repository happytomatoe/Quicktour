package com.quicktour.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "priceincludes", schema = "", catalog = "quicktour")
@Entity
public class PriceDescription {
    private int priceIncludesId;
    private String includeDescription;

    @Column(name = "PriceIncludesId")
    @Id
    public int getPriceIncludesId() {
        return priceIncludesId;
    }

    public void setPriceIncludesId(int priceIncludesId) {
        this.priceIncludesId = priceIncludesId;
    }

    @Column(name = "IncludeDescription")
    public String getIncludeDescription() {
        return includeDescription;
    }

    public void setIncludeDescription(String includeDescription) {
        this.includeDescription = includeDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PriceDescription that = (PriceDescription) o;

        if (priceIncludesId != that.priceIncludesId) return false;
        if (includeDescription != null ? !includeDescription.equals(that.includeDescription) : that.includeDescription != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = priceIncludesId;
        result = 31 * result + (includeDescription != null ? includeDescription.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return this.includeDescription;
    }
}
