package com.tambakudang.tomtom.tambak;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailNotifActivity extends AppCompatActivity {
    public static final String TAG = DetailNotifActivity.class.getSimpleName();

    private RequestQueue mRequestQueue;
    private RequestQueue mRequestQueue2;
    private RequestQueue mRequestQueue3;

    TextView deskripsi;

    public static String et_json_urlupdateph = "http://wiridhub.id/tambak/updatenotifph.php";
    public static String et_json_urlupdatesuhu = "http://wiridhub.id/tambak/updatenotifsuhu.php";
    public static String et_json_urlupdateoksi = "http://wiridhub.id/tambak/updatenotifoksi.php";
    public static String et_json_urlupdatepakan = "http://wiridhub.id/tambak/updatenotifpakan.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_notif);
        setTitle("Fish Feeder");
        deskripsi = findViewById(R.id.deskripsi);
        mRequestQueue = Volley.newRequestQueue(DetailNotifActivity.this);
        mRequestQueue2 = Volley.newRequestQueue(DetailNotifActivity.this);
        mRequestQueue3 = Volley.newRequestQueue(DetailNotifActivity.this);

        String tabel = (getIntent().getStringExtra("tabel"));
        deskripsi.setText(getIntent().getStringExtra("deskripsi"));

        switch (tabel){
            case "suhu":
                updatenotifsuhu();

                break;
            case "ph":
                updatenotifph();

                break;
            case "pakan":
                updatenotifpakan();

                break;
            case "oksi":
                updatenotifoksi();

                break;
        }
//        if (tabel.equals("suhu")){
//        }
//        else if (tabel.equals("ph")){
//        }
//        else if (tabel.equals("oksi")){
//        }
    }

    public void updatenotifpakan() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, et_json_urlupdatepakan,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response.toString());
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String berhasil = jsonObject.getString("success");
//                            menucontrol.setVisibleXRangeMaximum(65f);
                            //switch mati tom

                        } catch (JSONException e) {
                            e.printStackTrace();
                            //switch hidup tom
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
//                Toast.makeText(DetailNotifActivity.this, "Gagal " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        })

        {
            String kode = "0";
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("kode", kode);
                return params;
            }
        };
        mRequestQueue.getCache().clear();
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

    public void updatenotifsuhu() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, et_json_urlupdatesuhu,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response.toString());
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String berhasil = jsonObject.getString("success");
//                            menucontrol.setVisibleXRangeMaximum(65f);
                            //switch mati tom

                        } catch (JSONException e) {
                            e.printStackTrace();
                            //switch hidup tom
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
//                Toast.makeText(DetailNotifActivity.this, "Gagal " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        })

        {
            String kode = "0";
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("kode", kode);
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
    public void updatenotifph() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, et_json_urlupdateph,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response.toString());

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String berhasil = jsonObject.getString("success");
//                            menucontrol.setVisibleXRangeMaximum(65f);
                            //switch mati tom

                        } catch (JSONException e) {
                            e.printStackTrace();
                            //switch hidup tom
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
//                Toast.makeText(DetailNotifActivity.this, "Gagal " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        })

        {
            String kode = "0";
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("kode", kode);
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
    public void updatenotifoksi() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, et_json_urlupdateoksi,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response.toString());

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String berhasil = jsonObject.getString("success");
//                            menucontrol.setVisibleXRangeMaximum(65f);
                            //switch mati tom

                        } catch (JSONException e) {
                            e.printStackTrace();
                            //switch hidup tom
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
//                Toast.makeText(DetailNotifActivity.this, "Gagal " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        })

        {
            String kode = "0";
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("kode", kode);
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

}
