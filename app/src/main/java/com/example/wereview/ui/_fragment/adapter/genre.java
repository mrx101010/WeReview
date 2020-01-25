package com.example.wereview.ui._fragment.adapter;

public class genre {

    private String genre_name;
    private String genre_id;
    private int genre_photo;

    public genre() {
    }

    public genre(String genre_name, String genre_id, int genre_photo) {
        this.genre_name = genre_name;
        this.genre_id = genre_id;
        this.genre_photo = genre_photo;
    }

    public String getGenre_name() {
        return genre_name;
    }

    public void setGenre_name(String genre_name) {
        this.genre_name = genre_name;
    }


    public String getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(String genre_id) {
        this.genre_id = genre_id;
    }

    public int getGenre_photo() {
        return genre_photo;
    }

    public void setGenre_photo(int genre_photo) {
        this.genre_photo = genre_photo;
    }
}



