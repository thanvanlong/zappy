package com.longtv.zappy.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.longtv.zappy.R;
import com.longtv.zappy.ui.account.AccountActivity;
import com.longtv.zappy.ui.account.ManageAccountFragment;
import com.longtv.zappy.ui.login.OnboardingActivity;
import com.longtv.zappy.base.BaseActivity;
import com.longtv.zappy.base.BaseFragment;
import com.longtv.zappy.base.BasePresenter;
import com.longtv.zappy.ui.film.HomeBoxFilmFragment;
import com.longtv.zappy.ui.home.HomeBoxFragment;
import com.longtv.zappy.ui.login.ScreenPasswordFragment;
import com.longtv.zappy.ui.music.HomeBoxMusicFragment;
import com.longtv.zappy.ui.music.detail.HomeBoxMusicPlayerFragment;
import com.longtv.zappy.ui.story.HomeBoxStoryFragment;
import com.longtv.zappy.utils.DeviceUtils;

import java.util.List;

import butterknife.BindView;

public class HomeActivity extends BaseActivity {
    @BindView(R.id.bottom_nav)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.container_bottom_nav)
    ConstraintLayout containerBottomNav;
    @BindView(R.id.tool_bar)
    RelativeLayout mToolBar;

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
//        hideBottomBar();
//        toggleTopBar(View.GONE);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_learning:
                        break;
                    case R.id.navigation_story:
                        addOrReplaceFragment(new HomeBoxStoryFragment(), null, false, HomeBoxStoryFragment.class.getSimpleName());
                        break;
                    case R.id.navigation_home:
                        addOrReplaceFragment(new ManageAccountFragment(), null, false, HomeBoxFragment.class.getSimpleName());
                        break;
                    case R.id.navigation_video:
                        addOrReplaceFragment(new HomeBoxFilmFragment(), null,  false, HomeBoxFilmFragment.class.getSimpleName());
                        break;
                    case R.id.navigation_music:
                        addOrReplaceFragment(new HomeBoxMusicFragment(), null, false, HomeBoxMusicFragment.class.getSimpleName());
                        break;
                }

                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
    }

    public void addOrReplaceFragment(BaseFragment fragment, Bundle args, boolean addToBackStack, String tagName) {
        Log.e("anth", "addOrReplaceFragment: " + tagName);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (args != null) {
            fragment.setArguments(args);
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
    }

    public void toggleTopBar(int visibility) {
        mToolBar.setVisibility(visibility);
    }

    public void hideBottomBar() {
        if (containerBottomNav.getVisibility() == View.VISIBLE) {
            Animation slide_down = AnimationUtils.loadAnimation(getViewContext(), R.anim.slide_down);
            containerBottomNav.startAnimation(slide_down);
            containerBottomNav.setVisibility(View.GONE);
        }
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