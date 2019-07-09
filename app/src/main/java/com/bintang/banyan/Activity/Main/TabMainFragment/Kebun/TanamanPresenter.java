package com.bintang.banyan.Activity.Main.TabMainFragment.Kebun;

import android.support.annotation.NonNull;

import com.bintang.banyan.Api.ApiClient;
import com.bintang.banyan.Api.ApiInterface;
import com.bintang.banyan.Model.MyTanaman;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TanamanPresenter {
    private TanamanView view;

    public TanamanPresenter(TanamanView view) {
        this.view = view;
    }

    void getTanamanku(int user_id) {
        view.showLoading();
        //Request to server
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<MyTanaman>> call = apiInterface.getTanamanku(user_id);

        call.enqueue(new Callback<List<MyTanaman>>() {
            @Override
            public void onResponse(@NonNull Call<List<MyTanaman>> call, @NonNull Response<List<MyTanaman>> response) {
                view.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    view.onGetTanamanResult(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<MyTanaman>> call, @NonNull Throwable t) {
                view.hideLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });
    }

}
