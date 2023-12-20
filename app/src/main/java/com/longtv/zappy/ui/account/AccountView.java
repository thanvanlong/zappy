package com.longtv.zappy.ui.account;

import com.longtv.zappy.base.BaseView;
import com.longtv.zappy.network.dto.LoginData;

public interface AccountView extends BaseView<AccountPresenter> {
    void loginProfileSuccess(LoginData loginData);
}
