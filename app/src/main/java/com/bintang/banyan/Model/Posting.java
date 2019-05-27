package com.bintang.banyan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Posting {
    @Expose
    @SerializedName("id")
    private int id;
    @Expose
    @SerializedName("user_id")
    private String user_id;
    @Expose
    @SerializedName("judul")
    private String judul;
    @Expose
    @SerializedName("deskripsi")
    private String deskripsi;
    @Expose
    @SerializedName("gambar")
    private String gambar;
    @Expose
    @SerializedName("date")
    private String tanggal;
    @Expose
    @SerializedName("success")
    private Boolean success;
    @Expose
    @SerializedName("message")
    private String message;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
