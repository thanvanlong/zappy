package com.longtv.zappy.common.adapter;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.longtv.zappy.common.fragment.ContentMediaFragment;
import com.longtv.zappy.network.dto.Content;
import com.longtv.zappy.network.dto.DataListDTO;
import com.longtv.zappy.network.dto.Type;

import java.util.HashMap;
import java.util.Map;

public class ContentMediaPagerAdapter extends FragmentStateAdapter {
    private Map<String, DataListDTO<Content>> dataListDTOMap = new HashMap<>();
    public ContentMediaPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public void setDataListDTOMap(Map<String, DataListDTO<Content>> dataListDTOMap) {
        this.dataListDTOMap = dataListDTOMap;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Log.e("anth", "createFragment: " + dataListDTOMap);
        switch (position) {
            case 0:
                ContentMediaFragment ctnMusic = new ContentMediaFragment(dataListDTOMap.get("MUSIC"), "MUSIC");
                Bundle bdMusic = new Bundle();
                bdMusic.putString("type", Type.MUSIC.name());
                ctnMusic.setArguments(bdMusic);
                return ctnMusic;
            case 1:
                ContentMediaFragment ctnStory = new ContentMediaFragment(dataListDTOMap.get("STORY"), "STORY");
                Bundle bdStory = new Bundle();
                bdStory.putString("type", Type.STORY.name());
                return ctnStory;

            case 2:
                ContentMediaFragment ctnMovie = new ContentMediaFragment(dataListDTOMap.get("VOD"), "VOD");
                Bundle bdMovie = new Bundle();
                bdMovie.putString("type", Type.VOD.name());
                ctnMovie.setArguments(bdMovie);
                return ctnMovie;
            default:
                return new ContentMediaFragment(dataListDTOMap.get("MUSIC"), "MUSIC");
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
