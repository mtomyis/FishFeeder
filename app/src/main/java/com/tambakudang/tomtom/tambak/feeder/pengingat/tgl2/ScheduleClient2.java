package com.tambakudang.tomtom.tambak.feeder.pengingat.tgl2;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;


import java.util.Calendar;

public class ScheduleClient2 {
    // The hook into our service
    private ScheduleService2 mBoundService2;
    // The context to start the service in
    private Context mContext2;
    // A flag if we are connected to the service or not
    private boolean mIsBound2;

    public ScheduleClient2(Context context) {
        mContext2 = context;
    }

    /**
     * Call this to connect your activity to your service
     */
    public void doBindService() {
        // Establish a connection with our service
        mContext2.bindService(new Intent(mContext2, ScheduleService2.class), mConnection, Context.BIND_AUTO_CREATE);
        mIsBound2 = true;
    }

    /**
     * When you attempt to connect to the service, this connection will be called with the result.
     * If we have successfully connected we instantiate our service object so that we can call methods on it.
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with our service has been established,
            // giving us the service object we can use to interact with our service.
            mBoundService2 = ((ScheduleService2.ServiceBinder) service).getService();
        }

        public void onServiceDisconnected(ComponentName className) {
            mBoundService2 = null;
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
        if (mIsBound2) {
            // Detach our existing connection.
            mContext2.unbindService(mConnection);
            mIsBound2 = false;
        }
    }

    public void setAlarmForNotification(Calendar paditgl2) {
        mBoundService2.setAlarm(paditgl2);
    }

    public void cancelAlarm(){
        Intent intent = new Intent(mContext2, NotifyService2.class);
        intent.putExtra(NotifyService2.INTENT_NOTIFY2, true);
        PendingIntent pendingIntent = PendingIntent.getService(mContext2, 0, intent, 0);

        AlarmManager am = (AlarmManager) mContext2.getSystemService(Context.ALARM_SERVICE);
        am.cancel(pendingIntent);

    }
}
