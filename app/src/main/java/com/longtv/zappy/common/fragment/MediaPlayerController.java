package com.longtv.zappy.common.fragment;

import android.content.Context;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.longtv.zappy.network.dto.Content;
import com.longtv.zappy.utils.PrefManager;

import java.util.HashMap;
import java.util.Map;

public class MediaPlayerController implements Player.Listener {
    private Context context;
    private static MediaPlayerController instance;
    private Content content;
    private EventListener eventListener;
    private ExoPlayer player;
    private StyledPlayerView playerView;

    public MediaPlayerController(Context context, Content content) {
        this.context = context;
        this.content = content;
    }

    public MediaPlayerController(Context context, Content content, StyledPlayerView playerView) {
        instance = this;
        this.context = context;
        this.content = content;
        this.playerView = playerView;
    }

    public static MediaPlayerController getInstance() {
        return instance;
    }

    public void initExoplayer(String url) {
        if (player != null) {
            player.release();
            player = null;
        }
        MediaItem mediaItem = MediaItem.fromUri(url);

        Map<String, String> headersMap = new HashMap<>();
        headersMap.put("authorization", "Bearer " + PrefManager.getAccessToken(context));

        player = new SimpleExoPlayer.Builder(context)
                .setMediaSourceFactory(new
                        DefaultMediaSourceFactory(new DefaultHttpDataSource.Factory().setDefaultRequestProperties(headersMap)))
                .build();
        player.setMediaItem(mediaItem);
        player.prepare();
        player.play();
        player.addListener(this);
    }

    public ExoPlayer getPlayer() {
        return player;
    }

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void nextContent() {
        eventListener.onNext();
    }

    @Override
    public void onPlaybackStateChanged(int playbackState) {
        Player.Listener.super.onPlaybackStateChanged(playbackState);
        eventListener.onPlaybackStateChanged(playbackState);
    }

    public Context getContext() {
        return context;
    }

    public Content getContent() {
        return content;
    }

    public EventListener getEventListener() {
        return eventListener;
    }

    public StyledPlayerView getPlayerView() {
        return playerView;
    }

    public interface EventListener {
        void onPlaybackStateChanged(int playbackState);
        void onNext();
    }
}
