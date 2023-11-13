package com.longtv.zappy.ui.login;

import com.longtv.zappy.R;
import com.longtv.zappy.base.BaseFragment;

public class LoginFragment extends BaseFragment<LoginPresenter, LoginActivity> implements LoginView{
    @Override
    public int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void onPrepareLayout() {

    }

    @Override
    public LoginPresenter onCreatePresenter() {
        return new LoginPresenterImpl(this);
    }
}
