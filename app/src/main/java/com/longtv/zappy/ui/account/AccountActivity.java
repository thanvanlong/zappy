package com.longtv.zappy.ui.account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.longtv.zappy.R;
import com.longtv.zappy.base.BaseActivity;

public class AccountActivity extends BaseActivity<AccountPresenter> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_account;
    }

    @Override
    public void onPrepareLayout() {
        addFragment(R.id.container_account, new CreateprofileFragment(), true, CreateprofileFragment.class.getSimpleName());
    }

    @Override
    public AccountPresenter onCreatePresenter() {
        return null;
    }
}