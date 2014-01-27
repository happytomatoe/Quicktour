package com.quicktour.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * @author Roman Lukash
 */
@Table(name = "discount_dependency", schema = "", catalog = "quicktour")
@Entity
public class DiscountDependency {

    private int id;
    private String tag;
    private String tableField;
    private String description;

    @Column(name = "id")
    @GeneratedValue
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotEmpty(message = "Tag is empty")
    @Column(name = "tag")
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @NotEmpty(message = "Table field is empty")
    @Column(name = "table_field")
    public String getTableField() {
        return tableField;
    }

    public void setTableField(String tableField) {
        this.tableField = tableField;
    }

    @NotEmpty(message = "Description is empty")
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "DiscountDependency{" +
                "id=" + id +
                ", tag='" + tag + '\'' +
                ", tableField='" + tableField + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
