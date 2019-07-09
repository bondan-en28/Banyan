package com.bintang.banyan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Catatan {
    @Expose
    @SerializedName("id")
    private int id;
    @Expose
    @SerializedName("tanaman_id")
    private String tanaman_id;
    @Expose
    @SerializedName("user_id")
    private String user_id;
    @Expose
    @SerializedName("catatan")
    private String catatan;
    @Expose
    @SerializedName("date")
    private String date;
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

    public String getTanaman_id() {
        return tanaman_id;
    }

    public void setTanaman_id(String tanaman_id) {
        this.tanaman_id = tanaman_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
