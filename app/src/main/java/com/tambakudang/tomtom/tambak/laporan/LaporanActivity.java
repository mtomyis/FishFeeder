package com.tambakudang.tomtom.tambak.laporan;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.tambakudang.tomtom.tambak.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class LaporanActivity extends AppCompatActivity {

    private final static int WRITE_EXTERNAL_STORAGE_PERMMISSION_RESULT = 0;

    private RequestQueue mRequestQueue;

    public static String et_json_urll = "http://wiridhub.id/tambak/laporan.php";

    public static final String TAG = LaporanActivity.class.getSimpleName();

    private final static int cekWrite = 1;

    //ini view layout
    private View mView;

    TableView<String[]> tb;
    TableHelper tableHelper;

    ArrayList<Modelpdf> grafikModelArrayList = new ArrayList<>();

    String kode="1";

    private Menu action;

    private LinearLayout llScroll;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan);
        setTitle("Report");
        mRequestQueue = Volley.newRequestQueue(LaporanActivity.this);

        llScroll = findViewById(R.id.llScroll);

        checkReadExternalStoragePermission();
        ambildata();

//        if (savedInstanceState != null){
//            String hasil = savedInstanceState.getString(SimpanHasil);
//            Toast.makeText(this, "id "+hasil, Toast.LENGTH_SHORT).show();
//
////            kode = hasil;
//            switch (hasil){
//                case "1" :
//                    tableHelper=new TableHelper(this);
//                    tb = (TableView<String[]>) findViewById(R.id.tableView);
//                    tb.setColumnCount(4);
//                    tb.setColumnWeight(0, 2);
//                    tb.setHeaderBackgroundColor(Color.parseColor("#2ecc71"));
//                    tb.setHeaderAdapter(new SimpleTableHeaderAdapter(this,tableHelper.getSpaceProbeHeaders()));
//                    new MysqlClient(LaporanActivity.this).retrieve(tb);
//                    break;
//                case "2":
//                    tableHelper=new TableHelper(this);
//                    tb = (TableView<String[]>) findViewById(R.id.tableView);
//                    tb.setColumnCount(4);
//                    tb.setColumnWeight(0, 2);
//                    tb.setHeaderBackgroundColor(Color.parseColor("#2ecc71"));
//                    tb.setHeaderAdapter(new SimpleTableHeaderAdapter(this,tableHelper.getSpaceProbeHeaders()));
//                    new MysqlClientnow(LaporanActivity.this).retrieve(tb);
//                    break;
//                case "3":
//                    tableHelper=new TableHelper(this);
//                    tb = (TableView<String[]>) findViewById(R.id.tableView);
//                    tb.setColumnCount(4);
//                    tb.setColumnWeight(0, 2);
//                    tb.setHeaderBackgroundColor(Color.parseColor("#2ecc71"));
//                    tb.setHeaderAdapter(new SimpleTableHeaderAdapter(this,tableHelper.getSpaceProbeHeaders()));
//                    new MysqlClientyesterday(LaporanActivity.this).retrieve(tb);
//                    break;
//            }
//
//        }
//        else if (savedInstanceState == null){
//        }
//        lihatkode();

        tableHelper=new TableHelper(this);
        tb = (TableView<String[]>) findViewById(R.id.tableView);
        tb.setColumnCount(4);
        tb.setColumnWeight(0, 2);
        tb.setHeaderBackgroundColor(Color.parseColor("#2ecc71"));
        tb.setHeaderAdapter(new SimpleTableHeaderAdapter(this,tableHelper.getSpaceProbeHeaders()));
        new MysqlClient(LaporanActivity.this).retrieve(tb);

//        tampilkanTabel();

        //TABLE CLICK
        tb.addDataClickListener(new TableDataClickListener() {
            @Override
            public void onDataClicked(int rowIndex, Object clickedData) {
                Toast.makeText(LaporanActivity.this, "Pengukuran pada tanggal "+((String[])clickedData)[0], Toast.LENGTH_SHORT).show();
            }
        });


