package com.quicktour.entity;

import javax.persistence.*;

@Entity
@Table(name = "photos")
public class Photo {

    @Column(name = "photo_id")
    @Id
    @GeneratedValue
    private int photoId;
    @Column(name = "url")
    private String url;

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int id) {
        this.photoId = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String photoUrl) {
        this.url = photoUrl;
    }

}
