package com.tambakudang.tomtom.tambak.feeder.jadwal_jam;

import android.app.TimePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tambakudang.tomtom.tambak.R;
import com.tambakudang.tomtom.tambak.feeder.sqlite.DataHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DetailUmur1Activity extends AppCompatActivity {
    protected static String EXTRA_ID = "";
    public static final String TAG = DetailUmur1Activity.class.getSimpleName();

    public static String et_json_urlcek_umur = "http://wiridhub.id/tambak/cekumur.php";
    public static String et_json_url_umur = "http://wiridhub.id/tambak/updateumur.php";

    private RequestQueue mRequestQueue;
    private TextView tvpakan;
    private Button btSimpanU, btHapusU;
    public ProgressBar p_Tunggu;
    LinearLayout menuumur;

    String kode, waktutabur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_umur1);
        setTitle("Feeder");

//        menuumur = findViewById(R.id.idmenuumur1);
//        tvpakan = findViewById(R.id.id_U1_jam1);
//        btHapusU = findViewById(R.id.btn_hapusU1);
//        btSimpanU = findViewById(R.id.btn_simpanU1);
//        p_Tunggu = findViewById(R.id.idtungguumur1);
//        mRequestQueue = Volley.newRequestQueue(DetailUmur1Activity.this);
//
////        cekpakan();
//
//        btSimpanU.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                simpanpakan();
//                btSimpanU.setVisibility(View.GONE);
//            }
//        });
//
//        btHapusU.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hapuspakan();
//                btSimpanU.setVisibility(View.VISIBLE);
//            }
//        });
    }

//    private void cekpakan() {
//        p_Tunggu.setVisibility(View.VISIBLE);
//        menuumur.setVisibility(View.GONE);
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, et_json_urlcek_umur,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.i(TAG, response.toString());
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            kode = jsonObject.getString("kode");
//                            waktutabur = jsonObject.getString("waktutabur");
//
////                            JSONArray jsonArray = new JSONArray(response);
////
////                            for (int i=0; i<jsonArray.length(); i++){
////                                JSONObject result = jsonArray.getJSONObject(i);
////
////                                kode = result.getString("kode");
////                                waktutabur = result.getString("waktutabur");
////                            }
//
//                            if (kode.equals("1")){
//                                tvpakan.setText(waktutabur);
//                                btSimpanU.setVisibility(View.GONE);
//                            }
//                            else if (kode.equals("0")){
//                                Toast.makeText(DetailUmur1Activity.this, "Anda belum menambah waktu tabur ", Toast.LENGTH_SHORT).show();
//                                Toast.makeText(DetailUmur1Activity.this, "Silahkan cek halaman kontrol feeder ", Toast.LENGTH_SHORT).show();
//                                btSimpanU.setVisibility(View.VISIBLE);
//                                btHapusU.setVisibility(View.VISIBLE);
//                            }
////                            menuumur.setVisibleXRangeMaximum(65f);
//                            p_Tunggu.setVisibility(View.GONE);
//                            menuumur.setVisibility(View.VISIBLE);
//                            //switch hidup tom
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            p_Tunggu.setVisibility(View.GONE);
//                            menuumur.setVisibility(View.VISIBLE);
//                            btSimpanU.setVisibility(View.GONE);
//                            btHapusU.setVisibility(View.GONE);
//                            Toast.makeText(DetailUmur1Activity.this, "Connection Failed ", Toast.LENGTH_SHORT).show();
//                            //switch mati tom
//
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                p_Tunggu.setVisibility(View.GONE);
//                menuumur.setVisibility(View.VISIBLE);
//                btSimpanU.setVisibility(View.GONE);
//                btHapusU.setVisibility(View.GONE);
//                error.printStackTrace();
//                Toast.makeText(DetailUmur1Activity.this, "Connection Failed ", Toast.LENGTH_SHORT).show();
//                //switch mati tom
////                Toast.makeText(DetailUmur1Activity.this, "Gagal " + error.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        mRequestQueue.add(stringRequest);
//    }
//
//    public void simpanpakan() {
//        waktutabur = tvpakan.getText().toString();
//
//        p_Tunggu.setVisibility(View.VISIBLE);
//        menuumur.setVisibility(View.GONE);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, et_json_url_umur,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.i(TAG, response.toString());
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            String berhasil = jsonObject.getString("success");
//                            if (berhasil.equals("1")){
//                                Toast.makeText(DetailUmur1Activity.this, "Waktu berhasil disimpan ", Toast.LENGTH_SHORT).show();
//                            }
////                            menuumur.setVisibleXRangeMaximum(65f);
//                            p_Tunggu.setVisibility(View.GONE);
//                            menuumur.setVisibility(View.VISIBLE);
//                            //switch hidup tom
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Toast.makeText(DetailUmur1Activity.this, "Connection Failed ", Toast.LENGTH_SHORT).show();
//                            p_Tunggu.setVisibility(View.GONE);
//                            menuumur.setVisibility(View.VISIBLE);
//                            //switch mati tom
//
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                p_Tunggu.setVisibility(View.GONE);
//                menuumur.setVisibility(View.VISIBLE);
//                error.printStackTrace();
//                Toast.makeText(DetailUmur1Activity.this, "Connection Failed ", Toast.LENGTH_SHORT).show();
//                //switch mati tom
////                Toast.makeText(DetailUmur1Activity.this, "Gagal " + error.toString(), Toast.LENGTH_SHORT).show();
//            }
//        })
//
//        {
//            String kode = "1";
//            String status = "Hidup";
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("kode", kode);
//                params.put("waktutabur", waktutabur);
//                params.put("status", status);
//                return params;
//            }
//        };
//        mRequestQueue.add(stringRequest);
//    }
//
//    public void hapuspakan() {
//        p_Tunggu.setVisibility(View.VISIBLE);
//        menuumur.setVisibility(View.GONE);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, et_json_url_umur,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.i(TAG, response.toString());
//                        try {
//
//                            JSONObject jsonObject = new JSONObject(response);
//                            String berhasil = jsonObject.getString("success");
//                            if (berhasil.equals("1")){
//                                Toast.makeText(DetailUmur1Activity.this, "Waktu berhasil dihapus ", Toast.LENGTH_SHORT).show();
//                            }
////                            menuumur.setVisibleXRangeMaximum(65f);
//                            p_Tunggu.setVisibility(View.GONE);
//                            menuumur.setVisibility(View.VISIBLE);
//                            //switch mati tom
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Toast.makeText(DetailUmur1Activity.this, "Connection Failed ", Toast.LENGTH_SHORT).show();
//                            p_Tunggu.setVisibility(View.GONE);
//                            menuumur.setVisibility(View.VISIBLE);
//                            //switch hidup tom
//
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                p_Tunggu.setVisibility(View.GONE);
//                menuumur.setVisibility(View.VISIBLE);
//                Toast.makeText(DetailUmur1Activity.this, "Connection Failed ", Toast.LENGTH_SHORT).show();
//                error.printStackTrace();
//                //switch hidup tom
////                Toast.makeText(DetailUmur1Activity.this, "Gagal " + error.toString(), Toast.LENGTH_SHORT).show();
//            }
//        })
//
//        {
//            String kode = "0";
//            String waktu = "10";
//            String status = "Mati";
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("kode", kode);
//                params.put("waktutabur", waktu);
//                params.put("status", status);
//                return params;
//            }
//        };
//        mRequestQueue.add(stringRequest);
//    }

}
