package com.quicktour.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "priceincludes", schema = "", catalog = "quicktour")
@Entity
public class PriceDescription {
    private int id;
    private String description;

    @Column(name = "PriceIncludesId")
    @Id
    public int getId() {
        return id;
    }

    public void setId(int priceIncludesId) {
        this.id = priceIncludesId;
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PriceDescription that = (PriceDescription) o;

        if (id != that.id) return false;
        if (description != null ? !description.equals(that.description) : that.description != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