//        permisi();

    }

    private void checkReadExternalStoragePermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED) {
            } else {
                if(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(this, "App needs to view permission", Toast.LENGTH_SHORT).show();
                }
                requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
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

//    private void lihatkode(){
//        switch (kode){
//            case "1" :
//                tableHelper=new TableHelper(this);
//                tb = (TableView<String[]>) findViewById(R.id.tableView);
//                tb.setColumnCount(4);
//                tb.setColumnWeight(0, 2);
//                tb.setHeaderBackgroundColor(Color.parseColor("#2ecc71"));
//                tb.setHeaderAdapter(new SimpleTableHeaderAdapter(this,tableHelper.getSpaceProbeHeaders()));
//                new MysqlClient(LaporanActivity.this).retrieve(tb);
//                break;
//            case "2":
//                tableHelper=new TableHelper(this);
//                tb = (TableView<String[]>) findViewById(R.id.tableView);
//                tb.setColumnCount(4);
//                tb.setColumnWeight(0, 2);
//                tb.setHeaderBackgroundColor(Color.parseColor("#2ecc71"));
//                tb.setHeaderAdapter(new SimpleTableHeaderAdapter(this,tableHelper.getSpaceProbeHeaders()));
//                new MysqlClientnow(LaporanActivity.this).retrieve(tb);
//                break;
//            case "3":
//                tableHelper=new TableHelper(this);
//                tb = (TableView<String[]>) findViewById(R.id.tableView);
//                tb.setColumnCount(4);
//                tb.setColumnWeight(0, 2);
//                tb.setHeaderBackgroundColor(Color.parseColor("#2ecc71"));
//                tb.setHeaderAdapter(new SimpleTableHeaderAdapter(this,tableHelper.getSpaceProbeHeaders()));
//                new MysqlClientyesterday(LaporanActivity.this).retrieve(tb);
//                break;
//        }
//    }

//    private void permisi() {
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
//                    PackageManager.PERMISSION_GRANTED){
//
//            } else {
//                if(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                    Toast.makeText(this, "App needs Permission", Toast.LENGTH_SHORT).show();
//                }
//                requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, cekWrite);
//            }
//        } else {
//        }
//    }

//    private void tampilkanTabel() {
//               //tb.setDataAdapter(new SimpleTableseDataAdapter(this, tableHelper.getSpaceProbes()));
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        action = menu;
        action.findItem(R.id.idmenudownload);
//        action.findItem(R.id.idmenukemarin);
//        action.findItem(R.id.idmenuseluruhnya);
//        action.findItem(R.id.idmenuhariini);
//        action.findItem(R.id.idmenudelete);

        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.idmenudownload:
//                for (int i = 0; i < grafikModelArrayList.size(); i++){
//                    Toast.makeText(LaporanActivity.this, "waktu " + grafikModelArrayList.get(i).getWaktu(), Toast.LENGTH_SHORT).show();
//                }
//                Log.d("size"," "+llScroll.getWidth() +"  "+llScroll.getWidth());
//                bitmap = loadBitmapFromView(llScroll, llScroll.getWidth(), llScroll.getHeight());
//                createPdf();
                try {
                    createpdfitext();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
                return true;
//            case R.id.idmenuhariini:
//
//                kode = "2";
//                recreate();
//
//                return true;
//            case R.id.idmenukemarin:
//
//                kode = "3";
//                recreate();
//
//                return true;
//
//            case R.id.idmenuseluruhnya:
//
//                kode = "1";
//                recreate();
//
//                return true;
//            case R.id.idmenudelete:
//
//                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void ambildata(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, et_json_urll,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response.toString());
                        try {
                            //JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i=0; i<jsonArray.length(); i++){
                                Modelpdf modelPdf = new Modelpdf();
                                JSONObject result = jsonArray.getJSONObject(i);

                                modelPdf.setWaktu(result.getString("waktu"));
                                modelPdf.setSuhu(result.getString("suhu"));
                                modelPdf.setPh(result.getString("ph"));
                                modelPdf.setOksigen(result.getString("oksigen"));

                                grafikModelArrayList.add(modelPdf);
                            }

//                            for (int i = 0; i < grafikModelArrayList.size(); i++){
//                                Toast.makeText(LaporanActivity.this, "waktu " + grafikModelArrayList.get(i).getWaktu(), Toast.LENGTH_SHORT).show();
//                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
//                Toast.makeText(GrafikActivity.this, "Gagal " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
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

    private void createpdfitext() throws IOException, DocumentException {
        String nama = "fishfeeder";

        try {
            String fpath = "/sdcard/" + nama + ".pdf";
            File file = new File(fpath);
            if (!file.exists()) {
                file.createNewFile();
            }

            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            writer.setPageEvent(new PdfPageEventHelper()
            {
                public void onEndPage(PdfWriter writer, Document document)
                {
                    ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(""), 180, 30, 0);
                    ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("page " + document.getPageNumber()), 550, 30, 0);
                }
            });

            Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
            Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 12);

//            Document document = new Document();
            final PdfPTable table = new PdfPTable(new float[] { 2, 1, 1, 1 });
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell("Time");
            table.addCell("Temperatur");
            table.addCell("Ph");
            table.addCell("Oxygen");
            table.setHeaderRows(1);
            PdfPCell[] cells = table.getRow(0).getCells();
            for (int j=0;j<cells.length;j++){
                cells[j].setBackgroundColor(BaseColor.GRAY);
            }

            for (int i = 0; i < grafikModelArrayList.size(); i++){
//            for (int i=1;i<100;i++){
                table.addCell(""+(grafikModelArrayList.get(i).getWaktu()));
                table.addCell(""+(grafikModelArrayList.get(i).getSuhu()));
                table.addCell(""+(grafikModelArrayList.get(i).getPh()));
                table.addCell(""+(grafikModelArrayList.get(i).getOksigen()));
//                table.addCell(""+i);
//                table.addCell(""+i);
//                table.addCell(""+i);
//                table.addCell(""+i);
//                Toast.makeText(LaporanActivity.this, "waktu " +grafikModelArrayList.get(i).getWaktu(), Toast.LENGTH_SHORT).show();

            }

//            PdfWriter.getInstance(document, new FileOutputStream("sample3.pdf"));
            document.open();
            document.add(table);
            document.close();
//            final PdfPTable table = new PdfPTable(new float[] { 2, 1, 1, 1 });
//            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
//            table.setLockedWidth(true);
//            table.addCell("Waktu");
//            table.addCell("Suhu");
//            table.addCell("Ph");
//            table.addCell("Oksigen");
//
//            table.setHeaderRows(1);
//            PdfPCell[] cells = table.getRow(0).getCells();
//            for (int j=0;j<cells.length;j++){
//                cells[j].setBackgroundColor(BaseColor.GRAY);
//            }
//
//            for (int i=1;i<5;i++){
//                table.addCell("Name:"+i);
//                table.addCell("Age:"+i);
//                table.addCell("Location:"+i);
//                table.addCell("Location:"+i);
//
//            }

            //tom


            //tom

//            PdfWriter.getInstance(document, new FileOutputStream("Fishfeeder.pdf"));
//            document.open();
//            document.add(table);
//
//            document.add(new Paragraph("yo ayo"));
//
//            document.add(new Paragraph("yo ayo yo yo ayo"));
//
//            document.close();

//            Document document = new Document();

//            PdfWriter.getInstance(document,
//                    new FileOutputStream(file.getAbsoluteFile()));
//            document.open();
//
//            document.newPage();
//            document.addAuthor("Genetech Solutions");
//
//            document.add(new Paragraph("Sigueme en Twitter!"));
//            document.add(table);
//
//            document.add(new Paragraph("@DavidHackro"));
//            document.close();

            Toast.makeText(LaporanActivity.this, "fishfeeder.pdf created", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();

        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
//            return false;
        }
        openGeneratedPDF();
    }

    private void openGeneratedPDF(){
        File file = new File("/sdcard/fishfeeder/laporan.pdf");
        if (file.exists())
        {
            Intent intent=new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try
            {
                startActivity(intent);
            }
            catch(ActivityNotFoundException e)
            {
                Toast.makeText(LaporanActivity.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }
        }
    }


//    private static final String SimpanHasil = "simpanhasil";
//    @Override
//    protected void onSaveInstanceState(Bundle outState){
//        outState.putString(SimpanHasil, kode);
//        super.onSaveInstanceState(outState);
//    }

//    public static Bitmap loadBitmapFromView(View v, int width, int height) {
//        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        Canvas c = new Canvas(b);
//        v.draw(c);
//
//        return b;
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    private void createPdf(){
//        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
//        //  Display display = wm.getDefaultDisplay();
//        DisplayMetrics displaymetrics = new DisplayMetrics();
//        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//        float hight = displaymetrics.heightPixels ;
//        float width = displaymetrics.widthPixels ;
//
//        int convertHighet = (int) hight, convertWidth = (int) width;
//
////        Resources mResources = getResources();
////        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);
//
//        PdfDocument document = new PdfDocument();
//        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
//        PdfDocument.Page page = document.startPage(pageInfo);
//
//        Canvas canvas = page.getCanvas();
//
//        Paint paint = new Paint();
//        canvas.drawPaint(paint);
//
//        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);
//
//        paint.setColor(Color.BLUE);
//        canvas.drawBitmap(bitmap, 0, 0 , null);
//        document.finishPage(page);
//
//        // write the document content
//        String targetPdf = "/sdcard/fishfeeder/laporan.pdf";
//        File filePath;
//        filePath = new File(targetPdf);
//        try {
//            document.writeTo(new FileOutputStream(filePath));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
//        }
//
//        // close the document
//        document.close();
//        Toast.makeText(this, "PDF is created!!!", Toast.LENGTH_SHORT).show();
//        Toast.makeText(LaporanActivity.this,"Dokumen berada di folder fisfeeder", Toast.LENGTH_LONG).show();
//
//        openGeneratedPDF();
//
//    }
//
}
