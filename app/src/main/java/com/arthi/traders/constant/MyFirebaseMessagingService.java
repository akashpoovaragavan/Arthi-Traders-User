package com.arthi.traders.constant;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import android.util.Log;

import com.arthi.traders.R;
import com.arthi.traders.view.HomeScreen;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static final String FCM_PARAM = "picture";
    private static final String CHANNEL_NAME = "ats";
    private static final String CHANNEL_DESC = "Firebase Cloud Messaging";
    private int numMessages = 0;

    @Override
    public void onNewToken(@NonNull String s) {
        Helper.sharedpreferences =getSharedPreferences(Helper.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = Helper.sharedpreferences.edit();
        editor.putString("token", s.trim());
        editor.apply();
        super.onNewToken(s);
        Log.d("s", s+"....");
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        Map<String, String> data = remoteMessage.getData();
        Log.d("FROM", remoteMessage.getFrom());
        sendNotification(notification, data);
    }

    @SuppressLint("WrongConstant")
    private void sendNotification(RemoteMessage.Notification notification, Map<String, String> data) {
        try {
            Bundle bundle = new Bundle();
            Log.e("data ",data.toString()+".");
            bundle.putString(FCM_PARAM, data.get(FCM_PARAM));

            Intent intent = new Intent(this, HomeScreen.class);
            intent.putExtras(bundle);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder notificationBuilder = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(notification!=null) {
                    notificationBuilder = new NotificationCompat.Builder(this, getString(R.string.notificationid))
                            .setContentTitle(notification.getTitle())
                            .setContentText(notification.getBody())
                            .setAutoCancel(true)
                            //.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                            .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sound2))
                            .setContentIntent(pendingIntent)
                            .setContentInfo("Hello")
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo))
                            .setColor(getColor(R.color.navy))
                            .setLights(Color.RED, 1000, 300)
                            .setDefaults(Notification.DEFAULT_VIBRATE)
                            .setNumber(++numMessages)
                            .setBadgeIconType(R.drawable.logo)
                            .setSmallIcon(R.drawable.ic_notifications);
                }
                else{
                    notificationBuilder = new NotificationCompat.Builder(this, getString(R.string.notificationid))
                            .setContentTitle(data.get("title"))
                            .setContentText(data.get("message"))
                            .setAutoCancel(true)
                            //.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                            .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sound2))
                            .setContentIntent(pendingIntent)
                            .setContentInfo("Hello")
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo))
                            .setColor(getColor(R.color.navy))
                            .setLights(Color.RED, 1000, 300)
                            .setDefaults(Notification.DEFAULT_VIBRATE)
                            .setNumber(++numMessages)
                            .setBadgeIconType(R.drawable.logo)
                            .setSmallIcon(R.drawable.ic_notifications);
                }
            }

            try {
                String picture = data.get(FCM_PARAM);
                if (picture != null && !"".equals(picture)) {
                    URL url = new URL(picture);
                    Bitmap bigPicture = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    notificationBuilder.setStyle(
                            new NotificationCompat.BigPictureStyle().bigPicture(bigPicture).setSummaryText(notification.getBody())
                    );
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(
                        getString(R.string.notificationid), CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
                );
                channel.setDescription(CHANNEL_DESC);
                channel.setShowBadge(true);
                channel.canShowBadge();
                channel.enableLights(true);
                channel.setLightColor(Color.RED);
                channel.enableVibration(true);
                channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});

                assert notificationManager != null;
                notificationManager.createNotificationChannel(channel);
            }

            assert notificationManager != null;
            notificationManager.notify(0, notificationBuilder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}