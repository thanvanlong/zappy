package com.longtv.zappy.ui.film.mediaplayer;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.gson.JsonObject;
import com.longtv.zappy.R;
import com.longtv.zappy.base.BaseFragment;
import com.longtv.zappy.common.Constants;
import com.longtv.zappy.common.adapter.ContentMediaAdapter;
import com.longtv.zappy.common.dialog.InfoYesNoDialog;
import com.longtv.zappy.common.fragment.MediaPlayerController;
import com.longtv.zappy.common.view.MediaPlayerView;
import com.longtv.zappy.network.dto.Content;
import com.longtv.zappy.network.dto.ContentType;
import com.longtv.zappy.network.dto.DataListDTO;
import com.longtv.zappy.service.MediaNotificationService;
import com.longtv.zappy.ui.HomeActivity;
import com.longtv.zappy.ui.film.HomeBoxFilmPresenter;
import com.longtv.zappy.ui.film.HomeBoxFilmPresenterImpl;
import com.longtv.zappy.ui.film.HomeBoxFilmView;
import com.longtv.zappy.ui.payment.PackagePaymentFragment;
import com.longtv.zappy.utils.DeviceUtils;
import com.longtv.zappy.utils.PrefManager;
import com.longtv.zappy.utils.StringUtils;

import java.util.Date;
import java.util.stream.Collectors;

import butterknife.BindView;

public class MediaPlayerFragment extends BaseFragment<HomeBoxFilmPresenter, HomeActivity> implements Player.Listener, HomeBoxFilmView {
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
    @BindView(R.id.tv_detail_content_title)
    TextView tvTitle;
    @BindView(R.id.item_film_description)
    TextView tvDesc;
    @BindView(R.id.item_film_categories)
    TextView tvCategories;
    @BindView(R.id.item_film_author)
    TextView tvAuthors;
    @BindView(R.id.next)
    ImageView ivNext;
    @BindView(R.id.ivShare)
    ImageView btnShare;
    @BindView(R.id.iv_like_video)
    ImageView ivLikeVideo;

