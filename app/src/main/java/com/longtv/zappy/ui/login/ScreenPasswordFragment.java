package com.longtv.zappy.ui.login;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.longtv.zappy.R;
import com.longtv.zappy.base.BaseFragment;
import com.longtv.zappy.base.BasePresenter;
import com.longtv.zappy.common.view.MyPasswordTransformationMethod;
import com.longtv.zappy.ui.account.CreateprofileFragment;

import butterknife.BindView;

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

        edtPass.setTransformationMethod(new MyPasswordTransformationMethod());

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (true) {
                    getBaseActivity().addFragment(R.id.container, new CreateprofileFragment(), true, CreateprofileFragment.class.getSimpleName());
                }
            }
        });

    }

    @Override
    public BasePresenter onCreatePresenter() {
        return null;
    }
}
