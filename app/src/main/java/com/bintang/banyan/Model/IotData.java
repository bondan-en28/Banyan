package com.bintang.banyan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IotData {
    @Expose
    @SerializedName("id")
    private int id;
    @Expose
    @SerializedName("user_id")
    private String user_id;
    @Expose
    @SerializedName("cahaya")
    private String cahaya;
    @Expose
    @SerializedName("suhu")
    private String suhu;
    @Expose
    @SerializedName("lembab_udara")
    private String lembab_udara;
    @Expose
    @SerializedName("lembab_tanah")
    private String lembab_tanah;
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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCahaya() {
        return cahaya;
    }

    public void setCahaya(String cahaya) {
        this.cahaya = cahaya;
    }

    public String getSuhu() {
        return suhu;
    }

    public void setSuhu(String suhu) {
        this.suhu = suhu;
    }

    public String getLembab_udara() {
        return lembab_udara;
    }

    public void setLembab_udara(String lembab_udara) {
        this.lembab_udara = lembab_udara;
    }

    public String getLembab_tanah() {
        return lembab_tanah;
    }

    public void setLembab_tanah(String lembab_tanah) {
        this.lembab_tanah = lembab_tanah;
    }

    public String getDate() {
        return date;
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
