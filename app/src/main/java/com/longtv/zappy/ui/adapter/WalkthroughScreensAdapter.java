package com.longtv.zappy.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.longtv.zappy.ui.fragment.WalkthroughScreens1Fragment;
import com.longtv.zappy.ui.fragment.WalkthroughScreens2Fragment;
import com.longtv.zappy.ui.fragment.WalkthroughScreens3Fragment;

public class WalkthroughScreensAdapter extends FragmentStateAdapter {
    public WalkthroughScreensAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new WalkthroughScreens1Fragment();
            case 1:
                return new WalkthroughScreens2Fragment();
            case 2:
                return new WalkthroughScreens3Fragment();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
