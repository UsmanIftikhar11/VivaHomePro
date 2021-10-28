package com.viva.vivahomepro;

public class GalleryModel {
    private String id;
    private String tags;
    private String image;
    private String date;

    public GalleryModel(String id, String tags, String image, String date) {
        this.id = id;
        this.tags = tags;
        this.image = image;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
