package com.example.call.model;

import java.io.Serializable;

public class Call implements Serializable{
    private int id;
    private String ten;
    private int sdt;
    private boolean isFav;

    public Call(int id, String ten, int sdt) {
        this.id = id;
        this.ten = ten;
        this.sdt = sdt;
        this.isFav = false;
    }

    public Call(int id, String ten, int sdt, boolean isFav) {
        this.id = id;
        this.ten = ten;
        this.sdt = sdt;
        this.isFav = isFav;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFav() {
        return isFav;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getSdt() {
        return sdt;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }

    public void setSdt(int sdt) {
        this.sdt = sdt;
    }
}
