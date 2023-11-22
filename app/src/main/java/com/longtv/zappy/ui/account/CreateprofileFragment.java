package com.longtv.zappy.ui.account;

import android.app.TimePickerDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.longtv.zappy.R;
import com.longtv.zappy.base.BaseFragment;
import com.longtv.zappy.ui.adapter.AgeCircleAdapter;

import butterknife.BindView;

public class CreateprofileFragment extends BaseFragment<AccountPresenter, AccountActivity> implements AccountView {
    @BindView(R.id.btn_save_profile)
    protected Button btnSaveProfile;
    @BindView(R.id.vp2_age_profile)
    protected ViewPager2 viewPager2;
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
    }

    @Override
    public AccountPresenter onCreatePresenter() {
        return new AccountPresenterImpl(this);
    }
    public void setAdapterForViewPage2(){

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(5);

        MarginPageTransformer marginPageTransformer = new MarginPageTransformer(20);
        viewPager2.setPageTransformer(marginPageTransformer);
        viewPager2.setPageTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                if(position == 0){
                    page.setScaleX(1.3f);
                    page.setScaleY(1.3f);
                } else {
                    page.setScaleX(0.8f);
                    page.setScaleY(0.8f);
                }
            }
        });

        AgeCircleAdapter adapter = new AgeCircleAdapter(this);
        viewPager2.setAdapter(adapter);
    }
    public void setListener(){

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                age = position + 1;
                Log.e("Curren Age:", String.valueOf(position+1));
            }
        });

        ivPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentedPos = viewPager2.getCurrentItem();
                if(currentedPos > 0) viewPager2.setCurrentItem(currentedPos-1);
            }
        });

        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentedPos = viewPager2.getCurrentItem();
                if(currentedPos < 14) viewPager2.setCurrentItem(currentedPos+1);
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
                // làm gì đó để lưu profile
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
}
