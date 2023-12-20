package com.longtv.zappy.ui.account;

import com.google.gson.JsonObject;
import com.longtv.zappy.base.BaseActivity;
import com.longtv.zappy.base.BaseCallback;
import com.longtv.zappy.base.BasePresenterImpl;
import com.longtv.zappy.base.BaseView;
import com.longtv.zappy.network.ServiceBuilder;
import com.longtv.zappy.network.dto.LoginData;
import com.longtv.zappy.network.dto.Profile;

public class AccountPresenterImpl extends BasePresenterImpl<AccountView> implements AccountPresenter{

    public AccountPresenterImpl(AccountView view) {
        super(view);
    }

    @Override
    public void createProfile(Profile profile) {

    }

    @Override
    public void loginProfile(int id) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", id + "");
        ServiceBuilder.getService().loginProfile(jsonObject).enqueue(new BaseCallback<LoginData>() {
            @Override
            public void onError(String errorCode, String errorMessage) {

            }

            @Override
            public void onResponse(LoginData data) {
                if (data != null) {
                    mView.loginProfileSuccess(data);
                }
            }
        });
    }
}