    ExoPlayer player;
    private Handler handler;
    private Runnable runnable;
    private Content content;
    private MediaPlayerController controller;
    private long timeStart = 0;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_media_player;
    }

    private static MediaPlayerFragment mInstance;

    public static MediaPlayerFragment getInstance() {
        if (mInstance == null) {
            mInstance = new MediaPlayerFragment();
        }
        return mInstance;
    }

    @Override
    public void onPrepareLayout() {
        mInstance = this;
        timeStart = System.currentTimeMillis();
        createNotificationChannel();
        if (player != null) {
            player.pause();
            player.release();
        }
        Bundle bundle = getArguments();
        if (bundle != null) {
            Content content = ((Content) bundle.getSerializable(Constants.DATA));
            if (content != null) {
                this.content = content;
                if (content.isAccess()) {
                    getPresenter().getMoviesDetail(content.getId() + "");
                } else {
                    progressBar.setVisibility(View.GONE);
                    showPopup(content);
                }
                rcvContent.setLayoutManager(new GridLayoutManager(getViewContext(), 2));
                rcvContent.setAdapter(new ContentMediaAdapter(getViewContext(), content.getRelated()));
                initView(content);
            }
        }
        HomeActivity.getInstance().hideBottomBar();
        HomeActivity.getInstance().toggleTopBar(View.GONE);

        setListener();

    }

    private void initView(Content content) {
        tvTitle.setText(content.getName());
        tvCategories.setText( "Thể loại: " + StringUtils.getCategory(content.getTypes()));
        tvAuthors.setText("Đạo diễn: " + StringUtils.getAuthors(content.getAuthors()));
        tvDesc.setText(content.getDesc());

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");

                intent.putExtra(Intent.EXTRA_TEXT, Constants.APP_LINK + "movie?id=" + content.getId());
                getActivity().startActivity(Intent.createChooser(intent, "Share with"));
            }
        });

        if (content.isLike()) {
            ivLikeVideo.setImageResource(R.drawable.ic_liked_video_detail);
        } else {
            ivLikeVideo.setImageResource(R.drawable.ic_like_video_detail);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (player != null) {
            player.pause();
            Intent intent = new Intent(getViewContext(), MediaNotificationService.class);
            intent.putExtra("key_action", "pause");
            getActivity().startService(intent);
        }

        timeStart = System.currentTimeMillis() - timeStart;
        PrefManager.saveTimeOnFilm(getViewContext(), timeStart);
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
    public void onResume() {
        super.onResume();
        if (player != null) {
            HomeActivity.getInstance().toggleTopBar(8);
            player.play();
        }
    }

    private void initExoplayer(String url) {
        controller = new MediaPlayerController(getViewContext(), content, null);
        controller.initExoplayer(url);
        controller.setEventListener(new MediaPlayerController.EventListener() {
            @Override
            public void onNext() {
                player.release();
                progressBar.setVisibility(View.VISIBLE);
                showControl();
                Content tmp = content.getRelated().get(0);
                content.getRelated().add(content);
                tmp.setRelated(content.getRelated().stream().filter(item -> item.getId() != tmp.getId()).collect(Collectors.toList()));
                updateOnLoadingNewContent(tmp);
            }

            @Override
            public void onPlaybackStateChanged(int playbackState) {
                MediaPlayerFragment.this.onPlaybackStateChanged(playbackState);
            }
        });
        player = controller.getPlayer();
        playerView.setPlayer(player);
//        MediaItem mediaItem = MediaItem.fromUri(url);
//
//        Map<String, String> headersMap = new HashMap<>();
////        headersMap.put("authorization", "Bearer " + PrefManager.getAccessToken(getViewContext()));
//
//        player = new SimpleExoPlayer.Builder(getViewContext())
//                .setMediaSourceFactory(new
//                        DefaultMediaSourceFactory(new DefaultHttpDataSource.Factory().setDefaultRequestProperties(headersMap)))
//                .build();
//        player.setMediaItem(mediaItem);
//        player.prepare();
//        playerView.setPlayer(player);
//        player.play();
//        player.addListener(this);


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
                if (DeviceUtils.isLandscape(getViewContext())) {
                    DeviceUtils.forceRotateScreen(getViewContext(), Configuration.ORIENTATION_PORTRAIT);
                    DeviceUtils.showNavigationBar(getViewContext());
                    ivFullScreen.setImageResource(R.drawable.ic_fullscreen);
                } else {
                    DeviceUtils.forceRotateScreen(getViewContext(), Configuration.ORIENTATION_LANDSCAPE);
                    DeviceUtils.hideNavigationBar(getViewContext());
                    ivFullScreen.setImageResource(R.drawable.ic_exit_fullscreen);
                }
            }
        });

        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Content tmp = content.getRelated().get(0);
