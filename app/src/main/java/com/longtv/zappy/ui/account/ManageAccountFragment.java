package com.longtv.zappy.ui.account;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.longtv.zappy.R;
import com.longtv.zappy.base.BaseFragment;
import com.longtv.zappy.base.BasePresenter;
import com.longtv.zappy.common.Constants;
import com.longtv.zappy.network.dto.Content;
import com.longtv.zappy.network.dto.LoginData;
import com.longtv.zappy.network.dto.Profile;
import com.longtv.zappy.ui.HomeActivity;
import com.longtv.zappy.ui.film.mediaplayer.MediaPlayerFragment;
import com.longtv.zappy.ui.home.HomeBoxFragment;
import com.longtv.zappy.utils.PrefManager;
import com.longtv.zappy.utils.StringUtils;

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
    @BindView(R.id.ctn_profile_1)
    ConstraintLayout ctnProfile1;
    @BindView(R.id.ctn_profile_2)
    ConstraintLayout ctnProfile2;
    @BindView(R.id.ctn_profile_3)
    ConstraintLayout ctnProfile3;
    @BindView(R.id.ctn_profile_4)
    ConstraintLayout ctnProfile4;
    @BindView(R.id.ctn_profile_5)
    ConstraintLayout ctnProfile5;
    @BindView(R.id.iv_add_2)
    ImageView ivAdd2;
    @BindView(R.id.iv_add_3)
    ImageView ivAdd3;
    @BindView(R.id.iv_add_4)
    ImageView ivAdd4;
    @BindView(R.id.iv_add_5)
    ImageView ivAdd5;
    @BindView(R.id.tv_time_on)
    TextView tvTimeOn;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_profile_first;
    }

    private Map<Integer, Profile> map = new HashMap<>();
    private boolean isFromHome;
    private String data;
    @Override
    public void onPrepareLayout() {
        HomeActivity.getInstance().toggleSetting(0);
        Bundle bundle = getArguments();
        if (bundle != null) {
            isFromHome = bundle.getBoolean(Constants.FROM_HOME);
            data = bundle.getString(Constants.DATA);
        }
        getPresenter().getMe();
    }


    @OnClick({R.id.ctn_profile_1, R.id.ctn_profile_2_2, R.id.ctn_profile_3_3, R.id.ctn_profile_4_4, R.id.ctn_profile_5_5})
    public void login(View view) {
        Log.e("anth", "login: check");
        if (map.get(Integer.valueOf(view.getId())) == null || !map.containsKey(Integer.valueOf(view.getId()))) {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.TOOL_BAR, "Create");
            HomeActivity.getInstance().addOrReplaceFragment(new CreateprofileFragment(), bundle, true, SettingFragment.class.getSimpleName());
        } else {
            getPresenter().loginProfile(map.get(Integer.valueOf(view.getId())).getId());
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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                HomeActivity.getInstance().handleBtnBack(false);
                HomeActivity.getInstance().toggleCoin(0);
                HomeActivity.getInstance().toggleSetting(1);
                Profile profile = PrefManager.getProfileData(getViewContext()).stream().filter(p -> p.getId() == loginData.getId()).findFirst().get();
                HomeActivity.getInstance().countDown(profile.getTimeOnScreen());
                HomeActivity.getInstance().setupBottomNav(R.id.navigation_home);
                if (data != null && !data.isEmpty() && data.contains("type")) {
                    Uri uri = Uri.parse(data);
                    String type = uri.getQueryParameter("type");
                    String id = uri.getQueryParameter("id");
                    switch (type) {
                        case "film":
                            Bundle bundle = new Bundle();
                            bundle.putString(Constants.TOOL_BAR, "Media");
                            Content content = new Content();
                            content.setId(Integer.parseInt(id));
                            bundle.putSerializable(Constants.DATA, content);
                            HomeActivity.getInstance().addOrReplaceFragment(new MediaPlayerFragment(), bundle, true, MediaPlayerFragment.class.getSimpleName());
                            break;
                    }
                }
            }
        }, 1000);
    }

    @Override
    public void getMeSuccess(LoginData loginData) {
        PrefManager.saveGold(getViewContext(), loginData.getGolds());
        HomeActivity.getInstance().setGold(loginData.getGolds());
        List<Profile> profile = PrefManager.getProfileData(getViewContext());
        Map<Integer, Profile> map = new HashMap<>();
        if (profile != null && profile.size() > 0) {
            for (Profile p : profile) {
//                if ()
                map.put(p.getOrder(), p);
            }

            if (map.get(1) != null) {
                tvProfile1.setText(map.get(1).getNickname());
                ctnProfile1.setVisibility(View.VISIBLE);
                Log.e("anth", "getMeSuccess: " + map.get(1).getTimeOnScreen());
                tvTimeOn.setText(StringUtils.convertMillisecondToHMS(map.get(1).getTimeOnScreen() * 1000));
                this.map.put(ctnProfile1.getId(), map.get(1));
            }

            if (map.get(2) != null) {
                tvProfile2.setText(map.get(2).getNickname());
                ctnProfile2.setVisibility(View.VISIBLE);
                this.map.put(R.id.ctn_profile_2_2, map.get(2));
            } else {
                ctnProfile2.setVisibility(View.GONE);
                ivAdd2.setVisibility(View.VISIBLE);
            }

            if (map.get(3) != null) {
                tvProfile3.setText(map.get(3).getNickname());
                ctnProfile3.setVisibility(View.VISIBLE);
                this.map.put(ctnProfile3.getId(), map.get(3));
            } else {
                ctnProfile3.setVisibility(View.GONE);
                ivAdd3.setVisibility(View.VISIBLE);
            }

            if (map.get(4) != null) {
                tvProfile4.setText(map.get(4).getNickname());
                ctnProfile4.setVisibility(View.VISIBLE);
                this.map.put(ctnProfile4.getId(), map.get(4));
            } else {
                ctnProfile4.setVisibility(View.GONE);
                ivAdd4.setVisibility(View.VISIBLE);
            }

            if (map.get(5) != null) {
                tvProfile5.setText(map.get(5).getNickname());
                ctnProfile5.setVisibility(View.VISIBLE);
                this.map.put(ctnProfile5.getId(), map.get(5));
            } else {
                ctnProfile5.setVisibility(View.GONE);
                ivAdd5.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void saveProfileSuccess(Profile profile) {

    }
}
