package com.bintang.banyan.Activity.DetailPost.Komentar;

import android.support.annotation.NonNull;

import com.bintang.banyan.Api.ApiClient;
import com.bintang.banyan.Api.ApiInterface;
import com.bintang.banyan.Model.Komentar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KomentarPresenter {
    private KomentarView view;

    public KomentarPresenter(KomentarView view) {
        this.view = view;
    }

    public void getKomentar(final int post_id) {

        view.showKomentarProgress();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Komentar>> call = apiInterface.getKomentar(post_id);

        call.enqueue(new Callback<List<Komentar>>() {
            @Override
            public void onResponse(@NonNull Call<List<Komentar>> call, @NonNull Response<List<Komentar>> response) {

                view.hideKomentarProgress();

                if (response.isSuccessful() && response.body() != null) {
                    view.onRequestKomentarSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Komentar>> call, @NonNull Throwable t) {
                view.hideKomentarProgress();
                view.onRequestKomentarError(t.getLocalizedMessage());
            }
        });
    }
}