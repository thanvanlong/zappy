package com.longtv.zappy.ui.login;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.longtv.zappy.R;
import com.longtv.zappy.base.BaseActivity;
import com.longtv.zappy.base.BasePresenter;
import com.longtv.zappy.common.Constants;
import com.longtv.zappy.ui.HomeActivity;
import com.longtv.zappy.utils.PrefManager;

public class SplashActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void onPrepareLayout() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.e("anth", "onComplete: not");
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        PrefManager.saveToken(getViewContext(), token);

                        Log.e("anth", "onComplete: " + token);
                        if (PrefManager.isFirstStart(getViewContext())) {
                            gotoOnBoardingActivity();
                        } else if (!PrefManager.isLogged(getViewContext())) {
                            gotoLogin(token);
                        } else {
                            gotoHome();
                        }
                    }
                });
    }

    private void gotoOnBoardingActivity() {
        Intent intent = new Intent(getViewContext(), OnboardingActivity.class);
        startActivity(intent);
        finish();
    }

    private void gotoLogin(String token) {
        Intent intent = new Intent(getViewContext(), LoginActivity.class);
        intent.putExtra(Constants.DATA, token);
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
