package com.longtv.zappy.common.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.longtv.zappy.common.fragment.ContentMediaFragment;
import com.longtv.zappy.network.dto.Type;

public class ContentMediaPagerAdapter extends FragmentStateAdapter {
    public ContentMediaPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                ContentMediaFragment ctnMusic = new ContentMediaFragment();
                Bundle bdMusic = new Bundle();
                bdMusic.putString("type", Type.MUSIC.name());
                ctnMusic.setArguments(bdMusic);
                return ctnMusic;
            case 1:
                ContentMediaFragment ctnStory = new ContentMediaFragment();
                Bundle bdStory = new Bundle();
                bdStory.putString("type", Type.STORY.name());
                return ctnStory;

            case 2:
                ContentMediaFragment ctnMovie = new ContentMediaFragment();
                Bundle bdMovie = new Bundle();
                bdMovie.putString("type", Type.VOD.name());
                ctnMovie.setArguments(bdMovie);
                return ctnMovie;

            case 3:
                ContentMediaFragment ctnLearning = new ContentMediaFragment();
                Bundle bdLearning = new Bundle();
                bdLearning.putString("type", Type.LEARNING.name());
                return ctnLearning;
            default:
                return new ContentMediaFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
