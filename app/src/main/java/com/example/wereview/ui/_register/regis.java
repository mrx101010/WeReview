package com.example.wereview.ui._register;

public class regis {

    String id;
    String nama;
    String username;
    String email;
    String password;
    String level;
    String poin;

    public regis(){

    }

    public regis(String id, String nama, String username, String email, String password, String level, String poin) {
        this.id = id;
        this.nama = nama;
        this.username = username;
        this.email = email;
        this.password = password;
        this.level = level;
        this.poin = poin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getPoin() {
        return poin;
    }

    public void setPoin(String poin) {
        this.poin = poin;
    }
}
