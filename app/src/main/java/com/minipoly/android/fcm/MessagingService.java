package com.minipoly.android.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.minipoly.android.MainActivity;
import com.minipoly.android.R;
import com.minipoly.android.utils.LocalData;


/**
 * Created by kameloov on 9/2/2016.
 */
public class MessagingService extends FirebaseMessagingService {
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        final String refreshedToken = s;
        Log.e("Token", refreshedToken);
        LocalData.saveDeviceToken(refreshedToken);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String fromId = "";
        String gameId = "";
        String link = "";
        if (remoteMessage.getData() != null) {
            link = remoteMessage.getData().get("link");
            Log.e("LINK from Notification", link);
        } else
            Log.e("LINK from Notification", "data is null");
        String body = "";
        if (remoteMessage.getNotification() != null) {
            body = remoteMessage.getNotification().getBody();
        }
        link = "https://minipoly.page.link?link=https%3A%2F%2Fminipoly.page.link%2F1%2FyezWHZC5GyZpkNwYHsOD&apn=com.minipoly.android&dfl=https%3A%2F%2Fwww.arvana.io&st=Arvana%20Blog%20-%20All%20you%20need%20to%20know%20about%20arvana&sd=&si=";
        sendNotification(body, fromId, link);
    }

    private void sendNotification(String messageBody, String fromId, String link) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(link));
        intent.putExtra("fromId", fromId);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, getString(R.string.default_notification_channel_id))
                        .setSmallIcon(R.drawable.ic_bidders)
                        .setContentTitle(getResources().getString(R.string.app_name))
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}
