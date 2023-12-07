package com.longtv.zappy.ui.login;

import com.longtv.zappy.base.BasePresenter;
import com.longtv.zappy.network.dto.LoginRequest;
import com.longtv.zappy.network.dto.SignupRequest;

public interface LoginPresenter extends BasePresenter {
    public void doLogin(LoginRequest request);
    public void doSignup(SignupRequest request);
    void active(String email, String token);
}
