package com.bintang.banyan.Activity.DetailPost;

public interface AddCommentView {
    void showProgress();

    void hideProgress();

    void onRequestSuccess(String message);

    void onRequestError(String message);

}
