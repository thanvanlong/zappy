package com.longtv.zappy.ui.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.longtv.zappy.R;
import com.longtv.zappy.base.BaseFragment;
import com.longtv.zappy.network.dto.LoginData;
import com.longtv.zappy.utils.AuthUtils;

import butterknife.BindView;

public class SignupPhoneFragment extends BaseFragment<LoginPresenter, LoginActivity> implements LoginView {
    @BindView(R.id.edt_phone_signup)
    protected EditText edtPhone;
    @BindView(R.id.btn_next_signup)
    protected Button btnNext;
    @BindView(R.id.iv_back_signup_phone)
    protected ImageView ivBack;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_signup_phone;
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
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = edtPhone.getText().toString();
                if(AuthUtils.validatePhone(phone)){
                    Bundle bundle = new Bundle();
                    bundle.putString("phoneNumber", phone);
                    getBaseActivity().addFragment(R.id.container, new SignupFragment(), bundle, true, SignupFragment.class.getSimpleName());
                }else{
                    Toast.makeText(getBaseActivity(), "Số điên thoại phải bắt đầu bằng 0 và có ít nhất 10 chữ số", Toast.LENGTH_SHORT).show();
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

    }
}
