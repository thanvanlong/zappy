package com.longtv.zappy.base;

public interface BaseView<P extends BasePresenter> {

    void onPrepareLayout();
    P getPresenter();
    P onCreatePresenter();
    BaseActivity getViewContext();
}
