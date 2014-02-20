package com.quicktour.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author Roman Lukash
 */
@Entity
@Table(name = "validation_links")
public class ValidationLink {
    private int validationLinkId;
    private String url;
    private Timestamp createTime;
    private User user;

    @Id
    @GeneratedValue
    @Column(name = "validation_link_id")
    public int getValidationLinkId() {
        return validationLinkId;
    }

    public void setValidationLinkId(int validationLinkId) {
        this.validationLinkId = validationLinkId;
    }

    @Basic
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ValidationLink that = (ValidationLink) o;

        if (validationLinkId != that.validationLinkId) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = validationLinkId;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
