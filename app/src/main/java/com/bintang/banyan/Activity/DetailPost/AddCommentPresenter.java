package com.bintang.banyan.Activity.DetailPost;

import android.support.annotation.NonNull;

import com.bintang.banyan.Api.ApiClient;
import com.bintang.banyan.Api.ApiInterface;
import com.bintang.banyan.Model.Komentar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCommentPresenter {
    private AddCommentView view;

    public AddCommentPresenter(AddCommentView view) {
        this.view = view;
    }

    void postKomentar(final int post_id, final String user_id, final String komentar) {

        view.showProgress();

        ApiInterface apiInterface = ApiClient.getApiClient()
                .create(ApiInterface.class);
        Call<Komentar> call = apiInterface.postKomentar(post_id, user_id, komentar);

        call.enqueue(new Callback<Komentar>() {
            @Override
            public void onResponse(@NonNull Call<Komentar> call, @NonNull Response<Komentar> response) {

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
            public void onFailure(@NonNull Call<Komentar> call, @NonNull Throwable t) {
                view.hideProgress();
                view.onRequestError(t.getLocalizedMessage());
            }
        });
    }

}
