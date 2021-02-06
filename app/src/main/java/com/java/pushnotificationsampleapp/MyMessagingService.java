package com.java.pushnotificationsampleapp;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

public class MyMessagingService  extends FirebaseMessagingService {

    private static final String TAG = "Notification";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        System.out.println("--------MessageReceived-----" +"MessageReceived");
        ShowNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());


        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());


        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            System.out.println("--------Data Payload:-----" +remoteMessage.getData().size());
            System.out.println("--------Data Payload:-----" +remoteMessage.getData().toString());


        }
    }

    private void ShowNotification(String title, String message) {

        // Pass the intent to PendingIntent to start the
        // next Activity

        Intent intent
                = new Intent(this, MainActivity2.class);
        // Here FLAG_ACTIVITY_CLEAR_TOP flag is set to clear
        // the activities present in the activity stack,
        // on the top of the Activity that is to be launched
        intent.putExtra("title",title);
        intent.putExtra("message",message);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent
                = PendingIntent.getActivity(
                this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "MyNotifications")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setAutoCancel(true)
                //.setSound(defaultSound)
                .setContentText(message)
                //.setContentIntent(pendingIntent)
               // .setStyle(style)
               // .setLargeIcon(bitmap)
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(999, notificationBuilder.build());

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("com.package.ACTION_CLASS_MY_JOBS_REFRESH");
        broadcastIntent.putExtra("data",message);
        sendBroadcast(broadcastIntent);


      /*  Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,0);
*/



    }



}
