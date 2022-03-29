package com.tambakudang.tomtom.tambak;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tambakudang.tomtom.tambak.capture.CaptureActivity;
import com.tambakudang.tomtom.tambak.feeder.FeederActivity;
import com.tambakudang.tomtom.tambak.grafik.GrafikActivity;
import com.tambakudang.tomtom.tambak.MainActivity;
import com.tambakudang.tomtom.tambak.kontrol.KontrolActivity;
import com.tambakudang.tomtom.tambak.laporan.LaporanActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;



public class MainActivity extends AppCompatActivity {

    public static String et_json_urlcekph = "http://wiridhub.id/tambak/ceknotifph.php";
    public static String et_json_urlceksuhu = "http://wiridhub.id/tambak/ceknotifsuhu.php";
    public static String et_json_urlcekoksi = "http://wiridhub.id/tambak/ceknotifoksi.php";
    public static String et_json_urlcekpakan = "http://wiridhub.id/tambak/ceknotifpakan.php";

    private RequestQueue mRequestQueue;
    private RequestQueue mRequestQueue2;
    private RequestQueue mRequestQueue3;
    private RequestQueue mRequestQueue4;

    public String judul ="Fish Feeder";
    private Handler mRepeatHandler;
    private Runnable mRepeatRunnable;
    private final static int UPDATE_INTERVAL = 5000;

    public static final int NOTIFICATION_IDsuhu = 1;
    public static final int NOTIFICATION_IDph = 2;
    public static final int NOTIFICATION_IDoksi = 3;
    public static final int NOTIFICATION_IDpakan = 4;


    public String kodesuhu, suhu, kodeph, kodepakan, pakan, ph, kodeoksi, oksi, judulsh, judulph, juduloksi, deskripsish, deskripsiph, deskripsioksi;

//    final static String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/fishfeeder/" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scheduleSuhu();

//        new File(path  ).mkdir();

        mRequestQueue = Volley.newRequestQueue(MainActivity.this);
        mRequestQueue2 = Volley.newRequestQueue(MainActivity.this);
        mRequestQueue3 = Volley.newRequestQueue(MainActivity.this);
        mRequestQueue4 = Volley.newRequestQueue(MainActivity.this);


    }

    private void scheduleSuhu(){
        mRepeatHandler = new Handler();
        mRepeatRunnable = new Runnable() {
            @Override
            public void run() {
                ceknotifsuhu();
                ceknotifph();
                ceknotifoksi();
                ceknotifpakan();

//                cek tabel suhu
                //getServerGrafik();
                //Toast.makeText(getApplicationContext(),"SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
                Log.d(MainActivity.class.getSimpleName(), "responseerror ");
                //Do something awesome
                mRepeatHandler.postDelayed(mRepeatRunnable, UPDATE_INTERVAL);
            }
        };

        mRepeatHandler.postDelayed(mRepeatRunnable, UPDATE_INTERVAL);
    }

//pakan
public void ceknotifpakan() {
    StringRequest stringRequest = new StringRequest(Request.Method.GET, et_json_urlcekpakan,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        kodepakan = jsonObject.getString("kode");
                        pakan = jsonObject.getString("pakan");

                        if (kodepakan.equals("1")){
                            float mpakan = Float.parseFloat(pakan);
                            CekNotifPakan(kodepakan, mpakan);
//                            Toast.makeText(MainActivity.this, " "+kodepakan, Toast.LENGTH_LONG).show();
                        }
                        else if(kodepakan.equals("0")){
                            float mpakan = Float.parseFloat(pakan);
//                            Toast.makeText(MainActivity.this, " "+kodepakan, Toast.LENGTH_LONG).show();
                        }
//                            menucontrol.setVisibleXRangeMaximum(65f);

                    } catch (JSONException e) {
                        e.printStackTrace();
//                            Toast.makeText(MainActivity.this, "Connection Failed ", Toast.LENGTH_SHORT).show();
                        //switch mati tom

                    }
                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            error.printStackTrace();
//                Toast.makeText(MainActivity.this, "Connection Failed ", Toast.LENGTH_SHORT).show();
            //switch mati tom
//                Toast.makeText(MainActivity.this, "Gagal " + error.toString(), Toast.LENGTH_SHORT).show();
        }
    });
    mRequestQueue4.add(stringRequest);
    mRequestQueue4.getCache().clear();
    stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
}

    private void CekNotifPakan(String id, float mpakan){
        if (kodepakan.equals("1")){
            String deskripsi ="Udang telah diberi pakan.";
            tampilNotification4(id,judul,deskripsi);
        }
    }

    public void tampilNotification4(String id,String judul,String deskripsi) {
        /*Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.okedroid.com/"));*/
        Intent intent = new Intent(this, DetailNotifActivity.class);
        String table = "pakan";
        intent.putExtra("tabel",table);
        intent.putExtra("judul",judul);
        intent.putExtra("deskripsi",deskripsi);
        //menginisialiasasi intent
        //PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //untuk memanggil activity di Notification
        /*
Menmbangun atau mensetup Notification dengan NotificationCompat.Builder
 */
        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher) //ikon notification
                .setContentTitle(judul)//judul konten
                .setContentIntent(resultPendingIntent)//memanggil object pending intent
                .setAutoCancel(true)//untuk menswipe atau menghapus notification
                .setContentText(deskripsi); //isi text

