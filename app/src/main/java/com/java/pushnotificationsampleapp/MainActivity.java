package com.java.pushnotificationsampleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {
    Receiver receive;
   TextView txt;
   String Data="";
    class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("com.package.ACTION_CLASS_MY_JOBS_REFRESH")) {
               String   Datavv = intent.getStringExtra("data");

                List<String> extractedUrls = extractUrls(Datavv);

                for (String url : extractedUrls)
                {
                    System.out.println(url);
                    Data = url;
                }

                System.out.println("----------------Recieved------------------"+Data);
                txt.setText(Data);
            }
        }

    }

    public static List<String> extractUrls(String text)
    {
        List<String> containedUrls = new ArrayList<String>();
        String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(text);

        while (urlMatcher.find())
        {
            containedUrls.add(text.substring(urlMatcher.start(0),
                    urlMatcher.end(0)));
        }

        return containedUrls;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        receive = new Receiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.package.ACTION_CLASS_MY_JOBS_REFRESH");
        registerReceiver(receive, filter);

        txt = findViewById(R.id.text_id);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url =Data;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
       //  getIntentData();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel("MyNotifications", "MyNotifications", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);

            //Configure Notification Channel
          /*  notificationChannel.setDescription("Game Notifications");
            notificationChannel.enableLights(true);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);*/

            manager.createNotificationChannel(notificationChannel);
        }
    }

   /* private void getIntentData() {

        Intent i = getIntent();
        String Title = i.getStringExtra("title");
        String Desc = i.getStringExtra("message");

        System.out.println("--------MainActivityDTA-------"+Title+","+Desc);
    }*/
}