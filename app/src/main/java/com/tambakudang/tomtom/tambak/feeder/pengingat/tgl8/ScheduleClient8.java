package com.tambakudang.tomtom.tambak.feeder.pengingat.tgl8;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import java.util.Calendar;

public class ScheduleClient8 {
    // The hook into our service
    private ScheduleService8 mBoundService8;
    // The context to start the service in
    private Context mContext8;
    // A flag if we are connected to the service or not
    private boolean mIsBound8;

    public ScheduleClient8(Context context) {
        mContext8 = context;
    }

    /**
     * Call this to connect your activity to your service
     */
    public void doBindService() {
        // Establish a connection with our service
        mContext8.bindService(new Intent(mContext8, ScheduleService8.class), mConnection, Context.BIND_AUTO_CREATE);
        mIsBound8 = true;
    }

    /**
     * When you attempt to connect to the service, this connection will be called with the result.
     * If we have successfully connected we instantiate our service object so that we can call methods on it.
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with our service has been established,
            // giving us the service object we can use to interact with our service.
            mBoundService8 = ((ScheduleService8.ServiceBinder) service).getService();
        }

        public void onServiceDisconnected(ComponentName className) {
            mBoundService8 = null;
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
        if (mIsBound8) {
            // Detach our existing connection.
            mContext8.unbindService(mConnection);
            mIsBound8 = false;
        }
    }

    public void setAlarmForNotification(Calendar paditgl8) {
        mBoundService8.setAlarm(paditgl8);
    }

    public void cancelAlarm(){
        Intent intent = new Intent(mContext8, NotifyService8.class);
        intent.putExtra(NotifyService8.INTENT_NOTIFY8, true);
        PendingIntent pendingIntent = PendingIntent.getService(mContext8, 0, intent, 0);

        AlarmManager am = (AlarmManager) mContext8.getSystemService(Context.ALARM_SERVICE);
        am.cancel(pendingIntent);

    }
}
