package com.longtv.zappy.ui.payment;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.longtv.zappy.R;
import com.longtv.zappy.base.BaseFragment;
import com.longtv.zappy.base.BasePresenter;
import com.longtv.zappy.common.view.HorizontalItemDecoration;
import com.longtv.zappy.ui.HomeActivity;

import butterknife.BindView;

public class PackagePaymentFragment extends BaseFragment {

    @BindView(R.id.rcv_content)
    RecyclerView rcvContent;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_payment;
    }

    @Override
    public void onPrepareLayout() {
        rcvContent.setLayoutManager(new GridLayoutManager(getViewContext(), 2));
        rcvContent.addItemDecoration(new HorizontalItemDecoration(40));
        rcvContent.setAdapter(new PackageAdapter());

        HomeActivity.getInstance().hideBottomBar();
        HomeActivity.getInstance().toggleCoin(View.GONE);
    }

    @Override
    public BasePresenter onCreatePresenter() {
        return null;
    }
}
