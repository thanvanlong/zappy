package com.longtv.zappy.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.longtv.zappy.R;
import com.longtv.zappy.common.Constants;
import com.longtv.zappy.network.dto.Content;
import com.longtv.zappy.ui.HomeActivity;
import com.longtv.zappy.utils.PrefManager;
import com.longtv.zappy.utils.StringUtils;

import java.util.Date;

public class NotificationService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        if (message != null && message.getNotification() != null) {
            Log.e("anth", "onMessageReceived: " + new Gson().toJson(message.getNotification()) + " " + message.getData());
        }
        Content content = new Content();
        content.setName("Test");
        content.setDesc("Test");
        content.setCoverImage("https://www.google.com.vn/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png");
        createNotificationData(content);

    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }

    private void createNotificationData(Content content) {
        if (content != null) {
            Intent intent = new Intent(this, HomeActivity.class);
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
                e.printStackTrace();
            }

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            int notificationId = PrefManager.getCurrentNotificationId(this) +1;
            PendingIntent pendingIntent;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                // >= Android 12
                pendingIntent = PendingIntent.getActivity(this, notificationId /* Request code */, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            } else {
                pendingIntent = PendingIntent.getActivity(this, notificationId /* Request code */, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
            }

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(this, channelId)
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
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            // Since android Oreo notification channel is needed.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(channelId,
                        "Channel human readable title",
                        NotificationManager.IMPORTANCE_HIGH);
                notificationManager.createNotificationChannel(channel);
            }

//            Logger.e("notificationId" + notificationId);
            notificationManager.notify(notificationId /* ID of notification */, notificationBuilder.build());
            PrefManager.saveCurrentNotificationId(this, notificationId);
        }

    }
}
