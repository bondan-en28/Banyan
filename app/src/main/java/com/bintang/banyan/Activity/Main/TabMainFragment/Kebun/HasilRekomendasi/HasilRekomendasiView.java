package com.bintang.banyan.Activity.Main.TabMainFragment.Kebun.HasilRekomendasi;

import com.bintang.banyan.Model.RekomendasiTanaman;

import java.util.List;

public interface HasilRekomendasiView {
    void showLoading();

    void hideLoading();

    void onGetRekomendasiResult(List<RekomendasiTanaman> tanaman);

    void onErrorLoading(String message);
}
