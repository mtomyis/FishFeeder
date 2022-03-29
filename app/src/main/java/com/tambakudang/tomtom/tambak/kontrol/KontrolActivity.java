package com.tambakudang.tomtom.tambak.kontrol;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tambakudang.tomtom.tambak.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KontrolActivity extends AppCompatActivity {
    public static final String TAG = KontrolActivity.class.getSimpleName();

    public ProgressBar p_Tunggu;
    private RequestQueue mRequestQueue;
    private RelativeLayout menucontrol;
    private TextView idtvkoneksi;
    private Button test;

    private final static int WRITE_EXTERNAL_STORAGE_PERMMISSION_RESULT = 0;

    protected Switch switchSensor, switchFeeder;

    String kode, status;
    String kodef, statusf;

    public static String et_json_urlcek = "http://wiridhub.id/tambak/cekmesin.php";
    public static String et_json_url = "http://wiridhub.id/tambak/updatemotor.php";

    public static String et_json_urlcek_feed = "http://wiridhub.id/tambak/cekfeeder.php";
    public static String et_json_url_feed = "http://wiridhub.id/tambak/updatefeeder.php";

    public static String et_json_urlcek_umur = "http://wiridhub.id/tambak/cekumur.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kontrol);
        setTitle("Control");

        checkReadExternalStoragePermission();

        mRequestQueue = Volley.newRequestQueue(KontrolActivity.this);
        menucontrol = findViewById(R.id.idmenucontrol);
        p_Tunggu = findViewById(R.id.idtunggucontrol);
        idtvkoneksi = findViewById(R.id.idtvkoneksi);
        test = findViewById(R.id.idtestfeeder);
//        switchFeeder = findViewById(R.id.switchfeeder);
//        switchSensor = findViewById(R.id.switchsensor);

//        cekmotor();
//        cekfeeder();
//        switchSensor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
//                if (bChecked) {
//                    hidupkanmesin();
////                    Toast.makeText(KontrolActivity.this, "Sensor dihidupkan", Toast.LENGTH_SHORT).show();
//                } else {
//                    matikanmesin();
////                    Toast.makeText(KontrolActivity.this, "Sensor dimatikan", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

//        if (switchSensor.isChecked()) {
//            Toast.makeText(KontrolActivity.this, "Sensor dihidupkan", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(KontrolActivity.this, "Sensor dimatikan", Toast.LENGTH_SHORT).show();
//        }

//        switchFeeder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
//                if (bChecked) {
//                    hidupkanfeeder();
//                } else {
//                    matikanfeeder();
//                }
//            }
//        });

//        if (switchFeeder.isChecked()) {
//            Toast.makeText(KontrolActivity.this, "Feeder dihidupkan", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(KontrolActivity.this, "Feeder dimatikan", Toast.LENGTH_SHORT).show();
//        }


    }
