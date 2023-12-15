package com.longtv.zappy.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.longtv.zappy.R;
import com.longtv.zappy.common.Constants;
import com.longtv.zappy.common.dialog.InfoYesNoDialog;
import com.longtv.zappy.common.dialog.SuccessDialog;
import com.longtv.zappy.ui.account.ManageAccountFragment;
import com.longtv.zappy.ui.account.SettingFragment;
import com.longtv.zappy.ui.film.mediaplayer.MediaPlayerFragment;
import com.longtv.zappy.ui.login.OnboardingActivity;
import com.longtv.zappy.base.BaseActivity;
import com.longtv.zappy.base.BaseFragment;
import com.longtv.zappy.base.BasePresenter;
import com.longtv.zappy.ui.film.HomeBoxFilmFragment;
import com.longtv.zappy.ui.home.HomeBoxFragment;
import com.longtv.zappy.ui.music.HomeBoxMusicFragment;
import com.longtv.zappy.ui.payment.PackagePaymentFragment;
import com.longtv.zappy.ui.stats.StatsScreenFragment;
import com.longtv.zappy.ui.story.HomeBoxStoryFragment;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;
import com.longtv.zappy.ui.story.ReadingStoryFragment;
import com.longtv.zappy.utils.DeviceUtils;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import butterknife.BindView;

