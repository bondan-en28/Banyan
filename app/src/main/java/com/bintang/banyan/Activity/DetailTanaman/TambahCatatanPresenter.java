package com.bintang.banyan.Activity.DetailTanaman;

import android.support.annotation.NonNull;

import com.bintang.banyan.Api.ApiClient;
import com.bintang.banyan.Api.ApiInterface;
import com.bintang.banyan.Model.Catatan;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahCatatanPresenter {
    private TambahCatatanView view;

    public TambahCatatanPresenter(TambahCatatanView view) {
        this.view = view;
    }

    void postCatatan(final int tanaman_id, final String user_id, final String catatan) {

        view.showProgress();

        ApiInterface apiInterface = ApiClient.getApiClient()
                .create(ApiInterface.class);
        Call<Catatan> call = apiInterface.postCatatan(tanaman_id, user_id, catatan);

        call.enqueue(new Callback<Catatan>() {
            @Override
            public void onResponse(@NonNull Call<Catatan> call, @NonNull Response<Catatan> response) {

                view.hideProgress();

                if (response.isSuccessful() && response.body() != null) {
                    Boolean success = response.body().getSuccess();
                    if (success) {
                        view.onRequestPostCatatanSuccess(response.body().getMessage());
                    } else {
                        view.onRequestError(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Catatan> call, @NonNull Throwable t) {
                view.hideProgress();
                view.onRequestError(t.getLocalizedMessage());
            }
        });
    }

}
