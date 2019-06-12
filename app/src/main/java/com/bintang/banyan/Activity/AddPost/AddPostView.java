package com.bintang.banyan.Activity.AddPost;

public interface AddPostView {
    void showProgress();

    void hideProgress();

    void onRequestPostSuccess(String message);

    void onRequestPostError(String message);

}
