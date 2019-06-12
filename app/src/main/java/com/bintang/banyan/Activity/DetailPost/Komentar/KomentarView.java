package com.bintang.banyan.Activity.DetailPost.Komentar;

import com.bintang.banyan.Model.Komentar;

import java.util.List;

public interface KomentarView {
    void showKomentarProgress();

    void hideKomentarProgress();

    void onRequestKomentarSuccess(List<Komentar> komentars);

    void onRequestKomentarError(String message);
}
