package com.longtv.zappy.ui.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.longtv.zappy.R;
import com.longtv.zappy.base.BaseActivity;
import com.longtv.zappy.common.Constants;
import com.longtv.zappy.network.dto.LoginData;
import com.longtv.zappy.utils.PrefManager;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginView{
    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void onPrepareLayout() {
        Uri data = getIntent().getData();
        if (data != null && data.getQuery().contains("email") && data.getQueryParameter("accessToken") != null) {
            getPresenter().active(data.getQueryParameter("email"), data.getQueryParameter("accessToken"));
        }
        LoginFragment fragment = new LoginFragment();
        addFragment(R.id.container, fragment, null, true, LoginFragment.class.getSimpleName());
    }

    @Override
    public LoginPresenter onCreatePresenter() {
        return new LoginPresenterImpl(this);
    }

    @Override
    public void onLoginSucess(LoginData data) {

    }

    @Override
    public void onLoginError(String mess) {

    }

    @Override
    public void onSignupSucess() {

    }

    @Override
    public void onSignupError(String mess) {

    }
}
