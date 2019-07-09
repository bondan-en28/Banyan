package com.bintang.banyan.Activity.Main.TabMainFragment.Kebun;

import com.bintang.banyan.Model.MyTanaman;

import java.util.List;

public interface TanamanView {
    void showLoading();

    void hideLoading();

    void onGetTanamanResult(List<MyTanaman> myTanaman);

    void onErrorLoading(String message);
}
