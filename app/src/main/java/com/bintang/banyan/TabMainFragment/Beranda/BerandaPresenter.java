package com.bintang.banyan.TabMainFragment.Beranda;

import android.support.annotation.NonNull;

import com.bintang.banyan.Api.ApiClient;
import com.bintang.banyan.Api.ApiInterface;
import com.bintang.banyan.Model.Posting;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BerandaPresenter {
    private BerandaView view;

    public BerandaPresenter(BerandaView view) {
        this.view = view;
    }

    void getData() {
        view.showLoading();
        //Request to server
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Posting>> call = apiInterface.getPosting();

        call.enqueue(new Callback<List<Posting>>() {
            @Override
            public void onResponse(@NonNull Call<List<Posting>> call, @NonNull Response<List<Posting>> response) {
                view.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    view.onGetResult(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Posting>> call, @NonNull Throwable t) {
                view.hideLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });
    }

}
