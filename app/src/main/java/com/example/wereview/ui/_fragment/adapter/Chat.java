package com.example.wereview.ui._fragment.adapter;

public class Chat {

    public String isiChat;
    public String chatDari;
    public String chatKe;
    public String jamWaktu;

    public Chat() {
    }

    public Chat(String isiChat, String chatDari, String chatKe, String jamWaktu) {
        this.isiChat = isiChat;
        this.chatDari = chatDari;
        this.chatKe = chatKe;
        this.jamWaktu = jamWaktu;
    }

    public String getIsiChat() {
        return isiChat;
    }

    public void setIsiChat(String isiChat) {
        this.isiChat = isiChat;
    }

    public String getChatDari() {
        return chatDari;
    }

    public void setChatDari(String chatDari) {
        this.chatDari = chatDari;
    }

    public String getChatKe() {
        return chatKe;
    }

    public void setChatKe(String chatKe) {
        this.chatKe = chatKe;
    }

    public String getJamWaktu() {
        return jamWaktu;
    }

    public void setJamWaktu(String jamWaktu) {
        this.jamWaktu = jamWaktu;
    }



}
