package com.tambakudang.tomtom.tambak.feeder.pengingat.tgl7;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import java.util.Calendar;

public class ScheduleClient7 {
    // The hook into our service
    private ScheduleService7 mBoundService7;
    // The context to start the service in
    private Context mContext7;
    // A flag if we are connected to the service or not
    private boolean mIsBound7;

    public ScheduleClient7(Context context) {
        mContext7 = context;
    }

    /**
     * Call this to connect your activity to your service
     */
    public void doBindService() {
        // Establish a connection with our service
        mContext7.bindService(new Intent(mContext7, ScheduleService7.class), mConnection, Context.BIND_AUTO_CREATE);
        mIsBound7 = true;
    }

    /**
     * When you attempt to connect to the service, this connection will be called with the result.
     * If we have successfully connected we instantiate our service object so that we can call methods on it.
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with our service has been established,
            // giving us the service object we can use to interact with our service.
            mBoundService7 = ((ScheduleService7.ServiceBinder) service).getService();
        }

        public void onServiceDisconnected(ComponentName className) {
            mBoundService7 = null;
        }
    };

    /**
     * Tell our service to set an alarm for the given date
     * @param c a date to set the notification for
     */
//    public void setAlarmForNotification(Calendar c){
//        mBoundService.setAlarm(c);
//    }

    /**
     * When you have finished with the service call this method to stop it
     * releasing your connection and resources
     */
    public void doUnbindService() {
        if (mIsBound7) {
            // Detach our existing connection.
            mContext7.unbindService(mConnection);
            mIsBound7 = false;
        }
    }

    public void setAlarmForNotification(Calendar paditgl7) {
        mBoundService7.setAlarm(paditgl7);
    }

    public void cancelAlarm(){
        Intent intent = new Intent(mContext7, NotifyService7.class);
        intent.putExtra(NotifyService7.INTENT_NOTIFY7, true);
        PendingIntent pendingIntent = PendingIntent.getService(mContext7, 0, intent, 0);

        AlarmManager am = (AlarmManager) mContext7.getSystemService(Context.ALARM_SERVICE);
        am.cancel(pendingIntent);

    }
}
