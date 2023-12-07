package com.longtv.zappy.ui.account;

import com.longtv.zappy.base.BaseActivity;
import com.longtv.zappy.base.BasePresenterImpl;
import com.longtv.zappy.base.BaseView;
import com.longtv.zappy.network.dto.Profile;

public class AccountPresenterImpl extends BasePresenterImpl<AccountView> implements AccountPresenter{

    public AccountPresenterImpl(AccountView view) {
        super(view);
    }

    @Override
    public void createProfile(Profile profile) {

    }
}
