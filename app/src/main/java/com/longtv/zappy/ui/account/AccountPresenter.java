package com.longtv.zappy.ui.account;

import com.longtv.zappy.base.BasePresenter;
import com.longtv.zappy.network.dto.Profile;

public interface AccountPresenter extends BasePresenter {
    void createProfile(Profile profile);
}
