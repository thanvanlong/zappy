package com.longtv.zappy.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.longtv.zappy.ui.login.OnboardingFirstFragment;
import com.longtv.zappy.ui.login.OnboardingSecondFragment;
import com.longtv.zappy.ui.login.OnboardingThirdFragment;
// this is adapter for a ViewPager2
public class OnboardingAdapter extends FragmentStateAdapter {
    public OnboardingAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new OnboardingFirstFragment();
            case 1:
                return new OnboardingSecondFragment();
            case 2:
                return new OnboardingThirdFragment();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
