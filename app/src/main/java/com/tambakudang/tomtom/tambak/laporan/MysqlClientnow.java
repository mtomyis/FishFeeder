package com.tambakudang.tomtom.tambak.laporan;

import android.content.Context;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;

public class MysqlClientnow {
    public static final String TAG = MysqlClient.class.getSimpleName();

    public static String et_json_url = "http://wiridhub.id/tambak/laporannow.php";

    private final Context c;
    private SimpleTableDataAdapter adapter;

    String mWaktu, mSuhu, mPh, mOksigen;

    public MysqlClientnow(Context c){
        this.c=c;
    }

    public void retrieve(final TableView tb) {

        final ArrayList<Spacecraft> spacecrafts = new ArrayList<>();

        AndroidNetworking.get(et_json_url)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i(TAG, response.toString());
                        JSONObject jo;
                        Spacecraft s;
                        try {

                            for (int i = 0; i < response.length(); i++) {

                                jo = response.getJSONObject(i);

                                mWaktu = jo.getString("waktu");
                                mSuhu = jo.getString("suhu");
                                mPh = jo.getString("ph");
                                mOksigen = jo.getString("oksigen");

                                s = new Spacecraft();
                                s.setWaktu(mWaktu);
                                s.setSuhu(mSuhu);
                                s.setPh(mPh);
                                s.setOksigen(mOksigen);

                                spacecrafts.add(s);
                            }
                            adapter = new SimpleTableDataAdapter(c, new TableHelper(c).getSpaceProbes(spacecrafts));
                            tb.setDataAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

}
