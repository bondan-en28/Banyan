package com.bintang.banyan.Activity.DetailTanaman.IotData;

import com.bintang.banyan.Model.IotData;

import java.util.List;

public interface ReqDataView {
    void showProgress();

    void hideProgress();

    void onRequestIotDataSuccess(List<IotData> komentars);

    void onRequestIotDataError(String message);

}
