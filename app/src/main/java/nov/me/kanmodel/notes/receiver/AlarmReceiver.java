package nov.me.kanmodel.notes.receiver;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.util.Date;

import nov.me.kanmodel.notes.MainActivity;
import nov.me.kanmodel.notes.R;

/**
 * 接受通知
 * Created by KanModel on 2017/12/30.
 */

public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "AlarmReceiver";

    private static final int NOTIFICATION_ID = 1000;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("NOTIFICATION")) {
            Log.d(TAG, "onReceive: title :" + intent.getStringExtra("title"));
            NotificationManager manager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            Intent intent2 = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent2, 0);
            Notification notify = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.logo_appwidget_preview)
                    .setContentTitle("时间便笺提醒")
//                    .setTicker("您的***项目即将到期，请及时处理！")
//                    .setTicker(intent.getStringExtra("title"))
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(intent.getStringExtra("title")))
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setNumber(1).build();
            manager.notify(NOTIFICATION_ID, notify);
        }
    }

    public static void setAlarm(Context context, long time, String title) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setAction("NOTIFICATION");
        intent.putExtra("title", title);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        int type = AlarmManager.RTC_WAKEUP;
        //new Date()：表示当前日期，可以根据项目需求替换成所求日期
        //getTime()：日期的该方法同样可以表示从1970年1月1日0点至今所经历的毫秒数
        long triggerAtMillis = new Date().getTime() + time;
//        long intervalMillis = 1000 * 60;
//        manager.setInexactRepeating(type, triggerAtMillis, intervalMillis, pi);
        if (manager != null) {
            manager.set(type, triggerAtMillis, pi);
        }
    }
}