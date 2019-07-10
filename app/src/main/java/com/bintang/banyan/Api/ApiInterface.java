package com.bintang.banyan.Api;

import com.bintang.banyan.Model.AddTanaman;
import com.bintang.banyan.Model.Catatan;
import com.bintang.banyan.Model.Komentar;
import com.bintang.banyan.Model.MyPosting;
import com.bintang.banyan.Model.MyTanaman;
import com.bintang.banyan.Model.Posting;
import com.bintang.banyan.Model.RekomendasiTanaman;

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
    @POST("editpost.php")
    Call<Posting> editPost(
            @Field("id") int id,
            @Field("judul") String judul,
            @Field("deskripsi") String deskripsi,
            @Field("gambar") String gambar
    );

    @FormUrlEncoded
    @POST("deletepost.php")
    Call<Posting> deletePost(
            @Field("id") int id);


    @FormUrlEncoded
    @POST("komentar.php")
    Call<Komentar> postKomentar(
            @Field("post_id") int post_id,
            @Field("user_id") String user_id,
            @Field("komentar") String komentar
    );

    @FormUrlEncoded
    @POST("catatan.php")
    Call<Catatan> postCatatan(
            @Field("tanaman_id") int tanaman_id,
            @Field("user_id") String user_id,
            @Field("catatan") String catatan
    );

    @FormUrlEncoded
    @POST("getkomentar.php")
    Call<List<Komentar>> getKomentar(
            @Field("post_id") int post_id
    );

    @FormUrlEncoded
    @POST("getcatatan.php")
    Call<List<Catatan>> getCatatan(
            @Field("tanaman_id") int tanaman_id
    );

    @FormUrlEncoded
    @POST("getmyposting.php")
    Call<List<MyPosting>> getMyPosting(
            @Field("user_id") int user_id
    );

    @FormUrlEncoded
    @POST("getrekomendasitanaman.php")
    Call<List<RekomendasiTanaman>> getRekomendasiTanaman(
            @Field("ketinggian") String ketinggian,
            @Field("suhu") String suhu,
            @Field("kelembapan") String kelembapan,
            @Field("tekanan") String tekanan,
            @Field("struktur_tanah") String struktur_tanah,
            @Field("lahan") String lahan,
            @Field("air") String air
    );

    @FormUrlEncoded
    @POST("gettanamanku.php")
    Call<List<MyTanaman>> getTanamanku(
            @Field("user_id") int user_id
    );

    @FormUrlEncoded
    @POST("tambahtanaman.php")
    Call<AddTanaman> tambahTanaman(
            @Field("user_id") String user_id,
            @Field("tanaman_id") int tanaman_id
    );

}
