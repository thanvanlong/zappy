package com.longtv.zappy.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.longtv.zappy.R;
import com.longtv.zappy.base.BaseFragment;
import com.longtv.zappy.common.Constants;
import com.longtv.zappy.network.dto.LoginData;
import com.longtv.zappy.network.dto.LoginRequest;
import com.longtv.zappy.ui.HomeActivity;
import com.longtv.zappy.ui.account.AccountActivity;
import com.longtv.zappy.ui.account.ManageAccountFragment;
import com.longtv.zappy.utils.PrefManager;

import butterknife.BindView;


public class LoginFragment extends BaseFragment<LoginPresenter, LoginActivity> implements LoginView{
    @BindView(R.id.edt_email_login)
    protected EditText edtEmail;
    @BindView(R.id.edt_password_login)
    protected  EditText edtPassword;
    @BindView(R.id.btn_login)
    protected TextView btnLogin;
    @BindView(R.id.tv_link_create)
    protected TextView tvLink;
    @BindView(R.id.iv_show_pass)
    protected ImageView ivShowPass;
    private String token;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void onPrepareLayout() {
        token = PrefManager.getToken(getViewContext());
        setListener();
    }

    @Override
    public LoginPresenter onCreatePresenter() {
        return new LoginPresenterImpl(this);
    }

    public void setListener(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtEmail.getText().toString().isEmpty() || edtPassword.getText().toString().isEmpty()) {
                    Toast.makeText(getViewContext(), "Vui lòng nhập đẩy đủ thông tin để đăng nhập", Toast.LENGTH_SHORT).show();
                } else {
                    LoginRequest request = new LoginRequest(edtEmail.getText().toString(), edtPassword.getText().toString(), token);
                    getViewContext().hideSoftKeyboard();
                    getPresenter().doLogin(request);
                }
            }
        });
        tvLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBaseActivity().addFragment(R.id.container, new SignupPhoneFragment(), true, SignupPhoneFragment.class.getSimpleName());
            }
        });

        ivShowPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtPassword.getTransformationMethod() == null) {
                    ivShowPass.setImageResource(R.drawable.ic_eye_close);
                    edtPassword.setTransformationMethod(new PasswordTransformationMethod());
                } else {
                    edtPassword.setTransformationMethod(null);
                    ivShowPass.setImageResource(R.drawable.ic_eye_open);
                }
            }
        });


    }

    @Override
    public void onLoginSucess(LoginData data) {
        PrefManager.setLogin(getViewContext(), true);
        PrefManager.saveAccessTokenInfo(getViewContext(), data.getAccessToken());
        PrefManager.saveUserData(getViewContext(), new Gson().toJson(data));
        PrefManager.saveProfileData(getViewContext(), data.getProfiles());
        if (PrefManager.getPassword(getViewContext()).isEmpty()) {
            getViewContext().addFragment(R.id.container, new ScreenPasswordFragment(), true, ScreenPasswordFragment.class.getSimpleName());
        } else {
            startActivity(new Intent(getViewContext(), HomeActivity.class));
            getViewContext().finish();
        }
    }

    @Override
    public void onLoginError(String mess) {
        Toast.makeText(getViewContext(), mess, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSignupSucess() {

    }

    @Override
    public void onSignupError(String mess) {

    }
}
