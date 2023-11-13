package com.longtv.zappy.ui.login;

import com.longtv.zappy.R;
import com.longtv.zappy.base.BaseActivity;

public class LoginActivity extends BaseActivity<LoginPresenter> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void onPrepareLayout() {
        addFragment(R.id.container, new LoginFragment(), null, true, LoginFragment.class.getSimpleName());
    }

    @Override
    public LoginPresenter onCreatePresenter() {
        return null;
    }
}
