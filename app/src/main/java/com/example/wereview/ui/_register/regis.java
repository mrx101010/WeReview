package com.example.wereview.ui._register;

public class regis {

    String Id;
    String Nama;
    String Username;
    String Email;
    String Password;

    public regis(){

    }

    public regis(String id, String nama, String username, String email, String password) {
        Id = id;
        Nama = nama;
        Username = username;
        Email = email;
        Password = password;
    }

    public String getId() {
        return Id;
    }

    public String getNama() {
        return Nama;
    }

    public String getUsername() {
        return Username;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }
}
