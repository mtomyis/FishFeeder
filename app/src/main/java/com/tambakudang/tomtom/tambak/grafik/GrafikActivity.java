package com.tambakudang.tomtom.tambak.grafik;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.tambakudang.tomtom.tambak.R;

public class GrafikActivity extends AppCompatActivity {
    public static final String TAG = GrafikActivity.class.getSimpleName();

    private final static int WRITE_EXTERNAL_STORAGE_PERMMISSION_RESULT = 0;

    public ProgressBar p_Tunggu;
    private RequestQueue mRequestQueue;

    public static String et_json_url = "http://wiridhub.id/tambak/lihat.php";
    public static String et_json_urlcek = "http://wiridhub.id/tambak/cekid.php";

    String mWaktu, mSuhu, mPh, mOksigen, pilihspinner, mIdsensor;
    String suhu = "Temperature";
    String ph = "Ph";
    String oksigen = "Oxygen";
    Spinner spinner;
    LineChart lineChart;
    public String[] array = {"All","Temperature", "Ph", "Oxygen"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafik);
        setTitle("Graphics");

        checkReadExternalStoragePermission();
        mRequestQueue = Volley.newRequestQueue(GrafikActivity.this);
        lineChart = findViewById(R.id.lineChart);
        p_Tunggu = findViewById(R.id.idtunggum);

        spinner = findViewById(R.id.spinner);

