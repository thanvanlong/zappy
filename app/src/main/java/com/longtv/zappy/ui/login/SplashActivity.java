package com.longtv.zappy.ui.login;

import android.content.Intent;
import android.os.Handler;

import com.longtv.zappy.R;
import com.longtv.zappy.base.BaseActivity;
import com.longtv.zappy.base.BasePresenter;
import com.longtv.zappy.ui.HomeActivity;
import com.longtv.zappy.utils.PrefManager;

public class SplashActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void onPrepareLayout() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (PrefManager.isFirstStart(getViewContext())) {
                    gotoOnBoardingActivity();
                } else if (!PrefManager.isLogged(getViewContext())) {
                    gotoLogin();
                } else {
                    gotoHome();
                }
            }
        }, 1500);
    }

    private void gotoOnBoardingActivity() {
        Intent intent = new Intent(getViewContext(), OnboardingActivity.class);
        startActivity(intent);
        finish();
    }

    private void gotoLogin() {
        Intent intent = new Intent(getViewContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void gotoHome() {
        Intent intent = new Intent(getViewContext(), HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public BasePresenter onCreatePresenter() {
        return null;
    }
}
