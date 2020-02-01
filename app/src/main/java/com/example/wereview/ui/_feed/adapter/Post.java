package com.example.wereview.ui._feed.adapter;

import com.firebase.ui.auth.data.model.User;
import com.google.firebase.database.Exclude;

public class Post {
    public String key;
    public String caption;
    public String description;
    public String userId;
    public String downloadUrl;
    public String subgenreParent;
    public String genreParent;

    // these properties will not be saved to the database
    @Exclude
    public User user;

    @Exclude
    public int likes = 0;

    @Exclude
    public boolean hasLiked = false;

    @Exclude
    public String userLike;

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Post(String key, String caption, String description, String userId, String downloadUrl, String subgenreParent, String genreParent, User user) {
        this.key = key;
        this.caption = caption;
        this.description = description;
        this.userId = userId;
        this.downloadUrl = downloadUrl;
        this.subgenreParent = subgenreParent;
        this.genreParent = genreParent;
        this.user = user;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getSubgenreParent() {
        return subgenreParent;
    }

    public void setSubgenreParent(String subgenreParent) {
        this.subgenreParent = subgenreParent;
    }

    public String getGenreParent() {
        return genreParent;
    }

    public void setGenreParent(String genreParent) {
        this.genreParent = genreParent;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
