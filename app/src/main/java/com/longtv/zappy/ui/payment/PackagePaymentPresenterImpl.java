package com.longtv.zappy.ui.payment;

import android.content.Intent;
import android.net.Uri;

import com.google.gson.JsonObject;
import com.longtv.zappy.base.BaseCallback;
import com.longtv.zappy.base.BasePresenterImpl;
import com.longtv.zappy.network.ServiceBuilder;
import com.longtv.zappy.network.dto.DataListDTO;
import com.longtv.zappy.network.dto.PackagePayment;

public class PackagePaymentPresenterImpl extends BasePresenterImpl<PackagePaymentView> implements PackagePaymentPresenter {

    public PackagePaymentPresenterImpl(PackagePaymentView view) {
        super(view);
    }

    @Override
    public void getPackagePayment() {
        ServiceBuilder.getService().getPackage().enqueue(new BaseCallback<DataListDTO<PackagePayment>>() {
            @Override
            public void onError(String errorCode, String errorMessage) {

            }

            @Override
            public void onResponse(DataListDTO<PackagePayment> data) {
                mView.onLoadPackagePaymentSuccess(data);
            }
        });
    }

    @Override
    public void doPayment(JsonObject jsonObject) {
        ServiceBuilder.getServicePayment().doPayment(jsonObject).enqueue(new BaseCallback<Object>() {
            @Override
            public void onError(String errorCode, String errorMessage) {

            }

            @Override
            public void onResponse(Object data) {
                if (data instanceof String) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse((String) data));
                    getViewContext().startActivity(intent);
                }
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.longtv.zappy"));
//                getViewContext().startActivity(intent);

            }
        });
    }
}
