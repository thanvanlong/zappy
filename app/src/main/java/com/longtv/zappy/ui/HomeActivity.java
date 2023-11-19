package com.longtv.zappy.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.longtv.zappy.R;
import com.longtv.zappy.base.BaseActivity;
import com.longtv.zappy.base.BaseFragment;
import com.longtv.zappy.base.BasePresenter;
import com.longtv.zappy.ui.film.HomeBoxFilmFragment;
import com.longtv.zappy.ui.home.HomeBoxFragment;
import com.longtv.zappy.ui.music.HomeBoxMusicFragment;
import com.longtv.zappy.ui.story.HomeBoxStoryFragment;

import java.util.List;

import butterknife.BindView;

public class HomeActivity extends BaseActivity {
    @BindView(R.id.bottom_nav)
    BottomNavigationView bottomNavigationView;
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
                        addOrReplaceFragment(new HomeBoxFragment(), null, false, HomeBoxFragment.class.getSimpleName());
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

    private void addOrReplaceFragment(BaseFragment fragment, Bundle args, boolean addToBackStack, String tagName) {
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

    @Override
    public BasePresenter onCreatePresenter() {
        return null;
    }
}