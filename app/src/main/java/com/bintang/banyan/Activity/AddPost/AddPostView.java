package com.bintang.banyan.Activity.AddPost;

public interface AddPostView {
    void showProgress();

    void hideProgress();

    void onRequestSuccess(String message);

    void onRequestError(String message);

}