//                content.getRelated().add(content);
//                tmp.setRelated(content.getRelated().stream().filter(item -> item.getId() != tmp.getId()).collect(Collectors.toList()));
//                updateOnLoadingNewContent(tmp);
                getPresenter().getMoviesDetail(tmp.getId() + "");
            }
        });

        ivPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (content.isAccess()) {
                    doPauseResume();
                } else {
                    showPopup(content);
                }
            }
        });

        containerSeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.seekTo(player.getCurrentPosition() + 10000);
            }
        });

        ivLikeVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivLikeVideo.setImageResource(R.drawable.ic_liked_video_detail);
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("name", "Yêu thích");
                jsonObject.addProperty("movieId", content.getId());
                getPresenter().likeMovie(jsonObject);
            }
        });
    }

    private void updateOnLoadingNewContent(Content content) {
        if (player != null) {
            player.pause();
            player.release();
        }

        if (content != null) {
            this.content = content;
            if (content.isAccess()) {
                initExoplayer(content.getUrlStream());
            } else {
                progressBar.setVisibility(View.GONE);
                InfoYesNoDialog infoYesNoDialog = new InfoYesNoDialog();
                infoYesNoDialog.init(getViewContext(), "Nội dung yêu cầu trả phí để có thể trải nghiệm. Vui lòng mua nội dung để xem");
                infoYesNoDialog.setListener(new InfoYesNoDialog.ItemClickListener() {
                    @Override
                    public void btnYesClick() {
                        if (content.getGolds() > 20) {
                            //todo buy package
                        } else {
                            InfoYesNoDialog dialog = new InfoYesNoDialog();
                            dialog.init(getViewContext(), "Số dư không đủ. Vui lòng mua thêm vàng để thanh toán");
                            dialog.setListener(new InfoYesNoDialog.ItemClickListener() {
                                @Override
                                public void btnYesClick() {
                                    Bundle bundle = new Bundle();
                                    bundle.putString(Constants.TOOL_BAR, "Payment");
                                    HomeActivity.getInstance().addOrReplaceFragment(new PackagePaymentFragment(), bundle, true, PackagePaymentFragment.class.getSimpleName());
                                }

                                @Override
                                public void btnNoClick() {

                                }
                            });
                            dialog.show(HomeActivity.getInstance().getSupportFragmentManager(), "");
                        }
                    }

                    @Override
                    public void btnNoClick() {

                    }
                });

                infoYesNoDialog.show(HomeActivity.getInstance().getSupportFragmentManager(), "");
                playerView.setPlayer(null);
                ivPlayPause.setImageResource(R.drawable.player_ic_play_small);
                tvCurrentTime.setText("00:00");
                tvEndTime.setText("00:00");
            }
            rcvContent.setLayoutManager(new GridLayoutManager(getViewContext(), 2));
            rcvContent.setAdapter(new ContentMediaAdapter(getViewContext(), content.getRelated()));
            initView(content);
        }

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
    public void onPlaybackStateChanged(int playbackState) {

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
    public HomeBoxFilmPresenter onCreatePresenter() {
        return new HomeBoxFilmPresenterImpl(this);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (player != null) {
            player.pause();
            player.release();
        }
    }

    @Override
    public void onLoadGenreSuccess(DataListDTO<ContentType> contentTypes) {

    }

    @Override
    public void onLoadGenreError(String message) {

    }

    @Override
    public void onLoadMoviesSuccess(DataListDTO<Content> data) {
        if (data.getResults().size() > 0) {
            this.content.setRelated(data.getResults().stream().filter(it -> it.getId() != content.getId()).collect(Collectors.toList()));
            ((ContentMediaAdapter) rcvContent.getAdapter()).setmContents(this.content.getRelated());
        } else {
            ivNext.setEnabled(false);
            ivNext.setAlpha(0.5f);
        }
    }

    @Override
    public void onLoadMoviesError(String message) {

    }

    @Override
    public void onLoadSearchMoviesSuccess(DataListDTO<Content> data) {

    }

    @Override
    public void onLoadSearchMoviesError(String message) {

    }

    @Override
    public void onBuySuccess(Boolean data) {
        if (data) {
            HomeActivity.getInstance().setGold(PrefManager.getGold(getViewContext()) - content.getGolds());
            getPresenter().getMoviesDetail(content.getId() + "");
            Toast.makeText(getViewContext(), "Thanh toan thanh cong", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void doLoadFilmDetail(Content content) {
        if (content != null) {
            this.content = content;
            updateOnLoadingNewContent(content);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("genres.id", "$in:" + StringUtils.getContentTypesId(content.getTypes()));
            Log.e("anth", "doLoadFilmDetail: " + jsonObject);
            getPresenter().getContentRelated(jsonObject);
        }
    }

    private void createNotificationData(Content content) {
        if (content != null) {
            Intent intent = new Intent(getViewContext(), HomeActivity.class);
            String channelId = "" + (new Date()).getTime();
            intent.putExtra(Constants.IS_NOTIFICATION, true);
//            intent.putExtra(Constants.Extras.NOTIFICATION_RECORD_ID, content.getItemId());
            intent.putExtra(Constants.DATA, content);

            Bitmap imageNoti = null;
            try{
                imageNoti = Glide.with(this)
                        .asBitmap()
                        .load(content.getCoverImage())
                        .submit().get();
            }catch (Exception e){
                Log.e("anth", "createNotificationData: ", e);
            }

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            int notificationId = PrefManager.getCurrentNotificationId(getViewContext()) +1;
            PendingIntent pendingIntent;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                // >= Android 12
                pendingIntent = PendingIntent.getActivity(getViewContext(), notificationId /* Request code */, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            } else {
                pendingIntent = PendingIntent.getActivity(getViewContext(), notificationId /* Request code */, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
            }
            imageNoti = BitmapFactory.decodeResource(getResources(), R.drawable.logo_app_text);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(getViewContext(), channelId)
                            .setSmallIcon(R.drawable.logo_app_text)
                            .setContentTitle(content.getName())
                            .setContentText(content.getDesc())
                            .setAutoCancel(true)
                            .setSound(defaultSoundUri)
                            .setColor(getResources().getColor(R.color.white))
                            .setContentIntent(pendingIntent)
                            .setLargeIcon(imageNoti)
                            .setStyle(new NotificationCompat.BigPictureStyle()
                                    .bigPicture(imageNoti)
                                    .bigLargeIcon(null));
            ;
            NotificationManager notificationManager = (NotificationManager) getViewContext().getSystemService(Context.NOTIFICATION_SERVICE);

            // Since android Oreo notification channel is needed.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(channelId,
                        "Channel human readable title",
                        NotificationManager.IMPORTANCE_HIGH);
                notificationManager.createNotificationChannel(channel);
            }

//            Logger.e("notificationId" + notificationId);
            notificationManager.notify(notificationId /* ID of notification */, notificationBuilder.build());
            PrefManager.saveCurrentNotificationId(getViewContext(), notificationId);
        }

    }

    private void showPopup(Content content) {
        progressBar.setVisibility(View.GONE);
        InfoYesNoDialog infoYesNoDialog = new InfoYesNoDialog();
        infoYesNoDialog.init(getViewContext(), "Nội dung yêu cầu trả phí để có thể trải nghiệm. Vui lòng mua nội dung để xem");
        infoYesNoDialog.setListener(new InfoYesNoDialog.ItemClickListener() {
            @Override
            public void btnYesClick() {
                if (content.getGolds() < PrefManager.getGold(getViewContext())) {
                    //todo buy package
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("id", content.getId());
                    getPresenter().buyMovie(jsonObject);
                } else {
                    InfoYesNoDialog dialog = new InfoYesNoDialog();
                    dialog.init(getViewContext(), "Số dư không đủ. Vui lòng mua thêm vàng để thanh toán");
                    dialog.setListener(new InfoYesNoDialog.ItemClickListener() {
                        @Override
                        public void btnYesClick() {
                            Bundle bundle = new Bundle();
                            bundle.putString(Constants.TOOL_BAR, "Payment");
                            HomeActivity.getInstance().addOrReplaceFragment(new PackagePaymentFragment(), bundle, true, PackagePaymentFragment.class.getSimpleName());
                        }

                        @Override
                        public void btnNoClick() {

                        }
                    });
                    dialog.show(HomeActivity.getInstance().getSupportFragmentManager(), "");
                }
            }

            @Override
            public void btnNoClick() {

            }
        });
        infoYesNoDialog.show(HomeActivity.getInstance().getSupportFragmentManager(), "");
        if (player != null) {
            playerView.setPlayer(null);
            ivPlayPause.setImageResource(R.drawable.player_ic_play_small);
            tvCurrentTime.setText("00:00");
            sbProgress.setProgress(0);
            tvEndTime.setText("00:00");
        }
    }
}
