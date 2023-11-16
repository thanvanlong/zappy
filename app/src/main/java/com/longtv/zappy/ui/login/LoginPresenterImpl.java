package com.longtv.zappy.ui.login;

import com.longtv.zappy.base.BaseCallback;
import com.longtv.zappy.base.BasePresenterImpl;
import com.longtv.zappy.network.ServiceBuilder;
import com.longtv.zappy.network.dto.LoginData;
import com.longtv.zappy.network.dto.LoginRequest;
import com.longtv.zappy.network.dto.SignupRequest;

public class LoginPresenterImpl extends BasePresenterImpl<LoginView> implements LoginPresenter{
    public LoginPresenterImpl(LoginView view) {
        super(view);
    }

    @Override
    public void doLogin(LoginRequest request) {
        ServiceBuilder.getServiceWithoutAuth().login(request).enqueue(new BaseCallback<LoginData>() {
            @Override
            public void onError(String errorCode, String errorMessage) {
                mView.onLoginError(errorMessage);
            }

            @Override
            public void onResponse(LoginData data) {
                mView.onLoginSucess(data);
            }
        });
    }

    @Override
    public void doSignup(SignupRequest request) {
        ServiceBuilder.getServiceWithoutAuth().signup(request).enqueue(new BaseCallback<LoginData>() {
            @Override
            public void onError(String errorCode, String errorMessage) {
                mView.onSignupError(errorMessage);
            }

            @Override
            public void onResponse(LoginData data) {
                mView.onSignupSucess();
            }
        });
    }
}
