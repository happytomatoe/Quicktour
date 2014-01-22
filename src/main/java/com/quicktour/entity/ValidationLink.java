package com.quicktour.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "validation_links", schema = "", catalog = "quicktour")
public class ValidationLink {

    private int id;
    private int userId;
    private String validationLink;
    private Timestamp timeRegistered;

    @Column(name = "ID", insertable = false, updatable = false)
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Column(name = "userId")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Column(name = "validationLink")
    public String getValidationLink() {
        return validationLink;
    }

    public void setValidationLink(String validationLink) {
        this.validationLink = validationLink;
    }

    @Column(name = "time_registered")
    public Timestamp getTimeRegistered() {
        return timeRegistered;
    }

    public void setTimeRegistered(Timestamp time_registered) {
        this.timeRegistered = time_registered;
    }

}
