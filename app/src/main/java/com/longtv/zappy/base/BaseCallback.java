package com.longtv.zappy.base;

import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.longtv.zappy.network.dto.ResponseDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public abstract class BaseCallback<T> implements Callback<ResponseDTO<T>> {
    public static final String NETWORK_ERROR = "999";

    @Override
    public void onResponse(Call<ResponseDTO<T>> call, Response<ResponseDTO<T>> response) {
        final ResponseDTO<T> body = response.isSuccessful() ? response.body() : null;
        Log.d("longtv", "onResponse: base " + response.isSuccessful());
        String responseCode = NETWORK_ERROR;
        String message = "";

        if (!response.isSuccessful()) {
            onError(response.code() + "", response.message());
        }

        if (response.isSuccessful() && body != null) {
            responseCode = body.getErrorCode();
            message = body.getMessage();
        }
        try {
            if (body == null || body.getErrorCode() == null) {
                return;
            }

            if (!ResponseCode.SUCCESS.equals(responseCode)) {
                switch (body.getErrorCode()) {
                    case ResponseCode.UNAUTHORIZED:
                        onRequireLogin(message);
                        return;
                    case ResponseCode.REFRESH_TOKEN:
                        String refreshToken = "";
                        refreshToken(refreshToken);
                        return;
                    case ResponseCode.NETWORK_ERROR:
                        onError(body.getErrorCode(), body.getMessage());
                        return;
                    default:
                        onError(body.getErrorCode(), body.getMessage());
                        return;
                }
            }

            // Request success
            Log.e("longtv", "onResponse: success " );
            onResponse(body.getData());
            onMessage(message);
            if (call.request() != null) {
                onRequest(call.request().toString(), response.code()+"", body.getErrorCode());
            }
        } catch (IllegalStateException | JsonSyntaxException | NullPointerException ex) {
            Log.e("anth", "onResponse: ", ex);
            try {
            } catch (IllegalStateException | NullPointerException | JsonSyntaxException e) {

            }
        }
    }

    protected void onAccountExist(String message) {

    }

    protected void onRegisterFail(String message) {

    }

    private void refreshToken(String refreshToken) {

    }

    @Override
    public void onFailure(Call<ResponseDTO<T>> call, Throwable t) {
        Log.e("longtv", "onFailure: time out " + t.getMessage() );
        onError("400", "Có lỗi xảy ra vui lòng thử lại!");
    }


    public abstract void onError(String errorCode, String errorMessage);
    public void onNeedBuy(String message) {

    };

    public abstract void onResponse(T data);
    protected void onMessage(String message) {
        // Must implement when needed
    }

    protected void onRequest(String request, String httpCode, String bodyCode){

    }
    protected void onRequireLogin(String errorMessage){};
    protected void onLimitedDevice(T body, String message){ }


    public interface ResponseCode {
        String SUCCESS = "200";
        String UNAUTHORIZED = "401";
        String REFRESH_TOKEN = "412";
        String LIMITED_DEVICE  = "LIMIT_DEVICE";
        String ACCOUNT_EXIST= "PHONE_EXIST";
        String NETWORK_ERROR = "NETWORK_ERROR";
        String ACCOUNT_NON_EXIST = "USER_NON_EXIST";
        String NEED_BUY = "333";
    }
}

