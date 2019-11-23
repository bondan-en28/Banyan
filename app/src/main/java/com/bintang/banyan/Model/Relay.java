package com.bintang.banyan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Relay {
    @Expose
    @SerializedName("id")
    private int id;
    @Expose
    @SerializedName("user_id")
    private int user_id;
    @Expose
    @SerializedName("relay")
    private int relay;
    @Expose
    @SerializedName("success")
    private Boolean success;
    @Expose
    @SerializedName("message")
    private String message;
}
