package com.longtv.zappy.ui.music.detail;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.longtv.zappy.R;
import com.longtv.zappy.base.BaseFragment;
import com.longtv.zappy.base.BasePresenter;
import com.longtv.zappy.common.adapter.LyricsAdapter;
import com.longtv.zappy.ui.HomeActivity;
import com.longtv.zappy.utils.ImageUtils;

import butterknife.BindView;
import jp.wasabeef.blurry.Blurry;

public class HomeBoxMusicPlayerFragment extends BaseFragment {
    @BindView(R.id.bg_music)
    ImageView ivBgMusic;
    @BindView(R.id.iv_cover_image)
    ImageView ivCoverImage;
    @BindView(R.id.iv_singer)
    ImageView ivSinger;
    @BindView(R.id.bg_lyrics)
    LinearLayout bgLyrics;
    @BindView(R.id.rcv_lyrics)
    RecyclerView rcvLyrics;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_media_music;
    }

    @Override
    public void onPrepareLayout() {
        HomeActivity.getInstance().hideBottomBar();
        HomeActivity.getInstance().toggleTopBar(8);
        Glide.with(getViewContext())
                .asBitmap()
                .load("https://static.wikia.nocookie.net/vpop/images/b/bf/Hoang_Thuy_Linh_LINK.jpg")

                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Blurry.with(getViewContext()).radius(4)
                                .sampling(7)
                                .color(Color.parseColor("#66575757"))
                                .from(resource).into(ivBgMusic);

                        Palette.from(resource).generate(palette -> {
                            // Get the dominant color from the Palette
                            int dominantColor = palette.getDominantColor(0xFF000000);

                            bgLyrics.setBackgroundColor(darkenColor(dominantColor, 0.1f));
                        });

                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });

        ImageUtils.loadImageCorner(getViewContext(), ivCoverImage, "https://static.wikia.nocookie.net/vpop/images/b/bf/Hoang_Thuy_Linh_LINK.jpg", 15);
        ImageUtils.loadImageCorner(getViewContext(), ivSinger, "https://afamilycdn.com/150157425591193600/2022/12/14/thumb-5422-1670987053094-1670987053171791696448.jpg", 1);

        rcvLyrics.setLayoutManager(new LinearLayoutManager(getViewContext(), LinearLayoutManager.VERTICAL, false));
        rcvLyrics.setAdapter(new LyricsAdapter());
    }

    @Override
    public BasePresenter onCreatePresenter() {
        return null;
    }

    public static int darkenColor(int color, float percentage) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);

        // Reduce the value (brightness) component by the specified percentage
        hsv[2] *= 1 - percentage;

        return Color.HSVToColor(hsv);
    }
}
