package com.lpf.learn;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;

public class NotificationActivity extends AppCompatActivity {

    private Context mContext;

    private final int notificationId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        mContext = this;

        initViews();
    }

    private void initViews() {
        findViewById(R.id.sendNotification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext,NotificationResponseActivity.class);
                PendingIntent pi = PendingIntent.getActivity(mContext,0,intent,0);

                NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
//                manager.cancel(notificationId);                            // the second way to show once ,then dismiss
                Notification notification = new NotificationCompat
                        .Builder(mContext)
                        .setContentTitle("This is content file")
                        .setContentText("This is content text")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                        .setContentIntent(pi)
                        .setAutoCancel(true)                                //show once ,then dismiss
                        .setVibrate(new long[]{0,1000,1000,1000,1000,1000}) //vibrate one second,slient one second, and loop
                        .setLights(Color.RED,1000,1000)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("Learn how to build " +                     //show big text
                                "notifications,send and sync data,and use voice actions.Get the officaial " +               //show big picture
                                "Android IDE and developer tools to build apps for Android"))
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher)))
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .build();
//                notification.defaults |= Notification.DEFAULT_VIBRATE;    //vibrate once
//                notification.defaults |= Notification.DEFAULT_LIGHTS;
//                notification.ledARGB = Color.BLUE;
                manager.notify(notificationId,notification);
            }
        });
    }
}
