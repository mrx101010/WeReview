package com.example.wereview.ui._fragment.adapter;

public class genre {

    private String genreId;
    private String genreName;
    private String genrePhoto;

    public genre() {
    }

    public genre(String genreId, String genreName, String genrePhoto) {
        this.genreId = genreId;
        this.genreName = genreName;
        this.genrePhoto = genrePhoto;
    }


    public String getGenreId() {
        return genreId;
    }

    public void setGenreId(String genreId) {
        this.genreId = genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public String getGenrePhoto() {
        return genrePhoto;
    }

    public void setGenrePhoto(String genrePhoto) {
        this.genrePhoto = genrePhoto;
    }
}



