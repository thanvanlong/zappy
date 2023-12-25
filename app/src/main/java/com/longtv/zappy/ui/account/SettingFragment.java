package com.longtv.zappy.ui.account;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.longtv.zappy.R;
import com.longtv.zappy.base.BaseFragment;
import com.longtv.zappy.base.BasePresenter;
import com.longtv.zappy.common.Constants;
import com.longtv.zappy.ui.HomeActivity;
import com.longtv.zappy.ui.payment.PackagePaymentFragment;
import com.longtv.zappy.ui.stats.StatsScreenFragment;
import com.longtv.zappy.utils.PrefManager;

import butterknife.BindView;

public class SettingFragment extends BaseFragment {
    @BindView(R.id.ctn_stats)
    ConstraintLayout ctnStats;
    @BindView(R.id.ctn_payment)
    LinearLayout ctnPayment;
    @BindView(R.id.tv_account)
    TextView tvAccount;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_profile_second;
    }

    @Override
    public void onPrepareLayout() {
        tvAccount.setText(PrefManager.getUserData(getViewContext()).getUsername());
        ctnStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.TOOL_BAR, "Stats");
                HomeActivity.getInstance().addOrReplaceFragment(new StatsScreenFragment(), bundle, true, StatsScreenFragment.class.getSimpleName());
            }
        });

        ctnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.TOOL_BAR, "Payment");
                HomeActivity.getInstance().addOrReplaceFragment(new PackagePaymentFragment(), bundle, true, PackagePaymentFragment.class.getSimpleName());
            }
        });
    }

    @Override
    public BasePresenter onCreatePresenter() {
        return null;
    }
}
