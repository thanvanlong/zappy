package com.longtv.zappy.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadata;
import android.os.IBinder;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.exoplayer2.Player;
import com.longtv.zappy.BuildConfig;
import com.longtv.zappy.R;
import com.longtv.zappy.common.fragment.MediaPlayerController;
import com.longtv.zappy.network.dto.Content;
import com.longtv.zappy.ui.HomeActivity;

public class MediaNotificationService extends Service {
    private static final int ACTION_STOP = 5;
    public static final int ACTION_RESUME = 1;
    public static final int ACTION_PAUSE = 2;
    public static final int ACTION_NEXT = 3;
    public static final int ACTION_BACK_HOME = 4;
    public static final int ACTION_PREV = 6;
    private boolean isPlaying ;
    private boolean isEndSeries = false;
    private boolean canPlay;
    private boolean isStartSeries = false;
    private boolean isShowNotification = false;
    private long position;
    private long duration;
    private Content contentObj;
    private Player player;
    private MediaSessionCompat mediaSessionCompat;
    private static MediaNotificationService instance;

    public static MediaNotificationService getInstance() {
        return instance;
    }

    public static void setInstance(MediaNotificationService instance) {
        MediaNotificationService.instance = instance;
    }

    public Content getContentObj() {
        return contentObj;
    }

