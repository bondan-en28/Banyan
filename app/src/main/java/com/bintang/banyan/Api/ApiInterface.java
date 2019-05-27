package com.bintang.banyan.Api;

import com.bintang.banyan.Model.Posting;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("posting.php")
    Call<Posting> postKonten(
            @Field("user_id") String title,
            @Field("judul") String note,
            @Field("deskripsi") String deskripsi,
            @Field("gambar") String gambar
    );

}
