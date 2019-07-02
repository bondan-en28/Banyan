package com.bintang.banyan.Activity.Main.TabMainFragment.Kebun.HasilRekomendasi;

import android.support.annotation.NonNull;

import com.bintang.banyan.Api.ApiClient;
import com.bintang.banyan.Api.ApiInterface;
import com.bintang.banyan.Model.RekomendasiTanaman;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HasilRekomendasiPresenter {
    private HasilRekomendasiView view;

    public HasilRekomendasiPresenter(HasilRekomendasiView view) {
        this.view = view;
    }

    void getRekomendasiTanaman(String suhu, String ketinggian, String tanah) {
        view.showLoading();
        //Request to server
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<RekomendasiTanaman>> call = apiInterface.getRekomendasiTanaman(suhu, ketinggian, tanah);

        call.enqueue(new Callback<List<RekomendasiTanaman>>() {
            @Override
            public void onResponse(@NonNull Call<List<RekomendasiTanaman>> call, @NonNull Response<List<RekomendasiTanaman>> response) {
                view.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    view.onGetRekomendasiResult(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<RekomendasiTanaman>> call, @NonNull Throwable t) {
                view.hideLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });
    }

}
