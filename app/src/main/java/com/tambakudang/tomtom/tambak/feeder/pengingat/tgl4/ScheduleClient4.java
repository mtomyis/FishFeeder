package com.tambakudang.tomtom.tambak.feeder.pengingat.tgl4;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import java.util.Calendar;

public class ScheduleClient4 {
    // The hook into our service
    private ScheduleService4 mBoundService4;
    // The context to start the service in
    private Context mContext4;
    // A flag if we are connected to the service or not
    private boolean mIsBound4;

    public ScheduleClient4(Context context) {
        mContext4 = context;
    }

    /**
     * Call this to connect your activity to your service
     */
    public void doBindService() {
        // Establish a connection with our service
        mContext4.bindService(new Intent(mContext4, ScheduleService4.class), mConnection, Context.BIND_AUTO_CREATE);
        mIsBound4 = true;
    }

    /**
     * When you attempt to connect to the service, this connection will be called with the result.
     * If we have successfully connected we instantiate our service object so that we can call methods on it.
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with our service has been established,
            // giving us the service object we can use to interact with our service.
            mBoundService4 = ((ScheduleService4.ServiceBinder) service).getService();
        }

        public void onServiceDisconnected(ComponentName className) {
            mBoundService4 = null;
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
        if (mIsBound4) {
            // Detach our existing connection.
            mContext4.unbindService(mConnection);
            mIsBound4 = false;
        }
    }

    public void setAlarmForNotification(Calendar paditgl4) {
        mBoundService4.setAlarm(paditgl4);
    }

    public void cancelAlarm(){
        Intent intent = new Intent(mContext4, NotifyService4.class);
        intent.putExtra(NotifyService4.INTENT_NOTIFY4, true);
        PendingIntent pendingIntent = PendingIntent.getService(mContext4, 0, intent, 0);

        AlarmManager am = (AlarmManager) mContext4.getSystemService(Context.ALARM_SERVICE);
        am.cancel(pendingIntent);

    }
}
