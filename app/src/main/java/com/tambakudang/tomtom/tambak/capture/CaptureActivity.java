package com.tambakudang.tomtom.tambak.capture;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.tambakudang.tomtom.tambak.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CaptureActivity extends AppCompatActivity {
    public static final String TAG = CaptureActivity.class.getSimpleName();

    ImageButton imageButton;
    ProgressBar progressBar;
    ImageView image;
    View mView;


    String namagambar;
    public static String et_json_urlupdatemode = "http://wiridhub.id/tambak/updatemodecamera.php";
    public static String et_json_urllihatgambar = "http://wiridhub.id/tambak/lihatgambar.php";

    private RequestQueue mRequestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Capture");
        setContentView(R.layout.activity_capture);
        imageButton = findViewById(R.id.imageButton);
        progressBar = findViewById(R.id.progressBarcapture);
        image = findViewById(R.id.idgambar);

        mRequestQueue = Volley.newRequestQueue(CaptureActivity.this);

        mView = findViewById(R.id.idlineargambar);
        mView.setDrawingCacheEnabled(true);
        mView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        mView.layout(0, 0, mView.getMeasuredWidth(), mView.getMeasuredHeight());
        mView.buildDrawingCache(true);

    }

    public void updatemode() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, et_json_urlupdatemode,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response.toString());
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String berhasil = jsonObject.getString("success");
//                            menucontrol.setVisibleXRangeMaximum(65f);
                            if (berhasil.equals("1")){
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tampilkangambar();
                                    }
                                }, 5000L);
                                //jeda berapa detik hingga gambar tersimpan
                            }
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
            String kode = "1";
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

    private void tampilkangambar() {

        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, et_json_urllihatgambar,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response.toString());
                        try {
                            //JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i=0; i<jsonArray.length(); i++){
                                JSONObject result = jsonArray.getJSONObject(i);

                                namagambar = result.getString("namagambar");

                                if (namagambar.isEmpty()) {
                                    image.setImageResource(R.drawable.lg);
                                } else{
                                    imageButton.setVisibility(View.GONE);
                                    mView.setVisibility(View.VISIBLE);
                                    Picasso.with(getApplicationContext())
                                            .load(namagambar)
                                            .error(R.drawable.lg)
                                            .into(image, new com.squareup.picasso.Callback() {
                                                @Override
                                                public void onSuccess() {
//                                                    Toast.makeText(CaptureActivity.this, "Gambar tersedia",Toast.LENGTH_LONG).show();
                                                }

                                                @Override
                                                public void onError() {
//                                                    Toast.makeText(CaptureActivity.this, "Gambar belum tersedia",Toast.LENGTH_LONG).show();
                                                }
                                            });
                                }
                            }

//                            lineChart.setVisibleXRangeMaximum(65f);
                            progressBar.setVisibility(View.GONE);

                        } catch (JSONException e) {
                            e.printStackTrace();
//                            Toast.makeText(GrafikActivity.this, "Gagal " + e.toString(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                error.printStackTrace();
//                Toast.makeText(GrafikActivity.this, "Gagal " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            String mIdsensor = "1";
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", mIdsensor);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_capture, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.idhmenuapus:
                mView.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                imageButton.setVisibility(View.VISIBLE);

//                Toast.makeText(CaptureActivity.this,"Gambar berada di folder tambak", Toast.LENGTH_LONG).show();
                return true;

            case R.id.idmenudownload:

                Bitmap b = Bitmap.createBitmap(mView.getDrawingCache());
                mView.setDrawingCacheEnabled(false);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

                Calendar c = Calendar.getInstance();

                java.text.SimpleDateFormat currentDate = new java.text.SimpleDateFormat("HH_mm_ss");
                final String saveCurrentDate = currentDate.format(c.getTime());

                File folder = new File ("/sdcard/");


                File my_file = new File(folder, saveCurrentDate+".JPEG");

                try {
                    my_file.createNewFile();
                    FileOutputStream fo = new FileOutputStream(my_file);
                    fo.write(bytes.toByteArray());
                    fo.flush();
                    fo.close();
                    Toast.makeText(CaptureActivity.this,"Done", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void cekrek(View view) {
        updatemode();
    }
}
