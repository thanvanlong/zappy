package com.longtv.zappy.ui.login;

import android.content.Intent;
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
import com.longtv.zappy.common.dialog.SuccessDialog;
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
        Bundle receivedBundle = getArguments();
        phoneNumber = receivedBundle.getString("phoneNumber");
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
                if (edtPassword.getText().toString().isEmpty() || edtEmail.getText().toString().isEmpty()
                        || edtFirstName.getText().toString().isEmpty() || edtLastName.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseActivity(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else if (AuthUtils.validatePassword(edtPassword.getText().toString())){
                    Log.e("anth", "onClick: " + edtConfirmPassword.getText().toString() + " " + edtPassword.getText());
                    if(edtConfirmPassword.getText().toString().equals(edtPassword.getText().toString())){
                        String email = edtEmail.getText().toString();
                        String password = edtPassword.getText().toString();
                        String username = edtFirstName.getText().toString() + " " + edtLastName.getText().toString();
                        SignupRequest request = new SignupRequest(email, password, username, phoneNumber);
                        getPresenter().doSignup(request);
                    }else{
                        Toast.makeText(getBaseActivity(), "Hãy xác nhận lại mật khẩu", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getBaseActivity(), "Mật khẩu phải có ít nhất 6 kí tự", Toast.LENGTH_SHORT).show();
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
        SuccessDialog successDialog = new SuccessDialog();
        successDialog.init(getViewContext(), "");
        successDialog.setListener(new SuccessDialog.ItemClickListener() {
            @Override
            public void btnYesClick() {
                try {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                    startActivity(intent);
                } catch (android.content.ActivityNotFoundException e) {
                    Toast.makeText(getViewContext(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void btnNoClick() {

            }
        });

        successDialog.show(getChildFragmentManager(), "");
    }

    @Override
    public void onSignupError(String mess) {
        Toast.makeText(getViewContext(), mess, Toast.LENGTH_SHORT).show();
    }
}
