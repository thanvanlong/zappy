package com.longtv.zappy.ui.login;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.longtv.zappy.R;
import com.longtv.zappy.base.BaseFragment;
import com.longtv.zappy.network.dto.LoginData;
import com.longtv.zappy.network.dto.SignupRequest;
import com.longtv.zappy.utils.AuthUtils;

import butterknife.BindView;

public class SignupFragment extends BaseFragment<LoginPresenter, LoginActivity> implements LoginView {
    private String phoneNumber;
    @BindView(R.id.edt_firstname_signup)
    protected EditText edtFirstName;
    @BindView(R.id.edt_lastname_signup)
    protected EditText edtLastName;
    @BindView(R.id.edt_email_signup)
    protected EditText edtEmail;
    @BindView(R.id.edt_password_signup)
    protected EditText edtPassword;
    @BindView(R.id.edt_confirmpassword_signup)
    protected EditText edtConfirmPassword;
    @BindView(R.id.btn_signup)
    protected Button btnSignup;
    @BindView(R.id.iv_back_signup)
    protected ImageView ivBack;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_signup;
    }

    @Override
    public void onPrepareLayout() {
//        Bundle receivedBundle = getArguments();
//        phoneNumber = receivedBundle.getString("phoneNumber");
        setListener();
    }

    @Override
    public LoginPresenter onCreatePresenter() {
        return new LoginPresenterImpl(this);
    }

    public void setListener(){


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (true) {
                    getBaseActivity().addFragment(R.id.container, new ScreenPasswordFragment(), true, ScreenPasswordFragment.class.getSimpleName());
                    return;
                }
                if (AuthUtils.validatePassword(edtPassword.getText().toString())){
                    if(edtConfirmPassword.getText().toString().equals(edtPassword.getText().toString())){
                        String email = edtEmail.getText().toString();
                        String password = edtPassword.getText().toString();
                        String username = edtFirstName.getText().toString() + edtLastName.getText().toString();
                        String address = "address";
                        SignupRequest request = new SignupRequest(email, password, username, address);
                        getPresenter().doSignup(request);
                    }else{
                        Toast.makeText(getBaseActivity(), "Hãy xác nhận lại mật khẩu", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getBaseActivity(), "Mật khẩu phải có ít nhất 8 kí tự", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getBaseActivity().getSupportFragmentManager();
                fragmentManager.popBackStack(); // quay lại fragment trước đó
            }
        });
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
        Log.e("Signup", mess);
    }
}
