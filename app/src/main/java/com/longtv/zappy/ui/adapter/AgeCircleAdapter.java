package com.longtv.zappy.ui.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.longtv.zappy.ui.account.AgeCircleFragment;

public class AgeCircleAdapter extends FragmentStateAdapter {
    public AgeCircleAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("age", String.valueOf(position+1));

        AgeCircleFragment fragment = new AgeCircleFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public int getItemCount() {
        return 15; // từ 1 đến 15 tuổi, index 0 -> 14
    }
}
