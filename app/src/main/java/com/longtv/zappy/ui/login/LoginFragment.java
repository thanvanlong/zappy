package com.longtv.zappy.ui.login;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.longtv.zappy.R;
import com.longtv.zappy.base.BaseFragment;
import com.longtv.zappy.network.dto.LoginData;
import com.longtv.zappy.network.dto.LoginRequest;

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

    @Override
    public int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void onPrepareLayout() {
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
                LoginRequest request = new LoginRequest(edtEmail.getText().toString(), edtPassword.getText().toString());
                getPresenter().doLogin(request);
            }
        });
        tvLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBaseActivity().addFragment(R.id.container, new SignupPhoneFragment(), true, SignupPhoneFragment.class.getSimpleName());
            }
        });
    }

    @Override
    public void onLoginSucess(LoginData data) {
        Log.e("Login", "Failure");
    }

    @Override
    public void onLoginError(String mess) {
        Log.e("Login", mess);
    }

    @Override
    public void onSignupSucess() {

    }

    @Override
    public void onSignupError(String mess) {

    }
}
