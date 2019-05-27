package com.bintang.banyan.Activity.AddPost;

import android.support.annotation.NonNull;

import com.bintang.banyan.Api.ApiClient;
import com.bintang.banyan.Api.ApiInterface;
import com.bintang.banyan.Model.Posting;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPostPresenter {
    private AddPostView view;

    public AddPostPresenter(AddPostView view) {
        this.view = view;
    }

    void postKonten(final String user_id, final String judul, final String deskripsi, final String gambar) {

        view.showProgress();

        ApiInterface apiInterface = ApiClient.getApiClient()
                .create(ApiInterface.class);
        Call<Posting> call = apiInterface.postKonten(user_id, judul, deskripsi, gambar);

        call.enqueue(new Callback<Posting>() {
            @Override
            public void onResponse(@NonNull Call<Posting> call, @NonNull Response<Posting> response) {

                view.hideProgress();

                if (response.isSuccessful() && response.body() != null) {
                    Boolean success = response.body().getSuccess();
                    if (success) {
                        view.onRequestSuccess(response.body().getMessage());
                    } else {
                        view.onRequestError(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Posting> call, @NonNull Throwable t) {
                view.hideProgress();
                view.onRequestError(t.getLocalizedMessage());
            }
        });
    }

}
