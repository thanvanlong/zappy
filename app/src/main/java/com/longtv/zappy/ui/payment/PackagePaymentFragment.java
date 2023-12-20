package com.longtv.zappy.ui.payment;

import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.longtv.zappy.R;
import com.longtv.zappy.base.BaseFragment;
import com.longtv.zappy.base.BasePresenter;
import com.longtv.zappy.common.view.HorizontalItemDecoration;
import com.longtv.zappy.network.dto.DataListDTO;
import com.longtv.zappy.network.dto.PackagePayment;
import com.longtv.zappy.ui.HomeActivity;

import butterknife.BindView;

public class PackagePaymentFragment extends BaseFragment<PackagePaymentPresenter, HomeActivity> implements PackagePaymentView{

    @BindView(R.id.rcv_content)
    RecyclerView rcvContent;
    @BindView(R.id.btn_payment)
    Button btnPayment;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_payment;
    }

    @Override
    public void onPrepareLayout() {
        HomeActivity.getInstance().hideBottomBar();
        HomeActivity.getInstance().toggleCoin(View.GONE);
        getPresenter().getPackagePayment();

        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("packageId", 10);
                jsonObject.addProperty("paymentMethod", 1);
                getPresenter().doPayment(jsonObject);
            }
        });
    }

    @Override
    public PackagePaymentPresenter onCreatePresenter() {
        return new PackagePaymentPresenterImpl(this);
    }

    @Override
    public void onLoadPackagePaymentSuccess(DataListDTO<PackagePayment> data) {
        rcvContent.setLayoutManager(new GridLayoutManager(getViewContext(), 2));
        rcvContent.addItemDecoration(new HorizontalItemDecoration(40));
        rcvContent.setAdapter(new PackageAdapter(data.getResults()));

    }

    @Override
    public void onLoadPackagePaymentError(String message) {

    }

    @Override
    public void onPaymentSuccess() {

    }

    @Override
    public void onPaymentError(String message) {

    }
}
