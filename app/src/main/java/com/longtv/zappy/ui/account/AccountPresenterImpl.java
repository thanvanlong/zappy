package com.longtv.zappy.ui.account;

import com.google.gson.JsonObject;
import com.longtv.zappy.base.BaseActivity;
import com.longtv.zappy.base.BaseCallback;
import com.longtv.zappy.base.BasePresenterImpl;
import com.longtv.zappy.base.BaseView;
import com.longtv.zappy.network.ServiceBuilder;
import com.longtv.zappy.network.dto.LoginData;
import com.longtv.zappy.network.dto.Profile;
import com.longtv.zappy.utils.DialogUtils;

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
        DialogUtils.showProgressDialog(getViewContext());
        ServiceBuilder.getService().loginProfile(jsonObject).enqueue(new BaseCallback<LoginData>() {
            @Override
            public void onError(String errorCode, String errorMessage) {
                DialogUtils.dismissProgressDialog(getViewContext());
            }

            @Override
            public void onResponse(LoginData data) {
                DialogUtils.dismissProgressDialog(getViewContext());
                if (data != null) {
                    mView.loginProfileSuccess(data);
                }
            }
        });
    }

    @Override
    public void getMe() {
        DialogUtils.showProgressDialog(getViewContext());
        ServiceBuilder.getService().getMe().enqueue(new BaseCallback<LoginData>() {
            @Override
            public void onError(String errorCode, String errorMessage) {
                DialogUtils.dismissProgressDialog(getViewContext());
            }

            @Override
            public void onResponse(LoginData data) {
                DialogUtils.dismissProgressDialog(getViewContext());
                if (data != null) {
                    mView.getMeSuccess(data);
                }
            }
        });
    }

    @Override
    public void saveProfile(Profile profile) {
        DialogUtils.showProgressDialog(getViewContext());
        ServiceBuilder.getService().saveProfile(profile).enqueue(new BaseCallback<Profile>() {
            @Override
            public void onError(String errorCode, String errorMessage) {
                DialogUtils.dismissProgressDialog(getViewContext());
            }

            @Override
            public void onResponse(Profile data) {
                DialogUtils.dismissProgressDialog(getViewContext());
                if (data != null) {
                    mView.saveProfileSuccess(data);
                }
            }
        });
    }
}