/*
Kemudian kita harus menambahkan Notification dengan menggunakan NotificationManager
 */

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(NOTIFICATION_IDpakan, builder.build()
        );
    }

//pakan

//suhu
    public void ceknotifsuhu() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, et_json_urlceksuhu,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            kodesuhu = jsonObject.getString("kode");
                            suhu = jsonObject.getString("suhu");

                            if (kodesuhu.equals("1")){
                                float msuhu = Float.parseFloat(suhu);
                                CekNotifSuhu(kodesuhu, msuhu);
                            }
//                            menucontrol.setVisibleXRangeMaximum(65f);

                        } catch (JSONException e) {
                            e.printStackTrace();
//                            Toast.makeText(MainActivity.this, "Connection Failed ", Toast.LENGTH_SHORT).show();
                            //switch mati tom

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
//                Toast.makeText(MainActivity.this, "Connection Failed ", Toast.LENGTH_SHORT).show();
                //switch mati tom
//                Toast.makeText(MainActivity.this, "Gagal " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        mRequestQueue.add(stringRequest);
        mRequestQueue.getCache().clear();
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
    }

    private void CekNotifSuhu(String id, float msuhu){
        if (kodesuhu.equals("1") && msuhu<=25){
            String deskripsi ="Temperature values range from "+msuhu+", less than the minimum level.";
            tampilNotification(id,judul,deskripsi);
        }
        else if (kodesuhu.equals("1") && msuhu>40){
            String deskripsi ="Temperature values range from "+msuhu+", more than the maximum level.";
            tampilNotification(id,judul,deskripsi);
        }
    }

    public void tampilNotification(String id,String judul,String deskripsi) {
        /*Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.okedroid.com/"));*/
        Intent intent = new Intent(this, DetailNotifActivity.class);
        String table = "suhu";
        intent.putExtra("tabel",table);
        intent.putExtra("judul",judul);
        intent.putExtra("deskripsi",deskripsi);
        //menginisialiasasi intent
        //PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //untuk memanggil activity di Notification
        /*
Menmbangun atau mensetup Notification dengan NotificationCompat.Builder
 */
        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher) //ikon notification
                .setContentTitle(judul)//judul konten
                .setContentIntent(resultPendingIntent)//memanggil object pending intent
                .setAutoCancel(true)//untuk menswipe atau menghapus notification
                .setContentText(deskripsi); //isi text

/*
Kemudian kita harus menambahkan Notification dengan menggunakan NotificationManager
 */

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(NOTIFICATION_IDsuhu, builder.build()
        );
    }
//suhu


//ph
    public void ceknotifph() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, et_json_urlcekph,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            kodeph = jsonObject.getString("kode");
                            ph = jsonObject.getString("ph");

                            if (kodeph.equals("1")){
                                float mph = Float.parseFloat(ph);
                                CekNotifph(kodeph, mph);
                            }
