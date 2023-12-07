package com.longtv.zappy.ui.login;

import android.widget.Toast;

import com.longtv.zappy.base.BaseCallback;
import com.longtv.zappy.base.BasePresenterImpl;
import com.longtv.zappy.network.ServiceBuilder;
import com.longtv.zappy.network.dto.LoginData;
import com.longtv.zappy.network.dto.LoginRequest;
import com.longtv.zappy.network.dto.SignupRequest;
import com.longtv.zappy.utils.DialogUtils;

public class LoginPresenterImpl extends BasePresenterImpl<LoginView> implements LoginPresenter{
    public LoginPresenterImpl(LoginView view) {
        super(view);
    }

    @Override
    public void doLogin(LoginRequest request) {
        DialogUtils.showProgressDialog(getViewContext());
        ServiceBuilder.getService().login(request).enqueue(new BaseCallback<LoginData>() {
            @Override
            public void onError(String errorCode, String errorMessage) {
                DialogUtils.dismissProgressDialog(getViewContext());
                mView.onLoginError(errorMessage);
            }

            @Override
            public void onResponse(LoginData data) {
                DialogUtils.dismissProgressDialog(getViewContext());
                mView.onLoginSucess(data);
            }
        });
    }

    @Override
    public void doSignup(SignupRequest request) {
        DialogUtils.showProgressDialog(getViewContext());
        ServiceBuilder.getService().signup(request).enqueue(new BaseCallback<Object>() {
            @Override
            public void onError(String errorCode, String errorMessage) {
                DialogUtils.dismissProgressDialog(getViewContext());
                mView.onSignupError(errorMessage);
            }

            @Override
            public void onResponse(Object data) {
                DialogUtils.dismissProgressDialog(getViewContext());
                mView.onSignupSucess();
            }
        });
    }

    @Override
    public void active(String email, String token) {
        ServiceBuilder.getServiceWithoutAuth().active(email, token).enqueue(new BaseCallback<Object>() {
            @Override
            public void onError(String errorCode, String errorMessage) {
                Toast.makeText(getViewContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Object data) {
                Toast.makeText(getViewContext(), "Active thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
