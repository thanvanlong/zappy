package com.longtv.zappy.ui.payment;

import com.google.gson.JsonObject;
import com.longtv.zappy.base.BasePresenter;

public interface PackagePaymentPresenter extends BasePresenter {
    void getPackagePayment();
    void doPayment(JsonObject jsonObject);
}