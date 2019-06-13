package com.bintang.banyan.Activity.Main.TabMainFragment.Beranda;

import com.bintang.banyan.Model.Posting;

import java.util.List;

public interface BerandaView {
    void showLoading();

    void hideLoading();

    void onGetResult(List<Posting> notes);

    void onErrorLoading(String message);
}
