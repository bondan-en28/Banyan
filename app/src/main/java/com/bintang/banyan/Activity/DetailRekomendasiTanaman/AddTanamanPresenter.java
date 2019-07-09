package com.bintang.banyan.Activity.DetailRekomendasiTanaman;

import android.support.annotation.NonNull;

import com.bintang.banyan.Api.ApiClient;
import com.bintang.banyan.Api.ApiInterface;
import com.bintang.banyan.Model.AddTanaman;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTanamanPresenter {
    private AddTanamanView view;

    public AddTanamanPresenter(AddTanamanView view) {
        this.view = view;
    }

    void tambahTanaman(final String user_id, final int tanaman_id) {

        ApiInterface apiInterface = ApiClient.getApiClient()
                .create(ApiInterface.class);
        Call<AddTanaman> call = apiInterface.tambahTanaman(user_id, tanaman_id);

        call.enqueue(new Callback<AddTanaman>() {
            @Override
            public void onResponse(@NonNull Call<AddTanaman> call, @NonNull Response<AddTanaman> response) {

                if (response.isSuccessful() && response.body() != null) {
                    Boolean success = response.body().getSuccess();
                    if (success) {
                        view.onRequestAddTanamanSuccess(response.body().getMessage());
                    } else {
                        view.onRequestError(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<AddTanaman> call, @NonNull Throwable t) {
                view.onRequestError(t.getLocalizedMessage());
            }
        });
    }

}
