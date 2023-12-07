package com.longtv.zappy.ui.film.mediaplayer;

import android.content.res.Configuration;

import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.longtv.zappy.R;
import com.longtv.zappy.base.BaseFragment;
import com.longtv.zappy.base.BasePresenter;
import com.longtv.zappy.common.adapter.ContentMediaAdapter;
import com.longtv.zappy.common.view.MediaPlayerView;
import com.longtv.zappy.ui.HomeActivity;
import com.longtv.zappy.utils.DeviceUtils;
import com.longtv.zappy.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class MediaPlayerFragment extends BaseFragment implements Player.Listener {
    @BindView(R.id.player_view)
    StyledPlayerView playerView;
    @BindView(R.id.rcv_content)
    RecyclerView rcvContent;

    @BindView(R.id.layout_controls)
    LinearLayout containerControl;

    @BindView(R.id.ic_fullscreen)
    ImageView ivFullScreen;
    @BindView(R.id.sb_progress)
    SeekBar sbProgress;
    @BindView(R.id.layout_progress)
    RelativeLayout layoutProgress;
    @BindView(R.id.iv_play_pause)
    ImageView ivPlayPause;
    @BindView(R.id.common_progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.currentTime)
    TextView tvCurrentTime;
    @BindView(R.id.endTime)
    TextView tvEndTime;
    @BindView(R.id.control_seek_forward)
    LinearLayout containerSeek;
    @BindView(R.id.container_media)
    MediaPlayerView mediaPlayerView;

    SimpleExoPlayer player;
    private Handler handler;
    private Runnable runnable;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_media_player;
    }

    @Override
    public void onPrepareLayout() {
        initExoplayer("https://devstreaming-cdn.apple.com/videos/streaming/examples/img_bipbop_adv_example_fmp4/master.m3u8");

        rcvContent.setLayoutManager(new GridLayoutManager(getViewContext(), 2));
        rcvContent.setAdapter(new ContentMediaAdapter(getViewContext()));

        HomeActivity.getInstance().hideBottomBar();
        HomeActivity.getInstance().toggleTopBar(View.GONE);

        setListener();

    }

    private void initExoplayer(String url) {
        MediaItem mediaItem = MediaItem.fromUri(url);

        Map<String, String> headersMap = new HashMap<>();
//        headersMap.put("authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOjIsImlhdCI6MTcwMDQwNDk0NiwiZXhwIjoxNzAxMjY4OTQ2fQ.Cb2fC7C9o3-xbpS52XN_bYByGEkrZnpW7AonU9-dhIw");

        player = new SimpleExoPlayer.Builder(getViewContext())
                .setMediaSourceFactory(new
                        DefaultMediaSourceFactory(new DefaultHttpDataSource.Factory().setDefaultRequestProperties(headersMap)))
                .build();
        player.setMediaItem(mediaItem);
        player.prepare();
        playerView.setPlayer(player);
        player.play();
        player.addListener(this);

        hideControl();

    }

    private void initProgress(int position, int duration) {
        sbProgress.setProgress(position);
        sbProgress.setMax(duration);
        tvEndTime.setText(StringUtils.covertSecondToHMS(duration));
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                Log.e("anth", "run: " + player.getBufferedPercentage() + " " + player.getPlayWhenReady());
                if (player != null && player.getPlayWhenReady() && player.getPlaybackState() == Player.STATE_READY) {
                    sbProgress.setProgress((int) (player.getCurrentPosition() / 1000));
                    sbProgress.setSecondaryProgress((int) (player.getBufferedPosition() / 1000));
                    tvCurrentTime.setText(StringUtils.covertSecondToHMS(player.getCurrentPosition() / 1000));
                    handler.postDelayed(this, 300);
                }
            }
        };

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

    public void setListener() {
        mediaPlayerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (containerControl.getVisibility() == View.VISIBLE) {
                    hideControl();
                } else  {
                    showControl();
                }
                return false;
            }
        });

        ivFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (DeviceUtils.isLandscape(getViewContext())) {
//                    DeviceUtils.forceRotateScreen(getViewContext(), Configuration.ORIENTATION_PORTRAIT);
//                    DeviceUtils.showNavigationBar(getViewContext());
//                    ivFullScreen.setImageResource(R.drawable.ic_fullscreen);
//                } else {
//                    DeviceUtils.forceRotateScreen(getViewContext(), Configuration.ORIENTATION_LANDSCAPE);
//                    DeviceUtils.hideNavigationBar(getViewContext());
//                    ivFullScreen.setImageResource(R.drawable.ic_exit_fullscreen);
//                }

                HomeActivity.getInstance().enterPictureInPictureMode();
            }
        });

        ivPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doPauseResume();
            }
        });

        containerSeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.seekTo(player.getCurrentPosition() + 10000);
            }
        });
    }



    public void showControl() {
        try {
            handler.postDelayed(runnable, 300);
            containerControl.setVisibility(View.VISIBLE);
            layoutProgress.setVisibility(View.VISIBLE);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    hideControl();
                }
            }, 4000);
        } catch (Exception e) {

        }
    }

    public void hideControl() {
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
        containerControl.setVisibility(View.GONE);
        layoutProgress.setVisibility(View.GONE);
    }

    @Override
    public void onPlaybackStateChanged(int playbackState) {
        Player.Listener.super.onPlaybackStateChanged(playbackState);

        switch (playbackState) {
            case Player.STATE_BUFFERING:
                //TODO
                progressBar.setVisibility(View.VISIBLE);
                hideControl();
                break;

            case Player.STATE_READY:
                //TODO
                progressBar.setVisibility(View.GONE);
                initProgress((int) (player.getCurrentPosition() / 1000), (int) (player.getDuration() / 1000));
                ivPlayPause.setImageResource(!player.getPlayWhenReady() ? R.drawable.player_ic_play_small : R.drawable.player_ic_pause_small);
                break;
        }
    }

    public void doPauseResume() {
        if (player != null) {
            player.setPlayWhenReady(!player.getPlayWhenReady());
            ivPlayPause.setImageResource(!player.getPlayWhenReady() ? R.drawable.player_ic_play_small : R.drawable.player_ic_pause_small);
        }
    }

    @Override
    public BasePresenter onCreatePresenter() {
        return null;
    }
}
