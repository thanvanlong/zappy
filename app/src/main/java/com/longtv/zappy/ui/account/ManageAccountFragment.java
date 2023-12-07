package com.longtv.zappy.ui.account;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.longtv.zappy.R;
import com.longtv.zappy.base.BaseFragment;
import com.longtv.zappy.base.BasePresenter;
import com.longtv.zappy.common.Constants;
import com.longtv.zappy.ui.HomeActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class ManageAccountFragment extends BaseFragment {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_profile_first;
    }

    @Override
    public void onPrepareLayout() {
        HomeActivity.getInstance().toggleSetting(0);
    }

    @Override
    public BasePresenter onCreatePresenter() {
        return null;
    }

    @OnClick({R.id.iv_edt_1, R.id.iv_edt_2, R.id.iv_edt_3, R.id.iv_edt_4})
    public void editProfile(View view) {
        switch (view.getId()) {

        }
        Bundle bundle = new Bundle();
        bundle.putString(Constants.TOOL_BAR, "Edit");

        HomeActivity.getInstance().addOrReplaceFragment(new CreateprofileFragment(), bundle, true, SettingFragment.class.getSimpleName());
    }
}
