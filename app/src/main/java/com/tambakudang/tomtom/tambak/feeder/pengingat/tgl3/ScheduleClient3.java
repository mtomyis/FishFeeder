package com.tambakudang.tomtom.tambak.feeder.pengingat.tgl3;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;


import java.util.Calendar;

public class ScheduleClient3 {
    // The hook into our service
    private ScheduleService3 mBoundService3;
    // The context to start the service in
    private Context mContext3;
    // A flag if we are connected to the service or not
    private boolean mIsBound3;

    public ScheduleClient3(Context context) {
        mContext3 = context;
    }

    /**
     * Call this to connect your activity to your service
     */
    public void doBindService() {
        // Establish a connection with our service
        mContext3.bindService(new Intent(mContext3, ScheduleService3.class), mConnection, Context.BIND_AUTO_CREATE);
        mIsBound3 = true;
    }

    /**
     * When you attempt to connect to the service, this connection will be called with the result.
     * If we have successfully connected we instantiate our service object so that we can call methods on it.
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with our service has been established,
            // giving us the service object we can use to interact with our service.
            mBoundService3 = ((ScheduleService3.ServiceBinder) service).getService();
        }

        public void onServiceDisconnected(ComponentName className) {
            mBoundService3 = null;
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
        if (mIsBound3) {
            // Detach our existing connection.
            mContext3.unbindService(mConnection);
            mIsBound3 = false;
        }
    }

    public void setAlarmForNotification(Calendar paditgl3) {
        mBoundService3.setAlarm(paditgl3);
    }

    public void cancelAlarm(){
        Intent intent = new Intent(mContext3, NotifyService3.class);
        intent.putExtra(NotifyService3.INTENT_NOTIFY3, true);
        PendingIntent pendingIntent = PendingIntent.getService(mContext3, 0, intent, 0);

        AlarmManager am = (AlarmManager) mContext3.getSystemService(Context.ALARM_SERVICE);
        am.cancel(pendingIntent);

    }
}
