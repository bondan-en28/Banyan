package com.bintang.banyan.Activity.DetailTanaman.Catatan;

import android.support.annotation.NonNull;

import com.bintang.banyan.Api.ApiClient;
import com.bintang.banyan.Api.ApiInterface;
import com.bintang.banyan.Model.Catatan;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatatanPresenter {
    private CatatanView view;

    public CatatanPresenter(CatatanView view) {
        this.view = view;
    }

    public void getCatatan(final int tanaman_id) {

        view.showCatatanProgress();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Catatan>> call = apiInterface.getCatatan(tanaman_id);

        call.enqueue(new Callback<List<Catatan>>() {
            @Override
            public void onResponse(@NonNull Call<List<Catatan>> call, @NonNull Response<List<Catatan>> response) {

                view.hideCatatanProgress();

                if (response.isSuccessful() && response.body() != null) {
                    view.onRequestCatatanSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Catatan>> call, @NonNull Throwable t) {
                view.hideCatatanProgress();
                view.onRequestCatatanError(t.getLocalizedMessage());
            }
        });
    }
}