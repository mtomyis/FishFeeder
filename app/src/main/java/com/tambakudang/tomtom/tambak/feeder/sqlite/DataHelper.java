package com.tambakudang.tomtom.tambak.feeder.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by mobile2017 on 5/4/2018.
 */

public class DataHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tanggal.db";
    private static final int DATABASE_VERSION = 2;

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "create table udang(" +
                "et_tgl1 text null, et_tgl1_2 text null," +
                "et_tgl2 text null, et_tgl2_2 text null, " +
                "et_tgl3 text null, et_tgl3_2 text null, " +
                "et_tgl4 text null, et_tgl4_2 text null, " +
                "et_tgl5 text null, et_tgl5_2 text null, " +
                "et_tgl6 text null, et_tgl6_2 text null, " +
                "et_tgl7 text null, et_tgl7_2 text null, " +
                "et_tgl8 text null, et_tgl8_2 text null, awal text null);";
        Log.d("Data","onCreate: " + sql);
        db.execSQL(sql);

        String sqll = "create table jam(" +
                "jam1 text null, umur int null);";
        Log.d("Data","onCreate: " + sqll);
        db.execSQL(sqll);

    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

    }
}

