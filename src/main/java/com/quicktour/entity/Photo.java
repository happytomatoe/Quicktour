package com.quicktour.entity;

import javax.persistence.*;

@Entity
@Table(name = "photos", schema = "", catalog = "quicktour")
public class Photo {

    private int id;
    private String photoUrl;

    @Column(name = "ID")
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "PhotoUrl")
    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

}
