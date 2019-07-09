package com.bintang.banyan.Activity.DetailTanaman;

public interface TambahCatatanView {
    void showProgress();

    void hideProgress();

    void onRequestPostCatatanSuccess(String message);

    void onRequestError(String message);

}