    public void setContentObj(Content contentObj) {
        this.contentObj = contentObj;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public boolean isEndSeries() {
        return isEndSeries;
    }

    public void setEndSeries(boolean endSeries) {
        isEndSeries = endSeries;
    }

    public boolean isCanPlay() {
        return canPlay;
    }

    public void setCanPlay(boolean canPlay) {
        this.canPlay = canPlay;
    }

    public boolean isStartSeries() {
        return isStartSeries;
    }

    public void setStartSeries(boolean startSeries) {
        isStartSeries = startSeries;
    }

    public boolean isShowNotification() {
        return isShowNotification;
    }

    public void setShowNotification(boolean showNotification) {
        isShowNotification = showNotification;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String actionKey = intent.getStringExtra("key_action");
        mediaSessionCompat = new MediaSessionCompat(this, "media_notification");
        if (actionKey != null) {
            loadData();
            sendNotification(contentObj, isEndSeries, isStartSeries, position, duration);
        }
        int actionExtra = intent.getIntExtra("longtv", 0);
        if (actionExtra == 3 || actionExtra == 6) {
            loadData();
        }
        Log.e("longtv", "onStartCommand: " + actionExtra );

        handleMedia(actionExtra);
        return START_NOT_STICKY;
    }


    public void sendNotification(Content obj, boolean isEndSeries, boolean isStartSeries, long currentPosition, long duration) {
        if (obj != null) {
            Glide.with(this)
                    .asBitmap()
                    .load(contentObj.getCoverImage())
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            Log.e("anth", "onResourceReady: co vao day k");
                            showNotification(resource, obj.getName(), isEndSeries, isStartSeries, currentPosition, duration);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });
        }
    }

    private PendingIntent getPendingIntent(int action, Context context) {
        Intent intent = new Intent(this, MediaNotificationReceiver.class);
        intent.putExtra("action_media", action);
        return PendingIntent
                .getBroadcast(this,
                        action, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
    }


    private void handleMedia(int action) {
        switch (action) {
            case ACTION_PAUSE:
                onPause();
                break;
            case ACTION_RESUME:
                onResume();
                break;
            case ACTION_NEXT:
                onNext();
                break;
            case ACTION_BACK_HOME:
                onBack();
                break;
            case ACTION_STOP:
                onStop();
                break;
            case ACTION_PREV:
                onPrev();
                break;
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    private void onBack() {
        final Intent intents = this.getPackageManager()
                .getLaunchIntentForPackage(BuildConfig.APPLICATION_ID);
        intents.putExtra("notification", "notification");

        intents.putExtra("stateMedia", ""+ isPlaying);
        this.startActivity(intents);
    }

    private void onNext() {
        if (MediaPlayerController.getInstance() != null && MediaPlayerController.getInstance().getPlayer() != null) {
            MediaPlayerController.getInstance().nextContent();
        }

        isPlaying = true;
        sendNotification(contentObj, isEndSeries, isStartSeries, position, duration);

    }

    private void onPrev() {
//        if (CenterMediaControllerFragment.getInstance() != null) {
//            CenterMediaControllerFragment.getInstance().prevContent();
//        }

        isPlaying = true;
        sendNotification(contentObj, isEndSeries, isStartSeries, position, duration);
    }

    private void onResume() {
        if (MediaPlayerController.getInstance() != null && MediaPlayerController.getInstance().getPlayer() != null) {
            player.setPlayWhenReady(!player.getPlayWhenReady());
        }
        isPlaying = true;
        sendNotification(contentObj, isEndSeries, isStartSeries, position, duration);
    }

    private void onPause() {
        if (MediaPlayerController.getInstance() != null && MediaPlayerController.getInstance().getPlayer() != null) {
            player.setPlayWhenReady(!player.getPlayWhenReady());
        }
        isPlaying = false;
        sendNotification(contentObj, isEndSeries, isStartSeries, position, duration);
    }

    private void onStop() {
        stopSelf();

    }

    public void showNotification(Bitmap bitmap, String title, boolean isEndSeries, boolean isStartSeries, long currentPosition, long duration) {

        updateMediaPlaybackState(duration, currentPosition);
        Intent backIntent = new Intent(this, HomeActivity.class);
        backIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("fragment", "media");
        final PendingIntent pendingIntent = PendingIntent.getActivities(this, 1,
                new Intent[] {backIntent, intent}, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.logo_app_text)
                .setColor(getResources().getColor(R.color.black))
                .setLargeIcon(bitmap)
                .setContentText("Than Van Long")
                .setContentTitle(title)
                .setPriority(Notification.PRIORITY_MAX)
                .setContentIntent(pendingIntent);
        boolean isSeries = false;
        builder.setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                .setShowActionsInCompactView(0,1,2)
                .setShowCancelButton(true)
                .setCancelButtonIntent(getPendingIntent(ACTION_STOP, this))
                .setMediaSession(mediaSessionCompat.getSessionToken()));
        builder
                .addAction(R.drawable.player_ic_prev_video_small, "Prev",
                        getPendingIntent(ACTION_PREV, this))
                .addAction(isPlaying ? R.drawable.player_ic_pause_small : R.drawable.player_ic_play_small, "Pause",
                        isPlaying ? getPendingIntent(ACTION_PAUSE, this) : getPendingIntent(ACTION_RESUME, this))
                .addAction(R.drawable.player_ic_next_video_small
                        , "Next",
                        getPendingIntent(ACTION_NEXT, this));
        Notification notificationMobile = builder.build();
        startForeground(1, notificationMobile);
    }

    private void updateMediaPlaybackState(long duration, long position) {
//        if (CenterMediaControllerFragment.getInstance() != null) {
//            mediaSessionCompat.setCallback(new PlayerMediaSessionCallback(CenterMediaControllerFragment.getInstance().getPlayer()));
//            MediaMetadata mediaMetadata =
//                    new MediaMetadata.Builder().putLong(MediaMetadata.METADATA_KEY_DURATION, duration).build();
//            PlaybackStateCompat.Builder mStateBuilder = new PlaybackStateCompat.Builder()
//                    .setState(isPlaying ? PlaybackStateCompat.STATE_PLAYING : PlaybackStateCompat.STATE_PAUSED,  position, 1.0f)
//                    .setActions(PlaybackStateCompat.ACTION_SEEK_TO);
//            mediaSessionCompat.setMetadata(MediaMetadataCompat.fromMediaMetadata(mediaMetadata));
//            mediaSessionCompat.setPlaybackState(mStateBuilder.build());        }
    }

    private void loadData() {
        if (MediaPlayerController.getInstance() != null && MediaPlayerController.getInstance().getPlayer() != null) {
            player = MediaPlayerController.getInstance().getPlayer();
            contentObj = MediaPlayerController.getInstance().getContent();
            position = player.getCurrentPosition();
            duration = player.getDuration();
            isPlaying = player.getPlayWhenReady();
        }
    }


    class PlayerMediaSessionCallback extends MediaSessionCompat.Callback {
        private Player player;

        public PlayerMediaSessionCallback(Player player) {
            this.player = player;
        }

        public Player getPlayer() {
            return player;
        }

        public void setPlayer(Player player) {
            this.player = player;
        }

        @Override
        public void onSeekTo(long pos) {
            super.onSeekTo(pos);
            player.seekTo(pos);
            sendNotification(contentObj, isEndSeries, isStartSeries, position, duration);
        }
    }
}
