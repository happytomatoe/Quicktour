package com.quicktour.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "validation_links")
public class ValidationLink {

    private int validationLinkId;
    private int userId;
    private String url;
    private Timestamp createTime;

    @Column(name = "validation_link_id")
    @Id
    public int getValidationLinkId() {
        return validationLinkId;
    }

    public void setValidationLinkId(int id) {
        this.validationLinkId = id;
    }

    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String validationLink) {
        this.url = validationLink;
    }

    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp time_registered) {
        this.createTime = time_registered;
    }

}
