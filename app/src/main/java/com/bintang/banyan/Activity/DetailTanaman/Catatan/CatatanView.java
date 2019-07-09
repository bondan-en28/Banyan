package com.bintang.banyan.Activity.DetailTanaman.Catatan;

import com.bintang.banyan.Model.Catatan;

import java.util.List;

public interface CatatanView {
    void showCatatanProgress();

    void hideCatatanProgress();

    void onRequestCatatanSuccess(List<Catatan> catatan);

    void onRequestCatatanError(String message);
}
