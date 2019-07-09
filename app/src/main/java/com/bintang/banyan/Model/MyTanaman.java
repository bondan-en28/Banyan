package com.bintang.banyan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyTanaman {
    @Expose
    @SerializedName("id")
    private int id;
    @Expose
    @SerializedName("user_id")
    private String user_id;
    @Expose
    @SerializedName("tanaman_id")
    private String tanaman_id;
    @Expose
    @SerializedName("nama")
    private String nama;
    @Expose
    @SerializedName("nama_latin")
    private String nama_latin;
    @Expose
    @SerializedName("deskripsi")
    private String deskripsi;
    @Expose
    @SerializedName("jenis")
    private String jenis;
    @Expose
    @SerializedName("ketinggian")
    private String ketinggian;
    @Expose
    @SerializedName("struktur_tanah")
    private String struktur_tanah;
    @Expose
    @SerializedName("suhu")
    private String suhu;
    @Expose
    @SerializedName("ph")
    private String ph;
    @Expose
    @SerializedName("kelembapan")
    private String kelembapan;
    @Expose
    @SerializedName("tekanan")
    private String tekanan;
    @Expose
    @SerializedName("lahan")
    private String lahan;
    @Expose
    @SerializedName("air")
    private String air;
    @Expose
    @SerializedName("gambar")
    private String gambar;
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

    public String getUser_id() {
        return user_id;
    }

    public String getTanaman_id() {
        return tanaman_id;
    }

    public String getNama() {
        return nama;
    }

    public String getNama_latin() {
        return nama_latin;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getJenis() {
        return jenis;
    }

    public String getKetinggian() {
        return ketinggian;
    }

    public String getStruktur_tanah() {
        return struktur_tanah;
    }

    public String getSuhu() {
        return suhu;
    }

    public String getPh() {
        return ph;
    }

    public String getKelembapan() {
        return kelembapan;
    }

    public String getTekanan() {
        return tekanan;
    }

    public String getLahan() {
        return lahan;
    }

    public String getAir() {
        return air;
    }

    public String getGambar() {
        return gambar;
    }

    public String getDate() {
        return date;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
