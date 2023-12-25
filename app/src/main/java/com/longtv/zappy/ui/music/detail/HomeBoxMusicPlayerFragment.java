package com.longtv.zappy.ui.music.detail;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.*;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.longtv.zappy.R;
import com.longtv.zappy.base.BaseFragment;
import com.longtv.zappy.base.BasePresenter;
import com.longtv.zappy.common.Constants;
import com.longtv.zappy.common.adapter.LyricsAdapter;
import com.longtv.zappy.common.fragment.MediaPlayerController;
import com.longtv.zappy.common.view.ExpandableTextView;
import com.longtv.zappy.network.dto.Content;
import com.longtv.zappy.network.dto.DataListDTO;
import com.longtv.zappy.service.MediaNotificationService;
import com.longtv.zappy.ui.HomeActivity;
import com.longtv.zappy.ui.music.HomeBoxMusicPresenter;
import com.longtv.zappy.ui.music.HomeBoxMusicPresenterImpl;
import com.longtv.zappy.ui.music.HomeBoxMusicView;
import com.longtv.zappy.utils.ImageUtils;
import com.longtv.zappy.utils.PrefManager;
import com.longtv.zappy.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import butterknife.BindView;
import jp.wasabeef.blurry.Blurry;

public class HomeBoxMusicPlayerFragment extends BaseFragment<HomeBoxMusicPresenter, HomeActivity> implements HomeBoxMusicView {
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
    @BindView(R.id.sb_progress)
    SeekBar sbProgress;
    @BindView(R.id.iv_play_pause)
    ImageView ivPlayPause;
    @BindView(R.id.iv_next)
    ImageView ivNext;
    @BindView(R.id.iv_shuffle)
    ImageView ivShuffle;
    @BindView(R.id.iv_repeat)
    ImageView ivRepeat;
    @BindView(R.id.iv_share)
    ImageView btnShare;
    @BindView(R.id.tv_author)
    TextView tvAuthor;
    @BindView(R.id.tv_singer)
    TextView tvSinger;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_desc)
    ExpandableTextView tvDesc;
    @BindView(R.id.btn_back)
    ImageView btnBack;
    private Content content;

    ExoPlayer player;
    Handler handler;
    Runnable runnable;
    MediaPlayerController controller;
    private long timeOnMusic;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_media_music;
    }

    @Override
    public void onPrepareLayout() {
        HomeActivity.getInstance().hideBottomBar();
        HomeActivity.getInstance().toggleTopBar(8);
        createNotificationChannel();
        if (player != null) {
            player.release();
            player = null;
        }
        Bundle bundle = getArguments();
        if (bundle == null) {
            return;
        }
        content = (Content) bundle.getSerializable(Constants.DATA);
        getPresenter().getMusicDetail(content.getId());


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity.getInstance().onBackPressed();
            }
        });

        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player != null) {
                    content = content.getRelated().get(0);
                    initView(content);
                    controller.initExoplayer(content.getUrlStream());
                    player = controller.getPlayer();
                }
            }
        });
        timeOnMusic = System.currentTimeMillis();
    }

    @Override
    public HomeBoxMusicPresenter onCreatePresenter() {
        return new HomeBoxMusicPresenterImpl(this);
    }

    public static int darkenColor(int color, float percentage) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);

        // Reduce the value (brightness) component by the specified percentage
        hsv[2] *= 1 - percentage;

        return Color.HSVToColor(hsv);
    }

    private void initExoplayer(String url) {
        MediaItem mediaItem = MediaItem.fromUri(url);

        Map<String, String> headersMap = new HashMap<>();
//        headersMap.put("authorization", "Bearer " + PrefManager.getAccessToken(getViewContext()));

        player = new SimpleExoPlayer.Builder(getViewContext())
                .setMediaSourceFactory(new
                        DefaultMediaSourceFactory(new DefaultHttpDataSource.Factory().setDefaultRequestProperties(headersMap)))
                .build();
        player.setMediaItem(mediaItem);
        player.prepare();
        player.play();
    }


    public void onPlaybackStateChanged(int playbackState) {
        switch (playbackState) {
            case Player.STATE_BUFFERING:
                ivPlayPause.setImageResource(!player.getPlayWhenReady() ? R.drawable.player_ic_play_small : R.drawable.player_ic_pause_small);
                break;

            case Player.STATE_READY:
                initProgress((int) (player.getCurrentPosition() / 1000), (int) (player.getDuration() / 1000));
                ivPlayPause.setImageResource(!player.getPlayWhenReady() ? R.drawable.player_ic_play_small : R.drawable.player_ic_pause_small);
                break;
            case Player.STATE_ENDED:
                if (player != null && player.getRepeatMode() != Player.REPEAT_MODE_ONE) {
                    int random = (int) (Math.random() * content.getRelated().size());
                    controller.initExoplayer(content.getUrlStream());
                    player = controller.getPlayer();
                    content = content.getRelated().get(random);
                    initView(content);
                } else if (player != null && player.getRepeatMode() != Player.REPEAT_MODE_ONE) {
                    content = content.getRelated().get(0);
                    initView(content);
                    controller.initExoplayer(content.getUrlStream());
                    player = controller.getPlayer();
                }
                break;
        }
    }

    private void initProgress(int position, int duration) {
        sbProgress.setProgress(position);
        sbProgress.setMax(duration);
//        tvEndTime.setText(StringUtils.covertSecondToHMS(duration));
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (player != null && player.getPlayWhenReady() && player.getPlaybackState() == Player.STATE_READY) {
                    sbProgress.setProgress((int) (player.getCurrentPosition() / 1000));
                    sbProgress.setSecondaryProgress((int) (player.getBufferedPosition() / 1000));
                    handler.postDelayed(this, 300);
                }
            }
        };

        handler.postDelayed(runnable, 300);

        sbProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (player != null) {
                    player.seekTo(seekBar.getProgress() * 1000);
                }
            }
        });

    }

    private void initView(Content content) {
        if (content == null) {
            return;
        }
        this.content = content;
        Glide.with(getViewContext())
                .asBitmap()
                .load(content.getCoverImage())

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

        ImageUtils.loadImageCorner(getViewContext(), ivCoverImage, content.getCoverImage(), 15);
        try {
            ImageUtils.loadImageCorner(getViewContext(), ivSinger, content.getAuthors().get(0).getImage(), 1);
        } catch (Exception e) {
            ImageUtils.loadImageCorner(getViewContext(), ivSinger, "https://afamilycdn.com/150157425591193600/2022/12/14/thumb-5422-1670987053094-1670987053171791696448.jpg", 1);
        }
        if (content.getAuthors() != null && content.getAuthors().size() > 0) {
            tvAuthor.setText(content.getAuthors().get(0).getName());
            tvSinger.setText(content.getAuthors().get(0).getName());
        }

        tvTitle.setText(content.getName());


        rcvLyrics.setLayoutManager(new LinearLayoutManager(getViewContext(), LinearLayoutManager.VERTICAL, false));
        rcvLyrics.setAdapter(new LyricsAdapter());

        ivPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doPauseResume();
            }
        });

        ivShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                doShuffle();
                if (player != null && !player.getShuffleModeEnabled()) {
                    player.setShuffleModeEnabled(true);
                    ivShuffle.setImageResource(R.drawable.ic_shuffle_selected);
                    if (player.getRepeatMode() == Player.REPEAT_MODE_ONE) {
                        player.setRepeatMode(Player.REPEAT_MODE_OFF);
                        ivRepeat.setImageResource(R.drawable.ic_loop);
                    }
                }
                else if (player != null && player.getShuffleModeEnabled()) {
                    player.setShuffleModeEnabled(false);
                    ivShuffle.setImageResource(R.drawable.ic_shuffle);
                }
            }
        });

        ivRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("anth", "onClick: " + player.getRepeatMode());
                if (player != null && player.getRepeatMode() == Player.REPEAT_MODE_OFF) {
                    player.setRepeatMode(Player.REPEAT_MODE_ONE);
                    ivRepeat.setImageResource(R.drawable.ic_loop_selected);
                    if (player.getShuffleModeEnabled()) {
                        player.setShuffleModeEnabled(false);
                        ivShuffle.setImageResource(R.drawable.ic_shuffle);
                    }
                }
                else if (player != null && player.getRepeatMode() == Player.REPEAT_MODE_ONE) {
                    player.setRepeatMode(Player.REPEAT_MODE_OFF);
                    ivRepeat.setImageResource(R.drawable.ic_loop);
                }
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");

                intent.putExtra(Intent.EXTRA_TEXT, Constants.APP_LINK + "?type=music&id=1");
                getActivity().startActivity(Intent.createChooser(intent, "Share with"));
            }
        });
    }

    public void doPauseResume() {
        Intent intent = new Intent(getViewContext(), MediaNotificationService.class);
//        intent.putExtra("longtv", 1);
        intent.putExtra("key_action", "notification");
//        intent.
        getViewContext().startService(intent);
        if (player != null) {
            player.setPlayWhenReady(!player.getPlayWhenReady());

            ivPlayPause.setImageResource(!player.getPlayWhenReady() ? R.drawable.player_ic_play_small : R.drawable.player_ic_pause_small);
        }
    }

    private void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("1", "service",
                    NotificationManager.IMPORTANCE_LOW);
            NotificationManager notificationManager = (NotificationManager) requireActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationChannel != null)
                notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (player != null) {
            player.setPlayWhenReady(false);
        }
        timeOnMusic = System.currentTimeMillis() - timeOnMusic;
        PrefManager.saveTimeOnMusic(getViewContext(), timeOnMusic);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (player != null) {
            player.release();
            player = null;
        }
    }

    @Override
    public void onLoadMusicsSuccess(DataListDTO<Content> data) {

    }

    @Override
    public void doLoadMusicDetail(Content content) {
        this.content = content;
        getPresenter().getContentRelated(StringUtils.getContentTypesId(content.getTypes()));
        initView(content);
//        initExoplayer("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3");
        controller = new MediaPlayerController(getViewContext(), content, null);
        controller.initExoplayer(content.getUrlStream());
        player = controller.getPlayer();
        controller.setEventListener(new MediaPlayerController.EventListener() {
            @Override
            public void onPlaybackStateChanged(int playbackState) {
                HomeBoxMusicPlayerFragment.this.onPlaybackStateChanged(playbackState);
            }

            @Override
            public void onNext() {
                initView(content.getRelated().get(0));
                getPresenter().getContentRelated(StringUtils.getContentTypesId(content.getRelated().get(0).getTypes()));
                controller.initExoplayer(content.getRelated().get(0).getUrlStream());
                player = controller.getPlayer();
            }
        });
    }

    @Override
    public void doLoadContentRelated(DataListDTO<Content> data) {
        content.setRelated(data.getResults().stream().filter(content1 -> content1.getId() != content.getId()).collect(Collectors.toList()));
    }
}
