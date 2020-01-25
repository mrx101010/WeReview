package com.example.wereview.ui._fragment.adapter;

public class subgenre {

    private String subgenreId;
    private String subgenreName;
    private String subgenrePhoto;
    private String parent;

    public subgenre() {
    }

    public subgenre(String subgenreId, String subgenreName, String subgenrePhoto, String parent) {
        this.subgenreId = subgenreId;
        this.subgenreName = subgenreName;
        this.subgenrePhoto = subgenrePhoto;
        this.parent = parent;
    }

    public String getSubgenreId() {
        return subgenreId;
    }

    public void setSubgenreId(String subgenreId) {
        this.subgenreId = subgenreId;
    }

    public String getSubgenreName() {
        return subgenreName;
    }

    public void setSubgenreName(String subgenreName) {
        this.subgenreName = subgenreName;
    }

    public String getSubgenrePhoto() {
        return subgenrePhoto;
    }

    public void setSubgenrePhoto(String subgenrePhoto) {
        this.subgenrePhoto = subgenrePhoto;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}