package com.longtv.zappy.ui.login;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.longtv.zappy.R;
import com.longtv.zappy.base.BaseFragment;
import com.longtv.zappy.base.BasePresenter;
import com.longtv.zappy.common.view.MyPasswordTransformationMethod;
import com.longtv.zappy.ui.HomeActivity;
import com.longtv.zappy.ui.account.CreateprofileFragment;

import butterknife.BindView;
import butterknife.OnTextChanged;

public class ScreenPasswordFragment extends BaseFragment {

    @BindView(R.id.edt_pass_screen)
    EditText edtPass;
    @BindView(R.id.btn_next_signup)
    Button btnNext;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_screen_password;
    }

    @Override
    public void onPrepareLayout() {
        btnNext.setEnabled(false);
        edtPass.setTransformationMethod(new MyPasswordTransformationMethod());

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getViewContext(), HomeActivity.class));
                getViewContext().finish();
            }
        });

    }

    @OnTextChanged(R.id.edt_pass_screen)
    public void onTextChanged() {
        if (edtPass.getText().toString().length() > 0) {
            btnNext.setEnabled(true);
        } else {
            btnNext.setEnabled(false);
        }
    }

    @Override
    public BasePresenter onCreatePresenter() {
        return null;
    }
}
