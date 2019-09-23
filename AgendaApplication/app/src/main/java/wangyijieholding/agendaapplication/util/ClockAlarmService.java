package wangyijieholding.agendaapplication.util;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import wangyijieholding.agendaapplication.events.EData;
import wangyijieholding.agendaapplication.R;
import wangyijieholding.agendaapplication.MainActivity;

/**
 * Send notification based on the events in the calendar
 * Created by the Yijie Wang on wangyijie6646
 * Not literal alarms, no sound will be involved
 */

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class ClockAlarmService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_SETALARM = "wangyijieholding.agendaapplication.action.SetAlarm";
    public static final String ACTION_BAZ = "wangyijieholding.agendaapplication.action.SendNotification";

    // TODO: Rename parameters
    public static final String EXTRA_PARAM1 = "wangyijieholding.agendaapplication.extra.PARAM1";
    public static final String EXTRA_PARAM2 = "wangyijieholding.agendaapplication.extra.PARAM2";

    static final String DAY_DETAIL_NOTIFICATION_EXTRA = "selectedDateIndex";

    public ClockAlarmService() {
        super("ClockAlarmService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SETALARM.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionSetAlarm(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionSetAlarm(String param1, String param2) {
        // TODO: Handle action Foo
        Runnable r = new Runnable() {
            @Override
            public void run() {
                NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(ClockAlarmService.this)
                        .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                        .setContentTitle("Try This!")
                        .setContentText("Wow!");
                Intent intent = new Intent(ClockAlarmService.this,MainActivity.class);
                intent.putExtra(DAY_DETAIL_NOTIFICATION_EXTRA, EData.getTimeDifference2015Today());

                //TODO: REMOVE THIS FUNCTION FROM THE DEBUG BUTTON
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(ClockAlarmService.this);
                stackBuilder.addParentStack(MainActivity.class);
                stackBuilder.addNextIntent(intent);
                PendingIntent resultPendingIntent =
                        stackBuilder.getPendingIntent(
                                0,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );
                nBuilder.setContentIntent(resultPendingIntent);
                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify("EventReminder",1313, nBuilder.build());
                mNotificationManager.notify("EventReminder",1314, nBuilder.build());
            }
        };
        new Thread(r).start();
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
