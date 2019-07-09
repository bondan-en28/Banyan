package com.bintang.banyan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyTanaman {
    @Expose
    @SerializedName("id")
    private int id;
    @Expose
    @SerializedName("nama")
    private String nama;
    @Expose
    @SerializedName("jenis")
    private String jenis;
    @Expose
    @SerializedName("ketinggian")
    private String ketinggian;
    @Expose
    @SerializedName("struktur_tanah")
    private String tanah;
    @Expose
    @SerializedName("suhu")
    private String suhu;
    @Expose
    @SerializedName("ph")
    private String ph;
    @Expose
    @SerializedName("success")
    private Boolean success;
    @Expose
    @SerializedName("message")
    private String message;

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getJenis() {
        return jenis;
    }

    public String getKetinggian() {
        return ketinggian;
    }

    public String getTanah() {
        return tanah;
    }

    public String getSuhu() {
        return suhu;
    }

    public String getPh() {
        return ph;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
