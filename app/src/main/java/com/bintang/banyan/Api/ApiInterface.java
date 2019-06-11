package com.bintang.banyan.Api;

import com.bintang.banyan.Model.Komentar;
import com.bintang.banyan.Model.Posting;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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

    @GET("getposting.php")
    Call<List<Posting>> getPosting();

    @FormUrlEncoded
    @POST("komentar.php")
    Call<Komentar> postKomentar(
            @Field("post_id") int post_id,
            @Field("user_id") String user_id,
            @Field("komentar") String komentar
    );

}