//                            menucontrol.setVisibleXRangeMaximum(65f);

                        } catch (JSONException e) {
                            e.printStackTrace();
//                            Toast.makeText(MainActivity.this, "Connection Failed ", Toast.LENGTH_SHORT).show();
                            //switch mati tom

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
//                Toast.makeText(MainActivity.this, "Connection Failed ", Toast.LENGTH_SHORT).show();
                //switch mati tom
//                Toast.makeText(MainActivity.this, "Gagal " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        mRequestQueue2.add(stringRequest);
        mRequestQueue2.getCache().clear();
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
    }

    private void CekNotifph(String id, float mph){
        if (kodeph.equals("1") && mph<7){
            String deskripsi ="PH values range from "+mph+", less than the minimum level.";
            tampilNotification2(id,judul,deskripsi);
        }
        else if (kodeph.equals("1") && mph>8){
            String deskripsi ="PH values range from "+mph+", more than the maximum level.";
            tampilNotification2(id,judul,deskripsi);
        }
    }

    public void tampilNotification2(String id,String judul,String deskripsi) {
        /*Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.okedroid.com/"));*/
        String table = "ph";
        Intent intent = new Intent(this, DetailNotifActivity.class);
        intent.putExtra("tabel",table);
        intent.putExtra("judul",judul);
        intent.putExtra("deskripsi",deskripsi);
        //menginisialiasasi intent
        //PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //untuk memanggil activity di Notification
        /*
Menmbangun atau mensetup Notification dengan NotificationCompat.Builder
 */
        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher) //ikon notification
                .setContentTitle(judul)//judul konten
                .setContentIntent(resultPendingIntent)//memanggil object pending intent
                .setAutoCancel(true)//untuk menswipe atau menghapus notification
                .setContentText(deskripsi); //isi text

/*
Kemudian kita harus menambahkan Notification dengan menggunakan NotificationManager
 */

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(NOTIFICATION_IDph, builder.build()
        );
    }
//ph


//oksigen
    public void ceknotifoksi() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, et_json_urlcekoksi,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            kodeoksi = jsonObject.getString("kode");
                            oksi = jsonObject.getString("oksi");

                            if (kodeoksi.equals("1")){
                                float moksi = Float.parseFloat(oksi);
                                CekNotifoksi(kodeoksi, moksi);
                            }
//                            menucontrol.setVisibleXRangeMaximum(65f);

                        } catch (JSONException e) {
                            e.printStackTrace();
//                            Toast.makeText(MainActivity.this, "Connection Failed ", Toast.LENGTH_SHORT).show();
                            //switch mati tom

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
//                Toast.makeText(MainActivity.this, "Connection Failed ", Toast.LENGTH_SHORT).show();
                //switch mati tom
//                Toast.makeText(MainActivity.this, "Gagal " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        mRequestQueue3.add(stringRequest);
        mRequestQueue3.getCache().clear();
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
    }

    private void CekNotifoksi(String id, float moksi){
        if (kodeoksi.equals("1") && moksi<4){
            String deskripsi ="Oxygen values range from "+moksi+", less than the minimum level.";
            tampilNotification3(id,judul,deskripsi);
        }
        else if (kodeoksi.equals("1") && moksi>5){
            String deskripsi ="Oxygen values range from "+moksi+", more than the maximum level.";
            tampilNotification3(id,judul,deskripsi);
        }
    }

    public void tampilNotification3(String id,String judul,String deskripsi) {
        /*Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.okedroid.com/"));*/
        String table = "oksi";
        Intent intent = new Intent(this, DetailNotifActivity.class);
        intent.putExtra("tabel",table);
        intent.putExtra("judul",judul);
        intent.putExtra("deskripsi",deskripsi);
        //menginisialiasasi intent
        //PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //untuk memanggil activity di Notification
        /*
Menmbangun atau mensetup Notification dengan NotificationCompat.Builder
 */
        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher) //ikon notification
                .setContentTitle(judul)//judul konten
                .setContentIntent(resultPendingIntent)//memanggil object pending intent
                .setAutoCancel(true)//untuk menswipe atau menghapus notification
                .setContentText(deskripsi); //isi text

/*
Kemudian kita harus menambahkan Notification dengan menggunakan NotificationManager
 */

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(NOTIFICATION_IDoksi, builder.build()
        );
    }
//oksigen

    public void grafik(View view) {
        startActivity(new Intent(MainActivity.this, GrafikActivity.class));
    }

    public void laporan(View view) {
        startActivity(new Intent(MainActivity.this, LaporanActivity.class));
    }

    public void kontrol(View view) {
        startActivity(new Intent(MainActivity.this, TentangActivity.class));
//        startActivity(new Intent(MainActivity.this, KontrolActivity.class));
    }

    public void keluar(View view) {
        showAlertDialog();
    }

    @Override
    public void onBackPressed() {
        showAlertDialog();
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage("Exit ?")
                .setNegativeButton("No", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){
                        dialogInterface.dismiss();
                        finish();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void capture(View view) {
        startActivity(new Intent(MainActivity.this, CaptureActivity.class));
    }

    public void makan(View view) {
        startActivity(new Intent(MainActivity.this, FeederActivity.class));

    }

    public void tentang(View view) {

    }
}
