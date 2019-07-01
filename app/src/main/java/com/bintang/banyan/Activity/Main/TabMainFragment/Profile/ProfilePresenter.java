package com.bintang.banyan.Activity.Main.TabMainFragment.Profile;

import android.support.annotation.NonNull;

import com.bintang.banyan.Api.ApiClient;
import com.bintang.banyan.Api.ApiInterface;
import com.bintang.banyan.Model.MyPosting;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePresenter {

    private ProfileView view;

    public ProfilePresenter(ProfileView view) {
        this.view = view;
    }

    void getData(final int user_id) {
        view.showLoading();
        //Request to server
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<MyPosting>> call = apiInterface.getMyPosting(user_id);

        call.enqueue(new Callback<List<MyPosting>>() {
            @Override
            public void onResponse(@NonNull Call<List<MyPosting>> call, @NonNull Response<List<MyPosting>> response) {
                view.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    view.onGetMyPostResult(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<MyPosting>> call, @NonNull Throwable t) {
                view.hideLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });
    }

}
