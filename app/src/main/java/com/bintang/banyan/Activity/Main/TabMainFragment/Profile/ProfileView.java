package com.bintang.banyan.Activity.Main.TabMainFragment.Profile;

import com.bintang.banyan.Model.MyPosting;

import java.util.List;

public interface ProfileView {
    void showLoading();

    void hideLoading();

    void onGetMyPostResult(List<MyPosting> postings);

    void onErrorLoading(String message);

}
