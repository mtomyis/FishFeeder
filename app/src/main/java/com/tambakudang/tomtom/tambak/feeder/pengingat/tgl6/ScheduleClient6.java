package com.tambakudang.tomtom.tambak.feeder.pengingat.tgl6;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import java.util.Calendar;

public class ScheduleClient6 {
    // The hook into our service
    private ScheduleService6 mBoundService6;
    // The context to start the service in
    private Context mContext6;
    // A flag if we are connected to the service or not
    private boolean mIsBound6;

    public ScheduleClient6(Context context) {
        mContext6 = context;
    }

    /**
     * Call this to connect your activity to your service
     */
    public void doBindService() {
        // Establish a connection with our service
        mContext6.bindService(new Intent(mContext6, ScheduleService6.class), mConnection, Context.BIND_AUTO_CREATE);
        mIsBound6 = true;
    }

    /**
     * When you attempt to connect to the service, this connection will be called with the result.
     * If we have successfully connected we instantiate our service object so that we can call methods on it.
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with our service has been established,
            // giving us the service object we can use to interact with our service.
            mBoundService6 = ((ScheduleService6.ServiceBinder) service).getService();
        }

        public void onServiceDisconnected(ComponentName className) {
            mBoundService6 = null;
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
        if (mIsBound6) {
            // Detach our existing connection.
            mContext6.unbindService(mConnection);
            mIsBound6 = false;
        }
    }

    public void setAlarmForNotification(Calendar paditgl6) {
        mBoundService6.setAlarm(paditgl6);
    }

    public void cancelAlarm(){
        Intent intent = new Intent(mContext6, NotifyService6.class);
        intent.putExtra(NotifyService6.INTENT_NOTIFY6, true);
        PendingIntent pendingIntent = PendingIntent.getService(mContext6, 0, intent, 0);

        AlarmManager am = (AlarmManager) mContext6.getSystemService(Context.ALARM_SERVICE);
        am.cancel(pendingIntent);

    }
}
