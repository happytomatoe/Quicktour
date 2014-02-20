package com.quicktour.entity;

import javax.persistence.*;

@Entity
@Table(name = "photos")
public class Photo {

    private int photoId;
    private String url;
    private String flickrPhotoId;

    @Column(name = "photo_id")
    @Id
    @GeneratedValue
    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int id) {
        this.photoId = id;
    }

    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String photoUrl) {
        this.url = photoUrl;
    }

    @Column(name = "flickr_photo_id")
    public String getFlickrPhotoId() {
        return flickrPhotoId;
    }

    public void setFlickrPhotoId(String flickrPhotoId) {
        this.flickrPhotoId = flickrPhotoId;
    }

}
