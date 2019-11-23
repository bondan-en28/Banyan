package com.bintang.banyan.Activity.DetailTanaman.IotData;

import android.support.annotation.NonNull;

import com.bintang.banyan.Api.ApiClient;
import com.bintang.banyan.Api.ApiInterface;
import com.bintang.banyan.Model.IotData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReqDataPresenter {
    private ReqDataView view;

    public ReqDataPresenter(ReqDataView view) {
        this.view = view;
    }

    public void reqDataIoT(final int user_id) {

        view.showProgress();

        ApiInterface apiInterface = ApiClient.getApiClient()
                .create(ApiInterface.class);
        Call<List<IotData>> call = apiInterface.reqData(user_id);

        call.enqueue(new Callback<List<IotData>>() {
            @Override
            public void onResponse(@NonNull Call<List<IotData>> call, @NonNull Response<List<IotData>> response) {

                view.hideProgress();

                if (response.isSuccessful() && response.body() != null) {
                    view.onRequestIotDataSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<IotData>> call, @NonNull Throwable t) {
                view.hideProgress();
                view.onRequestIotDataError(t.getLocalizedMessage());
            }
        });
    }

}
