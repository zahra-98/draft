package com.example.isustain_app;

import android.app.Notification;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;
import androidx.media.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        getFirebaseMessage(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());

    }
    public void getFirebaseMessage(String title, String msg){

        //NotificationCompat. builder=new NotificationCompat.Builder(this,"myChannel");
        Notification notification = new Notification.Builder(this, "myChannel")
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle(title)
                .setContentText(msg)
                .setAutoCancel(true)
                .build();

        NotificationManagerCompat manager =NotificationManagerCompat.from(this
        );
        manager.notify(101,notification);




    }
}
