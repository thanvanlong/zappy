package com.longtv.zappy.ui.login;

import com.longtv.zappy.base.BaseView;
import com.longtv.zappy.network.dto.LoginData;

public interface LoginView extends BaseView<LoginPresenter> {
    public void onLoginSucess(LoginData data);
    public void onLoginError(String mess);
    public void onSignupSucess();
    public void onSignupError(String mess);
}
