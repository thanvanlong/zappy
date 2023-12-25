package com.longtv.zappy.ui.payment;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
import com.longtv.zappy.utils.DialogUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PackagePaymentFragment extends BaseFragment<PackagePaymentPresenter, HomeActivity> implements PackagePaymentView{

    @BindView(R.id.rcv_content)
    RecyclerView rcvContent;
    @BindView(R.id.btn_payment)
    Button btnPayment;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    private List<PackagePayment> packagePaymentList = new ArrayList<>();
    private PackageAdapter adapter;
    private PackagePayment currentPackage;


    private static PackagePaymentFragment instance;
    public static PackagePaymentFragment getInstance() {
        return instance;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_payment;
    }

    @Override
    public void onPrepareLayout() {
        HomeActivity.getInstance().hideBottomBar();
        HomeActivity.getInstance().toggleCoin(View.GONE);
        HomeActivity.getInstance().toggleTopBar(0);
        DialogUtils.showProgressDialog(getViewContext());
        getPresenter().getPackagePayment();
        instance = this;
        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("packageId", Integer.parseInt(packagePaymentList.get(((PackageAdapter) rcvContent.getAdapter()).getPos()).getId()));
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
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onLoadPackagePaymentSuccess(DataListDTO<PackagePayment> data) {
        DialogUtils.dismissProgressDialog(getViewContext());
        packagePaymentList = data.getResults();
        rcvContent.setLayoutManager(new GridLayoutManager(getViewContext(), 2));
        rcvContent.addItemDecoration(new HorizontalItemDecoration(40));
        adapter = new PackageAdapter(data.getResults());
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                currentPackage = packagePaymentList.get(adapter.getPos());
                //convert int to string currency
                tvTotalPrice.setText(formatCurrency(Integer.parseInt(currentPackage.getPrice())));
            }
        });
        rcvContent.setAdapter(adapter);
        currentPackage = packagePaymentList.get(0);
        //convert int to string currency
        tvTotalPrice.setText(formatCurrency(Integer.parseInt(currentPackage.getPrice())));

    }

    public static String formatCurrency(int amount) {
        // Định dạng chuỗi tiền tệ VND
        DecimalFormat currencyFormat = new DecimalFormat("###,### VND");

        return currencyFormat.format(amount);
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
