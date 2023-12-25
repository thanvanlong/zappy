package com.longtv.zappy.ui.account;

import android.app.TimePickerDialog;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.MarginPageTransformer;

import com.google.gson.Gson;
import com.longtv.zappy.R;
import com.longtv.zappy.base.BaseFragment;
import com.longtv.zappy.common.adapter.DiscreAdapter;
import com.longtv.zappy.common.view.HorizontalItemDecoration;
import com.longtv.zappy.network.dto.LoginData;
import com.longtv.zappy.network.dto.Profile;
import com.longtv.zappy.ui.HomeActivity;
import com.longtv.zappy.ui.home.HomeBoxFragment;
import com.longtv.zappy.utils.PrefManager;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
import com.yarolegovich.discretescrollview.transform.Pivot;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;

public class CreateprofileFragment extends BaseFragment<AccountPresenter, AccountActivity> implements AccountView {
    @BindView(R.id.btn_save_profile)
    protected Button btnSaveProfile;
    @BindView(R.id.vp2_age_profile)
    protected DiscreteScrollView viewPager2;
    @BindView(R.id.iv_previous_slide_profile)
    protected ImageView ivPrevious;
    @BindView(R.id.iv_next_slide_profile)
    protected ImageView ivNext;
    @BindView(R.id.cl_select_boy)
    protected ConstraintLayout clSelectBoy;
    @BindView(R.id.cl_select_girl)
    protected ConstraintLayout clSelectGirl;
    @BindView(R.id.iv_check_boy)
    protected  ImageView ivCheckBoy;
    @BindView(R.id.iv_check_girl)
    protected  ImageView ivCheckGirl;
    @BindView(R.id.ll_select_hr)
    protected LinearLayout llSelectHr;
    @BindView(R.id.ll_select_min)
    protected LinearLayout llSelectMin;
    @BindView(R.id.iv_increase_time)
    protected ImageView ivIncreaseTime;
    @BindView(R.id.iv_reduce_time)
    protected  ImageView ivReduceTime;
    @BindView(R.id.tv_hr)
    protected TextView tvHr;
    @BindView(R.id.tv_min)
    protected TextView tvMin;
    @BindView(R.id.edt_name_profile)
    EditText edtNameProfile;

    private int hr, min;
    private int age;
    private boolean isBoy;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_createprofile;
    }

    @Override
    public void onPrepareLayout() {
        setAdapterForViewPage2();
        setListener();
        HomeActivity.getInstance().hideBottomBar();
    }

    @Override
    public AccountPresenter onCreatePresenter() {
        return new AccountPresenterImpl(this);
    }
    public void setAdapterForViewPage2(){

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        DiscreAdapter adapter = new DiscreAdapter();
        InfiniteScrollAdapter wrapper = InfiniteScrollAdapter.wrap(adapter);
        viewPager2.setAdapter(wrapper);
        viewPager2.addItemDecoration(new HorizontalItemDecoration(35));
        viewPager2.setItemTransformer(
                new ScaleTransformer.Builder()
                        .setMaxScale(1.15f)
                        .setMinScale(0.8f)
                        .setPivotX(Pivot.X.CENTER) // CENTER is a default one
                        .setPivotY(Pivot.Y.CENTER) // CENTER is a default one
                        .build());

        viewPager2.addOnItemChangedListener(new DiscreteScrollView.OnItemChangedListener<RecyclerView.ViewHolder>() {
            @Override
            public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {
                age = wrapper.getRealCurrentPosition();
            }
        });

    }
    public void setListener(){
        ivPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager2.smoothScrollToPosition(Math.max(viewPager2.getCurrentItem() - 1, 0));
            }
        });
//
        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager2.smoothScrollToPosition(viewPager2.getCurrentItem() + 1);
            }
        });

        clSelectBoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivCheckBoy.setImageResource(R.drawable.ic_check_deep_pink);
                ivCheckGirl.setImageResource(0);
                isBoy = true;
            }
        });

        clSelectGirl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivCheckBoy.setImageResource(0);
                ivCheckGirl.setImageResource(R.drawable.ic_check_deep_purple);
                isBoy = false;
            }
        });

        llSelectHr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popTimePicker();
            }
        });

        llSelectMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popTimePicker();
            }
        });

        ivIncreaseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currenHr = Integer.valueOf(tvHr.getText().toString());
                int currenMin = Integer.valueOf(tvMin.getText().toString());

                if(currenMin < 59){
                    currenMin += 1;
                }else{
                    currenMin = 0;
                    if(hr < 23) currenHr += 1;
                    else currenHr = 0;
                }

                hr = currenHr;
                min = currenMin;
                tvHr.setText(String.valueOf(currenHr));
                tvMin.setText(String.valueOf(currenMin));
            }
        });

        ivReduceTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currenHr = Integer.valueOf(tvHr.getText().toString());
                int currenMin = Integer.valueOf(tvMin.getText().toString());

                if(currenMin > 0){
                    currenMin -= 1;
                }else{
                    currenMin = 59;
                    if(hr > 0) currenHr -= 1;
                    else currenHr = 23;
                }

                hr = currenHr;
                min = currenMin;
                tvHr.setText(String.valueOf(currenHr));
                tvMin.setText(String.valueOf(currenMin));
            }
        });

        btnSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get date time now calendar
                Calendar calendar = Calendar.getInstance();

                // Trừ 2 năm
                calendar.add(Calendar.YEAR, - age);

                // Lấy ngày sau khi trừ 2 năm
                Date date = calendar.getTime();
                //convert date to localdatetime for android 9
                Profile profile = new Profile(edtNameProfile.getText().toString(), date, hr * 3600L + min * 60L, PrefManager.getUserData(getViewContext()).getProfiles().size() + 1);

//                btnSaveProfile.requestFocus();
                getPresenter().saveProfile(profile);
            }
        });
    }
    public void popTimePicker(){
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hr = hourOfDay;
                min = minute;
                tvHr.setText(String.valueOf(hr));
                tvMin.setText(String.valueOf(min));
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(getBaseActivity(), onTimeSetListener, hr, min, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    @Override
    public void loginProfileSuccess(LoginData loginData) {

    }

    @Override
    public void getMeSuccess(LoginData loginData) {

    }

    @Override
    public void saveProfileSuccess(Profile profile) {
        LoginData loginData = PrefManager.getUserData(getViewContext());
        loginData.getProfiles().add(profile);
        PrefManager.saveUserData(getViewContext(), new Gson().toJson(loginData));
        PrefManager.saveProfileData(getViewContext(), loginData.getProfiles());
        HomeActivity.getInstance().setupBottomNav(R.id.navigation_home);

    }
}
