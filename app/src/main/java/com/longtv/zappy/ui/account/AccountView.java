package com.longtv.zappy.ui.account;

import com.longtv.zappy.base.BaseView;
import com.longtv.zappy.network.dto.LoginData;
import com.longtv.zappy.network.dto.Profile;

public interface AccountView extends BaseView<AccountPresenter> {
    void loginProfileSuccess(LoginData loginData);
    void getMeSuccess(LoginData loginData);
    void saveProfileSuccess(Profile profile);
}