//    public void cekfeeder() {
//        p_Tunggu.setVisibility(View.VISIBLE);
//        menucontrol.setVisibility(View.GONE);
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, et_json_urlcek_feed,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.i(TAG, response.toString());
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//
//                            kodef = jsonObject.getString("kodek");
//                            statusf = jsonObject.getString("status");
//
//                            int result = Integer.parseInt(kodef);
//                            Toast.makeText(KontrolActivity.this, "kode "+result, Toast.LENGTH_LONG).show();
//
//                            if (kodef.equals("1")){
//                                switchFeeder.setChecked(true);
//                            }
//                            else if (kodef.equals("0")){
//                                switchFeeder.setChecked(false);
//                            }
//
////                            if (result>0){
////                                switchFeeder.setChecked(true);
////                                Toast.makeText(KontrolActivity.this, "Feed machine is " +statusf, Toast.LENGTH_SHORT).show();
////                            }
//                            p_Tunggu.setVisibility(View.GONE);
//                            menucontrol.setVisibility(View.VISIBLE);
//                            //switch hidup tom
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            p_Tunggu.setVisibility(View.GONE);
//                            menucontrol.setVisibility(View.VISIBLE);
//                            Toast.makeText(KontrolActivity.this, "Connection Failed ", Toast.LENGTH_SHORT).show();
//                            switchFeeder.setChecked(false);
//                            //switch mati tom
//
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                p_Tunggu.setVisibility(View.GONE);
//                menucontrol.setVisibility(View.VISIBLE);
//                error.printStackTrace();
//                Toast.makeText(KontrolActivity.this, "Connection Failed ", Toast.LENGTH_SHORT).show();
//                switchFeeder.setChecked(false);
//
//            }
//        });
//        mRequestQueue.getCache().clear();
//        mRequestQueue.add(stringRequest);
//        stringRequest.setRetryPolicy(new RetryPolicy() {
//            @Override
//            public int getCurrentTimeout() {
//                return 50000;
//            }
//
//            @Override
//            public int getCurrentRetryCount() {
//                return 50000;
//            }
//
//            @Override
//            public void retry(VolleyError error) throws VolleyError {
//
//            }
//        });
//    }
//
//    public void hidupkanfeeder() {
//        p_Tunggu.setVisibility(View.VISIBLE);
//        menucontrol.setVisibility(View.GONE);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, et_json_url_feed,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.i(TAG, response.toString());
//                        try {
//
//                            JSONObject jsonObject = new JSONObject(response);
//                            String berhasil = jsonObject.getString("success");
//                            if (berhasil.equals("1")){
//                                Toast.makeText(KontrolActivity.this, "Feed machine is on ", Toast.LENGTH_SHORT).show();
//                                switchFeeder.setChecked(true);
//                            }
////                            menucontrol.setVisibleXRangeMaximum(65f);
//                            p_Tunggu.setVisibility(View.GONE);
//                            menucontrol.setVisibility(View.VISIBLE);
//                            //switch hidup tom
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Toast.makeText(KontrolActivity.this, "Connection Failed ", Toast.LENGTH_SHORT).show();
//                            p_Tunggu.setVisibility(View.GONE);
//                            menucontrol.setVisibility(View.VISIBLE);
//                            switchFeeder.setChecked(false);
//                            //switch mati tom
//
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                p_Tunggu.setVisibility(View.GONE);
//                menucontrol.setVisibility(View.VISIBLE);
//                error.printStackTrace();
//                Toast.makeText(KontrolActivity.this, "Connection Failed ", Toast.LENGTH_SHORT).show();
//                switchFeeder.setChecked(false);
//                //switch mati tom
////                Toast.makeText(kontrolActivity.this, "Gagal " + error.toString(), Toast.LENGTH_SHORT).show();
//            }
//        })
//
//        {
//            String kodef = "1";
//            String statusf = "On";
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("kode", kodef);
//                params.put("status", statusf);
//                return params;
//            }
//        };
//        mRequestQueue.add(stringRequest);
//        mRequestQueue.getCache().clear();
//        stringRequest.setRetryPolicy(new RetryPolicy() {
//            @Override
//            public int getCurrentTimeout() {
//                return 50000;
//            }
//
//            @Override
//            public int getCurrentRetryCount() {
//                return 50000;
//            }
//
//            @Override
//            public void retry(VolleyError error) throws VolleyError {
//
//            }
//        });
//    }
//
//    public void matikanfeeder() {
//        p_Tunggu.setVisibility(View.VISIBLE);
//        menucontrol.setVisibility(View.GONE);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, et_json_url_feed,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.i(TAG, response.toString());
//                        try {
//
//                            JSONObject jsonObject = new JSONObject(response);
//                            String berhasil = jsonObject.getString("success");
//                            if (berhasil.equals("1")){
//                                Toast.makeText(KontrolActivity.this, "Feed machine is off ", Toast.LENGTH_SHORT).show();
//                                switchFeeder.setChecked(false);
//                            }
////                            menucontrol.setVisibleXRangeMaximum(65f);
//                            p_Tunggu.setVisibility(View.GONE);
//                            menucontrol.setVisibility(View.VISIBLE);
//                            //switch mati tom
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Toast.makeText(KontrolActivity.this, "Connection Failed ", Toast.LENGTH_SHORT).show();
//                            p_Tunggu.setVisibility(View.GONE);
//                            menucontrol.setVisibility(View.VISIBLE);
//                            switchFeeder.setChecked(true);
//                            //switch hidup tom
//
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                p_Tunggu.setVisibility(View.GONE);
//                menucontrol.setVisibility(View.VISIBLE);
//                Toast.makeText(KontrolActivity.this, "Connection Failed ", Toast.LENGTH_SHORT).show();
//                error.printStackTrace();
//                switchFeeder.setChecked(true);
//                //switch hidup tom
////                Toast.makeText(kontrolActivity.this, "Gagal " + error.toString(), Toast.LENGTH_SHORT).show();
//            }
//        })
//
//        {
//            String kodef = "0";
//            String statusf = "off";
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("kode", kodef);
//                params.put("status", statusf);
//                return params;
//            }
//        };
//        mRequestQueue.add(stringRequest);
//        mRequestQueue.getCache().clear();
//        stringRequest.setRetryPolicy(new RetryPolicy() {
//            @Override
//            public int getCurrentTimeout() {
//                return 50000;
//            }
//
//            @Override
//            public int getCurrentRetryCount() {
//                return 50000;
//            }
//
//            @Override
//            public void retry(VolleyError error) throws VolleyError {
//
//            }
//        });
//    }

    public void testkoneksi(View view) {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            idtvkoneksi.setText("Connection available");
        }
        else {
            idtvkoneksi.setText("Connection not available");
        }
    }

    private void checkReadExternalStoragePermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) ==
                    PackageManager.PERMISSION_GRANTED) {
            } else {
                if(shouldShowRequestPermissionRationale(Manifest.permission.INTERNET)) {
                    Toast.makeText(this, "App needs to view permission", Toast.LENGTH_SHORT).show();
                }
                requestPermissions(new String[] {Manifest.permission.INTERNET},
                        WRITE_EXTERNAL_STORAGE_PERMMISSION_RESULT);
            }
        } else {
            // Start cursor loader
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case WRITE_EXTERNAL_STORAGE_PERMMISSION_RESULT:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    public void cekmotor() {
        p_Tunggu.setVisibility(View.VISIBLE);
        menucontrol.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, et_json_urlcek,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            kode = jsonObject.getString("kode");
                            status = jsonObject.getString("status");

//                            JSONArray jsonArray = new JSONArray(response);
//
//                            ArrayList<KontrolModel> kontrolModelArrayList = new ArrayList<>();
//
//                            for (int i=0; i<jsonArray.length(); i++){
//                                KontrolModel kontrolModel = new KontrolModel();
//                                JSONObject result = jsonArray.getJSONObject(i);
//
//                                kontrolModel.setkode(result.getString("kode"));
//                                kontrolModel.setstatus(result.getString("status"));
//
//                                kontrolModelArrayList.add(kontrolModel);
//                            }
//
//                            for (int j = 0; j < kontrolModelArrayList.size(); j++){
//                                kode = kontrolModelArrayList.get(j).kode;
//                                status = kontrolModelArrayList.get(j).status;
//                            }

                            if (kode.equals("1")){
                                switchSensor.setChecked(true);
                                Toast.makeText(KontrolActivity.this, "Sensor is " +status, Toast.LENGTH_SHORT).show();
                            }
//                            menucontrol.setVisibleXRangeMaximum(65f);
                            p_Tunggu.setVisibility(View.GONE);
                            menucontrol.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            p_Tunggu.setVisibility(View.GONE);
                            menucontrol.setVisibility(View.VISIBLE);
                            Toast.makeText(KontrolActivity.this, "Connection Failed ", Toast.LENGTH_SHORT).show();
                            switchSensor.setChecked(false);
                            //switch mati tom

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                p_Tunggu.setVisibility(View.GONE);
                menucontrol.setVisibility(View.VISIBLE);
                error.printStackTrace();
                Toast.makeText(KontrolActivity.this, "Connection Failed ", Toast.LENGTH_SHORT).show();
                switchSensor.setChecked(false);
                //switch mati tom
//                Toast.makeText(kontrolActivity.this, "Gagal " + error.toString(), Toast.LENGTH_SHORT).show();
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

    public void hidupkanmesin() {
        p_Tunggu.setVisibility(View.VISIBLE);
        menucontrol.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, et_json_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response.toString());
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String berhasil = jsonObject.getString("success");
                            if (berhasil.equals("1")){
                                Toast.makeText(KontrolActivity.this, "Sensor is on ", Toast.LENGTH_SHORT).show();
                                switchSensor.setChecked(true);
                            }

//                            menucontrol.setVisibleXRangeMaximum(65f);
                            p_Tunggu.setVisibility(View.GONE);
                            menucontrol.setVisibility(View.VISIBLE);
                            //switch hidup tom

                        } catch (JSONException e) {
                            e.printStackTrace();
                            p_Tunggu.setVisibility(View.GONE);
                            Toast.makeText(KontrolActivity.this, "Connection Failed ", Toast.LENGTH_SHORT).show();
                            menucontrol.setVisibility(View.VISIBLE);
                            switchSensor.setChecked(false);
                            //switch mati tom

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                p_Tunggu.setVisibility(View.GONE);
                menucontrol.setVisibility(View.VISIBLE);
                error.printStackTrace();
                Toast.makeText(KontrolActivity.this, "Connection Failed ", Toast.LENGTH_SHORT).show();
                switchSensor.setChecked(false);
                //switch mati tom
//                Toast.makeText(kontrolActivity.this, "Gagal " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        })

        {
            String kode = "1";
            String status = "on";
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("kode", kode);
                params.put("status", status);
                return params;
            }
        };
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

    public void matikanmesin() {
        p_Tunggu.setVisibility(View.VISIBLE);
        menucontrol.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, et_json_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response.toString());
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String berhasil = jsonObject.getString("success");
                            if (berhasil.equals("1")){
                                Toast.makeText(KontrolActivity.this, "Sensor is off ", Toast.LENGTH_SHORT).show();
                                switchSensor.setChecked(false);
                            }

//                            menucontrol.setVisibleXRangeMaximum(65f);
                            p_Tunggu.setVisibility(View.GONE);
                            menucontrol.setVisibility(View.VISIBLE);
                            //switch mati tom

                        } catch (JSONException e) {
                            e.printStackTrace();
                            p_Tunggu.setVisibility(View.GONE);
                            menucontrol.setVisibility(View.VISIBLE);
                            Toast.makeText(KontrolActivity.this, "Connection Failed ", Toast.LENGTH_SHORT).show();
                            switchSensor.setChecked(true);
                            //switch hidup tom

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                p_Tunggu.setVisibility(View.GONE);
                menucontrol.setVisibility(View.VISIBLE);
                Toast.makeText(KontrolActivity.this, "Connection Failed ", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
                switchSensor.setChecked(true);
                //switch hidup tom
//                Toast.makeText(kontrolActivity.this, "Gagal " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        })

        {
            String kode = "0";
            String status = "off";
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("kode", kode);
                params.put("status", status);
                return params;
            }
        };
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

    public void testfeeder(View view) {
        feeder();
        if (kodef.equals("0")){
            Toast.makeText(KontrolActivity.this, "Feeder is off", Toast.LENGTH_LONG).show();
        }
        else if (kodef.equals("1")){
            Toast.makeText(KontrolActivity.this, "Feeder is on", Toast.LENGTH_LONG).show();
        }
        else if (kodef.equals("2")){
            Toast.makeText(KontrolActivity.this, "Feeder is on", Toast.LENGTH_LONG).show();
        }
        else if (kodef.equals("3")){
            Toast.makeText(KontrolActivity.this, "Feeder is on", Toast.LENGTH_LONG).show();
        }
        else if (kodef.equals("4")){
            Toast.makeText(KontrolActivity.this, "Feeder is on", Toast.LENGTH_LONG).show();
        }
        else if (kodef.equals("5")){
            Toast.makeText(KontrolActivity.this, "Feeder is on", Toast.LENGTH_LONG).show();
        }
        else if (kodef.equals("6")){
            Toast.makeText(KontrolActivity.this, "Feeder is on", Toast.LENGTH_LONG).show();
        }

    }

    private void feeder() {
        p_Tunggu.setVisibility(View.VISIBLE);
        menucontrol.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, et_json_urlcek_feed,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            kodef = jsonObject.getString("kodek");
                            statusf = jsonObject.getString("status");

                            p_Tunggu.setVisibility(View.GONE);
                            menucontrol.setVisibility(View.VISIBLE);
                            //switch hidup tom

                        } catch (JSONException e) {
                            e.printStackTrace();
                            p_Tunggu.setVisibility(View.GONE);
                            menucontrol.setVisibility(View.VISIBLE);
                            Toast.makeText(KontrolActivity.this, "Connection Failed ", Toast.LENGTH_SHORT).show();

                            //switch mati tom

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                p_Tunggu.setVisibility(View.GONE);
                menucontrol.setVisibility(View.VISIBLE);
                error.printStackTrace();
                Toast.makeText(KontrolActivity.this, "Connection Failed ", Toast.LENGTH_SHORT).show();


            }
        });
        mRequestQueue.getCache().clear();
        mRequestQueue.add(stringRequest);
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
}
