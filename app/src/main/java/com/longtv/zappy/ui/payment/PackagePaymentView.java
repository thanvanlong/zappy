package com.longtv.zappy.ui.payment;

import com.longtv.zappy.base.BaseView;
import com.longtv.zappy.network.dto.DataListDTO;
import com.longtv.zappy.network.dto.PackagePayment;

public interface PackagePaymentView extends BaseView<PackagePaymentPresenter> {
    void onLoadPackagePaymentSuccess(DataListDTO<PackagePayment> data);
    void onLoadPackagePaymentError(String message);
    void onPaymentSuccess();
    void onPaymentError(String message);
}
