package com.bintang.banyan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddTanaman {
    @Expose
    @SerializedName("id")
    private int id;
    @Expose
    @SerializedName("user_id")
    private String user_id;
    @Expose
    @SerializedName("tanaman_id")
    private int tanaman_id;
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

    public int getTanaman_id() {
        return tanaman_id;
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