        cekid();

//        permisi();

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, array);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pilihspinner = spinner.getSelectedItem().toString();

                if (pilihspinner.equals(suhu)){
                    tampilSuhu();
                }else if (pilihspinner.equals(ph)){
                    tampilPh();
                }else if (pilihspinner.equals(oksigen)){
                    tampilOksigen();
                }else {
                    tampilSemua();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

    public void tampilSemua() {
        p_Tunggu.setVisibility(View.VISIBLE);
        lineChart.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, et_json_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response.toString());
                        try {
                            //JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = new JSONArray(response);

                            ArrayList<String> xWaktu = new ArrayList<>();
                            ArrayList<Entry> ySuhu = new ArrayList<>();
                            ArrayList<Entry> yPh = new ArrayList<>();
                            ArrayList<Entry> yOksigen = new ArrayList<>();

                            ArrayList<GrafikModel> grafikModelArrayList = new ArrayList<>();

                            for (int i=0; i<jsonArray.length(); i++){
                                GrafikModel grafikModel = new GrafikModel();
                                JSONObject result = jsonArray.getJSONObject(i);

                                grafikModel.setWaktu(result.getString("waktu"));
                                grafikModel.setSuhu(result.getString("suhu"));
                                grafikModel.setPh(result.getString("ph"));
                                grafikModel.setOksigen(result.getString("oksigen"));

                                grafikModelArrayList.add(grafikModel);
                            }

                            for (int j = 0; j < grafikModelArrayList.size(); j++){

//                                Toast.makeText(GrafikActivity.this, "Gagal " +grafikModelArrayList.get(j).suhu, Toast.LENGTH_SHORT).show();

                                ySuhu.add(new Entry(Float.parseFloat(grafikModelArrayList.get(j).suhu) ,j));
                                yPh.add(new Entry(Float.parseFloat(grafikModelArrayList.get(j).ph),j));
                                yOksigen.add(new Entry(Float.parseFloat(grafikModelArrayList.get(j).oksigen),j));
                                xWaktu.add(j, String.valueOf(grafikModelArrayList.get(j).waktu));
                            }

                            String[] garisx = new String[xWaktu.size()];
                            for(int k=0; k<xWaktu.size();k++){
                                garisx[k] = xWaktu.get(k).toString();
                            }

                            ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();

                            LineDataSet lineDataSet1 = new LineDataSet(yPh,"PH");
                            lineDataSet1.setDrawCircles(false);
                            lineDataSet1.setColor(Color.RED);

                            LineDataSet lineDataSet2 = new LineDataSet(ySuhu,"Temperature");
                            lineDataSet2.setDrawCircles(false);
                            lineDataSet2.setColor(Color.BLUE);

                            LineDataSet lineDataSet3 = new LineDataSet(yOksigen,"Oxygen");
                            lineDataSet3.setDrawCircles(false);
                            lineDataSet3.setColor(Color.YELLOW);

                            lineDataSets.add(lineDataSet1);
                            lineDataSets.add(lineDataSet2);
                            lineDataSets.add(lineDataSet3);

                            lineChart.setData(new LineData(garisx,lineDataSets));

//                            lineChart.setVisibleXRangeMaximum(65f);
                            p_Tunggu.setVisibility(View.GONE);
                            lineChart.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
//                            Toast.makeText(GrafikActivity.this, "Gagal " + e.toString(), Toast.LENGTH_SHORT).show();
                            p_Tunggu.setVisibility(View.GONE);
                            lineChart.setVisibility(View.VISIBLE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                p_Tunggu.setVisibility(View.GONE);
                lineChart.setVisibility(View.VISIBLE);
                error.printStackTrace();
//                Toast.makeText(GrafikActivity.this, "Gagal " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", mIdsensor);
                return params;
            }
        };
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

    public void tampilSuhu() {
        p_Tunggu.setVisibility(View.VISIBLE);
        lineChart.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, et_json_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response.toString());
                        try {
                            //JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = new JSONArray(response);

                            ArrayList<String> xWaktu = new ArrayList<>();
                            ArrayList<Entry> ySuhu = new ArrayList<>();
                            ArrayList<Entry> yPh = new ArrayList<>();
                            ArrayList<Entry> yOksigen = new ArrayList<>();

                            ArrayList<GrafikModel> grafikModelArrayList = new ArrayList<>();

                            for (int i=0; i<jsonArray.length(); i++){
                                GrafikModel grafikModel = new GrafikModel();
                                JSONObject result = jsonArray.getJSONObject(i);

                                grafikModel.setWaktu(result.getString("waktu"));
                                grafikModel.setSuhu(result.getString("suhu"));
                                grafikModel.setPh(result.getString("ph"));
                                grafikModel.setOksigen(result.getString("oksigen"));

                                grafikModelArrayList.add(grafikModel);
                            }

                            for (int j = 0; j < grafikModelArrayList.size(); j++){

//                                Toast.makeText(GrafikActivity.this, "Gagal " +grafikModelArrayList.get(j).suhu, Toast.LENGTH_SHORT).show();

                                ySuhu.add(new Entry(Float.parseFloat(grafikModelArrayList.get(j).suhu) ,j));
                                xWaktu.add(j, String.valueOf(grafikModelArrayList.get(j).waktu));
                            }

                            String[] garisx = new String[xWaktu.size()];
                            for(int k=0; k<xWaktu.size();k++){
                                garisx[k] = xWaktu.get(k).toString();
                            }

                            ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();

                            LineDataSet lineDataSet2 = new LineDataSet(ySuhu,"Suhu");
                            lineDataSet2.setDrawCircles(false);
                            lineDataSet2.setColor(Color.BLUE);

                            lineDataSets.add(lineDataSet2);
                            lineChart.setData(new LineData(garisx,lineDataSets));

//                            lineChart.setVisibleXRangeMaximum(65f);
                            p_Tunggu.setVisibility(View.GONE);
                            lineChart.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
//                            Toast.makeText(GrafikActivity.this, "Gagal " + e.toString(), Toast.LENGTH_SHORT).show();
                            p_Tunggu.setVisibility(View.GONE);
                            lineChart.setVisibility(View.VISIBLE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                p_Tunggu.setVisibility(View.GONE);
                lineChart.setVisibility(View.VISIBLE);
                error.printStackTrace();
//                Toast.makeText(GrafikActivity.this, "Gagal " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", mIdsensor);
                return params;
            }
        };
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

    public void tampilPh() {
        p_Tunggu.setVisibility(View.VISIBLE);
        lineChart.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, et_json_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response.toString());
                        try {
                            //JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = new JSONArray(response);

                            ArrayList<String> xWaktu = new ArrayList<>();
                            ArrayList<Entry> ySuhu = new ArrayList<>();
                            ArrayList<Entry> yPh = new ArrayList<>();
                            ArrayList<Entry> yOksigen = new ArrayList<>();

                            ArrayList<GrafikModel> grafikModelArrayList = new ArrayList<>();

                            for (int i=0; i<jsonArray.length(); i++){
                                GrafikModel grafikModel = new GrafikModel();
                                JSONObject result = jsonArray.getJSONObject(i);

                                grafikModel.setWaktu(result.getString("waktu"));
                                grafikModel.setSuhu(result.getString("suhu"));
                                grafikModel.setPh(result.getString("ph"));
                                grafikModel.setOksigen(result.getString("oksigen"));

                                grafikModelArrayList.add(grafikModel);
                            }

                            for (int j = 0; j < grafikModelArrayList.size(); j++){

//                                Toast.makeText(GrafikActivity.this, "Gagal " +grafikModelArrayList.get(j).suhu, Toast.LENGTH_SHORT).show();
                                yPh.add(new Entry(Float.parseFloat(grafikModelArrayList.get(j).ph),j));
                                xWaktu.add(j, String.valueOf(grafikModelArrayList.get(j).waktu));
                            }

                            String[] garisx = new String[xWaktu.size()];
                            for(int k=0; k<xWaktu.size();k++){
                                garisx[k] = xWaktu.get(k).toString();
                            }

                            ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();

                            LineDataSet lineDataSet1 = new LineDataSet(yPh,"Ph");
                            lineDataSet1.setDrawCircles(false);
                            lineDataSet1.setColor(Color.RED);

                            lineDataSets.add(lineDataSet1);

                            lineChart.setData(new LineData(garisx,lineDataSets));

//                            lineChart.setVisibleXRangeMaximum(65f);
                            p_Tunggu.setVisibility(View.GONE);
                            lineChart.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
//                            Toast.makeText(GrafikActivity.this, "Gagal " + e.toString(), Toast.LENGTH_SHORT).show();
                            p_Tunggu.setVisibility(View.GONE);
                            lineChart.setVisibility(View.VISIBLE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                p_Tunggu.setVisibility(View.GONE);
                lineChart.setVisibility(View.VISIBLE);
                error.printStackTrace();
//                Toast.makeText(GrafikActivity.this, "Gagal " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", mIdsensor);
                return params;
            }
        };
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

    public void tampilOksigen() {
        p_Tunggu.setVisibility(View.VISIBLE);
        lineChart.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, et_json_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response.toString());
                        try {
                            //JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = new JSONArray(response);

                            ArrayList<String> xWaktu = new ArrayList<>();
                            ArrayList<Entry> ySuhu = new ArrayList<>();
                            ArrayList<Entry> yPh = new ArrayList<>();
                            ArrayList<Entry> yOksigen = new ArrayList<>();

                            ArrayList<GrafikModel> grafikModelArrayList = new ArrayList<>();

                            for (int i=0; i<jsonArray.length(); i++){
                                GrafikModel grafikModel = new GrafikModel();
                                JSONObject result = jsonArray.getJSONObject(i);

                                grafikModel.setWaktu(result.getString("waktu"));
                                grafikModel.setSuhu(result.getString("suhu"));
                                grafikModel.setPh(result.getString("ph"));
                                grafikModel.setOksigen(result.getString("oksigen"));

                                grafikModelArrayList.add(grafikModel);
                            }

                            for (int j = 0; j < grafikModelArrayList.size(); j++){

//                                Toast.makeText(GrafikActivity.this, "Gagal " +grafikModelArrayList.get(j).suhu, Toast.LENGTH_SHORT).show();
                                yOksigen.add(new Entry(Float.parseFloat(grafikModelArrayList.get(j).oksigen),j));
                                xWaktu.add(j, String.valueOf(grafikModelArrayList.get(j).waktu));
                            }

                            String[] garisx = new String[xWaktu.size()];
                            for(int k=0; k<xWaktu.size();k++){
                                garisx[k] = xWaktu.get(k).toString();
                            }

                            ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();


                            LineDataSet lineDataSet3 = new LineDataSet(yOksigen,"Oksigen");
                            lineDataSet3.setDrawCircles(false);
                            lineDataSet3.setColor(Color.YELLOW);

                            lineDataSets.add(lineDataSet3);

                            lineChart.setData(new LineData(garisx,lineDataSets));

//                            lineChart.setVisibleXRangeMaximum(65f);
                            p_Tunggu.setVisibility(View.GONE);
                            lineChart.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
//                            Toast.makeText(GrafikActivity.this, "Gagal " + e.toString(), Toast.LENGTH_SHORT).show();
                            p_Tunggu.setVisibility(View.GONE);
                            lineChart.setVisibility(View.VISIBLE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                p_Tunggu.setVisibility(View.GONE);
                lineChart.setVisibility(View.VISIBLE);
                error.printStackTrace();
//                Toast.makeText(GrafikActivity.this, "Gagal " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", mIdsensor);
                return params;
            }
        };
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

    public void cekid() {
        p_Tunggu.setVisibility(View.VISIBLE);
        lineChart.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, et_json_urlcek,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response.toString());
                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            mIdsensor = jsonObject.getString("id_sensor");
                            JSONArray jsonArray = new JSONArray(response);

                            ArrayList<GrafikModelId> grafikModelIdArrayList = new ArrayList<>();

                            for (int i=0; i<jsonArray.length(); i++){
                                GrafikModelId grafikModelId = new GrafikModelId();
                                JSONObject result = jsonArray.getJSONObject(i);

                                grafikModelId.setId(result.getString("id_sensor"));
                                grafikModelIdArrayList.add(grafikModelId);

                            }
                            for (int j = 0; j < grafikModelIdArrayList.size(); j++){
                                mIdsensor = grafikModelIdArrayList.get(j).id;
                            }

//                            Toast.makeText(GrafikActivity.this, "id " + mIdsensor, Toast.LENGTH_SHORT).show();
                            tampilSemua();

                        } catch (JSONException e) {
                            e.printStackTrace();
//                            Toast.makeText(GrafikActivity.this, "Gagal " + e.toString(), Toast.LENGTH_SHORT).show();
                            p_Tunggu.setVisibility(View.GONE);
                            lineChart.setVisibility(View.VISIBLE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                p_Tunggu.setVisibility(View.GONE);
                lineChart.setVisibility(View.VISIBLE);
                error.printStackTrace();
//                Toast.makeText(GrafikActivity.this, "Gagal " + error.toString(), Toast.LENGTH_SHORT).show();
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