public class HomeActivity extends BaseActivity {
    @BindView(R.id.bottom_nav)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.container_bottom_nav)
    ConstraintLayout containerBottomNav;
    @BindView(R.id.tool_bar)
    RelativeLayout mToolBar;
    @BindView(R.id.container_coin)
    RelativeLayout mContainerCoin;
    @BindView(R.id.btn_back)
    ImageView ivBack;
    @BindView(R.id.container_btn_back)
    LinearLayout ctnBtnBack;
    @BindView(R.id.txt_tool_title)
    TextView tvTitle;

    @BindView(R.id.iv_setting)
    ImageView ivSetting;
    @BindView(R.id.layout_coin)
    RelativeLayout layoutCoin;

    private Stack<String> titles = new Stack<>();


    private static HomeActivity mInstance;
    public static synchronized void setmInstance(HomeActivity mInstance) {
        HomeActivity.mInstance = mInstance;
    }

    public static HomeActivity getInstance() {
        return mInstance;
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onPrepareLayout() {
        setmInstance(this);

        AppCenter.start(getApplication(), "9d04f849-66d0-41c7-8eae-13aa24277266",
                Analytics.class, Crashes.class);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_learning:
                        break;
                    case R.id.navigation_story:
                        Bundle str = new Bundle();
                        str.putString(Constants.TOOL_BAR, "Story");
                        addOrReplaceFragment(new HomeBoxStoryFragment(), str, false, HomeBoxStoryFragment.class.getSimpleName());
                        break;
                    case R.id.navigation_home:
                        Bundle home = new Bundle();
                        home.putString(Constants.TOOL_BAR, "Home");
                        addOrReplaceFragment(new StatsScreenFragment(), home, false, HomeBoxFragment.class.getSimpleName());
                        break;
                    case R.id.navigation_video:
                        Bundle vod = new Bundle();
                        vod.putString(Constants.TOOL_BAR, "Video");
                        addOrReplaceFragment(new HomeBoxFilmFragment(), vod,  false, HomeBoxFilmFragment.class.getSimpleName());
                        break;
                    case R.id.navigation_music:
                        Bundle music = new Bundle();
                        music.putString(Constants.TOOL_BAR, "Music");
                        addOrReplaceFragment(new HomeBoxMusicFragment(), music, false, HomeBoxMusicFragment.class.getSimpleName());
                        break;
                }

                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                Fragment topFragment = manager.findFragmentById(R.id.container_fragment);

                if (Constants.topFragment.contains(topFragment.getTag())) {
                    Bundle str = new Bundle();
                    str.putString(Constants.TOOL_BAR, "Account");
                    addOrReplaceFragment(new ManageAccountFragment(), str,  true, ManageAccountFragment.class.getSimpleName());
                } else {
                    onBackPressed();
                }
            }
        });

        ivSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle str = new Bundle();
                str.putString(Constants.TOOL_BAR, "Setting");
                addOrReplaceFragment(new SettingFragment(), str, true, SettingFragment.class.getSimpleName());
            }
        });
    }

    public void toggleSetting(int show) {
        if (show >= 0) {
            ivSetting.setVisibility(show == 0? View.VISIBLE : View.GONE);
            layoutCoin.setVisibility(show == 0? View.GONE : View.VISIBLE);
        }
    }

    public Fragment getVisibleFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if(fragments != null){
            for(Fragment fragment : fragments){
                if(fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }

    public void addOrReplaceFragment(BaseFragment fragment, Bundle args, boolean addToBackStack, String tagName) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (args != null) {
            fragment.setArguments(args);
        }

        if (args != null && args.getString(Constants.TOOL_BAR) != null) {
            tvTitle.setText(args.getString(Constants.TOOL_BAR));
            titles.add(args.getString(Constants.TOOL_BAR));
        }

        List<Fragment> fragments = fragmentManager.getFragments();

        if (fragments.size() == 0) {
            addFragment(R.id.container_fragment, fragment, addToBackStack, tagName);
        } else {
            Fragment findFragment = fragmentManager.findFragmentByTag(tagName);
            if (findFragment == null) {
                addFragment(R.id.container_fragment, fragment, addToBackStack, tagName);
            } else {
                for (Fragment frg : fragmentManager.getFragments()) {
                    if (frg == null) {
                        continue;
                    }
                    if (frg instanceof HomeBoxFragment || frg instanceof HomeBoxMusicFragment || frg instanceof HomeBoxFilmFragment || frg instanceof HomeBoxStoryFragment){
                        transaction.hide(frg);
                    }

                    FragmentManager fm = getSupportFragmentManager();
                    for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                        fm.popBackStack();
                    }
                }
                transaction.show(findFragment);
            }
        }

        if (Constants.topFragment.contains(tagName)) {
            toggleTopBar(View.VISIBLE);
            toggleCoin(0);
            showBottomBar();
            handleBtnBack(false);
        } else {
            toggleCoin(0);
            hideBottomBar();
            handleBtnBack(true);
        }
    }

    public void toggleTopBar(int visibility) {
        mToolBar.setVisibility(visibility);
    }

    @SuppressLint("ResourceAsColor")
    public void handleBtnBack(boolean needBack) {
        if (needBack) {
            ivBack.setImageResource(R.drawable.ic_arrow_back_deep_purple);
            ivBack.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.bg_avatar_v2));
        } else {
            ivBack.setImageResource(R.drawable.ic_baby);
            ivBack.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.bg_avatar));
        }
    }

    public void hideBottomBar() {
        if (containerBottomNav.getVisibility() == View.VISIBLE) {
            Animation slide_down = AnimationUtils.loadAnimation(getViewContext(), R.anim.slide_down);
            containerBottomNav.startAnimation(slide_down);
            containerBottomNav.setVisibility(View.GONE);
        }
    }

    public void showBottomBar() {
        if (containerBottomNav.getVisibility() != View.VISIBLE) {
            Animation slide_down = AnimationUtils.loadAnimation(getViewContext(), R.anim.slide_up);
            containerBottomNav.startAnimation(slide_down);
            containerBottomNav.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager manager = getSupportFragmentManager();
//        if (manager.getFragments().size() == 2) {
//            InfoYesNoDialog infoYesNoDialog = new InfoYesNoDialog();
//            infoYesNoDialog.init(getViewContext(), "");
//            infoYesNoDialog.setListener(new SuccessDialog.ItemClickListener() {
//                @Override
//                public void btnYesClick() {
//
//                }
//
//                @Override
//                public void btnNoClick() {
//
//                }
//            });
//
//            infoYesNoDialog.show(getSupportFragmentManager(), "");
//            return;
//        }
        super.onBackPressed();
        titles.pop();
        tvTitle.setText(titles.peek());
        Fragment topFragment = manager.findFragmentById(R.id.container_fragment);


        if (Constants.topFragment.contains(topFragment.getTag())) {
            toggleSetting(1);
            showBottomBar();
            toggleTopBar(0);
            handleBtnBack(false);
        }
    }

    public void toggleCoin(int visibility) {
        mContainerCoin.setVisibility(visibility);
    }

    @Override
    public BasePresenter onCreatePresenter() {
        return null;
    }

    private void startWalkthroughScreensActivity() {
        Intent intent = new Intent(this, OnboardingActivity.class);
        startActivity(intent);
        finish();
    }
}