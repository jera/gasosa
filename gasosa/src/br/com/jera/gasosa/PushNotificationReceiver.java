package br.com.jera.gasosa;

import java.util.UUID;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class PushNotificationReceiver extends BroadcastReceiver {
    private static Class<?> OPEN_APP_ACTIVITY = Principal.class;

    private NotificationManager notificationManager;
    private UUID notifUUID = UUID.randomUUID();

    @Override
    public void onReceive(Context context, Intent intent)
    {
        abortBroadcast();

        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int icon = R.drawable.notification;
        CharSequence tickerText = intent.getStringExtra("NOTIFICATION_TITLE");
        long when = System.currentTimeMillis();

        Notification notification = new Notification(icon, tickerText, when);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        CharSequence contentTitle = intent.getStringExtra("NOTIFICATION_TITLE");
        CharSequence contentText  = intent.getStringExtra("NOTIFICATION_DETAILS");

        Intent notificationIntent;
        String notificationURL = intent.getStringExtra("NOTIFICATION_URL");

        // workaround (xtify limitations)
        if (notificationURL != null && notificationURL.startsWith("http://www.jera.com.br/?url="))
        {
            notificationURL = notificationURL.replaceFirst("http://www.jera.com.br/\\?url=", "");
            notificationIntent = new Intent(Intent.ACTION_VIEW);
            notificationIntent.setData(Uri.parse(notificationURL));
            notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } 
        else if (notificationURL != null && !"".equals(notificationURL))
        {
            notificationIntent = new Intent(Intent.ACTION_VIEW);
            notificationIntent.setData(Uri.parse(notificationURL));
            notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } 
        else 
        {
            notificationIntent = new Intent(context, OPEN_APP_ACTIVITY);
        }

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
        notificationManager.notify(notifUUID.hashCode(), notification);
    }

}
