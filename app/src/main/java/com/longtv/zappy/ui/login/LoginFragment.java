package com.longtv.zappy.ui.login;

import android.widget.EditText;

import com.longtv.zappy.R;
import com.longtv.zappy.base.BaseFragment;

import butterknife.BindView;


public class LoginFragment extends BaseFragment<LoginPresenter, LoginActivity> implements LoginView{
    @BindView(R.id.edt_email)
    protected EditText edtPhone;

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
