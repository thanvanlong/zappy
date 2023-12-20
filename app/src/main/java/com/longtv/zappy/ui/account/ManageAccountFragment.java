package com.longtv.zappy.ui.account;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.longtv.zappy.R;
import com.longtv.zappy.base.BaseFragment;
import com.longtv.zappy.base.BasePresenter;
import com.longtv.zappy.common.Constants;
import com.longtv.zappy.network.dto.LoginData;
import com.longtv.zappy.network.dto.Profile;
import com.longtv.zappy.ui.HomeActivity;
import com.longtv.zappy.ui.home.HomeBoxFragment;
import com.longtv.zappy.utils.PrefManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ManageAccountFragment extends BaseFragment<AccountPresenter, HomeActivity> implements AccountView {
    @BindView(R.id.tv_profile_1)
    TextView tvProfile1;
    @BindView(R.id.tv_profile_2)
    TextView tvProfile2;
    @BindView(R.id.tv_profile_3)
    TextView tvProfile3;
    @BindView(R.id.tv_profile_4)
    TextView tvProfile4;
    @BindView(R.id.tv_profile_5)
    TextView tvProfile5;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_profile_first;
    }

    private Map<Integer, Profile> map = new HashMap<>();
    private boolean isFromHome;
    @Override
    public void onPrepareLayout() {
        HomeActivity.getInstance().toggleSetting(0);
        Bundle bundle = getArguments();
        if (bundle != null) {
            isFromHome = bundle.getBoolean(Constants.FROM_HOME);
        }
        List<Profile> profile = PrefManager.getProfileData(getViewContext());
        if (profile != null && profile.size() > 0) {
            for (Profile p : profile) {
                if (p != null) {
                    if (tvProfile1.getText() == null || tvProfile1.getText().toString().isEmpty()) {
                        tvProfile1.setText(p.getNickname());
                        map.put(Integer.valueOf(R.id.ctn_profile_1), p);
                    } else if (tvProfile2.getText() == null || tvProfile2.getText().toString().isEmpty()) {
                        tvProfile2.setText(p.getNickname());
                        map.put(Integer.valueOf(R.id.ctn_profile_2), p);
                    } else if (tvProfile3.getText() == null || tvProfile3.getText().toString().isEmpty()) {
                        tvProfile3.setText(p.getNickname());
                        map.put(Integer.valueOf(R.id.ctn_profile_3), p);
                    } else if (tvProfile4.getText() == null || tvProfile4.getText().toString().isEmpty()) {
                        tvProfile4.setText(p.getNickname());
                        map.put(Integer.valueOf(R.id.ctn_profile_4), p);
                    } else if (tvProfile5.getText() == null || tvProfile5.getText().toString().isEmpty()) {
                        tvProfile5.setText(p.getNickname());
                        map.put(Integer.valueOf(R.id.ctn_profile_5), p);
                    }
                }
            }
        }
    }

    @OnClick({R.id.ctn_profile_1, R.id.ctn_profile_2, R.id.ctn_profile_3, R.id.ctn_profile_4, R.id.ctn_profile_5})
    public void login(View view) {
        Log.e("anth", "login: check");
        switch (view.getId()) {
            case R.id.ctn_profile_1:
                getPresenter().loginProfile(map.get(Integer.valueOf(R.id.ctn_profile_1)).getId());
                break;
            case R.id.ctn_profile_2:
                PrefManager.setProfileId(getViewContext(), 1);
                break;
            case R.id.ctn_profile_3:
                PrefManager.setProfileId(getViewContext(), 2);
                break;
            case R.id.ctn_profile_4:
                PrefManager.setProfileId(getViewContext(), 3);
                break;
            case R.id.ctn_profile_5:
                PrefManager.setProfileId(getViewContext(), 4);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isFromHome) {
            HomeActivity.getInstance().handleBtnBack(false);
        }
    }

    @Override
    public AccountPresenter onCreatePresenter() {
        return new AccountPresenterImpl(this);
    }

    @OnClick({R.id.iv_edt_1, R.id.iv_edt_2, R.id.iv_edt_3, R.id.iv_edt_4})
    public void editProfile(View view) {
        switch (view.getId()) {

        }
        Bundle bundle = new Bundle();
        bundle.putString(Constants.TOOL_BAR, "Edit");

        HomeActivity.getInstance().addOrReplaceFragment(new CreateprofileFragment(), bundle, true, SettingFragment.class.getSimpleName());
    }

    @Override
    public void loginProfileSuccess(LoginData loginData) {
        PrefManager.setProfileId(getViewContext(), loginData.getId());
        PrefManager.setProfileToken(getViewContext(), loginData.getAccessToken());
        HomeActivity.getInstance().setupBottomNav(R.id.navigation_home);
        HomeActivity.getInstance().handleBtnBack(false);
    }
}
